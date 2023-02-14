/*--------------------------------------------------------* 
 * 
 * 2023 Charged Up
 * 
 * Spark Max + Neo 550 
--------------------------------------------------------*/
package frc.robot.subsystems;

import edu.wpi.first.wpilibj.motorcontrol.Spark;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class IntakeWheelsSubsystem extends SubsystemBase {
    private final Spark m_motor = new Spark(1); // TODO Check actual channel

    public IntakeWheelsSubsystem() {
        addChild("Wheels", m_motor);
    }

    public void intakeWheelsOn() {
        m_motor.set(-1);

    }

    public void intakeWheelsOff() {    
    m_motor.set(0);
    }

    // Should not be needed. May or may not assign to a button
    public void intakeWheelsReverse() {
        m_motor.set(1);
    }
}
