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
import frc.robot.autos.CubeLowTaxi;
import frc.robot.Constants.Arm;
import frc.robot.autos.CubeMidTaxi_version1;
import frc.robot.autos.ppCube2;
import frc.robot.autos.ppCubeLowTaxi;
import frc.robot.autos.ppTaxi4meters;
import frc.robot.autos.CubeMidTaxiDock;
import frc.robot.autos.CubeLowTaxiEngage;
import frc.robot.commands.*;
import frc.robot.subsystems.*;
/* PathPlanner */
// import com.pathplanner.lib.PathConstraints;
// import com.pathplanner.lib.PathPlanner;
import com.pathplanner.lib.PathPlannerTrajectory;
// import com.pathplanner.lib.PathConstraints;
// import com.pathplanner.lib.PathPlanner;
// import com.pathplanner.lib.PathPlannerTrajectory;
import com.pathplanner.lib.auto.PIDConstants;
// import com.pathplanner.lib.auto.SwerveAutoBuilder;

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
    // private final CANdle candleSub = new CANdle(Constants.CANDLE_ID);
    private final Vision visionSub = new Vision();
    private final static Swerve s_Swerve = new Swerve(); // changed to a static to work with PathPlanner
    private final IntakeSubsystem intakeSub = new IntakeSubsystem();
    private final ArmWristSubsystem wristSub = new ArmWristSubsystem();
    // private final SensorsSubsystem m_ArmSwitch = new SensorsSubsystem();

    // #endregion
    


    // #region Commands
    /* Commands */
    // ResetArmCommand resetArm = new ResetArmCommand(armSub, wristSub, armExtendSub);
    RotateArmToArg rotTo90 = new RotateArmToArg(armSub, 90);
    MoveUntilSensor rotationMoveUntilSensor;
    MoveUntilSensor extentionMoveUntilSensor;
    CubeLowTaxiEngage autonCommand = new CubeLowTaxiEngage(s_Swerve, robotCentric);
    ArmExtendToArg extendMiddle = new ArmExtendToArg(armExtendSub, () -> Arm.ARM_EXTEND_MIDDLE_ENCODER);//why is the ctor like this? whatever
    ReadyForCargoCommand wristReceive = new ReadyForCargoCommand(wristSub);
    ConeMid coneMid = new ConeMid(wristSub, armSub); 

    StowArmStage stageOne = new StowArmStage(armExtendSub, armSub, wristSub, 2000, 50, 500); //Can make it one stage if it makes mentors happy (though i still really don't recommend even trying)
    StowArmStage stageTwo = new StowArmStage(armExtendSub, armSub, wristSub, 2000, 30, 500); //Can make it one stage if it makes mentors happy (though i still really don't recommend even trying)
    Command stowCommand = stageOne.andThen(stageTwo);
    
    /* Autonomous Commands */
    Command autonDefault = new SetIntakeCone(intakeSub);
    Command chargeStation = new CubeLowTaxiEngage(s_Swerve, robotCentric); // Drives out, and then back onto the Charge Station
    Command coneLow = new ConeLow(armSub, armExtendSub, wristSub, intakeSub); // Deploys a cone to middle level in auton 
    Command cubeMidTaxi = new CubeMidTaxi_version1(s_Swerve, armExtendSub, armSub, intakeSub, wristSub, robotCentric);
    Command cubeLowTaxi = new CubeLowTaxi(s_Swerve, armExtendSub, armSub, intakeSub, wristSub, robotCentric);
    // Command cubeLowTaxiDock = new cgCubeLow_Taxi_Dock(s_Swerve, armExtendSub, armSub, intakeSub, wristSub, robotCentric);
    Command cubeMidTaxiDock = new CubeMidTaxiDock(s_Swerve, armExtendSub, armSub, intakeSub, wristSub, robotCentric);
    Command cubeMid = new CubeMid(armSub, wristSub, intakeSub);
    Command ppTaxi4meters = new ppTaxi4meters(); // PathPlanner Based
    Command ppCubeLowTaxi = new ppCubeLowTaxi(armExtendSub, armSub, intakeSub, wristSub, coneMidTEST); // FIXME
    Command ppCube2 = new ppCube2();

    // Command stowCommand = new StowArmCommand(armExtendSub, armSub, wristSub).withTimeout(2);   
    // StowArmCG _raw = new StowArmCG(armExtendSub, armSub, wristSub);
    StowArmStage stageOne = new StowArmStage(armExtendSub, armSub, wristSub, 2000, 50, 500); //Can make it one stage if it makes mentors happy (though i still really don't recommend even trying)
    StowArmStage stageTwo = new StowArmStage(armExtendSub, armSub, wristSub, 2000, 30, 500); //Can make it one stage if it makes mentors happy (though i still really don't recommend even trying)
    Command stowCommand = stageOne.andThen(stageTwo);

   /* 
   * Autonomous Specific Commands 
   */

    // PathPlanner based taxi out 4 meters
   Command taxi4meters = new ppTaxi4meters();

   // Drives out, and then back onto the Charge Station
   Command chargeStation = new CubeLowTaxiEngage(s_Swerve, robotCentric); 

   // Deploys a cone to middle level in auton
   Command coneLow = new ConeLow(armSub, armExtendSub, wristSub, intakeSub);
   
   Command cubeLowTaxi = new CubeLowTaxi(s_Swerve, armExtendSub, armSub, intakeSub, wristSub, robotCentric);
   Command cubeMidTaxi = new CubeMidTaxi_version1(s_Swerve, armExtendSub, armSub, intakeSub, wristSub, robotCentric);
   Command cubeMid = new CubeMid(armSub, wristSub, intakeSub);
   // Command cubeLowTaxiDock = new cgCubeLow_Taxi_Dock(s_Swerve, armExtendSub,
   // armSub, intakeSub, wristSub, robotCentric);
   Command cubeMidTaxiDock = new CubeMidTaxiDock(s_Swerve, armExtendSub, armSub, intakeSub, wristSub, robotCentric);
    

    // #endregion

    /* Added from Bobcats */
    public void displayGyro(){
        SmartDashboard.putNumber("pitch", s_Swerve.getPitch());
        SmartDashboard.putNumber("yaw", s_Swerve.getRoll());
    }

    /* Bobcat Example */
    // private final SequentialCommandGroup chargestation = new MountAndBalance(s_Swerve);
    // private final Command align = new AlignToTarget(s_Swerve, m_Limelight).withInterruptBehavior(InterruptionBehavior.kCancelIncoming).repeatedly();
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
        SmartDashboard.putNumber("pitch", (s_Swerve.GetPitch()));
        SmartDashboard.putBoolean("stage one", stageOne.isScheduled());
        SmartDashboard.putBoolean("stage two", stageTwo.isScheduled());
        // SmartDashboard.putString("mode", (s_Swerve.GetPitch()));
        // SmartDashboard.putString("mode", (s_Swerve.GetPitch()));
        try {
            SmartDashboard.putString("command", s_Swerve.getCurrentCommand().getName());
            
        } catch (Exception e) {
            // handle exception
        }
        


        // try {
        // System.out.println(("ex command " +  armExtendSub.getCurrentCommand().getName()));
            
        // } catch (Exception e) {
        //     handle exception
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

    /* Bobcat 177 Code */
    public static Command buildAuton(List<PathPlannerTrajectory> trajs) {

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
        // visionSub.setDefaultCommand(new GetTagID(visionSub));

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

    /* See Bobcat public void setUpAutos() {} for analogous method*/
    
    /* Sendable Chooser Setup */
    private void configureAutonChooser() {

        /* Added from Bobcat 177 code example */
        setUpEventMap();
        
        // TODO Verify that each of these works and then remove "β" from title
        // In theory nothing on "main" would have a "β" in title
        autonChooser.setDefaultOption("Do nothing", new WaitCommand(1)); // "Drive Only" Command or Command Group
        autonChooser.addOption("β Taxi 4 meters PP", ppTaxi4meters);
        autonChooser.addOption("β Mid Cube", cubeMid); 
        autonChooser.addOption("β Low Cube + Taxi (Side)", cubeLowTaxi); 
        autonChooser.addOption("β Low Cube + Taxi (Side) PP", ppCubeLowTaxi); 
        autonChooser.addOption("β Two Cube (Side)", cubeMidTaxiDock); 
        autonChooser.addOption("β Taxi + Dock (Middle)", cubeMidTaxiDock); 
        autonChooser.addOption("β Cube 2 (Side)", ppCube2); 
    }

    /* Added from Bobcat 177 code example 
     * We aren't currently using anything other than clear
     * Probably stuff we would do at start of auton everytime?
     * Cube Mid or Cube Low at start?
    */
    public void setUpEventMap() {
        Constants.AutoConstants.eventMap.clear();
   }

    /* Added from Bobcat 177 code example */
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

    /* Added from Bobcat 177 code example */
    // return buildAuton(autonChooser.getSelected());

    return autonChooser.getSelected();

    }

    public void DebugMethodSingle() {
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
