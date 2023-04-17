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

public class ArmRotationSubsystemMM extends SubsystemBase implements IArmSubsystem{

    // How to calculate kF: %output*maxOutputNum(1023?)/(native units at desired
    // point)
    // also recall that kF is a percentage between -1 and 1. Anything exceeding this
    // and you clearly don't own a printer

    /***
     * The left motor of the rotation of the arm, and host of the follower
     * configuration.
     */
    // private WPI_TalonFX rightMota = new WPI_TalonFX(Constants.ARM_RIGHT_ROT_MOTOR_ID);// integrated encoder //
                                                                                         // GetSelectedSensorPosition
    /***
     * The right motor of the rotation of the arm, and follower of the left motor,
     * for the follower configuration.
     */
    private WPI_TalonFX leftMota = new WPI_TalonFX(Constants. Arm.ARM_LEFT_ROT_MOTOR_ID);// integrated encoder, accessed via
    private WPI_TalonFX rightMota = new WPI_TalonFX(Constants.Arm.ARM_RIGHT_ROT_MOTOR_ID);// integrated encoder, accessed via
    
    private DigitalInput limitSwitch;
    
    /**
     * Rotates arm to deploment side of robot
     * Brake mode for both causes the motors to work against each other.
     * Brake mode for the right side only appears to work as desired.
     * Brake mode for the left side and coast for the right causes a strong
     * snap back.
     **/
    public double GetCurrent()
    {
        return rightMota.getSupplyCurrent();
    }
    public ArmRotationSubsystemMM(DigitalInput input) {
        leftMota.configFactoryDefault();
        rightMota.configFactoryDefault();


        rightMota.setInverted(true);
        rightMota.setSensorPhase(true);
        // leftMota.setInverted(true);
        this.limitSwitch = input;
        rightMota.setSelectedSensorPosition(Math.abs( this.ConvertDegToFXEncoder(Arm.ARM_OFFSET_DEGREES)));
        

        //:The arm is some degrees off from 0 being truly down pointing
        // rightMota.configIntegratedSensorOffset( ConvertDegToFXEncoder(Constants.Arm.ARM_OFFSET_DEGREES));


        // roughly 20 degree offset
        rightMota.configReverseSoftLimitThreshold(ConvertDegToFXEncoder(-Arm.ARM_MAX_DEG));// verify accuracy

        rightMota.configReverseSoftLimitEnable(true, 0);

        rightMota.setNeutralMode(NeutralMode.Brake);
        // rightMota.setNeutralMode(NeutralMode.Coast); // added 3/13/23

//
        // here we choose to use follower control mode as the left as host, to use
        // motionmagic
        leftMota.set(TalonFXControlMode.Follower, rightMota.getDeviceID());
        // closed-loop modes the demand0 output is the output of PID0.
        //
        rightMota.setStatusFramePeriod(StatusFrame.Status_12_Feedback1, 20, 100);
        // #region PIDF, motion profile configurations

        // main PID, no aux
        rightMota.selectProfileSlot(Arm.PIDSlotIDx, 0);
        rightMota.config_kP(0, Arm.kP);
        rightMota.config_kI(0, Arm.kI);
        rightMota.config_kD(0, Arm.kD);

        rightMota.configMotionCruiseVelocity(Arm.kMaxArmRotVelocity);
        rightMota.configMotionAcceleration(Arm.kMaxArmRotAcceletation);// max accel in units towards endgoal, in sensor
                                                                    // units /0.1 seconds
                                                                    // rightMota.setSensorPhase(true);

        // #endregion PIDF, motion profile configurations

    }


@Override
public void periodic() 
{

    //manual guard. Interruption behaviour cannot stop this, either

    if (GetRotationInDeg() > Arm.ARM_MAX_DEG) {
        // TODO Auto-generated method stub
        this.getCurrentCommand().cancel();
    }
}

    /***
     * Gets the rotation of the system in encoder ticks.
     *
     * @return
     *         The rotation of the arm in encoder ticks, adjusted so that 0 equals
     *         straight down.
     */
    public double GetRotation() {
        return (-rightMota.getSelectedSensorPosition());
    }

    /**
     * Equivalent to calling {@code ConvertFXEncodertoDeg()} after
     * {@code GetRotation()}.
     *
     * @return
     *         The rotation of the arm in degrees, adjusted so that 0 equals
     *         straight down.
     */
    
    public int GetRotationInDeg() {
        return -ConvertFXEncodertoDeg((rightMota.getSelectedSensorPosition()));
        
    }
    public boolean GetSwtichState()
    {
        // Keep in mind the pull-up reads positive when "off-duty"
        
        return limitSwitch.get();
    }

    
    public void PercentOutputSupplierDrive(double input) {
        
        //! this is terrible
        if (limitSwitch.get()) {
            ZeroArmEncoder();
        }

        if (input > 0 && limitSwitch.get()) {
            return;
        }
        else
        {
            //here we go again
    
            //#region arbFF stuff

            // at the deploy end of the arm?
double maxGravityFF = .03; // todo; doesnt cover the extended compensation where it may matter most; 0.04
// was the value at extention
int kMeasuredPosHorizontal = Arm.ARM_ROTATION_HORIZONTAL_TICKS; // todo Position measured when arm is
                     // horizontal/give an offset to resting position
double kTicksPerDegree = 4096 / 360; // Sensor is 1:1 with arm rotation
double degrees = (GetRotation() - kMeasuredPosHorizontal) / kTicksPerDegree;
double radians = java.lang.Math.toRadians(degrees);
double cosineScalar = java.lang.Math.cos(radians); // todo get the cosine of the motor

// FF is measured as

double arbFF = maxGravityFF * cosineScalar; // get ff, depends on cosine

//#endregion
        // DemandType.ArbitraryFeedForward, -0.01
            // rightMota.set(ControlMode.PercentOutput, input * .6);// took like 6.5 seconds at 10% output to make a
            rightMota.set(ControlMode.PercentOutput, input * .6);// took like 6.5 seconds at 10% output to make a
        }
                                                                  // 
    }

    

    public int ConvertDegToFXEncoder(double degs) {
        // 2pi = 2048
        // 2048 * 2pi/2pi
        int maxEncoder = Arm.ARM_ROT_360_TICKS;
        int maxDegree = 360;// trust the process

        return (int) (degs * maxEncoder / maxDegree);
    }

    public int ConvertFXEncodertoDeg(double ticks) {

        int maxEncoder = Arm.ARM_ROT_360_TICKS;
        int maxDegree = 360;// trust the process

        return (int) (ticks * maxDegree / maxEncoder);
    }

    // THE BLOCK;
    /*
     * profiledpid subsystem subclass
     * position controlmode
     * arb feedforward;
     * An example of ff; decipher this sometime
     * https://github.com/CrossTheRoadElec/Phoenix-Examples-Languages/blob/master/
     * Java%20Talon%20FX%20(Falcon%20500)/MotionMagic_ArbFeedForward/src/main/java/
     * frc/robot/Robot.java
     * motionmagic;
     * Motion Magic is a control mode [...] like Motion Profiling without needing to
     * generate motion profile trajectory points.
     * + gravity feedforward;
     * direct comment on tuning for gravity:
     * https://v5.docs.ctr-electronics.com/en/stable/ch16_ClosedLoop.html#gravity-
     * offset-arm
     * Don't use the motion profile mode:
     * https://www.chiefdelphi.com/t/motion-magic-vs-motion-profiling/365813/5
     * /
     * // 360/rangeOfEncoder = degPerTick
     * //degs = (GetPos - HorizontalPos ) * DegsPerTick // minus the hor, to get
     * cos(0) = 1
     * //FOR THIS: encoder range, encoder read at horizontal position, power needed
     * to keep horizontal
     * Encoder goes negative when driven from rest to other side
     * Gear ratio is 224: 1; base talonfx integrated encoder is 2048; 458752 is
     * expected total encoder range
     *
     * /** Rotates arm to intake side of robot
     */
    // we should try a position P(I)D with arbFF rather than jumping to motionmagic
    // in the case we don't need too advanced control
    // https://v5.docs.ctr-electronics.com/en/stable/ch16_ClosedLoop.html#position-closed-loop-control-mode
    public void rotateArmforIntake() {

    }

    public String GetMode()
    {
        try {
        return rightMota.getControlMode().name();
            
        } catch (Exception e) {
          return "No mode read";
        }
        
    }
    /**
     * Rotates the arm to a degree value, where straight down would be 0 degrees, up
     * would be 180, etc
     */
    public void RotateArmToDeg(int degrees) {
        double target_sensorUnits = ConvertDegToFXEncoder(degrees);// intake //todo the setpoint, figure this out
                                                                   // logically;
        rightMota.configPeakOutputForward(0.5);
        rightMota.configPeakOutputReverse(-0.5);
        

        rightMota.set(TalonFXControlMode.Position, -target_sensorUnits);// no arb for now
        // rightMota.set(TalonFXControlMode.MotionMagic, target_sensorUnits,
        // DemandType.ArbitraryFeedForward, arbFF);

    }

    // public void rotateArmForDeploy() {
    //     System.out.println("arm being moved sorta");

    //     // #region Gravity ArbFF configurations

    //     double target_sensorUnits = ConvertDegToFXEncoder(270);// intake //todo the setpoint, figure this out logically;
    //                                                            // at the deploy end of the arm?
    //     double maxGravityFF = .02; // todo; doesnt cover the extended compensation where it may matter most; 0.04
    //                                // was the value at extention
    //     int kMeasuredPosHorizontal = Arm.ARM_ROTATION_HORIZONTAL_TICKS; // todo Position measured when arm is
    //                                                                     // horizontal/give an offset to resting position
    //     double kTicksPerDegree = 4096 / 360; // Sensor is 1:1 with arm rotation
    //     double degrees = (GetRotation() - kMeasuredPosHorizontal) / kTicksPerDegree;
    //     double radians = java.lang.Math.toRadians(degrees);
    //     double cosineScalar = java.lang.Math.cos(radians); // todo get the cosine of the motor

    //     // FF is measured as

    //     double arbFF = maxGravityFF * cosineScalar; // todo get ff, depends on cosine

    //     // #endregion Gravity ArbFF configurations

    //     // :Position mode may not be the right choice, as it assumes a close, updating
    //     // position, rather than a final endpoint. It would likely be incompatible with
    //     // an arb. FF with this approach
    //     //// rightMota.set(TalonFXControlMode.Position,
    //     // Arm.ARM_ROTATE_POSITION_DEPLOY, DemandType.ArbitraryFeedForward, arbFF);
    //     rightMota.set(TalonFXControlMode.MotionMagic, target_sensorUnits);// no arb for now
    //     // rightMota.set(TalonFXControlMode.MotionMagic, target_sensorUnits,
    //     // DemandType.ArbitraryFeedForward, arbFF);

    // }

    /**
     * Use case scenario 1: Allow for a command to use any values for encoder
     * Use case scenarior 2: Use for incremental rotation, e.g. 10 ticks for every
     * button press
     **/
    public void ZeroArmEncoder() {
        // rightMota.setSelectedSensorPosition(this.ConvertDegToFXEncoder(
        // Arm.ARM_OFFSET_DEGREES));
        rightMota.setSelectedSensorPosition(-Math.abs(ConvertDegToFXEncoder(( Arm.ARM_OFFSET_DEGREES))));
    }
    /**
     * Made for compatability with interface
     * 
     */
    @Override
    public void SetToPosition(int setPoint) {
       this.RotateArmToDeg(setPoint);
    }
    /**
     * Made for compatability with interface
     * @return The rotation in degrees.
     */
    @Override
    public int GetPosition() {
         return GetRotationInDeg();
    }

}