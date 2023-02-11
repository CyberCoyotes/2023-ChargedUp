/*--------------------------------------------------------* 
 * 
 * 2023 Charged Up
 * 
 * Pneumatic 
--------------------------------------------------------*/

package frc.robot.subsystems;

//import static edu.wpi.first.wpilibj.Solenoid.Value.kForward;
//import static edu.wpi.first.wpilibj.Solenoid.Value.kReverse;

import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ClawSubsystem extends SubsystemBase{
    private final Solenoid m_clawSolenoid =
     new Solenoid(
      PneumaticsModuleType.CTREPCM, 0);
    
 public void ClawOpen(){
 // m_clawSolenoid.set(kForward);
 }   
  public void ClawClose(){
   // m_clawSolenoid.set(kReverse);  
  }     
 }


