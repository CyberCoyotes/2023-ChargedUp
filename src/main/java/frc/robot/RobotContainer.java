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
import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
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


    private final int LT = XboxController.Axis.kLeftTrigger.value;
    private final int RT = XboxController.Axis.kRightTrigger.value;

    /*--------------------------------------------------------*
    * Driver Buttons
    *--------------------------------------------------------*/
    /* START */private final JoystickButton zeroGyro = new JoystickButton(driver, XboxController.Button.kStart.value);
    /* LB */private final JoystickButton robotCentric = new JoystickButton(driver, XboxController.Button.kLeftBumper.value);
    
    // TODO Remove robot centric buttons
    /* B */private final JoystickButton creepButton = new JoystickButton(driver, XboxController.Button.kB.value); 
    
    /*--------------------------------------------------------*
    * Operator Buttons
    *--------------------------------------------------------*/

    /* SELECT */private final JoystickButton zeroArmEncoder = new JoystickButton(operator, XboxController.Button.kBack.value);
   
    /* X */private final JoystickButton intakeIn = new JoystickButton(operator, XboxController.Button.kX.value);
    /* Y */private final JoystickButton intakeOut = new JoystickButton(operator, XboxController.Button.kY.value);
   
    /* A */private final JoystickButton openClaw = new JoystickButton(operator, XboxController.Button.kA.value);
    /* B */private final JoystickButton closeClaw = new JoystickButton(operator, XboxController.Button.kB.value);


    /* Subsystems */
    private final ArmExtensionSubsystem m_extend = new ArmExtensionSubsystem();
    private final ArmSubsystem m_arm = new ArmSubsystem();
    private final CANdle m_candle = new CANdle(Constants.CANDLE_ID);
    private final ClawSubsystem m_claw = new ClawSubsystem();
    private final IntakeSubsystem m_intake = new IntakeSubsystem();
    private final Vision m_vision = new Vision();
    private final Swerve s_Swerve = new Swerve();
    // private final SensorsSubsystem m_ArmSwitch = new SensorsSubsystem();


    // #region Commands
    RotateArmIntake intakeCommand = new RotateArmIntake(armSubsystem);

    // #endregion

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
        SmartDashboard.putNumber("Arm_Extent_Attempt",operator.getRawAxis(RT));
        SmartDashboard.putNumber("Arm_Retract",(operator.getRawAxis(LT)));

        SmartDashboard.putNumber("Arm_Extent", m_extend.ReadExtension());

        SmartDashboard.putBoolean("rot intake command on", intakeCommand.isScheduled());
        SmartDashboard.putNumber("new gyro read", s_Swerve.getYaw().getDegrees());

        // SmartDashboard.putBoolean("Rotation Switch", m_ArmSwitch.getLimitSwitchState());
        //some data valiidation stuff

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

    }

    /**
     * Runs relevant code for any non-CAN sensors
     * 
     */
    
    /*
    public void SensorPeriodic() {
        // resets arm rotation encoder when it touches sensor
        TouchSensorEncoderReset();
    }

     
    private void TouchSensorEncoderReset() {
        if (m_ArmSwitch.getLimitSwitchState()) {
            armSubsystem.ZeroArmEncoder();
        }
    }
    */

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
        m_extend.setDefaultCommand(new ExtendArmManual(m_extend, () -> operator.getRawAxis(RT),() ->  operator.getRawAxis(LT)));

        m_vision.setDefaultCommand(new GetTagID(m_vision));

        // SmartDashboard.putBoolean("Rotation Switch", m_ArmSwitch.getLimitSwitchState());

        armSubsystem.setDefaultCommand(
                new RotateArmManual(armSubsystem, () -> -operator.getRawAxis(translationAxis)));

        s_Swerve.setDefaultCommand(
                new TeleopSwerve(
                        s_Swerve,
                        () -> -driver.getRawAxis(translationAxis),
                        () -> -driver.getRawAxis(strafeAxis),
                        () -> -driver.getRawAxis(rotationAxis),
                        () -> robotCentric.getAsBoolean(),
                        () -> GetCreepToggle()));


        //   m_vision.setDefaultCommand(new GetTagID(m_vision));

        // SmartDashboard.putBoolean("Rotation Switch", m_ArmSwitch.getLimitSwitchState());


        // armSubsystem.setDefaultCommand(
        //     new RotateArmManual(
        //         armSubsystem, 
        //         () -> -operator.getRawAxis(rotateArmInput)));
        m_extend.setDefaultCommand(
            new ExtendArmManual(
                m_extend,
                () -> operator.getRawAxis(RT),
                () ->  operator.getRawAxis(LT)));

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

        creepButton.onTrue(new InstantCommand(() -> SetCreepToggle(!GetCreepToggle())));//inverts creep when button pressed
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
        double inches = 2; //TODO calculate time to drive based off of inches
        double seconds = 1;// double seconds = inches * Constants.AutoConstants.AUTON_40_PERCENT_MULTIPLIER;
        var driveCommand = new TeleopSwerve(
            s_Swerve,
            () -> 0,
            () -> -0.4,
            () -> 0,
            () -> robotCentric.getAsBoolean(),
            () -> false);
        return new ParallelDeadlineGroup(new WaitCommand(seconds), driveCommand);
        //: 40% in a single direction for 1 second: ~51 inches 
        //: 40% in both directions for 1 second: ~75 inches total
        //: Both above seem to scale linearly

        //// return new PathPlannerTesting(s_Swerve).Generate();

    }
}

/**
 * 
 * Buttons
 * https://first.wpi.edu/wpilib/allwpilib/docs/release/java/edu/wpi/first/wpilibj/XboxController.html
 * 
 * https://first.wpi.edu/wpilib/allwpilib/docs/release/java/src-html/edu/wpi/first/wpilibj/XboxController.html 
 */