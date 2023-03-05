/*--------------------------------------------------------* 
 * 
 * 2023 Charged Up
 * RobotContainer.java 
 * 
--------------------------------------------------------*/
package frc.robot;

import java.util.List;

import com.ctre.phoenix.led.CANdle;
import com.ctre.phoenixpro.configs.SoftwareLimitSwitchConfigs;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.math.trajectory.TrajectoryConfig;
import edu.wpi.first.math.trajectory.TrajectoryGenerator;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.SwerveControllerCommand;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.Constants.Arm;
import frc.robot.Constants.AutoConstants;
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
    /* Controllers */
    private final Joystick driver = new Joystick(0);
    private final Joystick operator = new Joystick(1);

    /* Analog Controls */
    private final int translationAxis = XboxController.Axis.kLeftY.value;
    private final int strafeAxis = XboxController.Axis.kLeftX.value;
    private final int rotationAxis = XboxController.Axis.kRightX.value;

    private final int LT = XboxController.Axis.kLeftTrigger.value;
    private final int RT = XboxController.Axis.kRightTrigger.value;

    /*--------------------------------------------------------*
    * Driver Buttons
    *--------------------------------------------------------*/
    /* A */private final JoystickButton RotateArmTEST = new JoystickButton(driver, XboxController.Button.kA.value);

    /* START */private final JoystickButton zeroGyro = new JoystickButton(driver, XboxController.Button.kStart.value);
    /* LB */private final JoystickButton robotCentric = new JoystickButton(driver,
            XboxController.Button.kLeftBumper.value);

    // TODO Remove robot centric buttons
    /* B */private final JoystickButton creepButton = new JoystickButton(driver, XboxController.Button.kB.value);

    /*--------------------------------------------------------*
    * Operator Buttons
    *--------------------------------------------------------*/

    /* SELECT */private final JoystickButton zeroArmEncoder = new JoystickButton(operator,
            XboxController.Button.kBack.value);
    /* START */private final JoystickButton stowArm = new JoystickButton(operator, XboxController.Button.kStart.value);

    /* X */private final JoystickButton intakeIn = new JoystickButton(operator, XboxController.Button.kX.value);
    /* Y */private final JoystickButton intakeOut = new JoystickButton(operator, XboxController.Button.kY.value);

    /* A */private final JoystickButton openClaw = new JoystickButton(operator, XboxController.Button.kA.value);
    /* B */private final JoystickButton closeClaw = new JoystickButton(operator, XboxController.Button.kB.value);

    private final DigitalInput limit = new DigitalInput(Constants.LIMIT_SWITCH_ARM_PORT);
    /* Subsystems */
    private final ArmExtensionSubsystem m_extend = new ArmExtensionSubsystem();
    private final ArmSubsystem armSubsystem = new ArmSubsystem(limit);

    private final CANdle m_candle = new CANdle(Constants.CANDLE_ID);
    private final ClawSubsystem m_claw = new ClawSubsystem();
    private final IntakeSubsystem m_intake = new IntakeSubsystem();
    private final Vision m_vision = new Vision();
    private final Swerve s_Swerve = new Swerve();
    // private final SensorsSubsystem m_ArmSwitch = new SensorsSubsystem();

    public final Command exTestProper = new ArmExtendToArg(m_extend, Arm.EXTENSTION_MID_ENCODER);
    public final Command exTest = new ParallelDeadlineGroup(new SensorHoldup(m_extend::ReadExtension, 3500),
            new InstantCommand(() -> System.out.println("running the primitive form")).andThen(new ExtendArmManual(
                    m_extend,
                    () -> .3,
                    () -> 0)));

    // #region Commands
    // StowArmCommand stowCommand = new StowArmCommand(m_extend, armSubsystem);
    RotateArmIntake intakeCommand = new RotateArmIntake(armSubsystem);
    RotateArm90 rotTo90 = new RotateArm90(armSubsystem);

    // todo: find a way to detect when the sensor has reach an acceptable point
    // while still continuing to move towards the final point.
    MoveUntilSensor rotationMoveUntilSensor;
    MoveUntilSensor extentionMoveUntilSensor;

    // #endregion

    /**
     * The container for the robot. Contains subsystems, OI devices, and commands.
     */

    // public SendableChooser<Command> autonChooser = new SendableChooser<>();

    public void DebugMethod() {
        // SmartDashboard.putNumber("Module
        // Rotation0",s_Swerve.mSwerveMods[0].getState().angle.getDegrees());
        // SmartDashboard.putNumber("Module
        // Rotation1",s_Swerve.mSwerveMods[1].getState().angle.getDegrees());
        // SmartDashboard.putNumber("Module
        // Rotation2",s_Swerve.mSwerveMods[2].getState().angle.getDegrees());
        // SmartDashboard.putNumber("Module
        // Rotation3",s_Swerve.mSwerveMods[3].getState().angle.getDegrees());

        // SmartDashboard.p
        SmartDashboard.putNumber("Arm_Extent", m_extend.ReadExtension());
        // SmartDashboard.putNumber("Arm Rotation Current", m_arm.GetCurrent());

        SmartDashboard.putBoolean("extend command on", exTest.isScheduled());
        // !The very existence of extest broke the arm entirely.
        SmartDashboard.putNumber("new gyro read", s_Swerve.getYaw().getDegrees());
        System.out.println(armSubsystem.GetSwtichState() + " Is the switch state");
        // SmartDashboard.putBoolean("Rotation Switch",
        // m_ArmSwitch.getLimitSwitchState());
        // some data valiidation stuff

        // Using degrees maximum encoder range and offsets, getting the calculated
        // measure

        // SmartDashboard.putNumber("Encoder value @ horziontal: Calculated:",
        // hypoIntake);
        // tested value
        // SmartDashboard.putNumber("Encoder value @ horziontal:
        // Tested:",armSubsystem.ConvertFXEncodertoDeg(Arm.ARM_ROTATE_POSITION_INTAKE
        // ));

        // SmartDashboard.putNumber("Arm Rotation(ticks)",armSubsystem.GetRotation());
        SmartDashboard.putNumber("Arm Rotation(°)", armSubsystem.ConvertFXEncodertoDeg(armSubsystem.GetRotation()));
        SmartDashboard.putBoolean("Limit Switch", armSubsystem.GetSwtichState());
        SmartDashboard.putBoolean("Limit Switch", limit.isAnalogTrigger());

    }

    /**
     * Runs relevant code for any non-CAN sensors
     * 
     */

    /*
     * public void SensorPeriodic() {
     * // resets arm rotation encoder when it touches sensor
     * TouchSensorEncoderReset();
     * }
     * 
     * 
     * private void TouchSensorEncoderReset() {
     * if (m_ArmSwitch.getLimitSwitchState()) {
     * armSubsystem.ZeroArmEncoder();
     * }
     * }
     */

    /**
     * Runs relevant code for any non-CAN sensors
     * 
     */
    // public void SensorPeriodic()
    // {
    // //resets arm rotation encoder when it touches sensor
    // TouchSensorEncoderReset();
    // }

    // private void TouchSensorEncoderReset()
    // {
    // if (m_ArmSwitch.getLimitSwitchState()) {
    // armSubsystem.ZeroArmEncoder();
    // }
    // }

    private boolean creepMode;

    private void SetCreepToggle(boolean toggle) {
        creepMode = toggle;
    }

    private boolean GetCreepToggle() {
        return creepMode;
    }

    public RobotContainer() {

        // m_vision.setDefaultCommand(new SetLEDtags(m_candle, m_vision));
        zeroGyro.onTrue(new InstantCommand(() -> s_Swerve.zeroGyro()));
        // SmartDashboard.putNumber("April Tag", m_vision.getEntry("tid").getDouble(0));
        // m_extend.setDefaultCommand(new ExtendArmManual(m_extend, () ->
        // operator.getRawAxis(RT),() -> operator.getRawAxis(LT)));
        // the stinky one
        m_vision.setDefaultCommand(new GetTagID(m_vision));

        // SmartDashboard.putBoolean("Rotation Switch",
        // m_ArmSwitch.getLimitSwitchState());

        armSubsystem.setDefaultCommand(
                new RotateArmManual(armSubsystem, () -> -0.65 * operator.getRawAxis(translationAxis)));

        s_Swerve.setDefaultCommand(
                new TeleopSwerve(
                        s_Swerve,
                        () -> -driver.getRawAxis(translationAxis),
                        () -> -driver.getRawAxis(strafeAxis),
                        () -> -driver.getRawAxis(rotationAxis),
                        () -> robotCentric.getAsBoolean(),
                        () -> GetCreepToggle()));

        // m_vision.setDefaultCommand(new GetTagID(m_vision));

        // SmartDashboard.putBoolean("Rotation Switch",
        // m_ArmSwitch.getLimitSwitchState());

        // armSubsystem.setDefaultCommand(
        // new RotateArmManual(
        // armSubsystem,
        // () -> -operator.getRawAxis(rotateArmInput)));
        m_extend.setDefaultCommand(
                new ExtendArmManual(
                        m_extend,
                        () -> operator.getRawAxis(RT),
                        () -> operator.getRawAxis(LT)));

        // Configure the button bindings
        configureButtonBindings();

        // #region autochooser
        // !NOT THE AUTON, DO NOT TOUCH THIS YOU BIG SILLY 「F O O L」
        // short polarity = 1;
        // double power = .4;
        // double seconds = 3.5;// double seconds = inches *
        // Constants.AutoConstants.AUTON_40_PERCENT_MULTIPLIER;
        // final float input = (float) (polarity * power);

        // var longDriveCommand = new TeleopSwerve(
        // s_Swerve,
        // () -> polarity*.4,
        // () -> 0,
        // () -> 0,
        // () -> robotCentric.getAsBoolean(),
        // () -> false);

        // var command0 = new ParallelDeadlineGroup(new WaitCommand(2.6),
        // longDriveCommand);
        // var command1 = new WaitCommand(1);
        // var command2 = new LongDriveCubeLow(armSubsystem, m_extend, m_claw, s_Swerve,
        // robotCentric);
        // autonChooser.addOption("Long Drive", command0 );
        // autonChooser.addOption("Long Drive Cube Low", command2);
        // autonChooser.setDefaultOption("Watch Paint Dry", command1);
        // System.out.println("Here's the thing " + command0 == null );
        // System.out.println("Here's the thing " + command1 == null );
        // System.out.println("Here's the thing " + command2 == null );
        //
        // Shuffleboard.getTab("Auton").add(autonChooser).withSize(2, 4);
        // #endregion
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

        /* Driver Button Bindings */
        zeroGyro.onTrue(new InstantCommand(() -> s_Swerve.zeroGyro()));
        zeroArmEncoder.onTrue(new InstantCommand(() -> armSubsystem.ZeroArmEncoder()));
        RotateArmTEST.whileTrue(exTest);
        // RotateArmTEST.onTrue(new InstantCommand(() ->
        // m_extend.SetArmToTickPosition(2500), m_extend));
        // logs confirmation//8611
        // setArmIntake.whileTrue(intakeCommand);
        // stowArm.onTrue(stowCommand);//todo test arm stow
        creepButton.onTrue(new InstantCommand(() -> SetCreepToggle(!GetCreepToggle())));// inverts creep when button
                                                                                        // pressed
        // creepButton.onFalse(new InstantCommand(() -> SetCreepToggle(false)));

        /* Operator Button Bindings */
        intakeIn.whileTrue(new SetIntakeIn(m_intake));
        intakeOut.whileTrue(new SetIntakeOut(m_intake));

        openClaw.onTrue(new SetClawOpen2(m_claw));
        closeClaw.onTrue(new SetClawClose2(m_claw));

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

        Pose2d startingPose = new Pose2d(0, 0, new Rotation2d(0));
        Pose2d endingPose = new Pose2d(2, 1, new Rotation2d(0));
        TrajectoryConfig trajectoryConfig = new TrajectoryConfig(AutoConstants.kMaxSpeedMetersPerSecond, AutoConstants.kMaxAccelerationMetersPerSecondSquared)

        s_Swerve.resetOdometry(startingPose);

        Trajectory trajectory = TrajectoryGenerator.generateTrajectory
        (
            startingPose,
            List.of
            (
                new Translation2d(1,0),
                new Translation2d(1,1)
            ),
            endingPose,
            trajectoryConfig
        );
        PIDController XPIDcontroller = new PIDController(AutoConstants.kPXController, 0, 0);
        PIDController YPIDcontroller = new PIDController(AutoConstants.kPYController, 0, 0);
        ProfiledPIDController thetaController = AutoConstants.thetaProfiledPID; //todo define this here


        SwerveControllerCommand autoCommand = 
        new SwerveControllerCommand
        (trajectory, 
        s_Swerve::getPose,
        frc.robot.Constants.Swerve.swerveKinematics, 
        , 
        null, 
        null);


    }
}

/**
 * 
 * Buttons
 * https://first.wpi.edu/wpilib/allwpilib/docs/release/java/edu/wpi/first/wpilibj/XboxController.html
 * 
 * https://first.wpi.edu/wpilib/allwpilib/docs/release/java/src-html/edu/wpi/first/wpilibj/XboxController.html
 */