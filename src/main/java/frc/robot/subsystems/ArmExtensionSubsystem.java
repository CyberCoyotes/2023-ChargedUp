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

import com.ctre.phoenix.motorcontrol.TalonSRXControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
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

    public void setArmExtend() {
        
    }

    /**
     * 
     * @return The encoder reading of the motor
     */
    public float ReadExtension()
    {
        m_motorController.set(TalonSRXControlMode.Position, 0, null, 0);
        return 0;
    }

    public ArmExtensionSubsystem() {
        
    }
    /**
     * Retracts arm from a deployement to level 2 or level 3
     * Should only be used when arm has been rotated for deployment position
     */
      //no state?

    public void setArmIn() {
        m_motorController.set(TalonSRXControlMode.Position, Arm.EXTENSION_POSITION_OUT);
    }

}
