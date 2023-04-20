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

        arm.RotateArmToDeg(125 ); // Previously 110; drive team requested an increase
        wrist.SetToPosition(57558 - 2500); //51558 was alright, but we wanted it up

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
