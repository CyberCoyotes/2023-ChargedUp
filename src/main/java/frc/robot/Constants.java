/*--------------------------------------------------------* 
 * 
 * 2023 Charged Up
 * Constants.java 
 * 
--------------------------------------------------------*/

package frc.robot;

import java.util.HashMap;

import com.ctre.phoenix.motorcontrol.NeutralMode;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj2.command.Command;
import frc.lib.util.COTSFalconSwerveConstants;
import frc.lib.util.SwerveModuleConstants;

public final class Constants {
    /**
     * 
     * CAN IDs for various motors and controllers
     * that are not part of the drive train.
     * REV Power Distribution Hub is CAN 40 and Rio 0,
     * but I don't think they an ID
     *  
     **/
    
    public static final int CANDLE_ID = 26;


    // public static final int REV_CLAW_ID = 0; // REV Pneumatics Hub Single channel for Claw
    
    public static final double stickDeadband = 0.1;

    
    public static final class Arm 
    {
        /**The (estimated, cry abt it Michigan math) encoder reading at 360 degrees of rotation. Used ONLY for calculations, and if you get near this you did something sososososos<b>SO</b> wrong */
        public static final int ARM_ROT_360_TICKS = 458752;
        
    public static final int ARM_RIGHT_ROT_MOTOR_ID = 9; // Connected to Falcon 500

    public static final int ARM_LEFT_ROT_MOTOR_ID = 10; // Connected to Falcon 500
    public static final int ARM_EXTENDER_MOTOR_ID = 27; // Connected to SRX and Neo

    public static final int INTAKE_WHEELS_MOTOR_ID = 11; // Connected to Victor SPX
        public static int STOW_WRIST_POS = 2000;

        public static int LEVEL_WRIST_POS = 19000;
    
        /* First attempt 11000
        * experimental range is probably between 9200 to 11200
        */
        public static int LOAD_WRIST_POS = 6000; 
        
        /* First attempt 72250
        * experimental range is probably between 9200 to 11200
        */
        public static int WRIST_POS_MID = 72250;
    
         //#region arm   
        // public static final int ARM_EXTENT_LIMIT = -14500;//playing it safe
        // public static final int ARM_EXTENT_LIMIT_UPPER = 200;//playing it safe

        // public static final int ARM_EXTENT_RANGE = //todo determine


        
        /**
         *Total encoder tick distance of the falcon500s on the arm, in encoder ticks of 224:1 * 2048
         */
        public static final int ARM_ROTATION_HORIZONTAL_TICKS = 94505;//horizontal
        // 314446
        
       
        public static final int ARM_INTAKE_PWM_PORT = 0;
       
        /**
         * The estimated encoder position at the resting  
         */
        // public final static double ARM_ROTATE_POSITION_REST = 79* (458752/360);
        // public final static double ARM_ROTATE_POSITION_DEPLOY = 1000; // TBD experimentally
        // public final static double ARM_ROTATE_POSITION_INTAKE  = 8878; // approximate from testing; compare to the change in angle from rest to deploy
        // public final static double ARM_ROTATE_POSITION_DEPLOY_DEG =  220;//should be 260? TESTING
        public final static int EXTENSION_POSITION_OUT = 13000; //playing it safe for now; should be around -14000?
        public final static double EXTENSION_POSITION_IN  = -200;
        // public final static int EXTENSION_FLOOR_POS = -9450; // Scoy's attempt

        public final static int EXTENSION_FLOOR_POS = -1000; // Scoy's TEST attempt

        public static final int PIDSlotIDx = 0; //keep this
        public static final double kP =  (0.50 * 1023) / 2048; //50% power at total error
        public static final double kI = 0;//may need tuning
        public static final double kD = 0; //may need tuning
        public static final double kMaxArmRotVelocity = 11468/2; //currently so that the robot may go 1/16th rotation in a second
        public static final double kMaxArmRotAcceletation = 11468/2; //tuning needed
        //very important; this is the offset to make 90deg = true horizontal
        public static final int ARM_OFFSET_DEGREES = 20;
        
        //DON'T TOUCH (wihout first filing form ID10-T from HR(me(i am HR)))
        public static final double ARM_MAX_DEG = 110;
        //obsolete but shhh
        public static final int ARM_STOW_ROTATION_DEG = 20;
        public static final int ARM_STOW_EXTENT_ENCODER = 200;
        // public static final int LimitDIO = 0;

        
        public static final int WRIST_TALONFX_ID = 12;
        /**The encoder reading for a "mid" use this for a generally safe setpoint. */

        public static final int ARM_EXTEND_MIDDLE_ENCODER = 9500;
        public static final double WRIST_MIN = -2343;
        public static final double WRIST_MAX = 72000; 
        


    }
    //#endregion

    public static final class Swerve {

        
        public static final int pigeonID = 25; // Team 3603 

        public static final boolean invertGyro = false; // Always ensure Gyro is CCW+ CW-

        public static final COTSFalconSwerveConstants chosenModule =
                COTSFalconSwerveConstants.SDSMK4i(COTSFalconSwerveConstants.driveGearRatios.SDSMK4i_L3);

        /* Drivetrain Constants */
        public static final double trackWidth = Units.inchesToMeters(23.03); 
        public static final double wheelBase = Units.inchesToMeters(23.03); 
        public static final double wheelCircumference = chosenModule.wheelCircumference;

        /*
         * Swerve Kinematics
         * No need to ever change this unless you are not doing a traditional
         * rectangular/square 4 module swerve
         */
        public static final SwerveDriveKinematics swerveKinematics = new SwerveDriveKinematics(
                new Translation2d(wheelBase / 2.0, trackWidth / 2.0),
                new Translation2d(wheelBase / 2.0, -trackWidth / 2.0),
                new Translation2d(-wheelBase / 2.0, trackWidth / 2.0),
                new Translation2d(-wheelBase / 2.0, -trackWidth / 2.0));

        public static final float CREEP_MODE_MULTIPLIER = .15f;
        /* Module Gear Ratios */
        public static final double driveGearRatio = chosenModule.driveGearRatio;
        public static final double angleGearRatio = chosenModule.angleGearRatio;

        /* Motor Inverts */
        public static final boolean angleMotorInvert = chosenModule.angleMotorInvert;
        public static final boolean driveMotorInvert = chosenModule.driveMotorInvert;

        /* Angle Encoder Invert */
        public static final boolean canCoderInvert = chosenModule.canCoderInvert;

        /* Swerve Current Limiting */
        public static final int angleContinuousCurrentLimit = 25;
        public static final int anglePeakCurrentLimit = 40;
        public static final double anglePeakCurrentDuration = 0.1;
        public static final boolean angleEnableCurrentLimit = true;

        public static final int driveContinuousCurrentLimit = 35;
        public static final int drivePeakCurrentLimit = 60;
        public static final double drivePeakCurrentDuration = 0.1;
        public static final boolean driveEnableCurrentLimit = true;

        /*
         * These values are used by the drive falcon to ramp in open loop and closed
         * loop driving.
         * We found a small open loop ramp (0.25) helps with tread wear, tipping, etc
         */
        public static final double openLoopRamp = 0.25;
        public static final double closedLoopRamp = 0.0;

        /* Angle Motor PID Values */
        public static final double angleKP = chosenModule.angleKP;
        public static final double angleKI = chosenModule.angleKI;
        public static final double angleKD = chosenModule.angleKD;
        public static final double angleKF = chosenModule.angleKF;

        /* Drive Motor PID Values */
        public static final double driveKP = 0.2;
        public static final double driveKI = 0.0;
        public static final double driveKD = 0.1;
        public static final double driveKF = 0.0;
        public static final PIDController xPIDController = new PIDController(driveKP, 0, 0);
        public static final PIDController yPIDController = new PIDController(driveKP, 0, 0);
        /*
         * Drive Motor Characterization Values
         * Divide SYSID values by 12 to convert from volts to percent output for CTRE
         */
        public static final double driveKS = (0.32 / 12);
        public static final double driveKV = (1.51 / 12);
        public static final double driveKA = (0.27 / 12);

        /* Swerve Profiling Values */
        /** Meters per Second */
        public static final double maxSpeed = 4.4196;
        /** Radians per Second */
        public static final double maxAngularVelocity = 10.0;

        /* Neutral Modes; can change from brake to neutral for testing purposes, but both on brake is the intended use */
        public static final NeutralMode angleNeutralMode = NeutralMode.Brake;
        public static final NeutralMode driveNeutralMode = NeutralMode.Brake;

        /* Front Right Module - Module 1 */
        public static final class Mod1 {
            public static final int driveMotorID = 1; // Team 3603
            public static final int angleMotorID = 2; // Team 3603
            public static final int canCoderID = 21; // Team 3603
            public static final Rotation2d angleOffset = Rotation2d.fromDegrees(269.00); // Team 3603 all set 4/14
            public static final SwerveModuleConstants constants = new SwerveModuleConstants(driveMotorID, angleMotorID,
                    canCoderID, angleOffset);
        }

        /* Back Right Module - Module 3 */
        public static final class Mod3 {
            public static final int driveMotorID = 3; // Team 3603
            public static final int angleMotorID = 4; // Team 3603
            public static final int canCoderID = 22; // Team3 603
            public static final Rotation2d angleOffset = Rotation2d.fromDegrees(-101.8);
            // removing 180 
            public static final SwerveModuleConstants constants = new SwerveModuleConstants(driveMotorID, angleMotorID,
                    canCoderID, angleOffset);
        }

        /* Back Left Module - Module 2 */
        public static final class Mod2 {
            public static final int driveMotorID = 5; // Team 3603
            public static final int angleMotorID = 6; // Team 3603
            public static final int canCoderID = 23; // Team 3603
            public static final Rotation2d angleOffset = Rotation2d.fromDegrees(145.21);
            public static final SwerveModuleConstants constants = new SwerveModuleConstants(driveMotorID, angleMotorID,
                    canCoderID, angleOffset);
        }

        /* Module Specific Constants */
        /* Front Left Module - Module 0 */
        public static final class Mod0 {
            public static final int driveMotorID = 7; // Team 3603
            public static final int angleMotorID = 8; // Team 3603
            public static final int canCoderID = 24; // Team 3603
            public static final Rotation2d angleOffset = Rotation2d.fromDegrees(159.71); // Team 3603 All set 4/14
            public static final SwerveModuleConstants constants = new SwerveModuleConstants(driveMotorID, angleMotorID,
                    canCoderID, angleOffset);
        }
    }

    public static final class AutoConstants {
        public static final double kMaxSpeedMetersPerSecond = 4.4196;
        public final static double AUTON_40_PERCENT_MULTIPLIER = 0.05992509363d;

        public static final double kMaxAccelerationMetersPerSecondSquared = 3;
        public static final double kMaxAngularSpeedRadiansPerSecond = Math.PI;
        public static final double kMaxAngularSpeedRadiansPerSecondSquared = Math.PI;
        public static final double kPXController = 1;
        public static final double kPYController = 1;
        public static final double kPThetaController = 1;


        /* Constraint for the motion profilied robot angle controller */
        public static final TrapezoidProfile.Constraints kThetaControllerConstraints = new TrapezoidProfile.Constraints(
                kMaxAngularSpeedRadiansPerSecond, kMaxAngularSpeedRadiansPerSecondSquared);
        public static final ProfiledPIDController thetaProfiledPID = new ProfiledPIDController(kPThetaController,
                kMaxAccelerationMetersPerSecondSquared, stickDeadband, kThetaControllerConstraints);
        public static final PIDController kThetaPIDController = new PIDController(kPThetaController, 0,0);
        
        /* added for PathPlanner */ 
        public static HashMap<String, Command> eventMap = new HashMap<>(); 
        // eventMap.put("marker1", new PrintCommand("Passed marker 1"));
        // eventMap.put("intakeDown", new IntakeDown());
        
        }

    // Needed for LED
    public static final class PWMPorts {
        public static final int kBlinkin = 0;
    }

    /**
     * 
     * These all need to be figured experimentally
     * 
    */
    
    double CLAW_OPEN;
    double CLAW_CLOSED;

    boolean LED_CONE_LOCK;
    // double LED_CONE_MISSING;
    double LED_CUBE_LOCK;

    public static final int VISION_LED_OFF = 1;
    public static final int VISION_LED_ON = 2;

    public static final double WHEEL_MAX = 1; //maybe not be needed

    public static final int LIMIT_SWITCH_ARM_PORT = 3; // channel or port on roboRIO DIO

    public static final int ARM_ROTATE_POS_MID = -40;
    
} // end of class