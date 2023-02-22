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
--------------------------------------------------------*/
package frc.robot.subsystems;

import java.util.function.DoubleSupplier;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.DemandType;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.StatusFrame;
import com.ctre.phoenix.motorcontrol.TalonFXControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.ctre.phoenix.sensors.CANCoder;
import com.ctre.phoenix.motorcontrol.NeutralMode;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Constants.Arm;

public class ArmSubsystem extends SubsystemBase {
 

    //How to calculate kF: %output*maxOutputNum(1023?)/(native units at desired point)
    //also recall that kF is a percentage between -1 and 1. Anything exceeding this and you clearly don't own a printer
    
    /***
     * The left motor of the rotation of the arm, and host of the follower configuration. 
     */
    private WPI_TalonFX rightMotHost = new  WPI_TalonFX(Constants.ARM_RIGHT_ROT_MOTOR_ID);//integrated encoder, accessed via GetSelectedSensorPosition
    /***
     * The right motor of the rotation of the arm, and follower of the left motor, for the follower configuration. 
     */
    private static void dsfsdf()
    {
        
    }
    private WPI_TalonFX leftMota = new WPI_TalonFX(Constants.ARM_LEFT_ROT_MOTOR_ID);//integrated encoder, accessed via GetSelectedSensorPosition

    /** Rotates arm to deploment side of robot */ 
    public ArmSubsystem() {
        rightMotHost.setNeutralMode(NeutralMode.Brake); // TODO Test
        leftMota.setNeutralMode(NeutralMode.Brake); // TODO Test
        

        //here we choose to use follower control mode as the left as host, to use motionmagic
        leftMota.set(TalonFXControlMode.Follower, leftMota.getDeviceID());
        //closed-loop modes the demand0 output is the output of PID0.
        rightMotHost.setSelectedSensorPosition(0);
        //
        rightMotHost.setStatusFramePeriod(StatusFrame.Status_12_Feedback1, 20, 100);
        

        // rightMotHost.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor); 
        // leftMota.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor);
        
    }
    /***
     * Gets the rotation of the system in encoder ticks.
     * 
     */
    public double GetRotation()
    {
        return( rightMotHost.getSelectedSensorPosition() );
    }
    public void PercentOutputSupplierDrive(DoubleSupplier input)
    {
        rightMotHost.set(ControlMode.PercentOutput, input.getAsDouble() * .2);//took like 6.5 seconds at 10% output to make a revolution 
    }
    public void rotateArmDeploy(){
        double target_sensorUnits= Arm.ARM_ROTATE_POSITION_DEPLOY; //todo the setpoint, figure this out logically; at the deploy end of the arm?
        double maxGravityFF = .02; //todo haha
        
        
        int kMeasuredPosHorizontal =  Arm.ARM_ROTATION_HORIZONTAL_TICKS; //todo Position measured when arm is horizontal/give an offset to resting position
        double kTicksPerDegree = 4096 / 360; //Sensor is 1:1 with arm rotation
        double degrees = (GetRotation() - kMeasuredPosHorizontal) / kTicksPerDegree;
        double radians = java.lang.Math.toRadians(degrees);
        double cosineScalar = java.lang.Math.cos(radians);  //todo get the cosine of the motor
        
        //FF is measured as 


        double feedFwdTerm = maxGravityFF * cosineScalar; // todo get ff, depends on cosine

        rightMotHost.set(TalonFXControlMode.MotionMagic, target_sensorUnits, DemandType.ArbitraryFeedForward, feedFwdTerm);

    }
    private int ConvertRotToFXEncoder(double angle) {
        //2pi =  2048
        //2048 * 2pi/2pi 
        return 0;
    }
    private int ConvertFXEncodertoRot(double angle) {
        return 0;
    }
    //too many possible apporoaches;
    /*
     * profiledpid subsystem subclass
     * position controlmode
     * arb feedforward;
     * An example of ff; decipher this sometime https://github.com/CrossTheRoadElec/Phoenix-Examples-Languages/blob/master/Java%20Talon%20FX%20(Falcon%20500)/MotionMagic_ArbFeedForward/src/main/java/frc/robot/Robot.java
     * motionmagic;
     *  Motion Magic is a control mode [...] like Motion Profiling without needing to generate motion profile trajectory points.
     * + gravity feedforward;
     *  direct comment on tuning for gravity: https://v5.docs.ctr-electronics.com/en/stable/ch16_ClosedLoop.html#gravity-offset-arm
     * Don't use the motion profile mode: https://www.chiefdelphi.com/t/motion-magic-vs-motion-profiling/365813/5
     * /
     * // 360/rangeOfEncoder = degPerTick
     * //degs = (GetPos - HorizontalPos ) * DegsPerTick // minus the hor, to get cos(0) = 1
     * //FOR THIS:  encoder range, encoder read at horizontal position, power needed to keep horizontal
     * Encoder goes negative when driven from rest to other side
     * Gear ratio is 224: 1; base talonfx integrated encoder is 2048; 458752 is expected total encoder range


    /** Rotates arm to intake side of robot */
    public void rotateArmforIntake(){
        
    }

    /** 
     * Use case scenario 1: Allow for a command to use any values for encoder
     * Use case scenarior 2: Use for incremental rotation, e.g. 10 ticks for every button press
    **/
    public void rotateArmManual(){
        
    }
    public void ZeroArmEncoder() {
        rightMotHost.setSelectedSensorPosition(0);
    }
}