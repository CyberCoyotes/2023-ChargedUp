package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ArmExtensionSubsystem;

public class SetArmExtentCommand extends CommandBase
{

    private ArmExtensionSubsystem subsystem;
    private int position;

    @Override
    public void initialize() {
        super.initialize();
    }    
    @Override
    public void execute() {
        subsystem.SetToPosition(position);
    }
    @Override
    public boolean isFinished() {
        return false;
    }
    public SetArmExtentCommand(int desiredEncoderPosition, ArmExtensionSubsystem subsystem) {
        this.position = desiredEncoderPosition;
        this.subsystem = subsystem;
        addRequirements(subsystem);
    }
    @Override
    public InterruptionBehavior getInterruptionBehavior() {
        return InterruptionBehavior.kCancelSelf;
    }
}
