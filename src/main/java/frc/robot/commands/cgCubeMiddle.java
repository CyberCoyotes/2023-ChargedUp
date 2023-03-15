/*--------------------------------------------------------
*
* Deploy Cube to Mid Level
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


public class cgCubeMiddle extends SequentialCommandGroup{

    public cgCubeMiddle
    (ArmSubsystem armSub, ArmExtensionSubsystem armExtSub, WristSubsystem wristSub, IntakeSubsystemV2 intakeSub) {
        addCommands(
            new RotateArmArg(armSub, -60).withTimeout(1)
            , new WaitCommand(0.5) 
            //, new ArmExtendToArg(armExtSub, (Constants.ARM_EXTENT_LIMIT * 1/2))
            // , new WaitCommand(0.5) // FIXME excessive for testing            
            // , new WristToArg(wristSub, Constants.WRIST_POS_MID)
            , new SetIntakeCone(intakeSub).withTimeout(1) // deploys a cube 
            //,  new cgStow(armSub, armExtSub, wristSub, intakeSub)
            // , new WristToArg(wristSub, Constants.WRIST_POS_LEVEL)
            // , new WaitCommand(0.5) // FIXME excessive for testing
            , new RotateArmArg(armSub, -10).withTimeout(1)
        );
    }
}