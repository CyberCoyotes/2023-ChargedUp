/*--------------------------------------------------------
*
* 
*--------------------------------------------------------*/

package frc.robot.commands;

import frc.robot.Constants;
import frc.robot.subsystems.ArmExtensionSubsystem;
import frc.robot.subsystems.ArmSubsystem;
import frc.robot.subsystems.IntakeSubsystemV2;
import frc.robot.subsystems.WristSubsystem;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

import edu.wpi.first.wpilibj2.command.WaitCommand;


public class cgConeLow extends SequentialCommandGroup{

    public cgConeLow
    (ArmSubsystem armSub, ArmExtensionSubsystem armExtSub, WristSubsystem wristSub, IntakeSubsystemV2 intakeSub) {
        addCommands(
            new RotateWristLevel(wristSub).withTimeout(1)
            , new RotateArmArg(armSub, -30).withTimeout(.75)
            , new RotateWristLevel(wristSub).withTimeout(1)
            
            // , new WaitCommand(0.25) // FIXME excessive for testing
            , new SetIntakeCube(intakeSub).withTimeout(1.5) // deploys a cone)
            // , new WaitCommand(0.5) // FIXME excessive for testing 
            //,  new cgStow(armSub, armExtSub, wristSub, intakeSub)
            // , new WristToArg(wristSub, Constants.WRIST_POS_LEVEL)
            // , new WaitCommand(0.5) // FIXME excessive for testing
            // , new RotateArmArg(armSub, 10)
        );
    }
}