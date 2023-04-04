package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ArmRotationSubsystem;
import frc.robot.subsystems.ArmWristSubsystem;

public class ConeMid extends CommandBase {

    // lesson learned: command groups aren't always the answer

    // somewhere in a magical text file i have the values we like. To get them,
    //// solve my riddles three

    private ArmRotationSubsystem arm;
    private ArmWristSubsystem wrist;

    /** Intended for macro usage. */
    public ConeMid(ArmWristSubsystem wrist, ArmRotationSubsystem armRot) {

        this.arm = armRot;
        this.wrist = wrist;
        addRequirements(arm, wrist);
    }

    @Override
    public void execute() {

        arm.RotateArmToDeg(105);
        wrist.setWristToPosition(51558);

    }
    @Override
    public boolean isFinished() {
        return false;
    }
    @Override
    public InterruptionBehavior getInterruptionBehavior() {
        return InterruptionBehavior.kCancelSelf;
    }

}
