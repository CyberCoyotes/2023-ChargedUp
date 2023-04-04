/*--------------------------------------------------------* 
 * 
 * 2023 Charged Up
 * RobotContainer.java 
 * 
--------------------------------------------------------*/

// I know this is redundant b/c using instant commmands elsewhere,
// but wanting to use in sequential command groups

package frc.robot;

import java.util.List;
import java.util.function.Supplier;

// import com.ctre.phoenix.led.CANdle;
import com.pathplanner.lib.auto.SwerveAutoBuilder;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.Subsystem;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.autos.CubeLowTaxiEngage;
import frc.robot.autos.CubeLowTaxi;
import frc.robot.autos.CubeMidTaxiDock;
import frc.robot.Constants.Arm;
import frc.robot.autos.ppCube2_sum;
import frc.robot.autos.ppCube3_sum;
import frc.robot.autos.ppCubeLowTaxi;
import frc.robot.autos.ppCubeMidTaxi;
import frc.robot.autos.ppTaxi4meters;
import frc.robot.autos.ppTaxiFloorPickup;
import frc.robot.autos.ppCubeMidTaxiDock;
import frc.robot.commands.*;
import frc.robot.subsystems.*;
/* PathPlanner */
import com.pathplanner.lib.PathPlannerTrajectory;
import com.pathplanner.lib.auto.PIDConstants;

// 94505//horizontaL
// 314446 // serve
/**
 * This class is where the bulk of the robot should be declared. Since
 * Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in
 * the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of
 * the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {

    // #region Misc

    private boolean creepMode;

    private void SetCreepToggle(boolean toggle) {
        creepMode = toggle;
    }

    private boolean GetCreepToggle() {
        return creepMode;
    }

    private final DigitalInput limit = new DigitalInput(Constants.LIMIT_SWITCH_ARM_PORT);

    // #endregion
    // #region Controllers
    private final Joystick driver = new Joystick(0);
    private final Joystick operator = new Joystick(1);
    // #endregion
    // #region Analog Controls
    private final int translationAxis = XboxController.Axis.kLeftY.value;
    private final int rightControllerY = XboxController.Axis.kRightY.value;

    private final int strafeAxis = XboxController.Axis.kLeftX.value;
    private final int rotationAxis = XboxController.Axis.kRightX.value;

    private final int LT = XboxController.Axis.kLeftTrigger.value;
    private final int RT = XboxController.Axis.kRightTrigger.value;
    // #endregion
    // #region Driver Buttons
    /* A */private final JoystickButton coneMidTEST = new JoystickButton(driver, XboxController.Button.kA.value);

    /* START */private final JoystickButton zeroGyro = new JoystickButton(driver, XboxController.Button.kStart.value);
    /* LB */private final JoystickButton robotCentric = new JoystickButton(driver,
            XboxController.Button.kLeftBumper.value);

    /* B */private final JoystickButton creepButton = new JoystickButton(driver, XboxController.Button.kB.value);
    // #endregion
    // #region Operator Buttons

    /* SELECT */private final JoystickButton zeroArmEncoder = new JoystickButton(operator,
            XboxController.Button.kBack.value);
    /* LB */private final JoystickButton loadElement = new JoystickButton(operator,
            XboxController.Button.kLeftBumper.value);

    /* RB */private final JoystickButton stowArm = new JoystickButton(operator,
            XboxController.Button.kRightBumper.value);

    /* X */private final JoystickButton intakeCone = new JoystickButton(operator, XboxController.Button.kY.value);
    // Intake Cone is same as Outtake Cube
    /* Y */private final JoystickButton intakeCube = new JoystickButton(operator, XboxController.Button.kX.value);
    // /* A */private final JoystickButton resetArmCommand = new
    // JoystickButton(operator, XboxController.Button.kA.value);
    // Intake Cube is same as OuttakeCone

    /* A */private final JoystickButton operatorA = new JoystickButton(operator, XboxController.Button.kA.value);
    /* B */private final JoystickButton operatorB = new JoystickButton(operator, XboxController.Button.kB.value);

    // #endregion Operator Buttons
    // #region Subsystems

    private final ArmExtensionSubsystem armExtendSub = new ArmExtensionSubsystem();
    private final ArmRotationSubsystem armSub = new ArmRotationSubsystem(limit);
    // private final CANdle candleSub = new CANdle(Constants.CANDLE_ID);
    // private final Vision visionSub = new Vision();
    private final static Swerve s_Swerve = new Swerve(); // changed to a static to work with PathPlanner
    private final IntakeSubsystem intakeSub = new IntakeSubsystem();
    private final ArmWristSubsystem wristSub = new ArmWristSubsystem();
    // private final SensorsSubsystem m_ArmSwitch = new SensorsSubsystem();

    // #endregion

    // #region Commands

    Command fooToTerminal = new InstantCommand(() -> System.out.println("FOO")).repeatedly();
    Command barToTerminal = new InstantCommand(() -> System.out.println("BAR")).repeatedly();

    RotateArmToArg rotTo90 = new RotateArmToArg(armSub, 90);
    MoveUntilSensor rotationMoveUntilSensor;
    MoveUntilSensor extentionMoveUntilSensor;

    ArmExtendToArg extendMiddle = new ArmExtendToArg(armExtendSub, () -> Arm.ARM_EXTEND_MIDDLE_ENCODER);//why is the ctor like this? whatever
    ReadyForCargoCommand wristReceive = new ReadyForCargoCommand(wristSub);
    ConeMid coneMid = new ConeMid(wristSub, armSub);
    ConeLow coneLow = new ConeLow(armSub, armExtendSub, wristSub, intakeSub);

    CubeMid cubeMid = new CubeMid(armSub, wristSub, intakeSub);
    // CubeMidOld cubeMidOld = new CubeMidOld(armSub, wristSub, intakeSub); // Deprecated

    
    StowArmStage stageOne = new StowArmStage(armExtendSub, armSub, wristSub, 2000, 50, 500); //Can make it one stage if it makes mentors happy (though i still really don't recommend even trying)
    StowArmStage stageTwo = new StowArmStage(armExtendSub, armSub, wristSub, 2000, 30, 500); //Can make it one stage if it makes mentors happy (though i still really don't recommend even trying)
    Command stowCommand = stageOne.andThen(stageTwo);



    // Command stowCommand = new StowArmCommand(armExtendSub, armSub, wristSub).withTimeout(2);   
    // StowArmCG _raw = new StowArmCG(armExtendSub, armSub, wristSub);
    
    /* Autonomous Only Commands */
    // Drives out, and then back onto the Charge Station
    Command chargeStation = new CubeLowTaxiEngage(s_Swerve, robotCentric);

    Command cubeLowTaxi = new CubeLowTaxi(s_Swerve, armExtendSub, armSub, intakeSub, wristSub, robotCentric);
    CubeLowTaxiEngage autonCommand = new CubeLowTaxiEngage(s_Swerve, robotCentric);
    Command cubeMidTaxiDock = new CubeMidTaxiDock(s_Swerve, armExtendSub, armSub, intakeSub, wristSub, robotCentric);

    // Command cubeMidTaxi = new CubeMidTaxi_version1(s_Swerve, armExtendSub, armSub, intakeSub, wristSub, robotCentric);
    // Command cubeLowTaxiDock = new cgCubeLow_Taxi_Dock(s_Swerve, armExtendSub, armSub, intakeSub, wristSub, robotCentric);
    
    /* PathPlanner based taxi out 4 meters */
    Command ppTaxi4meters = new ppTaxi4meters();
    Command ppCubeLowTaxi = new ppCubeLowTaxi(armExtendSub, armSub, intakeSub, wristSub);
    Command ppCubeMidTaxi = new ppCubeMidTaxi(armExtendSub, armSub, intakeSub, wristSub);
    Command ppCubeMidTaxiDock = new ppCubeMidTaxiDock(armExtendSub, armSub, intakeSub, wristSub);

    /* Primary Autons */
    Command ppCube2_sum = new ppCube2_sum(armExtendSub, armSub, intakeSub, wristSub);
    Command ppCube3_sum = new ppCube3_sum(armExtendSub, armSub, intakeSub, wristSub);

    /* Idealized "complete" autons but alas */
    // Command ppCube2 = new ppCube2();
    // Command ppCube3 = new ppCube3();
    


    Command ppTaxiFloorPickup = new ppTaxiFloorPickup(armExtendSub, armSub, intakeSub, wristSub, robotCentric);
    // private CommandCycle coneCargoCycle = new CommandCycle(coneLow, coneMid);
    // private CommandCycle exampleCommandCycle = new CommandCycle(fooToTerminal, barToTerminal);
    // private Supplier<Command> coneCargoCommandSupplier = () ->
    // coneCargoCycle.Get();

    /*
     * // This will load the file "Example Path.path" and generate it with a max
     * velocity of 4 m/s and a max acceleration of 3 m/s^2
     * PathPlannerTrajectory cube2path = PathPlanner.loadPath("Cube2", new
     * PathConstraints(4, 2));
     * 
     * // This is just an example event map. It would be better to have a constant,
     * global event map
     * // in your code that will be used by all path following commands.
     * HashMap<String, Command> eventMap = new HashMap<>();
     * eventMap.put("marker1", new PrintCommand("Passed marker 1"));
     * eventMap.put("intakeDown", new IntakeDown());
     * 
     * FollowPathWithEvents cube2events = new FollowPathWithEvents(
     * getPathFollowingCommand(cube2path),
     * cube2path.getMarkers(),
     * eventMap
     * );
     */

    // #endregion

    /* Added from Bobcats */
    public void displayGyro() {
        SmartDashboard.putNumber("pitch", s_Swerve.getPitch());
        SmartDashboard.putNumber("yaw", s_Swerve.getRoll());
    }

    private static SwerveAutoBuilder swerveAutonBuilder;

    /* SendableChooser */
    SendableChooser<Command> autonChooser = new SendableChooser<>();

    public void DebugMethod() {

        SmartDashboard.putNumber("Arm_Extent", armExtendSub.ReadExtension());
        SmartDashboard.putNumber("new gyro read", s_Swerve.getYaw().getDegrees());
        SmartDashboard.putNumber("Arm Rotation(°)", (armSub.GetRotationInDeg()));
        SmartDashboard.putNumber("Arm Rotation(Ticks)", (armSub.GetRotation()));
        SmartDashboard.putBoolean("Limit Switch", limit.get());
        SmartDashboard.putNumber("Wrist Encoder", wristSub.getWristPos());
        SmartDashboard.putString("arm mode", armSub.GetMode());
        // SmartDashboard.putString("Current Cone cargo commmand", coneCargoCommandSupplier.get().getName());
        SmartDashboard.putNumber("pitch", (s_Swerve.GetPitch()));
        // SmartDashboard.putString("mode", (s_Swerve.GetPitch()));

        // try {
        // System.out.println(("ex command " +
        // armExtendSub.getCurrentCommand().getName()));

        // } catch (Exception e) {
        // handle exception
        // }

    }

    /**
     * Runs relevant code for any non-CAN sensors
     */
    public void SensorPeriodic() {
    }

    public RobotContainer() {

        Shuffleboard.getTab("Auton Chooser").add(autonChooser).withSize(2, 4); // Create an Auton "Tab"

        Shuffleboard.getTab("Experimental Commands"); // Create an Experimental "Tab"

        configureButtonBindings();
        configureDefaultCommands();
        configureAutonChooser();

    }

    /**
     * Use this method to define your button->command mappings. Buttons can be
     * created by
     * instantiating a {@link GenericHID} or one of its subclasses ({@link
     * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing
     * it to a {@link
     * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
     */
    private void configureButtonBindings() {


        // SmartDashboard.putData("Stow Arm", new cgStow(armSub, armExtendSub, wristSub,
        // intakeSub));
        // SmartDashboard.putData("Load Element", new cgLoad(armSub, armExtendSub,
        // wristSub, intakeSub));

        // SmartDashboard.putData("Stow Arm", new cgStow(armSub, armExtendSub, wristSub, intakeSub));
        // SmartDashboard.putData("Load Element", new cgLoad(armSub, armExtendSub, wristSub, intakeSub));

        /* Driver Button Bindings */
        zeroGyro.onTrue(new InstantCommand(() -> s_Swerve.zeroGyro()));
        // coneMidTEST.whileTrue(USETHISPICKUP);
        zeroArmEncoder.onTrue(new InstantCommand(() -> armSub.ZeroArmEncoder()));
        creepButton.onTrue(new InstantCommand(() -> SetCreepToggle(!GetCreepToggle())));// inverts creep when button
        stowArm.onTrue(stowCommand);
        loadElement.onTrue(wristReceive);

        /* Operator Button Bindings */
        // stowArm.onTrue(new cgStow(armSub, armExtendSub, wristSub, intakeSub));
        // loadElement.onTrue(new cgLoad(armSub, armExtendSub, wristSub, intakeSub));
        intakeCone.whileTrue(new InstantCommand(() -> intakeSub.SetDriveIntake()));
        intakeCube.whileTrue(new InstantCommand(() -> intakeSub.SetDriveOutake()));
        // resetArmCommand.onTrue( resetArm);
        intakeCone.whileFalse(new InstantCommand(() -> intakeSub.ShutUp()));
        intakeCube.whileFalse(new InstantCommand(() -> intakeSub.ShutUp()));

        autonCommand.incrementPIDs(() -> driver.getRawAxis(LT), () -> driver.getRawAxis(RT));

        // :functionalUse
        // //to the next command of the conecargocommand
        // operatorA.onTrue(new InstantCommand(() -> coneCargoCycle.Increment()));
        // //Runs the command currently ran.
        // operatorB.onTrue(
        // new RunCommand(
        // () -> coneCargoCycle.Get(),
        // coneCargoCycle.Get().getRequirements().toArray(new
        // Subsystem[0])//KAKEROOOOOOOOOOOT
        // ));

        /* 
        operatorA.onTrue(new InstantCommand(() -> exampleCommandCycle.Increment()));
        // Runs the command currently ran.
        operatorB.whileTrue(
                new RunCommand(
                        () -> exampleCommandCycle.Get(),
                        //magic that turns a collection into an ellipsies argument
                        exampleCommandCycle.Get().getRequirements().toArray(new Subsystem[0])
                ));
        */
    }

    /* Bobcat 177 Code */
    public static Command buildAuton(List<PathPlannerTrajectory> trajs) {

        // s_Swerve.resetOdometry(trajs.get(0).getInitialHolonomicPose());
        swerveAutonBuilder = new SwerveAutoBuilder(
                s_Swerve::getPose,
                s_Swerve::resetOdometry,
                Constants.Swerve.swerveKinematics,
                new PIDConstants(Constants.AutoConstants.kPXController, 0, 0),
                new PIDConstants(Constants.AutoConstants.kPThetaController, 0, 0),
                s_Swerve::setModuleStates,
                Constants.AutoConstants.eventMap,
                true,
                s_Swerve);

        return swerveAutonBuilder.fullAuto(trajs);
    }

    private void configureDefaultCommands() {
        // visionSub.setDefaultCommand(new GetTagID(visionSub));

        armSub.setDefaultCommand(
                new RotateArmManual(armSub, () -> operator.getRawAxis(translationAxis)));

        wristSub.setDefaultCommand(new MoveWristManual(wristSub, () -> .25 * operator.getRawAxis(rightControllerY)));

        s_Swerve.setDefaultCommand(
                new TeleopSwerve(
                        s_Swerve,
                        () -> -driver.getRawAxis(translationAxis),
                        () -> -driver.getRawAxis(strafeAxis),
                        () -> -driver.getRawAxis(rotationAxis),
                        () -> robotCentric.getAsBoolean(),
                        () -> GetCreepToggle()));
        armExtendSub.setDefaultCommand(
                new ExtendArmManual(
                        armExtendSub,
                        () -> operator.getRawAxis(RT),
                        () -> operator.getRawAxis(LT)));
        // new ArmExtendToArg(armExtendSub, () -> 9500));
    }

    /* See Bobcat public void setUpAutos() {} for analogous method */

    /* Sendable Chooser Setup */
    private void configureAutonChooser() {

        /* Added from Bobcat 177 code example */
        setUpEventMap();

        // TODO Verify that each of these works and then remove "β" from title
        // In theory nothing on "main" would be BETA
        autonChooser.setDefaultOption("Do nothing", new WaitCommand(1)); // "Drive Only" Command or Command Group

        
        /* Taxi out 4 meters in a straight line, no game element deposits; PathPlanner based drive */
        autonChooser.addOption("Taxi 4 meters only", ppTaxi4meters);
        
        /* Deposits a cube to the mid shelf, no drive */
        autonChooser.addOption("BETA Mid Cube (no drive)", cubeMid); 

        /* Deposits low cube and taxi out; timed based drive  */
        // autonChooser.addOption("BETA Low Cube + Taxi (Side)", cubeLowTaxi); 

        /* Deposits low cube and taxi out; PathPlanner based drive */
        autonChooser.addOption("BETA Low Cube + Taxi (Side)", ppCubeLowTaxi); 
        
        /* Deposits mid cube and taxi out; PathPlanner based drive */
        autonChooser.addOption("BETA Mid Cube + Taxi (Side)", ppCubeMidTaxi); 

        /* Taxi and Dock; timed based drive */
        autonChooser.addOption("BETA Mid Cube + Taxi + Dock (Middle)", cubeMidTaxiDock); 
    
        /* Taxi and Dock; PathPlanner based drive */
        autonChooser.addOption("BETA Mid Cube + Taxi + Dock (Middle)", ppCubeMidTaxiDock); 
        
        /* Deposits Cone 1 Mid, pickups up Cone 2, deposits low; PathPlanner based drive */
        autonChooser.addOption("BETA Cube 2 (Cable Side Only)", ppCube2_sum); 
        
        /* Runs Cone 2, and then picks up Cone 3, deposits low; PathPlanner based drive */
        autonChooser.addOption("BETA Cube 3 (Cable Side Only)", ppCube3_sum); 

    }

    /*
     * Added from Bobcat 177 code example
     * We aren't currently using anything other than clear
     * Probably stuff we would do at start of auton everytime?
    */
  
    public void setUpEventMap() {
        Constants.AutoConstants.eventMap.clear();
    }

    /* Added from Bobcat 177 code example */
    /* Not currently in use. Only for PathPlanner AutonBuilder with events?
    public void printHashMap() {
        SmartDashboard.putString("eventMap", Constants.AutoConstants.eventMap.toString());
    }
    */

    /**
     * Use this to pass the autonomous command to the main {@link Robot} class.
     * @return the command to run in autonomous
     */

    public Command getAutonomousCommand() {

    /* Added from Bobcat 177 code example */
    // return buildAuton(autonChooser.getSelected());

      return autonChooser.getSelected();

    }

    public void DebugMethodSingle() {
        var tab = Shuffleboard.getTab("Driver Diagnostics");
        tab.addNumber("Arm_Extent", () -> armExtendSub.ReadExtension());
        tab.addNumber("new gyro read", () -> s_Swerve.getYaw().getDegrees());
        tab.addNumber("Arm Rotation(°)", () -> (armSub.GetRotationInDeg()));
    }
}
