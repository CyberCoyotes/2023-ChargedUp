/*--------------------------------------------------------* 
 * 
 * 2023 Charged Up
 * 
--------------------------------------------------------*/
package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ClawSubsystem;

public class setClawOpen extends CommandBase {
   
    private final ClawSubsystem m_clawSubsystem;
   
    public setClawOpen(ClawSubsystem subsystem) {
     m_clawSubsystem = subsystem;
     addRequirements(m_clawSubsystem);
    }
}
