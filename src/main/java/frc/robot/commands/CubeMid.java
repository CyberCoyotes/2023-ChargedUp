package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ArmRotationSubsystem;
import frc.robot.subsystems.ArmWristSubsystem;
import frc.robot.subsystems.IntakeSubsystem;

public class CubeMid extends CommandBase {

    // lesson learned: command groups aren't always the answer
    // Lol, I don't doubt it. Curious to learn more!

    // somewhere in a magical text file i have the values we like. To get them,
    //// solve my riddles three

    private ArmRotationSubsystem arm;
    private ArmWristSubsystem wrist;

    /** Intended for macro usage. */
    public CubeMid( ArmWristSubsystem wrist, ArmRotationSubsystem armRot) {

        this.arm = armRot;
        this.wrist = wrist;
        addRequirements(arm, wrist);
    }

    @Override
    public void execute() {

        arm.RotateArmToDeg(80);
        wrist.setWristToPosition(21000);

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