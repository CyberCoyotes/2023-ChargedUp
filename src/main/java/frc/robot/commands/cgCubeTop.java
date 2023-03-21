/*--------------------------------------------------------
*
* Deploy Cube to Mid Level
* 
*--------------------------------------------------------*/

package frc.robot.commands;

import frc.robot.subsystems.ArmExtensionSubsystem;
import frc.robot.subsystems.ArmSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.WristSubsystem;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

import edu.wpi.first.wpilibj2.command.WaitCommand;


public class cgCubeTop extends SequentialCommandGroup{

    public cgCubeTop
    (ArmSubsystem armSub, ArmExtensionSubsystem armExtSub, WristSubsystem wristSub, IntakeSubsystem intakeSub) {
        addCommands(
            new RotateArmArg(armSub, 90).withTimeout(.75)
            , new WaitCommand(0.25)
            , new ArmExtendToArg(armExtSub, ()->(9500)).withTimeout(1.5)
            , new WaitCommand(0.25)            
            , new WristToArg(wristSub, 11000).withTimeout(.75)
            , new SetIntakeCone(intakeSub).withTimeout(.75) // deploys a cube
            , new WaitCommand(0.25) 
        );
    }
    @Override
    public InterruptionBehavior getInterruptionBehavior() {
        return InterruptionBehavior.kCancelSelf;
    }
}