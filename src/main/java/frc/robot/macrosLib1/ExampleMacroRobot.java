package frc.robot.macrosLib1;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.MotorSafety;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

//!Observed problems:
//! 1) This was done with an iterative robot.

//! 2) this was done with joysticks (opposed to controllers)
//! 3) Uses speedcontrollers and tanklike drivetrain
//! ? 4) Where is the state contained for the sticks?

/**
 * An example IterativeRobot class implementing Macro recording, Macro playback, storing and reading values from a
 * config file, and Joystick event binding.
 * <p>
 * Feel free to make your actual Robot class extend this one instead of IterativeRobot.
 *
 * @author Nicholas DeLello
 * @see Macro
 * @see MacroHelper
 */
public class ExampleMacroRobot extends IterativeRobot {
    //Paths used by the program
    private static final String macroDir = "/home/lvuser/macros";
    private static final String configDir = "/home/lvuser/cfg";

    //Defaults for values loaded from the config
    private static double minSpeed = .45; //The slowest the robot can move, (because under a certain threshold it won't move!)
    private static double maxSpeed = 1; //The fastest it can move. (In case you don't want the motors to run at full power)
    //Stuff whose IDs may need to be changed
    private static final int driveStickId = 0;
    private static final int auxStickId = 1;
    private static final int recordButtonId = 5;
    private static final int frontLeftMotorId = 0; //ID of the front left motor
    private static final int backLeftMotorId = 1; //ID of the back left motor
    private static final int frontRightMotorId = 2; //ID of the front right motor
    private static final int backRightMotorId = 3; //ID of the back right motor
    private static final boolean debug = false; //Makes all errors print out their stack traces. Shouldn't be true unless you're debugging.

    //Initializing variables, no need to change these.
    private static Joystick realDriveStick;
    private static Joystick realAuxStick;
    private static simulatedJoystick driveStick;
    private static simulatedJoystick auxStick;
    private static final SendableChooser<String> autoChooser = new SendableChooser<>();
    private static Macro currentMacro; //Used to keep track of the current macro
    private static MacroHelper macroHelper;
    //! Literally a dict that links controller inputs to methods. There exists a method that calls manually all these binds.

    private static final HashMap<JoystickEvent, Runnable> methods = new HashMap<>();
    private static final ArrayList<Runnable> scheduledEvents = new ArrayList<>();
    private static double throttle;
    private static SpeedController[] motors;
    private static boolean stoppedMacro = false;


    
    public void robotInit() { //Joysticks work oddly at competition when you initialize them outside of robotInit...
        realDriveStick = new Joystick(driveStickId);
        realAuxStick = new Joystick(auxStickId);
        driveStick = new simulatedJoystick(realDriveStick);
        auxStick = new simulatedJoystick(realAuxStick);
        macroHelper = new MacroHelper(macroDir, autoChooser, realDriveStick, realAuxStick);
        macroHelper.addExistingMacrosToSendableChooser(); //The method name should explain itself, if not the JavaDoc.
        //Change these to your actual motor controllers.
        motors = new SpeedController[]{new CANTalon(frontLeftMotorId), new CANTalon(backLeftMotorId),
                new CANTalon(frontRightMotorId), new CANTalon(backRightMotorId)};

        drive = new RobotDrive(motors[0], motors[1], motors[2], motors[3]);
        //! This lib has a mechanic of adding callbacks to different inputs. This method configures the record macro command
        addJoystickMethods();
        //! Vars being maxSpeed, minSpeed (for what?)
        loadVarsFromConfig();
    }

    /**
     * Registers all of the methods to be run on joystick events.
     */
    //! ?
    public void addJoystickMethods() {
        addJoystickMethod(JoystickEvent.eventType.PRESS, recordButtonId, driveStickId, () -> { //When the record button on the drive stick is pressed...
            try {
                macroHelper.startOrStopMacro(currentMacro); //Start or stop the macro!
            } catch (IOException e) {
                if (debug)
                    e.printStackTrace();
                System.err.println("Could not access the macro, not starting/stopping...");
            }
        });
        for (int i = 1; i <= driveStick.getButtonCount(); i++) {
            int i2 = i; //It has to be final, thus this variable.
            addJoystickMethod(JoystickEvent.eventType.PRESS, i, auxStickId, () -> System.out.println("ID: " + i2));
        }
    }

    /**
     * Loads variables from the config file, if possible.
     *
     * @return If the config file could be read.
     */
    private boolean loadVarsFromConfig() { //Example to load variables from config
        String[] configFile;
        try { //Read the config
            configFile = MacroHelper.readFile(configDir);
        } catch (IOException e) { //You can't access the file for some reason.
            if (debug)
                e.printStackTrace();
            System.err.println("Could not read config at " + configDir + '.');
            return false;
        }

        //The try and catch is pretty tedious, but it's nice to know which ones failed to load, which is why I use them.
        try {
            minSpeed = Double.parseDouble(configFile[0]); //If it's an int, not a double, use Integer.parseInt().
        } catch (Exception e) { //If it's a boolean, use Boolean.parseBoolean, and so on for other primitives.
            if (debug)
                e.printStackTrace();
            System.err.println("Could not load minSpeed.");
        }
        try {
            maxSpeed = Double.parseDouble(configFile[1]);
        } catch (Exception e) {
            if (debug)
                e.printStackTrace();
            System.err.println("Could not load maxSpeed.");
        }
        return true;
    }

    /**
     * Writes the current configurable values to the config file. Run it every time a value changes.
     *
     * @return If the values could successfully be written.
     */
    private boolean updateConfig() {
        String vars = String.valueOf(minSpeed) + '\n' + maxSpeed; //Add more variables with '\n' between as necessary.
        try {
            MacroHelper.overwriteFile(configDir, vars);
        } catch (IOException e) {
            if (debug)
                e.printStackTrace();
            System.err.println("Could not update config.");
            return false;
        }
        return true;
    }

    public void autonomousInit() {
        stoppedMacro = false; //In case you run autonomous more than once, reset stoppedMacro.
    }

    public void autonomousPeriodic() {
        Boolean runMacro = null;
        try {
            runMacro = macroHelper.autonMacro(currentMacro);
        } catch (Exception e) {
            if (debug)
                e.printStackTrace();
            System.err.println("Could not load macro at " + macroDir + macroHelper.getSelectedAuton().substring(5));
        }
        if (runMacro != null)
            if (runMacro)
                teleopPeriodic();
            else if (!stoppedMacro) { //The macro is over.
                //Add any additional motors you want, or a function to run before stopping these motors.
                stopRobot(motors[0], motors[1], motors[2], motors[3]);
                stoppedMacro = true; //Make sure it only runs once.
            }
        else
            switch (macroHelper.getSelectedAuton()) { //Non-macro autons
                case "example":
                    break;
            }
    }

    /**
     * Adds a new method to run on a given JoystickEvent.
     *
     * @param type    The eventType of event (PRESS,RELEASE,AXIS,POV)
     * @param stickId The ID of the Joystick
     * @param id      The ID of the button/axis or the value of the POV
     * @param method  The method to run
     */
    public void addJoystickMethod(JoystickEvent.eventType type, int id, int stickId, Runnable method) {
        addJoystickMethod(new JoystickEvent(type, stickId, id), method);
    }

    /**
     * Adds a new method to run on a given JoystickEvent.
     *
     * @param j      The JoystickEvent for the method to run on
     * @param method The method to run on the given event.
     */
    
    public void addJoystickMethod(JoystickEvent j, Runnable method) {
        methods.put(j, method);
    }

    /**
     * Runs the event associated with the given JoystickEvent.
     *
     * @param j The JoystickEvent's method to run.
     */
    public void runJoystickMethod(JoystickEvent j) {
        methods.get(j).run();
    }

    /**
     * Schedules <code>function</code> to run in <code>seconds</code> seconds
     *
     * @param seconds  The number of seconds until <code>function</code> runs
     * @param function The function to run when <code>seconds</code> seconds has passed.
     */
    public void scheduleEvent(double seconds, Runnable function) {
        Timer timer = new Timer();
        Runnable finalFunction = new Runnable() {
            public void run() {
                if (timer.hasPeriodPassed(seconds)) { //If the time has passed...
                    function.run(); //Run the function...
                    timer.stop(); //Stop and reset the timer...
                    timer.reset();
                    scheduledEvents.remove(this); //And then remove this from the ArrayList automatically!
                }
            }
        };
        timer.start();
        scheduledEvents.add(finalFunction);
    }

    public void teleopPeriodic() { //It drives, has joystick events, and has the throttle.
        //! The brain! Igor, fetch me the brain! The BRAAAAAAAAAAAAIIIIIIIIIIN! With kind regards, sham
        driveStick.updateWithEvents(realDriveStick, driveStickId).forEach(this::runJoystickMethod);
        auxStick.updateWithEvents(realAuxStick, auxStickId).forEach(this::runJoystickMethod);
        //! ? What is this
        //! ? Answer: This attempts to run an event that comes from the damn aether that holds an internal clock as means to time things

        scheduledEvents.forEach(Runnable::run); //Check all scheduled events, then run them if ready (they remove themselves from the ArrayList automatically)
        throttle = (-driveStick.getThrottle() + 1) / 2 * (maxSpeed - minSpeed) + minSpeed;
        drive.arcadeDrive(driveStick.getY() * throttle, -driveStick.getTwist() * throttle);
    }

    /**
     * Stops all of the motors, the shooter, or anything else that should be shut off at the end of autonomous.
     * <br><br>
     * Yes, a CANTalon or {@link edu.wpi.first.wpilibj.RobotDrive RobotDrive}, for
     * example, implements {@link MotorSafety} or {@link SpeedController}, so it'll work fine.
     *
     * @param motors All motors which need to be stopped. Can be passed as varargs or as an array. (in the form
     *               "motor1,motor2,motor3" or "new MotorSafety[] {motor1,motor2,motor3}" will both work)
     */
    public static void stopRobot(Object... motors) {
        for (Object motor : motors)
            if (motor instanceof SpeedController)
                ((SpeedController) motor).set(0);
            else if (motor instanceof MotorSafety)
                ((MotorSafety) motor).stopMotor();
    }

    /**
     * Stops all of the motors, the shooter, or anything else that should be shut off at the end of autonomous.
     * <br><br>
     * Yes, a CANTalon or {@link edu.wpi.first.wpilibj.RobotDrive RobotDrive}, for
     * example, implements {@link MotorSafety} or {@link SpeedController}, so it'll work fine.
     *
     * @param preFunction Any function you want run before stopping all of the motors. (Like if you need to move back an arm or something).<br>
     *                    Feel free to pass it a lambda.
     * @param motors      All motors which need to be stopped. Can be passed as varargs or as an array. (in the form
     *                    "motor1,motor2,motor3" or "new MotorSafety[] {motor1,motor2,motor3}" will both work)
     */
    public static void stopRobot(Runnable preFunction, Object... motors) {
        preFunction.run();
        stopRobot(motors);
    }
}