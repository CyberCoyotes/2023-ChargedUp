/*--------------------------------------------------------
*
* Pickup a cube off the ground
* 
*--------------------------------------------------------*/

package frc.robot.nonProduction;

import frc.robot.commands.ArmExtendToArg;
import frc.robot.commands.RotateArmToArg;
import frc.robot.commands.SetIntakeCube;
import frc.robot.commands.WristToArg;
import frc.robot.subsystems.ArmExtensionSubsystem;
import frc.robot.subsystems.ArmRotationSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.ArmWristSubsystem;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class PickupGroundCubeV1 extends SequentialCommandGroup{

    public PickupGroundCubeV1
    (ArmRotationSubsystem armSub, ArmWristSubsystem wristSub, IntakeSubsystem intakeSub, ArmExtensionSubsystem extend) {
        addCommands(
            new RotateArmToArg(armSub, 36).withTimeout(0.20),     // TODO 0.25 -> 0.20 Test
            new WristToArg(wristSub, 24130).withTimeout(0.50), //TODO 0.75 -> 0.50 Test
            //added 1200 ~ 1 inch after second practice match
            new ArmExtendToArg(extend, () -> 9420).withTimeout(1.6), 

            new SetIntakeCube(intakeSub).withTimeout(1.0)
                                                                        // Total time = 3 (s)

        );
    }
    @Override
    public InterruptionBehavior getInterruptionBehavior() {
        return InterruptionBehavior.kCancelSelf;
    }
}