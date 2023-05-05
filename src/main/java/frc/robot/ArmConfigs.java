package frc.robot;

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

public class ArmConfigs {
 
    ArmConfigs () {

        WPI_TalonFX leftMotor = new WPI_TalonFX(Constants.Arm.ARM_LEFT_ROT_MOTOR_ID);// integrated encoder
        WPI_TalonFX rightMotor = new WPI_TalonFX(Constants.Arm.ARM_RIGHT_ROT_MOTOR_ID);// integrated encoder
        
    double speed = 0.25;

    /* Right Side Motor */
    // rightMotor.configFactoryDefault(); // Move to init?
    // rightMotor.setInverted(true);
    // rightMotor.set(TalonFXInvertType.Clockwise, 0.1);

    // rightMotor.setSensorPhase(true);
    // rightMotor.setNeutralMode(NeutralMode.Brake);
    // rightMotor.set(ControlMode.Position, 0.25);

    // leftMotor.configFactoryDefault(); // Move to init?        
    leftMotor.setInverted(true);
    leftMotor.setSensorPhase(true);
    leftMotor.setNeutralMode(NeutralMode.Brake);
    leftMotor.set(TalonFXControlMode.MotionMagic, 1); // or generic control mode?


    // leftMotor.set(TalonFXControlMode.Follower, rightMotor.getDeviceID());
    }
}
