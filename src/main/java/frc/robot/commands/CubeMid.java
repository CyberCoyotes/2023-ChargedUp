package frc.robot.commands;

import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import edu.wpi.first.wpilibj2.command.RepeatCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.subsystems.ArmRotationSubsystem;
import frc.robot.subsystems.ArmWristSubsystem;
import frc.robot.subsystems.IntakeSubsystem;

public class CubeMid extends CommandBase {

    // lesson learned: command groups aren't always the answer

    // somewhere in a magical text file i have the values we like. To get them,
    //// solve my riddles three

    private ArmRotationSubsystem arm;
    private ArmWristSubsystem wrist;
    private IntakeSubsystem intake;

    /** Intended for macro usage. */
    public CubeMid( ArmRotationSubsystem armRot,ArmWristSubsystem wrist, IntakeSubsystem intakeSub) {

        this.arm = armRot;
        this.wrist = wrist;
        this.intake = intakeSub;
        addRequirements(arm, wrist);
    }

    @Override
    public void execute() {

        arm.RotateArmToDeg(80);
        wrist.setWristToPosition(21000);
        intake.SetDriveIntake();

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
