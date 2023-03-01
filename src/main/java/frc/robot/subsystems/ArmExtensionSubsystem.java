/*--------------------------------------------------------* 
 * 
 * 2023 Charged Up
 * 
 * ArmExtentensionSubsystem.java a.k.a Go-go gadget arm
 * 
 * Pro 775 + TalonSRX
 * 
--------------------------------------------------------*/

/*
 * Using a PID to smoothly move the PID, with a seperate FF for fighting gravity.
 * The FeedForward may be reperesented with cos * ff
 * Cosine - typical math cosine of representing x value
 * 
 * determine encoder reading at rest
 * encoder on talonsrx, FX
 * Correct subsystem(arm, pid, profiledpid)
 * determine use for motionmagic
 * 
 * 
 */

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.DemandType;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.TalonSRXControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.TalonSRXConfiguration;
import com.ctre.phoenix.sensors.CANCoder;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.motorcontrol.MotorController;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Constants.Arm;

public class ArmExtensionSubsystem extends SubsystemBase {

    /**
     * Extends the arm for deployement to level 2 or level 2
     * Should only be used when arm has been rotated for deployment position
     */
    private TalonSRX m_motorController = new TalonSRX(Constants.ARM_EXTENDER_MOTOR_ID);
    
    // private Encoder m_Encoder = new Encoder(0, 0, 0)

   
    /**
     * 
     * @return The encoder reading of the motor
     */
    public double ReadExtension()
    {
        //absolute quad mag encoder; Placed after the gearboxes (1:4, 1:9). Need to test if a single lap ()
        return m_motorController.getSelectedSensorPosition();
    }

    public ArmExtensionSubsystem() {

        // m_motorController.setSelectedSensorPosition(0);
        // m_motorController.setSensorPhase(false);//todo find if this is good enough
       
        m_motorController.setNeutralMode(NeutralMode.Brake); // TODO Test
        m_motorController.configReverseSoftLimitThreshold(Arm.EXTENSION_POSITION_IN + m_motorController.getSelectedSensorPosition());
        m_motorController.configForwardSoftLimitEnable(true, 0);
        m_motorController.configForwardSoftLimitThreshold(Arm.EXTENSION_POSITION_OUT+ m_motorController.getSelectedSensorPosition());
        m_motorController.configReverseSoftLimitEnable(true, 0);
        m_motorController.setSensorPhase(true);

        // m_motorController.configPeakOutputReverse(.4);
        // m_motorController.configPeakOutputForward(.4);

    }
    /**
     * Retracts arm from a deployement to level 2 or level 3
     * Should only be used when arm has been rotated for deployment position
     */
      //no state?

    public void setArmIn() {
        m_motorController.set(TalonSRXControlMode.Position, Arm.EXTENSION_POSITION_IN);
        
    }
    public void setArmOut() {
        m_motorController.set(TalonSRXControlMode.Position, Arm.EXTENSION_POSITION_OUT);
    }
    public void PercentOutputSupplierDrive(double input)
    {
        m_motorController.set(ControlMode.PercentOutput, input);
    }

}
