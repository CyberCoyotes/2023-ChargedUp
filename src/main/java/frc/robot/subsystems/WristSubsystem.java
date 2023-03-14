package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;

/*
 * DATA COLLECTION
 * Rot  | Wrist     | Notes
 * -------------------------------------
 * 0    | 00,000    | Home position
 * 0    | 44,000    | Wrist is resting, flipped down
 * 10   | 22,000    | Wrist is level
 * 
 */


public class WristSubsystem extends SubsystemBase {
    private TalonFX m_motorController = new TalonFX(Constants.Arm.WRIST_TALONFX_ID);

    double WRIST_HOME_POS = 22000;

    /* If ARM ROTATION = 10 or less, then setWristHome() */
    /*
    
    TODO test encoder values (direction of sensors relative to positive motor input), limits
    TODO Find comfortable input value, create virtual speed limiter

    
    */
    public WristSubsystem() {
        m_motorController.setNeutralMode(NeutralMode.Brake);
        
        m_motorController.configPeakOutputForward(0.5);
        m_motorController.configPeakOutputReverse(-0.5);
        
    }
    public void PercentOutputSupplierDrive(double input)
    {
        m_motorController.set(ControlMode.PercentOutput, input);
    }

    public void setWristHome(double input) {
        
        m_motorController.set(ControlMode.Position, WRIST_HOME_POS);
        
    }
    public double getWristPos() {
        return m_motorController.getSelectedSensorPosition();
        // Return the current encoder position of the Wrist
        // return 0; // leaving for the LOLs 
    }

}
