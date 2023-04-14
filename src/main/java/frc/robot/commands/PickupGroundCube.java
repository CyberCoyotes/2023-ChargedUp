/*--------------------------------------------------------
*
* Pickup a cube off the ground
* 
*--------------------------------------------------------*/

package frc.robot.commands;

import frc.robot.subsystems.ArmExtensionSubsystem;
import frc.robot.subsystems.ArmRotationSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.ArmWristSubsystem;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class PickupGroundCube extends SequentialCommandGroup{

    public PickupGroundCube
    (ArmRotationSubsystem armSub, ArmWristSubsystem wristSub, IntakeSubsystem intakeSub, ArmExtensionSubsystem extend) {
        addCommands(
            new RotateArmToArg(armSub, 36).withTimeout(0.20),     // TODO 0.25 -> 0.20 Test
            new WristToArg(wristSub, 24130).withTimeout(0.50), //TODO 0.75 -> 0.50 Test
            //added 1200 ~ 1 inch after second practice match
            new ExtendToFloor(extend), 
            // TODO Test 1.6 --> 1.5
            // 8220+1200 = 9420

            new SetIntakeCube(intakeSub).withTimeout(0.5 + .5)//1       // TODO Test 0.50
                                                                        // Total time = 3 (s)

        );
    }
    @Override
    public InterruptionBehavior getInterruptionBehavior() {
        return InterruptionBehavior.kCancelSelf;
    }
}