package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.Subsystem;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.subsystems.ClawSubsystem;

public class SetClawOpen extends CommandBase {
    private ClawSubsystem clawSub;
    public SetClawOpen(ClawSubsystem subsystem) {
        this.addRequirements(subsystem);
        this.clawSub = subsystem;
    }
    
}
