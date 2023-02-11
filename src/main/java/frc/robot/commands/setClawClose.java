/*--------------------------------------------------------* 
 * 
 * 2023 Charged Up
 * 
--------------------------------------------------------*/
package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ClawSubsystem;

public class setClawClose extends CommandBase {
    
    private final ClawSubsystem m_clawSubsystem;
   
    public setClawClose(ClawSubsystem subsytem) {
     m_clawSubsystem = subsytem;
     addRequirements(m_clawSubsystem);
    }
}
