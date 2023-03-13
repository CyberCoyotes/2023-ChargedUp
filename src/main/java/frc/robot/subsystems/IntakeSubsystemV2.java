package frc.robot.subsystems;

import edu.wpi.first.wpilibj.motorcontrol.PWMSparkMax;
import edu.wpi.first.wpilibj.motorcontrol.Spark;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;


/**
 * 
 * Made for sucking in the cargo, with 3 rollers all connected to a {@code PWMSparkMax}.
 */
public class IntakeSubsystemV2 extends SubsystemBase  
{

    //todo Check if the motor needs inversion to properly intake.

    private PWMSparkMax m_motorController = new PWMSparkMax(Constants.Arm.ARM_INTAKE_PWM_PORT);


    private float power = 0.6f;

    public void SetDriveOutake()
    {
        m_motorController.set(power*-1);
    }
    public void SetDriveIntake()
    {
        m_motorController.set(power);  
    }

    public void ShutUp()
    {
        m_motorController.set(0);  
    }



}
