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

import com.ctre.phoenix.motorcontrol.DemandType;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.TalonFXControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.ctre.phoenix.sensors.CANCoder;

import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class ArmSubsystem extends SubsystemBase {
 

    /***
     * The left motor of the rotation of the arm, and host of the follower configuration. 
     */
    private WPI_TalonFX leftMotorHost = new  WPI_TalonFX(Constants.ARM_LEFT_ROT_MOTOR);//integrated encoder, accessed via GetSelectedSensorPosition
    /***
     * The right motor of the rotation of the arm, and follower of the left motor, for the follower configuration. 
     */
    private WPI_TalonFX rightMotor = new WPI_TalonFX(Constants.ARM_RIGHT_ROT_MOTOR);//integrated encoder, accessed via GetSelectedSensorPosition

    /** Rotates arm to deploment side of robot */ 
    public ArmSubsystem() {
        //here we choose to use follower control mode as the left as host, to use motionmagic
        rightMotor.set(TalonFXControlMode.Follower, rightMotor.getDeviceID());
        //closed-loop modes the demand0 output is the output of PID0.
        //
        leftMotorHost.set(TalonFXControlMode.MotionMagic, DemandType.);

        leftMotorHost.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Absolute); //todo: verify this
        rightMotor.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Absolute);
        
    }
    public void rotateArmDeploy(){
        leftMotor.set(TalonFXControlMode.MotionProfileArc, 0);    
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
     */


    /** Rotates arm to intake side of robot */
    public void rotateArmforIntake(){
        
    }

    /** 
     * Use case scenario 1: Allow for a command to use any values for encoder
     * Use case scenarior 2: Use for incremental rotation, e.g. 10 ticks for every button press
    **/
    public void rotateArmManual(){
        
    }
}