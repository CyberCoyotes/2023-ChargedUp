package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ArmSubsystem;

public class SetArmRot extends CommandBase {

    private final ArmSubsystem m_ArmSub;

    private int m_degrees;

    public SetArmRot(int degrees, ArmSubsystem subsystem) {
        m_ArmSub = subsystem;
        this.m_degrees = degrees;

        addRequirements(m_ArmSub);
    }

    @Override
    public void initialize() {
        m_ArmSub.RotateArmToDeg(m_degrees);
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
