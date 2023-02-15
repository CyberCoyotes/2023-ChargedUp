/*--------------------------------------------------------* 
 * 
 * 2023 Charged Up
 * 
 * Spark Max + Neo 550 
--------------------------------------------------------*/
package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.jni.CANSparkMaxJNI;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;


public class IntakeSubsystem extends SubsystemBase {

    
    private final  CANSparkMax m_spark = new CANSparkMax(Constants.INTAKE_WHEELS_MOTOR, MotorType.kBrushless);

    public IntakeSubsystem() {
        // addChild("Wheels", m_motor);
    }

    public void intakeWheelsIn() {
        m_spark.set(1);

    }

    // Should not be needed. May or may not assign to a button
    public void intakeWheelsReverse() {
        m_spark.set(-1);
    }

    public void intakeWheelsOff() {    
        m_spark.set(0);
        }
}
