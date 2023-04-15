package frc.robot.commands;

import javax.sound.midi.Sequence;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.ArmExtensionSubsystem;
import frc.robot.subsystems.ArmRotationSubsystem;
import frc.robot.subsystems.ArmWristSubsystem;
import frc.robot.subsystems.IntakeSubsystem;

public class PickupGroundCubeV2 extends SequentialCommandGroup{

    public PickupGroundCubeV2(ArmRotationSubsystem armSub, ArmWristSubsystem wristSub, IntakeSubsystem intakeSub, ArmExtensionSubsystem extend) {
        
        

        addCommands
        (
            new ArmSetpoint(extend, armSub, wristSub,   extend.GetPosition(), 32, 24130/2)
                .WithAllowedErrors(100, 2,2000),
            new ArmSetpoint(extend, armSub, wristSub,   9420, 36, 24130),
            new SetIntakeCube(intakeSub).withTimeout(2)
        );
    }
    @Override
    public InterruptionBehavior getInterruptionBehavior() {
        return InterruptionBehavior.kCancelSelf;
    }

}
