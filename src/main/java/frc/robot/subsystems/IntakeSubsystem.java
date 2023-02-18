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
import frc.robot.Constants.*;

public class IntakeSubsystem extends SubsystemBase {
    
    
    private static final int INTAKE_WHEELS_MOTOR = 41;

    private final Spark m_motor = new Spark(14); // TODO Check actual channel

    public IntakeSubsystem() {
        addChild("Wheels", m_motor);
    }

    public void intakeWheelsIn() {
        m_motor.set(-1);

    }

    // Should not be needed. May or may not assign to a button
    public void intakeWheelsReverse() {
        m_motor.set(1);
    }

    public void intakeWheelsOff() {    
        m_motor.set(0);
        }
}
