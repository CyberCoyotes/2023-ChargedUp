/*--------------------------------------------------------* 
 * 
 * 2023 Charged Up
 * 
--------------------------------------------------------*/

package frc.robot;

import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.TalonFXConfiguration;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.ctre.phoenix.motorcontrol.ControlMode;

import com.ctre.phoenix.motorcontrol.StatusFrame;
import com.ctre.phoenix.motorcontrol.TalonFXControlMode;
import com.ctre.phoenix.motorcontrol.TalonFXInvertType;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.ctre.phoenix.motorcontrol.NeutralMode;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Constants.Arm;

/* The 364 Base swerve loads their CTRE configs in Robot.java 
This is presumably to ensure the settings carry across any drivetrain command references regardless of teleop vs auton.
I've taken a simular approach with the arm settings.
As yet to be confirmed by running on the robot using code; only Phoenix Tuner at the momement */

public class CTREConfigsArm {
    public TalonFX armLeftConfig;
    public TalonFX armRightConfig;

    CTREConfigsArm () {
        armLeftConfig = new TalonFX(Constants.Arm.ARM_LEFT_ROT_MOTOR_ID);// integrated encoder
        armRightConfig = new TalonFX(Constants.Arm.ARM_RIGHT_ROT_MOTOR_ID);// integrated encoder
        // rightArmMotor = new WPI_TalonFX(Constants.Arm.ARM_RIGHT_ROT_MOTOR_ID);// integrated encoder

    /* Right Side Motor */
    // rightMotor.configFactoryDefault(); // Move to init?
    // rightMotor.setInverted(true);
    // rightMotor.set(TalonFXInvertType.Clockwise, 0.1);

    // rightMotor.setSensorPhase(true);
    // rightMotor.setNeutralMode(NeutralMode.Brake);
    // rightMotor.set(ControlMode.Position, 0.25);

    // leftMotor.configFactoryDefault(); // Move to init?        
    armLeftConfig.setInverted(true);
    armLeftConfig.setSensorPhase(true);
    armLeftConfig.setNeutralMode(NeutralMode.Brake);
    armLeftConfig.set(TalonFXControlMode.MotionMagic, 1); // or generic control mode?


    // leftMotor.set(TalonFXControlMode.Follower, rightMotor.getDeviceID());
    }
}
