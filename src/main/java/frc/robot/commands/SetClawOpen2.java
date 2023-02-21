package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ClawSubsystem;

public class SetClawOpen2 extends CommandBase {

    private final ClawSubsystem m_clawSubsystem;

    public SetClawOpen2(ClawSubsystem subsystem) {
        m_clawSubsystem = subsystem;
        addRequirements(m_clawSubsystem);
    }

    @Override
    public void initialize() {
        m_clawSubsystem.clawOpen();
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
