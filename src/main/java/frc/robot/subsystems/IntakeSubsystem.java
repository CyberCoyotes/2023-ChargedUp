/*--------------------------------------------------------* 
 * 
 * 2023 Charged Up
 * 
 * Spark Max + Neo 550 
--------------------------------------------------------*/
package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.ctre.phoenix.motorcontrol.VictorSPXControlMode;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class IntakeSubsystem extends SubsystemBase {

    private final VictorSPX m_motor = new VictorSPX(Constants.INTAKE_WHEELS_MOTOR_ID);

    public IntakeSubsystem() {
        // addChild("Intake Wheels", m_motor);
    }

    public void intakeWheelsIn() {
        m_motor.set(ControlMode.PercentOutput, 1.0);

    }

    // Should not be needed. May or may not assign to a button
    public void intakeWheelsReverse() {
        m_motor.set(ControlMode.PercentOutput, -1.0);
    }

    public void intakeWheelsOff() {
        m_motor.set(ControlMode.PercentOutput, 0.0);
    }
}
