/*--------------------------------------------------------*
 *
 * 2023 Charged Up
 *
 * subsystems/ArmSubsystem.java
 *
 * Uses 2 Falcon 500 subsytem for arm movement
 *
 * Documentation and references
 * https://docs.wpilib.org/en/stable/docs/software/advanced-controls/introduction/tuning-vertical-arm.html
 *  a combined feedforward-feedback strategy is needed.
 *
 * https://www.chiefdelphi.com/t/smoothly-controlling-an-arm/343880/
 *
 * Math stuff from 5 yrs ago
 * https://youtu.be/RLrZzSpHP4E
 * 
 * 
 * https://www.youtube.com/watch?v=VQIgdLslU_E
 * https://github.com/CrossTheRoadElec/Phoenix-Examples-Languages 
 * https://docs.google.com/presentation/d/1zzMI3DW-elButNH0QLFdYnDyaIapeI-zZnv9CAC6WY8/edit#slide=id.g5595b853ce_0_24 
 * https://github.com/CrossTheRoadElec/Phoenix-Examples-Languages/blob/master/Java%20Talon%20FX%20(Falcon%20500)/MotionMagic_ArbFeedForward/src/main/java/frc/robot/Robot.java
--------------------------------------------------------*/

/*
 * 
 * /* Disable all motors
		_rightMaster.set(TalonFXControlMode.PercentOutput, 0);
		_leftMaster.set(TalonFXControlMode.PercentOutput,  0);
		
		/* Set neutral modes
		_leftMaster.setNeutralMode(NeutralMode.Brake);
		_rightMaster.setNeutralMode(NeutralMode.Brake);

		/* Configure output 
		_leftMaster.setInverted(TalonFXInvertType.CounterClockwise);
		_rightMaster.setInverted(TalonFXInvertType.Clockwise);
		/*
		 * Talon FX does not need sensor phase set for its integrated sensor
		 * This is because it will always be correct if the selected feedback device is integrated sensor (default value)
		 * and the user calls getSelectedSensor* to get the sensor's position/velocity.
		 * 
		 * https://phoenix-documentation.readthedocs.io/en/latest/ch14_MCSensor.html#sensor-phase
		 */
// _leftMaster.setSensorPhase(true);
// _rightMaster.setSensorPhase(true);

/** Feedback Sensor Configuration */

/** Distance Configs */

/* Configure the left Talon's selected sensor as integrated sensor 
_leftConfig.primaryPID.selectedFeedbackSensor = TalonFXFeedbackDevice.IntegratedSensor.toFeedbackDevice(); //Local Feedback Source

/* Configure the Remote (Left) Talon's selected sensor as a remote sensor for the right Talon
_rightConfig.remoteFilter0.remoteSensorDeviceID = _leftMaster.getDeviceID(); //Device ID of Remote Source
_rightConfig.remoteFilter0.remoteSensorSource = RemoteSensorSource.TalonFX_SelectedSensor; //Remote Source Type

/* Now that the Left sensor can be used by the master Talon,
 * set up the Left (Aux) and Right (Master) distance into a single
 * Robot distance as the Master's Selected Sensor 0.
setRobotDistanceConfigs(_rightInvert, _rightConfig);

/* FPID for Distance
_rightConfig.slot0.kF = Constants.kGains_Distanc.kF;
_rightConfig.slot0.kP = Constants.kGains_Distanc.kP;
_rightConfig.slot0.kI = Constants.kGains_Distanc.kI;
_rightConfig.slot0.kD = Constants.kGains_Distanc.kD;
_rightConfig.slot0.integralZone = Constants.kGains_Distanc.kIzone;
_rightConfig.slot0.closedLoopPeakOutput = Constants.kGains_Distanc.kPeakOutput;

/* false means talon's local output is PID0 + PID1, and other side Talon is PID0 - PID1
 *   This is typical when the master is the right Talon FX and using Pigeon
 * 
 * true means talon's local output is PID0 - PID1, and other side Talon is PID0 + PID1
 *   This is typical when the master is the left Talon FX and using Pigeon
 
_rightConfig.auxPIDPolarity = false;

/* FPID for Heading
_rightConfig.slot1.kF = Constants.kGains_Turning.kF;
_rightConfig.slot1.kP = Constants.kGains_Turning.kP;
_rightConfig.slot1.kI = Constants.kGains_Turning.kI;
_rightConfig.slot1.kD = Constants.kGains_Turning.kD;
_rightConfig.slot1.integralZone = Constants.kGains_Turning.kIzone;
_rightConfig.slot1.closedLoopPeakOutput = Constants.kGains_Turning.kPeakOutput;


/* Config the neutral deadband.
_leftConfig.neutralDeadband = Constants.kNeutralDeadband;
_rightConfig.neutralDeadband = Constants.kNeutralDeadband;

/**
 * 1ms per loop.  PID loop can be slowed down if need be.
 * For example,
 * - if sensor updates are too slow
 * - sensor deltas are very small per update, so derivative error never gets large enough to be useful.
 * - sensor movement is very slow causing the derivative error to be near zero.
 
int closedLoopTimeMs = 1;
_rightConfig.slot0.closedLoopPeriod = closedLoopTimeMs;
_rightConfig.slot1.closedLoopPeriod = closedLoopTimeMs;
_rightConfig.slot2.closedLoopPeriod = closedLoopTimeMs;
_rightConfig.slot3.closedLoopPeriod = closedLoopTimeMs;

/* Motion Magic Configs
_rightConfig.motionAcceleration = 2000; //(distance units per 100 ms) per second
_rightConfig.motionCruiseVelocity = 2000; //distance units per 100 ms



/* APPLY the config settings
_leftMaster.configAllSettings(_leftConfig);
_rightMaster.configAllSettings(_rightConfig);

/* Set status frame periods to ensure we don't have stale data 
_rightMaster.setStatusFramePeriod(StatusFrame.Status_12_Feedback1, 20, Constants.kTimeoutMs);
_rightMaster.setStatusFramePeriod(StatusFrame.Status_13_Base_PIDF0, 20, Constants.kTimeoutMs);
_leftMaster.setStatusFramePeriod(StatusFrame.Status_2_Feedback0, 5, Constants.kTimeoutMs);

/* Initialize 
_firstCall = true;
_state = false;
_rightMaster.setStatusFramePeriod(StatusFrameEnhanced.Status_10_Targets, 10);
zeroSensors();
* 
*/
package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;

import com.ctre.phoenix.motorcontrol.StatusFrame;
import com.ctre.phoenix.motorcontrol.TalonFXControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.ctre.phoenix.motorcontrol.NeutralMode;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Constants.Arm;

public class ArmRotationMotionMagic extends SubsystemBase {

    // How to calculate kF: %output*maxOutputNum(1023?)/(native units at desired
    // point)
    // also recall that kF is a percentage between -1 and 1. Anything exceeding this
    // and you clearly don't own a printer

    /***
     * The left motor of the rotation of the arm, and host of the follower
     * configuration.
     */
    // private WPI_TalonFX rightMota = new
    // WPI_TalonFX(Constants.ARM_RIGHT_ROT_MOTOR_ID);// integrated encoder //
    // GetSelectedSensorPosition
    /***
     * The right motor of the rotation of the arm, and follower of the left motor,
     * for the follower configuration.
     */
    private WPI_TalonFX leftMotor = new WPI_TalonFX(Constants.Arm.ARM_LEFT_ROT_MOTOR_ID);// integrated encoder
    private WPI_TalonFX rightMotor = new WPI_TalonFX(Constants.Arm.ARM_RIGHT_ROT_MOTOR_ID);// integrated encoder

    // private DigitalInput limitSwitch;

    /**
     * Rotates arm to deploment side of robot
     * Brake mode for both causes the motors to work against each other.
     * Brake mode for the right side only appears to work as desired.
     * Brake mode for the left side and coast for the right causes a strong
     * snap back.
     **/

    public ArmRotationMotionMagic() {

        leftMotor.configFactoryDefault();
        rightMotor.configFactoryDefault();

        rightMotor.setInverted(true);
        rightMotor.setSensorPhase(true);
        rightMotor.setNeutralMode(NeutralMode.Brake);
        
        
        leftMotor.setInverted(true);
        leftMotor.setSensorPhase(true);
        leftMotor.setNeutralMode(NeutralMode.Brake);

        // leftMotor.set(TalonFXControlMode.Follower, rightMotor.getDeviceID());

    }

    @Override
    public void periodic() {

    }

}