/*--------------------------------------------------------* 
 * 
 * 2023 Charged Up
 * RobotContainer.java 
 * 
--------------------------------------------------------*/
package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.XboxController.Button;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.Subsystem;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.autos.exampleAuto;
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
    private final ArmSubsystem armSubsystem = new ArmSubsystem();

    /* Drive Controls */
    private final int translationAxis = XboxController.Axis.kLeftY.value;
    private final int strafeAxis = XboxController.Axis.kLeftX.value;
    private final int rotationAxis = XboxController.Axis.kRightX.value;

    /* Driver Buttons */
    private final JoystickButton zeroGyro = new JoystickButton(driver, XboxController.Button.kStart.value);
    private final JoystickButton zeroArmEncoder = new JoystickButton(driver, XboxController.Button.kBack.value);
    // private final JoystickButton IntakeIn = new JoystickButton(driver, XboxController.Button.kX.value);
    private final JoystickButton IntakeIn = new JoystickButton(driver, XboxController.Button.kY.value);
    // private final JoystickButton IntakeIn = new JoystickButton(driver, XboxController.Button.kA.value);
    // private final JoystickButton IntakeIn = new JoystickButton(driver, XboxController.Button.kB.value);


    private final JoystickButton robotCentric = new JoystickButton(driver, XboxController.Button.kLeftBumper.value);

    /* Subsystems */
    private final Swerve s_Swerve = new Swerve();

    // declare? the subsystems
    private final IntakeSubsystem m_intake = new IntakeSubsystem();
    // TODO Liam
    /** Path planner testing purposes only */

    /**
     * The container for the robot. Contains subsystems, OI devices, and commands.
     */
    public void DebugMethod()
    {
        SmartDashboard.putNumber("Arm Rotation",armSubsystem.GetRotation());
        SmartDashboard.putNumber("Module Rotation0",s_Swerve.mSwerveMods[0].getState().angle.getDegrees());
        SmartDashboard.putNumber("Module Rotation1",s_Swerve.mSwerveMods[1].getState().angle.getDegrees());
        SmartDashboard.putNumber("Module Rotation2",s_Swerve.mSwerveMods[2].getState().angle.getDegrees());
        SmartDashboard.putNumber("Module Rotation3",s_Swerve.mSwerveMods[3].getState().angle.getDegrees());
    }
    public RobotContainer() {

        armSubsystem.setDefaultCommand(
               // new TeleopSwerve(
                //         s_Swerve,
                //         () -> -driver.getRawAxis(translationAxis),
                //         () -> -driver.getRawAxis(strafeAxis),
                //         () -> -driver.getRawAxis(rotationAxis),
                //         () -> robotCentric.getAsBoolean()));
            new RotateArmManual2(armSubsystem, () -> -driver.getRawAxis(translationAxis)
                    ));
        // Configure the button bindings
        configureButtonBindings();
        System.out.println();
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
        /* Driver Buttons */
        zeroGyro.onTrue(new InstantCommand(() -> s_Swerve.zeroGyro()));

        zeroArmEncoder.onTrue(new InstantCommand(() -> armSubsystem.ZeroArmEncoder()));
        IntakeIn.onTrue(new SetIntakeIn(m_intake)); // TODO test
        // IntakeIn.whileTrue(new SetIntakeIn(m_intake)); // TODO Test

    }
        

    /**
     * Use this to pass the autonomous command to the main {@link Robot} class.
     *
     * @return the command to run in autonomous
     */
    
    public Command getAutonomousCommand() {
        // An ExampleCommand will run in autonomous
        return new WaitCommand(2);
        // return new PathPlannerTesting(s_Swerve).Generate();
        
    }
}
