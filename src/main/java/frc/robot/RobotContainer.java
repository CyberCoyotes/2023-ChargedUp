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
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.WaitCommand;
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
    /* Controllers */
    private final Joystick driver = new Joystick(0);
    private final Joystick operator = new Joystick(1);
    private final ArmSubsystem armSubsystem = new ArmSubsystem();

    /* Analog Controls */
    private final int translationAxis = XboxController.Axis.kLeftY.value;
    private final int strafeAxis = XboxController.Axis.kLeftX.value;
    private final int rotationAxis = XboxController.Axis.kRightX.value;

    private final int rotateArmInput = XboxController.Axis.kLeftY.value;
    private final int retractArm = XboxController.Axis.kLeftTrigger.value;
    private final int extendArm = XboxController.Axis.kRightTrigger.value;
    
    
    /*--------------------------------------------------------*
    * Driver Buttons
    *--------------------------------------------------------*/
    private final JoystickButton zeroGyro = new JoystickButton(driver, XboxController.Button.kStart.value);
    // private final JoystickButton intakeIn = new JoystickButton(driver, XboxController.Button.kX.value);
    // private final JoystickButton intakeOut = new JoystickButton(driver, XboxController.Button.kY.value);
    // private final JoystickButton openClaw = new JoystickButton(driver, XboxController.Button.kA.value);
    // private final JoystickButton closeClaw = new JoystickButton(driver, XboxController.Button.kB.value);
    private final JoystickButton robotCentric = new JoystickButton(driver, XboxController.Button.kLeftBumper.value); // TODO Remove robot centric buttons


    /*--------------------------------------------------------*
    * Operator Buttons
    *--------------------------------------------------------*/
    private final JoystickButton zeroArmEncoder = new JoystickButton(operator, XboxController.Button.kBack.value);
   
    private final JoystickButton intakeIn = new JoystickButton(operator, XboxController.Button.kX.value);
    private final JoystickButton intakeOut = new JoystickButton(operator, XboxController.Button.kY.value);
   
    private final JoystickButton openClaw = new JoystickButton(operator, XboxController.Button.kA.value);
    private final JoystickButton closeClaw = new JoystickButton(operator, XboxController.Button.kB.value);
   


    // private final JoystickButton intakeIn = new JoystickButton(operator, XboxController.Button.kX.value); 
    // private final JoystickButton intakeOut = new JoystickButton(operator, XboxController.Button.kY.value);

    // private final JoystickButton clawOpen = new JoystickButton(operator, XboxController.Button.kA.value);
    // private final JoystickButton <intakeOut> = new JoystickButton(operator, XboxController.Button.kB.value);


    
    /* Subsystems */
    private final ArmExtensionSubsystem m_extend = new ArmExtensionSubsystem();
    private final ArmSubsystem m_arm = new ArmSubsystem();
    private final CANdle m_candle = new CANdle(Constants.CANDLE_ID);
    private final ClawSubsystem m_claw = new ClawSubsystem();
    private final IntakeSubsystem m_intake = new IntakeSubsystem();
    private final Vision m_vision = new Vision();
    private final Swerve s_Swerve = new Swerve();
    // private final SensorsSubsystem m_ArmSwitch = new SensorsSubsystem();

    //#region Commands
    RotateArmIntake intakeCommand = new RotateArmIntake(armSubsystem); 
    

    //#endregion


    /**
     * The container for the robot. Contains subsystems, OI devices, and commands.
     */

    public void DebugMethod()
    {
        // SmartDashboard.putNumber("Module Rotation0",s_Swerve.mSwerveMods[0].getState().angle.getDegrees());
        // SmartDashboard.putNumber("Module Rotation1",s_Swerve.mSwerveMods[1].getState().angle.getDegrees());
        // SmartDashboard.putNumber("Module Rotation2",s_Swerve.mSwerveMods[2].getState().angle.getDegrees());
        // SmartDashboard.putNumber("Module Rotation3",s_Swerve.mSwerveMods[3].getState().angle.getDegrees());
        SmartDashboard.putNumber("Arm_Extent",m_extend.ReadExtension());

        SmartDashboard.putBoolean("rot intake command on",intakeCommand.isScheduled());

        // SmartDashboard.putBoolean("Rotation Switch", m_ArmSwitch.getLimitSwitchState());


                //some data valiidation stuff

        //Using degrees maximum encoder range and offsets, getting the calculated measure
        
        var hypoIntake = armSubsystem.ConvertFXEncodertoDeg(armSubsystem.GetRotation()) + Constants.Arm.ARM_OFFSET_DEGREES;
        // SmartDashboard.putNumber("Encoder value @ horziontal: Calculated:", hypoIntake);
        //tested value
        // SmartDashboard.putNumber("Encoder value @ horziontal: Tested:",armSubsystem.ConvertFXEncodertoDeg(Arm.ARM_ROTATE_POSITION_INTAKE ));


        // SmartDashboard.putNumber("Arm Rotation(ticks)",armSubsystem.GetRotation());
        SmartDashboard.putNumber("Arm Rotation(°)", armSubsystem.ConvertFXEncodertoDeg(armSubsystem.GetRotation()));



    }

    /**
     * Runs relevant code for any non-CAN sensors 
     * 
     */
    // public void SensorPeriodic()
    // {  
    //     //resets arm rotation encoder when it touches sensor
    //     TouchSensorEncoderReset();
    // }

    // private void TouchSensorEncoderReset()
    // {
    //     if (m_ArmSwitch.getLimitSwitchState()) {
    //         armSubsystem.ZeroArmEncoder();
    //     }
    // }

    public RobotContainer() {

        // m_vision.setDefaultCommand(new SetLEDtags(m_candle, m_vision));
        zeroGyro.onTrue(new InstantCommand(() -> s_Swerve.zeroGyro()));       
        // SmartDashboard.putNumber("April Tag", m_vision.getEntry("tid").getDouble(0));    


        //   m_vision.setDefaultCommand(new GetTagID(m_vision));

        // SmartDashboard.putBoolean("Rotation Switch", m_ArmSwitch.getLimitSwitchState());
        
        armSubsystem.setDefaultCommand(
            new RotateArmManual(armSubsystem, () -> -operator.getRawAxis(translationAxis)
                    ));



        s_Swerve.setDefaultCommand(
                new TeleopSwerve(
                        s_Swerve,
                        () -> -driver.getRawAxis(translationAxis),
                        () -> -driver.getRawAxis(strafeAxis),
                        () -> -driver.getRawAxis(rotationAxis),
                        () -> robotCentric.getAsBoolean()));

        // armSubsystem.setDefaultCommand(
        //     new RotateArmManual(
        //         armSubsystem, 
        //         () -> -operator.getRawAxis(rotateArmInput)));
        m_extend.setDefaultCommand(ExtendArmManual(extendArm));
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

        /* Driver Button Bindings */
        zeroGyro.onTrue(new InstantCommand(() -> s_Swerve.zeroGyro()));       
        zeroArmEncoder.onTrue(new InstantCommand(() -> armSubsystem.ZeroArmEncoder()));
        //logs confirmation
        // setArmIntake.whileTrue(intakeCommand);

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
        // An ExampleCommand will run in autonomous
        return new WaitCommand(2);
        // return new PathPlannerTesting(s_Swerve).Generate();
        
    }
}

/**
 * 
 * Buttons
 * https://first.wpi.edu/wpilib/allwpilib/docs/release/java/edu/wpi/first/wpilibj/XboxController.html
 * 
 * https://first.wpi.edu/wpilib/allwpilib/docs/release/java/src-html/edu/wpi/first/wpilibj/XboxController.html 
 */