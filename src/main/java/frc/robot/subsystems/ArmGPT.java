package frc.robot.subsystems;


import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import frc.robot.Constants;

public class ArmGPT {
  private TalonSRX rightMotor;

  // Constants for motion magic control
  private final int kTimeoutMs = 30;
  private final int kSlotIdx = 0;
  private final double kP = 0.1;
  private final double kI = 0.0;
  private final double kD = 0.0;
  private final double kF = 0.0;

  // Constants for arm motion
  private final double kMaxVelocity = 1000.0; // degrees per second
  private final double kMaxAcceleration = 2000.0; // degrees per second squared


//   public ArmGPT(int ARM_RIGHT_ROT_MOTOR_ID) {

  public ArmGPT() {
    rightMotor = new TalonSRX(Constants.Arm.ARM_EXTENDER_MOTOR_ID);

    // Set up feedback device
    rightMotor.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, kTimeoutMs, kTimeoutMs);

    // armMotor.setSensorPhase(false);
    // armMotor.configNominalOutputForward(0, 10);
    // armMotor.configNominalOutputReverse(0, 10);
    // armMotor.configPeakOutputForward(1, 10);
    // armMotor.configPeakOutputReverse(-1, 10);
    // armMotor.selectProfileSlot(ARM_SLOT_IDX, ARM_PID_IDX);


    // Set neutral mode
    rightMotor.setNeutralMode(NeutralMode.Brake);

    // Set up motion magic control
    rightMotor.config_kP(kSlotIdx, kP, kTimeoutMs);
    rightMotor.config_kI(kSlotIdx, kI, kTimeoutMs);
    rightMotor.config_kD(kSlotIdx, kD, kTimeoutMs);
    rightMotor.config_kF(kSlotIdx, kF, kTimeoutMs);
    rightMotor.configMotionCruiseVelocity((int)(kMaxVelocity * 4096.0 / 360.0), kTimeoutMs);
    rightMotor.configMotionAcceleration((int)(kMaxAcceleration * 4096.0 / 360.0), kTimeoutMs);
  }

  public void setArmPosition(double degrees) {
    int position = (int)(degrees * 4096.0 / 360.0);
    rightMotor.set(ControlMode.MotionMagic, position);
  }
}

/*
 * In this code, we first import the necessary classes from the CTRE library. We then define a RobotArm class with a single TalonSRX member variable for the arm motor.

We define some constants for motion magic control, including a timeout value, a slot index for the motor controller, and gains for the proportional, integral, derivative, and feedforward terms. We also define constants for the maximum velocity and acceleration of the arm.

In the constructor, we create a new TalonSRX object with the given motor ID. We then configure the feedback device to be a relative magnetic encoder, set the neutral mode to brake, and configure the motion magic control parameters. We convert the maximum velocity and acceleration values from degrees per second to encoder ticks per 100ms using the 4096.0 / 360.0 conversion factor.

Finally, we define a setArmPosition method that takes a desired arm angle in degrees and converts it to an encoder position in ticks using the same conversion factor. We then set the motor control mode to motion magic and set the target position.

To use this code, you would create a RobotArm object with the correct motor ID, and then call the setArmPosition method with the desired arm angle. The motor controller will automatically ramp up to the desired velocity and acceleration, and move the arm to the target position.

 */