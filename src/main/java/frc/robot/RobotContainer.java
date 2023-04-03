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
import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import edu.wpi.first.wpilibj2.command.RepeatCommand;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.autos.cgCubeLow_Taxi;
// import frc.robot.autos.cgCubeLow_Taxi_Dock;
import frc.robot.Constants.Arm;
import frc.robot.autos.CubeMidTaxiV1;
import frc.robot.autos.cgCubeMid_Taxi_Dock;
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
    /* A */private final JoystickButton coneMidTEST = new JoystickButton(driver, XboxController.Button.kA.value);

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
    private final ArmRotationSubsystem armSub = new ArmRotationSubsystem(limit);
    private final CANdle candleSub = new CANdle(Constants.CANDLE_ID);
    private final Vision visionSub = new Vision();
    private final static Swerve s_Swerve = new Swerve(); // changed to a static to work with PathPlanner
    private final IntakeSubsystem intakeSub = new IntakeSubsystem();
    private final ArmWristSubsystem wristSub = new ArmWristSubsystem();
    // private final SensorsSubsystem m_ArmSwitch = new SensorsSubsystem();

    // #endregion
    // #region Commands
    ConeMid coneMid = new ConeMid(wristSub, armSub);
    // ResetArmCommand resetArm = new ResetArmCommand(armSub, wristSub, armExtendSub);
    RotateArmToArg rotTo90 = new RotateArmToArg(armSub, 90);
    MoveUntilSensor rotationMoveUntilSensor;
    MoveUntilSensor extentionMoveUntilSensor;
    cgCubeLow_Taxi_Engaged autonCommand = new cgCubeLow_Taxi_Engaged(s_Swerve, robotCentric);
    ArmExtendToArg extendMiddle = new ArmExtendToArg(armExtendSub, () -> Arm.ARM_EXTEND_MIDDLE_ENCODER);//why is the ctor like this? whatever
    ReadyForCargoCommand wristReceive = new ReadyForCargoCommand(wristSub);



    
    // Command stowCommand = new StowArmCommand(armExtendSub, armSub, wristSub).withTimeout(2);
    
    
    // StowArmCG _raw = new StowArmCG(armExtendSub, armSub, wristSub);
    StowArmStage stageOne = new StowArmStage(armExtendSub, armSub, wristSub, 2000, 50, 500); //Can make it one stage if it makes mentors happy (though i still really don't recommend even trying)
    StowArmStage stageTwo = new StowArmStage(armExtendSub, armSub, wristSub, 2000, 30, 500); //Can make it one stage if it makes mentors happy (though i still really don't recommend even trying)

    Command stowCommand = stageOne.andThen(stageTwo);
    Command auton_Default = // TODO Set
        new SetIntakeCone(intakeSub); //
    Command auton_ChargeStation = // Drives out, and then back onto the Charge Station
        new cgCubeLow_Taxi_Engaged(s_Swerve, robotCentric);
    Command auton_ConeLow = // Deploys a cone to middle level in auton
        new cgConeLow(armSub, armExtendSub, wristSub, intakeSub); 
        
   
    Command cubeMidTaxi = new CubeMidTaxiV1(s_Swerve, armExtendSub, armSub, intakeSub, wristSub, robotCentric);
    Command cubeLowTaxi = new cgCubeLow_Taxi(s_Swerve, armExtendSub, armSub, intakeSub, wristSub, robotCentric);
    // Command cubeLowTaxiDock = new cgCubeLow_Taxi_Dock(s_Swerve, armExtendSub, armSub, intakeSub, wristSub, robotCentric);
    Command cubeMidTaxiDock = new cgCubeMid_Taxi_Dock(s_Swerve, armExtendSub, armSub, intakeSub, wristSub, robotCentric);
    Command midCubeAuto = new CubeMidAuton(armSub, wristSub, intakeSub);

    // #endregion

    SendableChooser<Command> autonChooser = new SendableChooser<>(); // TODO Auton test
    
        // Shuffleboard.getTab("Auton").add(autonChooser).withSize(2, 4); // Create an Auton "Tab"

        // Shuffleboard.getTab("Experimental Commands"); // Create an Auton "Tab"

    public void DebugMethod() {
        
        SmartDashboard.putNumber("Arm_Extent", armExtendSub.ReadExtension());
        SmartDashboard.putNumber("new gyro read", s_Swerve.getYaw().getDegrees());
        SmartDashboard.putNumber("Arm Rotation(°)", (armSub.GetRotationInDeg()));
        SmartDashboard.putNumber("Arm Rotation(Ticks)", (armSub.GetRotation()));
        SmartDashboard.putBoolean("Limit Switch", limit.get());
        SmartDashboard.putNumber("Wrist Encoder", wristSub.getWristPos());
        SmartDashboard.putString("arm mode", armSub.GetMode());
        SmartDashboard.putNumber("pitch", (s_Swerve.GetPitch()));
        SmartDashboard.putBoolean("stage one", stageOne.isScheduled());
        SmartDashboard.putBoolean("stage two", stageTwo.isScheduled());
        // SmartDashboard.putString("mode", (s_Swerve.GetPitch()));
        // SmartDashboard.putString("mode", (s_Swerve.GetPitch()));
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

        // SmartDashboard.putData("Stow Arm", new cgStow(armSub, armExtendSub, wristSub, intakeSub));
        // SmartDashboard.putData("Load Element", new cgLoad(armSub, armExtendSub, wristSub, intakeSub));

        
        /* Driver Button Bindings */
        zeroGyro.onTrue(new InstantCommand(() -> s_Swerve.zeroGyro()));
        coneMidTEST.whileTrue(coneMid);
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
    private static SwerveAutoBuilder swerveAutoBuilder;

    public static Command buildAuto(List<PathPlannerTrajectory> trajs) {
        //s_Swerve.resetOdometry(trajs.get(0).getInitialHolonomicPose());
        swerveAutoBuilder = new SwerveAutoBuilder(
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

        return swerveAutoBuilder.fullAuto(trajs);
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

    private void configureAutonChooser() 
    {

        autonChooser.setDefaultOption("Do nothing", new WaitCommand(1)); // "Drive Only" Command or Command Group
        autonChooser.addOption("Low cube Taxi (Side pref.)", cubeLowTaxi); 
        // autonChooser.addOption("0.02 Cube 2 Path Only (PP)", (Command) PathPlanner.loadPathGroup("ppCableCube2", new PathConstraints(4, 3)));


        // autonChooser.addOption("Mid cube Taxi (Side pref.)", cubeMidTaxi); 
        // autonChooser.addOption("Low cube Taxi + dock (Mid pref.)", cubeLowTaxiDock);
        autonChooser.addOption("Taxi + dock (Mid pref.)", cubeMidTaxiDock); 
        autonChooser.addOption("Mid Cube Auto", midCubeAuto); 
        // autonChooser.addOption("Test Cone Mid", coneMid); 
        // autonChooser.addOption("Arm Extent Auto Test", extendMiddle); //! for testing; getting this command to work is a MUST
        // autonChooser.addOption("Arm Rotate to 90 deg", rotTo90); //! for testing; getting this command to work is a MUST
        // autonChooser.addOption("wristReceive", wristReceive);
       
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
