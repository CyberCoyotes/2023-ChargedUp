/*--------------------------------------------------------
*
* "cgCubeTop.java"
* 
*--------------------------------------------------------*/

package frc.robot.commands;

import frc.robot.subsystems.ArmExtensionSubsystem;
import frc.robot.subsystems.ArmRotationSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.ArmWristSubsystem;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

import edu.wpi.first.wpilibj2.command.WaitCommand;

public class PickupGroundCube extends SequentialCommandGroup{

    public PickupGroundCube
    (ArmRotationSubsystem armSub, ArmWristSubsystem wristSub, IntakeSubsystem intakeSub, ArmExtensionSubsystem extend) {
        addCommands(
            new RotateArmToArg(armSub, 36).withTimeout(.25), //TODO Test
            new WristToArg(wristSub, 23703).withTimeout(.75), //TODO Test
            new ArmExtendToArg(extend, () -> 8220).withTimeout(1.5), // TODO Test 1.6 --> 1.5
            new SetIntakeCube(intakeSub).withTimeout(0.5) // TODO Test
            // Total time = 3 (s)

        );
    }
    @Override
    public InterruptionBehavior getInterruptionBehavior() {
        return InterruptionBehavior.kCancelSelf;
    }
}