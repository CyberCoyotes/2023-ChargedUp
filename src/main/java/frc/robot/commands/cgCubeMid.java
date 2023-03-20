/*--------------------------------------------------------
*
* Deploy Cube to Mid Level
* 
*--------------------------------------------------------*/

package frc.robot.commands;

import frc.robot.Constants;
import frc.robot.subsystems.ArmExtensionSubsystem;
import frc.robot.subsystems.ArmSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.WristSubsystem;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

import edu.wpi.first.wpilibj2.command.WaitCommand;


public class cgCubeMid extends SequentialCommandGroup{

    public cgCubeMid
    (ArmSubsystem armSub, ArmExtensionSubsystem armExtSub, WristSubsystem wristSub, IntakeSubsystem intakeSub) {
        addCommands(
            new RotateArmMid(armSub).withTimeout(.75) // because I couldn't get smarter approaches to work
            , new WaitCommand(0.5) // FIXME excessive for testing 
            , new ArmExtendToArg(armExtSub, ()->(Constants.ARM_EXTENT_LIMIT * 1/2))
            , new WaitCommand(0.5) // FIXME excessive for testing            
            , new WristToArg(wristSub, Constants.WRIST_POS_MID)
            , new WaitCommand(0.25) // FIXME excessive for testing
            , new SetIntakeCone(intakeSub).withTimeout(1) // deploys a cube
            , new WaitCommand(0.5) // FIXME excessive for testing 
            //,  new cgStow(armSub, armExtSub, wristSub, intakeSub)
            // , new WristToArg(wristSub, Constants.WRIST_POS_LEVEL)
            // , new WaitCommand(0.5) // FIXME excessive for testing
            // , new RotateArmArg(armSub, 10)
        );
    }
}