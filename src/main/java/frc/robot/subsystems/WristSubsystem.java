package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;

public class WristSubsystem extends SubsystemBase {
    private TalonFX m_motorController = new TalonFX(Constants.Arm.WRIST_TALONFX_ID);

    /*
    
    TODO test encoder values (direction of sensors relative to positive motor input), limits
    TODO Find comfortable input value, create virtual speed limiter

    
    */
    public WristSubsystem() {
        m_motorController.configPeakOutputForward(0.5);
        m_motorController.configPeakOutputReverse(-0.5);
        
    }
    public void PercentOutputSupplierDrive(double input)
    {
        m_motorController.set(ControlMode.PercentOutput, input);
    }
}
