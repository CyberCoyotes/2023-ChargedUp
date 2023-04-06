/*--------------------------------------------------------
*
* Pickup a Cube from the Floor
* 
*--------------------------------------------------------*/

package frc.robot.commands;

import frc.robot.subsystems.ArmExtensionSubsystem;
import frc.robot.subsystems.ArmRotationSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.ArmWristSubsystem;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class GroundCubePickup extends SequentialCommandGroup{

    public GroundCubePickup
    (ArmRotationSubsystem armSub, ArmWristSubsystem wristSub, IntakeSubsystem intakeSub, ArmExtensionSubsystem extend) {
        addCommands(
            new RotateArmToArg(armSub, 36).withTimeout(.25),
            new WristToArg(wristSub, 23703).withTimeout(.7),
            new ArmExtendToArg(extend, () -> 8220).withTimeout(1.6),
            new SetIntakeCube(intakeSub).withTimeout(0.5)
        );
    }
    @Override
    public InterruptionBehavior getInterruptionBehavior() {
        return InterruptionBehavior.kCancelSelf;
    }
}