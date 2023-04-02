package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ArmRotationSubsystem;

public class SetArmRotationCommand extends CommandBase {

    private final ArmRotationSubsystem m_ArmSub;

    private int m_degrees;

    /**
     * A command to set arm rotation. This will <b>never finish alone,</b> and will
     * instead be cancellable by any other commands that demand the need of the arms
     * rotation mechanism.
     * 
     * @param degrees The degrees to set the arm to.
     * @param subsystem The subsystem to blah blah blah you know the drill dude
     * 
     */
    @Override
    public InterruptionBehavior getInterruptionBehavior() {
        return InterruptionBehavior.kCancelSelf;
    }
    public SetArmRotationCommand(int degrees, ArmRotationSubsystem subsystem) {
        m_ArmSub = subsystem;
        this.m_degrees = degrees;
        

        addRequirements(m_ArmSub);
    }

    @Override
    public void initialize() {

    }

    @Override
    public void execute() {
        m_ArmSub.RotateArmToDeg(m_degrees);
    }

    @Override
    public boolean isFinished() {
        return false;
    }

}
