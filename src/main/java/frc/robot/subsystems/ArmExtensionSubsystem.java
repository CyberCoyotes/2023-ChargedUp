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
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.TalonSRXControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Constants.Arm;

public class ArmExtensionSubsystem extends SubsystemBase {

 
    private TalonSRX m_motorController = new TalonSRX(Constants.Arm.ARM_EXTENDER_MOTOR_ID);
    
    // private Encoder m_Encoder = new Encoder(0, 0, 0)

   
    /**
     * 
     * @return The encoder reading of the motor
     */
    public int ReadExtension()
    {
        
        //absolute quad mag encoder; Placed after the gearboxes (1:4, 1:9). Need to test if a single lap ()
        return (int)m_motorController.getSelectedSensorPosition();
    }

    public void Setup()
    {

        


        m_motorController.setSelectedSensorPosition(0);
        
        m_motorController.config_kP(0,1);
        m_motorController.config_kI(0,0);
        m_motorController.config_kD(0,0);

        m_motorController.selectProfileSlot(0, 0);
        
        
        m_motorController.setNeutralMode(NeutralMode.Brake);
        m_motorController.configReverseSoftLimitThreshold(Arm.EXTENSION_POSITION_IN + m_motorController.getSelectedSensorPosition());
        m_motorController.configForwardSoftLimitEnable(true, 0);
        m_motorController.configForwardSoftLimitThreshold(Arm.EXTENSION_POSITION_OUT + m_motorController.getSelectedSensorPosition());
        m_motorController.configReverseSoftLimitEnable(true, 0);
        m_motorController.setSensorPhase(true);
    
    }
    public ArmExtensionSubsystem() {
      Setup();
    }
    public void setArmIn() {
        m_motorController.set(TalonSRXControlMode.Position, Arm.EXTENSION_POSITION_IN);    
    }

    @Override
    public void periodic() {
        // System.out.println(m_motorController.getSelectedSensorPosition());

    }
    public void setArmMid() {
        m_motorController.set(TalonSRXControlMode.Position, 9500);
    }
    public void PercentOutputSupplierDrive(double input)
    {
        m_motorController.set(ControlMode.PercentOutput, input);
    }
    
    /**
     * @param input the encoder degrees to set the arm at. Note the arm extends to roughly 0 at rest, and 14500 units maximum. 
     */
    public void SetArmToTickPosition(int input)
    {
        
        double target = (double)input;
        // System.out.println();
        m_motorController.configPeakOutputForward(0.4);
        m_motorController.configPeakOutputReverse(-0.4);
        m_motorController.set(ControlMode.Position, target);

    }

    // VanScoyoc attempt
    public double getExtensionPosition()
    {
        //absolute quad mag encoder; Placed after the gearboxes (1:4, 1:9). Need to test if a single lap ()
        double extensionPosition = m_motorController.getSelectedSensorPosition();

        return extensionPosition;
    }

    // TODO Test VanScoyoc attempt
    public void pickupFloorCube() {
        m_motorController.configPeakOutputForward(0.4);
        m_motorController.configPeakOutputReverse(-0.4);
        m_motorController.set(TalonSRXControlMode.Position, Arm.EXTENSION_FLOOR_POS);
    }

}
