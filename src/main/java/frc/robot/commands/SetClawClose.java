/*--------------------------------------------------------* 
 * 
 * 2023 Charged Up
 * 
--------------------------------------------------------*/
package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ClawSubsystem;

public class SetClawClose extends CommandBase {
   
    private final ClawSubsystem m_clawSubsystem;
   
    public SetClawClose(ClawSubsystem subsystem) {
     m_clawSubsystem = subsystem;
     addRequirements(m_clawSubsystem);
    }

    @Override
    public void initialize() {
        m_clawSubsystem.clawClose();
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
