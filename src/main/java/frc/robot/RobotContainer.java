/*--------------------------------------------------------* 
 * 
 * 2023 Charged Up
 * RobotContainer.java 
 * 
--------------------------------------------------------*/

// I know this is redundant b/c using instant commmands elsewhere,
// but wanting to use in sequential command groups

package frc.robot;

import com.ctre.phoenix.led.CANdle;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.commands.*;
import frc.robot.subsystems.*;

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
            // Intake Cube is same as OuttakeCone

    // /* A */private final JoystickButton wristDown = new JoystickButton(operator, XboxController.Button.kA.value);
    // /* B */private final JoystickButton wristUp = new JoystickButton(operator, XboxController.Button.kB.value);


    // #endregion Operator Buttons
    // #region Subsystems
//// private final ClawSubsystem m_claw = new ClawSubsystem();
    ////private final IntakeSubsystem m_intake = new IntakeSubsystem();

    private final ArmExtensionSubsystem armExtendSub = new ArmExtensionSubsystem();
    private final ArmSubsystem armSub = new ArmSubsystem(limit);
    private final CANdle candleSub = new CANdle(Constants.CANDLE_ID);
    private final Vision visionSub = new Vision();
    private final Swerve s_Swerve = new Swerve();
    private final IntakeSubsystemV2 intakeSub = new IntakeSubsystemV2();
    private final WristSubsystem wristSub = new WristSubsystem();
    // private final SensorsSubsystem m_ArmSwitch = new SensorsSubsystem();

    // #endregion
    // #region Commands
    RotateArmIntake intakeCommand = new RotateArmIntake(armSub);
    RotateArm90 rotTo90 = new RotateArm90(armSub);
    MoveUntilSensor rotationMoveUntilSensor;
    MoveUntilSensor extentionMoveUntilSensor;
    DriveOutAndChargeStation autonCommand = new DriveOutAndChargeStation(s_Swerve, robotCentric);
    ArmExtendMiddle extendMiddle = new ArmExtendMiddle(armExtendSub);

    Command auton_Default = // TODO Set
        new SetIntakeCone(intakeSub); //
    Command auton_ChargeStation = // Drives out, and then back onto the Charge Station
        new DriveOutAndChargeStation(s_Swerve, robotCentric);
    Command auton_ConeMidLevel = // Deploys a cone to middle level in auton
        new cgConeToMiddle(armSub, armExtendSub, wristSub, intakeSub); 
    Command auton_CubeMidLevel = //Deploys a cube to middle level in auton
        new cgCubeToMiddleV2(armSub, armExtendSub, wristSub, intakeSub); // 
    // #endregion

    SendableChooser<Command> autonChooser = new SendableChooser<>(); // TODO Auton test
    
        // Shuffleboard.getTab("Auton").add(autonChooser).withSize(2, 4); // Create an Auton "Tab"

        // Shuffleboard.getTab("Experimental Commands"); // Create an Auton "Tab"

    public void DebugMethod() {
        
        SmartDashboard.putNumber("Arm_Extent", armExtendSub.ReadExtension());
        SmartDashboard.putNumber("new gyro read", s_Swerve.getYaw().getDegrees());
        SmartDashboard.putNumber("Arm Rotation(°)", armSub.ConvertFXEncodertoDeg(armSub.GetRotation()));
        SmartDashboard.putBoolean("Limit Switch", limit.get());
        SmartDashboard.putNumber("Wrist Encoder", wristSub.getWristPos());
        
    }

    /**
     * Runs relevant code for any non-CAN sensors
     */
      public void SensorPeriodic() {
      }
      
    public RobotContainer() {

        // autonChooser.addOption("* Low Cube + Balance", auton_Default); // TODO
        // autonChooser.addOption("* Med Cube + Balance", auton_Default); // TODO
        // autonChooser.addOption("* Low Cube + Out & Back", auton_Default); // TODO
        // autonChooser.addOption("* Med Cube + Out & Back", auton_Default); // TODO
        // autonChooser.addOption("Order 66 NO DRIVE + Low Cube", auton_Default); // TODO

        Shuffleboard.getTab("Auton").add(autonChooser).withSize(2, 4); // Create an Auton "Tab"

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

        SmartDashboard.putData("Stow Arm", new cgStow(armSub, armExtendSub, wristSub, intakeSub));
        SmartDashboard.putData("Load Element", new cgLoad(armSub, armExtendSub, wristSub, intakeSub));

        
        /* Driver Button Bindings */
        zeroGyro.onTrue(new InstantCommand(() -> s_Swerve.zeroGyro()));
        zeroArmEncoder.onTrue(new InstantCommand(() -> armSub.ZeroArmEncoder()));
        creepButton.onTrue(new InstantCommand(() -> SetCreepToggle(!GetCreepToggle())));// inverts creep when button

        /* Operator Button Bindings */
        stowArm.onTrue(new cgStow(armSub, armExtendSub, wristSub, intakeSub));
        loadElement.onTrue(new cgLoad(armSub, armExtendSub, wristSub, intakeSub));
        intakeCone.whileTrue(new  InstantCommand(() -> intakeSub.SetDriveIntake()));
        intakeCube.whileTrue(new InstantCommand(() -> intakeSub.SetDriveOutake()));

        intakeCone.whileFalse(new InstantCommand(() -> intakeSub.ShutUp()));
        intakeCube.whileFalse(new InstantCommand(() -> intakeSub.ShutUp()));

        autonCommand.incrementPIDs(() -> driver.getRawAxis(LT),() ->  driver.getRawAxis(RT));


    }

    private void configureDefaultCommands() {
        visionSub.setDefaultCommand(new GetTagID(visionSub));

        armSub.setDefaultCommand(
                new RotateArmManual(armSub, () -> operator.getRawAxis(translationAxis)));

                wristSub.setDefaultCommand(new MoveWristManual(wristSub,  () ->  operator.getRawAxis(rotationAxis)));

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



    }

    private void configureAutonChooser() 
    {

        autonChooser.setDefaultOption("XXX Run Intake XXX", auton_Default); // "Drive Only" Command or Command Group
        autonChooser.addOption("XXX Cone to Middle XXX", auton_ConeMidLevel); // " "Low Cube + Drive" TODO Replace * with No. when working
        autonChooser.addOption("XXX Cube to Middle XXX", auton_CubeMidLevel); // TODO replace the variable representing the auton command group from above
        autonChooser.addOption("XXX Out & back Charge Station XXX", auton_ChargeStation); // TODO replace the variable representing the auton command group from above
        autonChooser.addOption("Arm Extent Auto Test", extendMiddle); //! for testing; getting this command to work is a MUST
       
    }

    /**
     * Use this to pass the autonomous command to the main {@link Robot} class.
     *
     * @return the command to run in autonomous
     */

     public Command getAutonomousCommand() {
        // #region Q+A
        // PDH is front, and should be facing away from us
        // gyro resets (predictibly?) at the start of a match
        // No official restrictions on starting rotation, just placement
        // pdh facing away from us at the end of auton is ALWAYS Ryker's preference
        // #endregion
        
        //: If polarity is 1, the PDH/gyro/robot is facing away from us, the
        //: technically "right" orient.

        //#region PID stuff
        
        // Pose2d startingPose = new Pose2d(0, 0, new Rotation2d(0));
        // Pose2d endingPose = new Pose2d(-4, 0, new Rotation2d(0));
        // TrajectoryConfig trajectoryConfig = new TrajectoryConfig(AutoConstants.kMaxSpeedMetersPerSecond, AutoConstants.kMaxAccelerationMetersPerSecondSquared);

        // s_Swerve.resetOdometry(startingPose);

        // Trajectory trajectory = TrajectoryGenerator.generateTrajectory
        // (
        //     startingPose,
        //     List.of
        //     (
                
        //         new Translation2d(1,0),
        //         new Translation2d(20,0)
        //         // new Translation2d(3,0)
        //         // new Translation2d(0,0),
        //         // new Translation2d(1,0)
        //     ),
        //     endingPose,
        //     trajectoryConfig
        // );
        // PIDController XPIDcontroller = new PIDController(AutoConstants.kPXController, 0, 0);
        // PIDController YPIDcontroller = new PIDController(AutoConstants.kPYController, 0, 0);
        // ProfiledPIDController thetaController = AutoConstants.thetaProfiledPID; //todo define this here


        // SwerveControllerCommand autoCommand = 
        // new SwerveControllerCommand
        // (trajectory, 
        // s_Swerve::getPose,
        // frc.robot.Constants.Swerve.swerveKinematics, 
        // XPIDcontroller,
        // YPIDcontroller,
        // thetaController,
        // s_Swerve::setModuleStates,
        // s_Swerve);
//#endregion

//// return new SequentialCommandGroup(
////             new InstantCommand(() -> s_Swerve.resetOdometry(trajectory.getInitialPose())),
////             // autoCommand.withTimeout(seconds),
////             // new InstantCommand(() -> s_Swerve.StopModules()),
////             driveCommand,
////             new SeekBeginofChargeStation(s_Swerve),
////             new SeekBalanceCommand(s_Swerve));
//todo test this in the first place
    return autonChooser.getSelected();

    }

    public void DebugMethodSingle() 
    {
        
  var tab = Shuffleboard.getTab("Driver Diagnostics");
  tab.addNumber("Arm_Extent", () -> armExtendSub.ReadExtension());
  tab.addNumber( "new gyro read", () -> s_Swerve.getYaw().getDegrees());
  tab.addNumber( "Arm Rotation(°)", () -> armSub.ConvertFXEncodertoDeg(armSub.GetRotation()));
  tab.addBoolean("Arm Main Limit Switch", () -> limit.get());


    }
}

/**
 * 
 * Buttons
 * https://first.wpi.edu/wpilib/allwpilib/docs/release/java/edu/wpi/first/wpilibj/XboxController.html
 * 
 * https://first.wpi.edu/wpilib/allwpilib/docs/release/java/src-html/edu/wpi/first/wpilibj/XboxController.html
 */
