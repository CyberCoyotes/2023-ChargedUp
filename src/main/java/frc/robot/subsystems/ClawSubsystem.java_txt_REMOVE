/*--------------------------------------------------------* 
 * 
 * 2023 Charged Up
 * 
 * Pneumatic with a single solenoid that opens and closes the intake claw
 * 
 * Documentation reference
 * https://docs.wpilib.org/en/stable/docs/software/hardware-apis/pneumatics/pneumatics.html
 * https://www.revrobotics.com/rev-11-1852/
 * 
 * Compressor Test Mode
 * https://docs.revrobotics.com/rev-11-1852/pneumatic-hub-troubleshooting#compressor-test-mode 
 * 
 * 
--------------------------------------------------------*/

package frc.robot.subsystems;

import static edu.wpi.first.wpilibj.DoubleSolenoid.Value.kForward;
import static edu.wpi.first.wpilibj.DoubleSolenoid.Value.kReverse;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ClawSubsystem extends SubsystemBase {
    
    // REV Pneumatics Hub
    // private final Solenoid m_clawSolenoid = new Solenoid(PneumaticsModuleType.REVPH, Constants.REV_CLAW_ID);
    private final DoubleSolenoid m_claw = 
        new DoubleSolenoid(PneumaticsModuleType.REVPH,0,1);


    Compressor phCompressor = new Compressor(21, PneumaticsModuleType.REVPH); // Using 21 from the PDH
    
    boolean enabled = phCompressor.isEnabled();
    boolean pressureSwitch = phCompressor.getPressureSwitchValue();
    // double current = phCompressor.getCompressorCurrent();
    // AnalogInput pressureSensor = new AnalogInput(0);



    public void clawOpen() {
        m_claw.set(kForward);
    }

    public void clawClose() {
        m_claw.set(kReverse);
    }
}
