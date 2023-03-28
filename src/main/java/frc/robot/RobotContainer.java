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

import com.ctre.phoenix.led.CANdle;
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
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.autos.PathPlannerTest;
import frc.robot.autos.cgCubeLow_Taxi_Engaged;
import frc.robot.commands.*;
import frc.robot.subsystems.*;
import com.pathplanner.lib.PathConstraints;
import com.pathplanner.lib.PathPlanner;
import com.pathplanner.lib.PathPlannerTrajectory;
import com.pathplanner.lib.PathConstraints;
import com.pathplanner.lib.PathPlanner;
import com.pathplanner.lib.PathPlannerTrajectory;
import com.pathplanner.lib.auto.PIDConstants;
import com.pathplanner.lib.auto.SwerveAutoBuilder;

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
    /* A */private final JoystickButton RotateArmTEST = new JoystickButton(driver, XboxController.Button.kA.value);

    /* START */private final JoystickButton zeroGyro = new JoystickButton(driver, XboxController.Button.kStart.value);
    /* LB */private final JoystickButton robotCentric = new JoystickButton(driver,
            XboxController.Button.kLeftBumper.value);

    /* B */private final JoystickButton creepButton = new JoystickButton(driver, XboxController.Button.kB.value);
    // #endregion
    // #region Operator Buttons

    /* SELECT */private final JoystickButton zeroArmEncoder = new JoystickButton(operator,
            XboxController.Button.kBack.value);
    /* LB */private final JoystickButton loadElement = new JoystickButton(operator, XboxController.Button.kLeftBumper.value);

    /* RB */private final JoystickButton stowArm = new JoystickButton(operator, XboxController.Button.kRightBumper.value);

    /* X */private final JoystickButton intakeCone = new JoystickButton(operator, XboxController.Button.kY.value);
            // Intake Cone is same as Outtake Cube 
    /* Y */private final JoystickButton intakeCube = new JoystickButton(operator, XboxController.Button.kX.value);
    /* A */private final JoystickButton resetArmCommand = new JoystickButton(operator, XboxController.Button.kA.value);
            // Intake Cube is same as OuttakeCone

    // /* A */private final JoystickButton  = new JoystickButton(operator, XboxController.Button.kA.value);
    // /* B */private final JoystickButton  = new JoystickButton(operator, XboxController.Button.kB.value);


    // #endregion Operator Buttons
    // #region Subsystems

    private final ArmExtensionSubsystem armExtendSub = new ArmExtensionSubsystem();
    private final ArmSubsystem armSub = new ArmSubsystem(limit);
    private final CANdle candleSub = new CANdle(Constants.CANDLE_ID);
    private final Vision visionSub = new Vision();
    private final static Swerve s_Swerve = new Swerve(); // changed to a static to work with PathPlanner
    private final IntakeSubsystem intakeSub = new IntakeSubsystem();
    private final WristSubsystem wristSub = new WristSubsystem();
    // private final SensorsSubsystem m_ArmSwitch = new SensorsSubsystem();

    // #endregion
    
    // #region Commands
    /* Commands */
    // ResetArmCommand resetArm = new ResetArmCommand(armSub, wristSub, armExtendSub);
    RotateArmIntake intakeCommand = new RotateArmIntake(armSub);
    RotateArm90 rotTo90 = new RotateArm90(armSub);
    MoveUntilSensor rotationMoveUntilSensor;
    MoveUntilSensor extentionMoveUntilSensor;
    cgCubeLow_Taxi_Engaged autonCommand = new cgCubeLow_Taxi_Engaged(s_Swerve, robotCentric);
    ArmExtendMiddle extendMiddle = new ArmExtendMiddle(armExtendSub);
    ReadyForCargoCommand wristReceive = new ReadyForCargoCommand(wristSub);
    Command stowCommand = new StowArmCommand(armExtendSub, armSub, wristSub).withTimeout(2);
    // private final SequentialCommandGroup chargestation = new MountAndBalance(s_Swerve); // Bobcats
    // private final PathPlannerTest pathPlannerTest = new PathPlannerTest();

    // Command auton_Default = //
        // new SetIntakeCone(intakeSub); //
    // Command auton_ChargeStation = // Drives out, and then back onto the Charge Station
        // new cgCubeLow_Taxi_Engaged(s_Swerve, robotCentric);
    // Command auton_ConeLow = // Deploys a cone to middle level in auton
        // new cgConeLow(armSub, armExtendSub, wristSub, intakeSub); 
        
    // Command auton_ConeMiddle = // Deploys a cone to middle level in auton
        // new cgConeMid(armSub, armExtendSub, wristSub, intakeSub); 
    // Command auton_CubeMiddle = //Deploys a cube to middle level in auton
        // new cgCubeMid_ver1(armSub, armExtendSub, wristSub, intakeSub); // 
    // Command cubeMidTaxi = new cgCubeMid_Taxi_ver1(s_Swerve, armExtendSub, armSub, intakeSub, wristSub, robotCentric);
    // Command cubeLowTaxi = new cgCubeLow_Taxi(s_Swerve, armExtendSub, armSub, intakeSub, wristSub, robotCentric);
    // Command cubeLowTaxiDock = new cgCubeLow_Taxi_Dock(s_Swerve, armExtendSub, armSub, intakeSub, wristSub, robotCentric);
    // Command cubeMidTaxiDock = new cgCubeMid_Taxi_Dock(s_Swerve, armExtendSub, armSub, intakeSub, wristSub, robotCentric);

    // Command auton_cgCubeTop =  new cgCubeMid_ver3(armSub, armExtendSub, wristSub, intakeSub);

    // #endregion

    // TODO Added from Bobcats
    public void displayGyro(){
        SmartDashboard.putNumber("pitch", s_Swerve.getPitch());
        SmartDashboard.putNumber("yaw", s_Swerve.getRoll());
    }

    /* TODO Bobcat Example */
    // private final SequentialCommandGroup chargestation = new MountAndBalance(s_Swerve);
    // private final Command align = new AlignToTarget(s_Swerve, m_Limelight).withInterruptBehavior(InterruptionBehavior.kCancelIncoming).repeatedly();
    private static SwerveAutoBuilder swerveAutonBuilder;

    /* SendableChooser */
    SendableChooser<List<PathPlannerTrajectory>> autonChooser = new SendableChooser<>();
    //     SendableChooser<List<PathPlannerTrajectory>> autonChooser = new SendableChooser<>();

    // End Bobcat Example

    

    public void DebugMethod() {
        
        SmartDashboard.putNumber("Arm_Extent", armExtendSub.ReadExtension());
        SmartDashboard.putNumber("new gyro read", s_Swerve.getYaw().getDegrees());
        SmartDashboard.putNumber("Arm Rotation(°)", (armSub.GetRotationInDeg()));
        SmartDashboard.putNumber("Arm Rotation(Ticks)", (armSub.GetRotation()));
        SmartDashboard.putBoolean("Limit Switch", limit.get());
        SmartDashboard.putNumber("Wrist Encoder", wristSub.getWristPos());
        SmartDashboard.putString("arm mode", armSub.GetMode());
        SmartDashboard.putNumber("pitch", (s_Swerve.GetPitch()));
        try {
            SmartDashboard.putString("command", s_Swerve.getCurrentCommand().getName());
            
        } catch (Exception e) {
            // TODO: handle exception
        }
        


        // try {
        // System.out.println(("ex command " +  armExtendSub.getCurrentCommand().getName()));
            
        // } catch (Exception e) {
        //     // TODO: handle exception
        // }

        
    }

    /**
     * Runs relevant code for any non-CAN sensors
     */
      public void SensorPeriodic() {
      }
      
    public RobotContainer() {

        Shuffleboard.getTab("Auton Chooser").add(autonChooser).withSize(2, 4); // Create an Auton "Tab"

        Shuffleboard.getTab("Experimental Commands"); // Create an Auton "Tab"


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

        // SmartDashboard.putData("Stow Arm", new cgStow(armSub, armExtendSub, wristSub, intakeSub));
        // SmartDashboard.putData("Load Element", new cgLoad(armSub, armExtendSub, wristSub, intakeSub));

        
        /* Driver Button Bindings */
        zeroGyro.onTrue(new InstantCommand(() -> s_Swerve.zeroGyro()));
        zeroArmEncoder.onTrue(new InstantCommand(() -> armSub.ZeroArmEncoder()));
        creepButton.onTrue(new InstantCommand(() -> SetCreepToggle(!GetCreepToggle())));// inverts creep when button
        stowArm.onTrue(stowCommand);
        loadElement.whileTrue(wristReceive);

        /* Operator Button Bindings */
        // stowArm.onTrue(new cgStow(armSub, armExtendSub, wristSub, intakeSub));
        // loadElement.onTrue(new cgLoad(armSub, armExtendSub, wristSub, intakeSub));
        intakeCone.whileTrue(new  InstantCommand(() -> intakeSub.SetDriveIntake()));
        intakeCube.whileTrue(new InstantCommand(() -> intakeSub.SetDriveOutake()));
        // resetArmCommand.onTrue( resetArm);
        intakeCone.whileFalse(new InstantCommand(() -> intakeSub.ShutUp()));
        intakeCube.whileFalse(new InstantCommand(() -> intakeSub.ShutUp()));

        autonCommand.incrementPIDs(() -> driver.getRawAxis(LT),() ->  driver.getRawAxis(RT));


    }

    // TODO Bobcat Code
    public static Command buildAuton(List<PathPlannerTrajectory> trajs) {
        // public static Command buildAuton(List<PathPlannerTrajectory> trajs) {

        //s_Swerve.resetOdometry(trajs.get(0).getInitialHolonomicPose());
        swerveAutonBuilder = new SwerveAutoBuilder(
            s_Swerve::getPose,
            s_Swerve::resetOdometry,
            Constants.Swerve.swerveKinematics,
            new PIDConstants(Constants.AutoConstants.kPXController, 0, 0),
            new PIDConstants(Constants.AutoConstants.kPThetaController, 0, 0),
            s_Swerve::setModuleStates,
            Constants.AutoConstants.eventMap,
            true,
            s_Swerve
        );

        return swerveAutonBuilder.fullAuto(trajs);
    }

    private void configureDefaultCommands() {
        visionSub.setDefaultCommand(new GetTagID(visionSub));

        armSub.setDefaultCommand(
                new RotateArmManual(armSub, () -> operator.getRawAxis(translationAxis)));
        
                wristSub.setDefaultCommand(new MoveWristManual(wristSub,  () -> .25 * operator.getRawAxis(rightControllerY)));

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

    // TODO See Bobcat public void setUpAutos() {}
    // Sendable Chooser Setup

    private void configureAutonChooser() {

        setUpEventMap(); // FIXME Bobcat code

        // autonChooser.setDefaultOption("Score1HighCubeDirtyBalance", 
            // PathPlanner.loadPathGroup("Score1HighCubeRightBalance", 
            // new PathConstraints(4.5, 3)));

        // FIXME autonChooser.setDefaultOption("Do nothing", new WaitCommand(1)); // "Drive Only" Command or Command Group
        autonChooser.setDefaultOption("Do nothing", (List<PathPlannerTrajectory>) new WaitCommand(1)); // "Drive Only" Command or Command Group

        // autonChooser.addOption("Low cube Taxi (Side pref.)", cubeLowTaxi); 
        // autonChooser.addOption("0.02 Cube 2 Path Only (PP)", (Command) PathPlanner.loadPathGroup("ppCableCube2", new PathConstraints(4, 3)));
        
        // FIXME Test this auton with Path Planner implementation
        // 3603 Original
        // autonChooser.addOption("Out and Turn v3.4", (Command) PathPlanner.loadPathGroup("TestOutAndTurn", new PathConstraints(4, 3)));

        autonChooser.addOption("BETA 3.4 Out and Turn", (List<PathPlannerTrajectory>) PathPlanner.loadPathGroup("TestOutAndTurn", new PathConstraints(4, 3)));

       
    }

    /* TODO from Bobcat Auton */
    public void setUpEventMap() {
        Constants.AutoConstants.eventMap.clear();

        // Constants.AutoConstants.eventMap.put("chargeStation", new MountAndBalance(s_Swerve)); // <-- An auto command group goes here
        
        /* These are all Bobcat examples and should be deleted */
  
        // Constants.AutoConstants.eventMap.put("scoreCubeHigh", new SequentialCommandGroup(
            // new InstantCommand(m_Wrist::wristSolenoidON),
            // new ParallelRaceGroup(new ScoreHigh(m_Elevator, m_Arm, m_Intake, m_Wrist), new WaitCommand(2.125)), 
            // new InstantCommand(m_Wrist::wristSolenoidON),
            // new WaitCommand(0.2),
            // new IntakeOutFullSpeed(m_Intake), 
            // new StartingConfig(m_Elevator, m_Arm, m_Wrist)
            // )
        // );
   }

    // TODO Added from Bobcat auton
    public void printHashMap() {
        SmartDashboard.putString("eventMap", Constants.AutoConstants.eventMap.toString());
    }
    /**
     * Use this to pass the autonomous command to the main {@link Robot} class.
     *
     * @return the command to run in autonomous
     */

     public Command getAutonomousCommand() {

    
    /* 3603 Original code */
    // return autonChooser.getSelected();

    /* TODO Bobcat code */
    return buildAuton(autonChooser.getSelected());

    }

    public void DebugMethodSingle() 
    {
        
  var tab = Shuffleboard.getTab("Driver Diagnostics");
  tab.addNumber("Arm_Extent", () -> armExtendSub.ReadExtension());
  tab.addNumber( "new gyro read", () -> s_Swerve.getYaw().getDegrees());
  tab.addNumber( "Arm Rotation(°)", () -> (armSub.GetRotationInDeg()));



    }

}

/**
 * 
 * Buttons
 * https://first.wpi.edu/wpilib/allwpilib/docs/release/java/edu/wpi/first/wpilibj/XboxController.html
 * 
 * https://first.wpi.edu/wpilib/allwpilib/docs/release/java/src-html/edu/wpi/first/wpilibj/XboxController.html
 */
