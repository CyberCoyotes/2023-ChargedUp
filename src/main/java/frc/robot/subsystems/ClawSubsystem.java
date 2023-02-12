/*--------------------------------------------------------* 
 * 
 * 2023 Charged Up
 * 
 * Pneumatic with a single solenoid that opens and closes the intake claw
 * 
 * Documentation reference
 * https://docs.wpilib.org/en/stable/docs/software/hardware-apis/pneumatics/pneumatics.html
 * 
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
      PneumaticsModuleType.CTREPCM, 0); // TODO what is our module type?
    
 public void clawOpen(){
 // m_clawSolenoid.set(kForward);
 }   
  public void clawClose(){
   // m_clawSolenoid.set(kReverse);  
  }     
 }


