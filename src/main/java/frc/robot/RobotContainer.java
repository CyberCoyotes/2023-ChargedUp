/*--------------------------------------------------------* 
 * 
 * 2023 Charged Up
 * RobotContainer.java 
 * 
--------------------------------------------------------*/
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
import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.Constants.Arm;
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

    // TODO Remove robot centric buttons
    /* B */private final JoystickButton creepButton = new JoystickButton(driver, XboxController.Button.kB.value);
    // #endregion
    // #region Operator Buttons

    /* SELECT */private final JoystickButton zeroArmEncoder = new JoystickButton(operator,
            XboxController.Button.kBack.value);
    /* START */private final JoystickButton stowArm = new JoystickButton(operator, XboxController.Button.kStart.value);

    /* X */private final JoystickButton intakeIn = new JoystickButton(operator, XboxController.Button.kX.value);
    /* Y */private final JoystickButton intakeOut = new JoystickButton(operator, XboxController.Button.kY.value);

    /* A */private final JoystickButton openClaw = new JoystickButton(operator, XboxController.Button.kA.value);
    /* B */private final JoystickButton closeClaw = new JoystickButton(operator, XboxController.Button.kB.value);

    // #endregion Operator Buttons
    // #region Subsystems
    private final ArmExtensionSubsystem m_extend = new ArmExtensionSubsystem();
    private final ArmSubsystem armSubsystem = new ArmSubsystem(limit);

    private final CANdle m_candle = new CANdle(Constants.CANDLE_ID);
    private final ClawSubsystem m_claw = new ClawSubsystem();
    private final IntakeSubsystem m_intake = new IntakeSubsystem();
    private final Vision m_vision = new Vision();
    private final Swerve s_Swerve = new Swerve();
    // private final SensorsSubsystem m_ArmSwitch = new SensorsSubsystem();

    // #endregion
    // #region Commands
    RotateArmIntake intakeCommand = new RotateArmIntake(armSubsystem);
    RotateArm90 rotTo90 = new RotateArm90(armSubsystem);
    MoveUntilSensor rotationMoveUntilSensor;
    MoveUntilSensor extentionMoveUntilSensor;

    // #endregion

    public void DebugMethod() {
        SmartDashboard.putNumber("Arm_Extent", m_extend.ReadExtension());
        SmartDashboard.putNumber("new gyro read", s_Swerve.getYaw().getDegrees());
        SmartDashboard.putNumber("Arm Rotation(°)", armSubsystem.ConvertFXEncodertoDeg(armSubsystem.GetRotation()));
        SmartDashboard.putBoolean("Limit Switch", limit.get());

    }

    /**
     * Runs relevant code for any non-CAN sensors
     */
      public void SensorPeriodic() {
      }
      
    public RobotContainer() {

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

        /* Driver Button Bindings */
        zeroGyro.onTrue(new InstantCommand(() -> s_Swerve.zeroGyro()));
        zeroArmEncoder.onTrue(new InstantCommand(() -> armSubsystem.ZeroArmEncoder()));
        creepButton.onTrue(new InstantCommand(() -> SetCreepToggle(!GetCreepToggle())));// inverts creep when button

        /* Operator Button Bindings */
        intakeIn.whileTrue(new SetIntakeIn(m_intake));
        intakeOut.whileTrue(new SetIntakeOut(m_intake));

        openClaw.onTrue(new SetClawOpen2(m_claw));
        closeClaw.onTrue(new SetClawClose2(m_claw));

    }

    private void configureDefaultCommands() {
        m_vision.setDefaultCommand(new GetTagID(m_vision));

        armSubsystem.setDefaultCommand(
                new RotateArmManual(armSubsystem, () -> 0.65 * operator.getRawAxis(translationAxis)));

        s_Swerve.setDefaultCommand(
                new TeleopSwerve(
                        s_Swerve,
                        () -> -driver.getRawAxis(translationAxis),
                        () -> -driver.getRawAxis(strafeAxis),
                        () -> -driver.getRawAxis(rotationAxis),
                        () -> robotCentric.getAsBoolean(),
                        () -> GetCreepToggle()));
        m_extend.setDefaultCommand(
                new ExtendArmManual(
                        m_extend,
                        () -> operator.getRawAxis(RT),
                        () -> operator.getRawAxis(LT)));

    }

    private void configureAutonChooser() {

    }

    /**
     * Use this to pass the autonomous command to the main {@link Robot} class.
     *
     * @return the command to run in autonomous
     */

    public Command getAutonomousCommand() {

        // #region Ryker interrogation, day 1 of Calvin
        // PDH is front, and should be facing away from us
        // gyro resets (predictibly?) at the start of a match
        // No official restrictions on starting rotation
        // pdh facing away from us at the end of auton is ALWAYS Ryker's preference
        // #endregion

        // ? If polarity is 1, the PDH/gyro/robot is facing away from us, the
        // technically "right" orient.
        short polarity = 1;
        double power = .4;
        double seconds = 3;
        final float input = (float) (polarity * power);
        var driveCommand = new TeleopSwerve(
                s_Swerve,
                () -> input,
                () -> 0,
                () -> 0,
                () -> robotCentric.getAsBoolean(),
                () -> false);
        return new ParallelDeadlineGroup(new WaitCommand(seconds), driveCommand);

        // return new cgCubeDeployLow(armSubsystem, m_extend, m_claw).andThen( new
        // ParallelDeadlineGroup(new WaitCommand(seconds), driveCommand));
        // : 40% in a single direction for 1 second: ~51 inches
        // : 40% in both directions for 1 second: ~75 inches total
        // : Both above seem to scale linearly
    }
}

/**
 * 
 * Buttons
 * https://first.wpi.edu/wpilib/allwpilib/docs/release/java/edu/wpi/first/wpilibj/XboxController.html
 * 
 * https://first.wpi.edu/wpilib/allwpilib/docs/release/java/src-html/edu/wpi/first/wpilibj/XboxController.html
 */