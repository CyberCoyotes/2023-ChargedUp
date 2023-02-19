/*--------------------------------------------------------* 
 * 
 * 2023 Charged Up
 * RobotContainer.java 
 * 
--------------------------------------------------------*/
package frc.robot;

import com.ctre.phoenix.led.CANdle;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.XboxController.Button;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.Subsystem;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.autos.exampleAuto;
import frc.robot.commands.*;
import frc.robot.subsystems.*;

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

    /* Drive Controls */
    private final int translationAxis = XboxController.Axis.kLeftY.value;
    private final int strafeAxis = XboxController.Axis.kLeftX.value;
    private final int rotationAxis = XboxController.Axis.kRightX.value;
    
    /*--------------------------------------------------------*
    * Driver Buttons
    *--------------------------------------------------------*/
    private final JoystickButton zeroGyro = new JoystickButton(driver, XboxController.Button.kStart.value);
    private final JoystickButton intakeIn = new JoystickButton(driver, XboxController.Button.kX.value);
    private final JoystickButton intakeOut = new JoystickButton(driver, XboxController.Button.kY.value);
    private final JoystickButton openClaw = new JoystickButton(driver, XboxController.Button.kA.value);
    // private final JoystickButton <IntakeIn> = new JoystickButton(driver, XboxController.Button.kB.value);

    /*--------------------------------------------------------*
    * Operator Buttons
    *--------------------------------------------------------*/

    /* TODO
    armRotReset
    extendArm
    retractArm

    // private final JoystickButton intakeIn = new JoystickButton(operator, XboxController.Button.kX.value); // TODO
    // private final JoystickButton intakeOut = new JoystickButton(operator, XboxController.Button.kY.value); // TODO

    // private final JoystickButton clawOpen = new JoystickButton(operator, XboxController.Button.kA.value); // TODO
    // private final JoystickButton <intakeOut> = new JoystickButton(operator, XboxController.Button.kB.value);

    */

    private final JoystickButton robotCentric = new JoystickButton(driver, XboxController.Button.kLeftBumper.value); // TODO Remove robot centric buttons
    
    /* Subsystems */
    private final ArmExtensionSubsystem m_extend = new ArmExtensionSubsystem();
    private final ArmSubsystem m_arm = new ArmSubsystem();
    private final CANdle m_candle = new CANdle(Constants.CANDLE_ID);
    private final ClawSubsystem m_claw = new ClawSubsystem();
    private final IntakeSubsystem m_intake = new IntakeSubsystem();
    private final Vision m_vision = new Vision();
    private final Swerve s_Swerve = new Swerve();

    /**
     * The container for the robot. Contains subsystems, OI devices, and commands.
     */
    public RobotContainer() {

        // m_vision.setDefaultCommand(new SetLEDtags(m_candle, m_vision));

        
        s_Swerve.setDefaultCommand(
                new TeleopSwerve(
                        s_Swerve,
                        () -> -driver.getRawAxis(translationAxis),
                        () -> -driver.getRawAxis(strafeAxis),
                        () -> -driver.getRawAxis(rotationAxis),
                        () -> robotCentric.getAsBoolean()));

        // Configure the button bindings
        configureButtonBindings();
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

        /* Operator Button Bindings */
        intakeIn.whileTrue(new SetIntakeIn(m_intake));
        intakeOut.whileTrue(new SetIntakeOut(m_intake));
        openClaw.whileTrue(new SetClawOpen(m_claw));

        // new Trigger(m_vision::checkTagID).onTrue(new SetIntakeIn(m_intake));
        // ArmExtensionSubsystem.onTrue(new (m_extend));
        // ArmSubsystem.onTrue(new (m_arm));
        
    }

    /**
     * Use this to pass the autonomous command to the main {@link Robot} class.
     *
     * @return the command to run in autonomous
     */

    public Command getAutonomousCommand() {
        // An ExampleCommand will run in autonomous

        // FIXME
        // return new PathPlannerTesting(s_Swerve).Generate();
        return null;
    }
}
