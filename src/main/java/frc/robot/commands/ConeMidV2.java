package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.ArmRotationSubsystem;
import frc.robot.subsystems.ArmWristSubsystem;

public class ConeMidV2 extends SequentialCommandGroup {

    // lesson learned: command groups aren't always the answer

    // somewhere in a magical text file i have the values we like. To get them,
    //// solve my riddles three

    private ArmRotationSubsystem arm;
    private ArmWristSubsystem wrist;

    /** Intended for macro usage. */
    public ConeMidV2(ArmWristSubsystem wrist, ArmRotationSubsystem armRot) {

        this.arm = armRot;
        this.wrist = wrist;
        addRequirements(arm, wrist);

        //:important
//! arm.RotateArmToDeg(110 );
//!wrist.SetToPosition(51558 - 5000); //51558 was alright, but we wanted it up


        addCommands(
            new ArmSetpoint(null, armRot, wrist, 0, 0, 0)
            );
    }

  
    @Override
    public InterruptionBehavior getInterruptionBehavior() {
        return InterruptionBehavior.kCancelSelf;
    }

}
