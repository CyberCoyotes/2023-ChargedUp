/*--------------------------------------------------------* 
 * 
 * 2023 Charged Up
 * Constants.java 
 * 
--------------------------------------------------------*/

package frc.robot;

import com.ctre.phoenix.motorcontrol.NeutralMode;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.math.util.Units;
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
    public static final int ARM_RIGHT_ROT_MOTOR_ID = 9; // Connected to Falcon 500

    public static final int ARM_LEFT_ROT_MOTOR_ID = 10; // Connected to Falcon 500
    
    public static final int CANDLE_ID = 26;

    public static final int ARM_EXTENDER_MOTOR_ID = 27; // Connected to SRX and Neo

    public static final int INTAKE_WHEELS_MOTOR_ID = 11; // Connected to Victor SPX

    public static final int REV_CLAW_ID = 0; // REV Pneumatics Hub Single channel for Claw
    
    public static final double stickDeadband = 0.1;
    public static final int ARM_EXTENT_LIMIT = -14000;//playing it safe
    public static final class Arm 
    {

        // public static final int ARM_EXTENT_RANGE = //todo determine


        
        /**
         *Total encoder tick distance of the falcon500s on the arm, in encoder ticks of 224:1 * 2048
         */
        public static final int ARM_ROTATION_HORIZONTAL_TICKS = 94505;//horizontaL
        // 314446
        public static final int ARM_ROTATION_RANGE_TICKS = 458752;
       
        public static final int ARM_INTAKE_PWM_PORT = 0;
       
        /**
         * The estimated encoder position at the resting  
         */
        // public final static double ARM_ROTATE_POSITION_REST = 79* (458752/360);
        public final static double ARM_ROTATE_POSITION_DEPLOY = 1000; // TODO TBD experimentally
        public final static double ARM_ROTATE_POSITION_INTAKE  = 8878; // approximate from testing; compare to the change in angle from rest to deploy
        public final static double ARM_ROTATE_POSITION_DEPLOY_DEG =  220;//should be 260? TESTING
        public final static double EXTENSION_POSITION_OUT = 14500; //playing it safe for now; should be around -14000?
        public final static double EXTENSION_POSITION_IN  = -200;

        public static final int PIDSlotIDx = 0; //keep this
        public static final double kP =  (0.50 * 1023) / 2048; //50% power at total error
        public static final double kI = 0;//may need tuning
        public static final double kD = 0; //may need tuning
        public static final double kMaxVelocity = 11468/2; //currently so that the robot may go 1/16th rotation in a second
        public static final double kMaxAcceletation = 11468/2; //tuning needed
        public static final int ARM_OFFSET_DEGREES = 19;//!Obsolete
        public static final double DEG_TO_mRAD = 17.4533;
        public static final double ARM_MAX_DEG = 260;
        public static final int ARM_STOW_ROTATION_DEG = 20;
        public static final int ARM_STOW_EXTENT_ENCODER = 200;
        public static final int EXTENSTION_MID_ENCODER = 5500;
        // public static final int LimitDIO = 0;

        
        public static final int WRIST_TALONFX_ID = 12;
        public static final int ARM_EXTEND_MIDDLE_ENCODER = 9500; 
        


    }
    public static final class Swerve {

        
        public static final int pigeonID = 25; // Team3603

        public static final boolean invertGyro = false; // Always ensure Gyro is CCW+ CW-

        public static final COTSFalconSwerveConstants chosenModule =
                COTSFalconSwerveConstants.SDSMK4i(COTSFalconSwerveConstants.driveGearRatios.SDSMK4i_L2);

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
        public static final double driveKP = 0.2; // TODO Tuned to specific robot
        public static final double driveKI = 0.0;
        public static final double driveKD = 0.1;
        public static final double driveKF = 0.0;
        public static final PIDController xPIDController = new PIDController(driveKP, 0, 0);
        public static final PIDController yPIDController = new PIDController(driveKP, 0, 0);
        /*
         * Drive Motor Characterization Values
         * Divide SYSID values by 12 to convert from volts to percent output for CTRE
         */
        public static final double driveKS = (0.32 / 12); //TODO Tuned to specific robot
        public static final double driveKV = (1.51 / 12);
        public static final double driveKA = (0.27 / 12);

        /* Swerve Profiling Values */
        /** Meters per Second */
        public static final double maxSpeed = 4.4196; // TODO: This must be tuned to specific robot
        /** Radians per Second */
        public static final double maxAngularVelocity = 10.0; // TODO: This must be tuned to specific robot

        /* Neutral Modes; can change from brake to neutral for testing purposes, but both on brake is the intended use */
        public static final NeutralMode angleNeutralMode = NeutralMode.Brake;
        public static final NeutralMode driveNeutralMode = NeutralMode.Brake;

        /* Front Right Module - Module 1 */
        public static final class Mod1 {
            public static final int driveMotorID = 1; // Team3603
            public static final int angleMotorID = 2; // Team3603
            public static final int canCoderID = 21; // Team3603
            public static final Rotation2d angleOffset = Rotation2d.fromDegrees(67.49 + 180); // 180 Need to make the robot align with "front"
            public static final SwerveModuleConstants constants = new SwerveModuleConstants(driveMotorID, angleMotorID,
                    canCoderID, angleOffset);
        }

        /* Back Right Module - Module 3 */
        public static final class Mod3 {
            public static final int driveMotorID = 3; // Team3603
            public static final int angleMotorID = 4; // Team3603
            public static final int canCoderID = 22; // Team3603
            public static final Rotation2d angleOffset = Rotation2d.fromDegrees(79.35 + 180);//invert
            // removing 180 
            public static final SwerveModuleConstants constants = new SwerveModuleConstants(driveMotorID, angleMotorID,
                    canCoderID, angleOffset);
        }

        /* Back Left Module - Module 2 */
        public static final class Mod2 {
            public static final int driveMotorID = 5; // Team3603
            public static final int angleMotorID = 6; // Team3603
            public static final int canCoderID = 23; // Team3603
            public static final Rotation2d angleOffset = Rotation2d.fromDegrees(-33.94 + 180);
            public static final SwerveModuleConstants constants = new SwerveModuleConstants(driveMotorID, angleMotorID,
                    canCoderID, angleOffset);
        }

        /* Module Specific Constants */
        /* Front Left Module - Module 0 */
        public static final class Mod0 {
            public static final int driveMotorID = 7; // Team3603
            public static final int angleMotorID = 8; // Team3603
            public static final int canCoderID = 24; // Team3603
            public static final Rotation2d angleOffset = Rotation2d.fromDegrees(361.07 + 180);
            public static final SwerveModuleConstants constants = new SwerveModuleConstants(driveMotorID, angleMotorID,
                    canCoderID, angleOffset);
        }
    }

    public static final class AutoConstants { // TODO: The below constants are used in the example auto, and must be
                                              // tuned to specific robot
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

    public static final int ARM_ROTATE_POS_MID = 50;

    public static final double WRIST_POS_LOAD = 6000; // FIXME

    public static final double WRIST_POS_MID = 1000; // FIXME

    public static final int WRIST_POS_LEVEL = 20000; // FIXME

    public static final double WRIST_POS_HOME = 0;
    
} // end of class
