/*--------------------------------------------------------* 
 * 
 * 2023 Charged Up
 * 
 * Sensors.java
 * 
 * --------------------------------------------------------*/
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
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class SensorsSubsystem extends SubsystemBase {

    private DigitalInput armLimitSwitch = new DigitalInput(Constants.LIMIT_SWITCH_ARM_PORT);

    public SensorsSubsystem() {
    }

    // Start
    public boolean getLimitSwitchState() {
        return armLimitSwitch.get();
        // SmartDashboard.putBoolean("Rotation Switch", getLimitSwitchState());
    }

}

/*
 * Encoder encoder = new Encoder(0, 1);
 * Spark spark = new Spark(0);
 * // Limit switch on DIO 2
 * DigitalInput limit = new DigitalInput(2);
 * public void autonomousPeriodic() {
 * // Runs the motor backwards at half speed until the limit switch is pressed
 * // then turn off the motor and reset the encoder
 * if(!limit.get()) {
 * spark.set(-0.5);
 * } else {
 * spark.set(0);
 * encoder.reset();
 * }
 * }
 */

/*
 * Magnetic Limit Switch
 * https://www.revrobotics.com/rev-31-1462/
 * 
 * https://www.chiefdelphi.com/t/rev-magnetic-limit-switches/365147
 * 
 * https://docs.wpilib.org/en/stable/docs/software/hardware-apis/sensors/limit-switch.html
 * 
 * https://docs.wpilib.org/en/stable/docs/software/dashboards/smartdashboard/displaying-expressions.html 
 * 
 * https://docs.wpilib.org/en/stable/docs/hardware/sensors/digital-inputs-hardware.html 
 */
