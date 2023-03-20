/*--------------------------------------------------------
*
* Deploy Cone to Mid Level
* Wrist Pos = 72250;
* Arm Rot = -90;
* Arm Extension = full;
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


public class cgConeMiddle extends SequentialCommandGroup{

    public cgConeMiddle
    (ArmSubsystem armSub, ArmExtensionSubsystem armExtSub, WristSubsystem wristSub, IntakeSubsystem intakeSub) {
        addCommands(
            new RotateArmMid(armSub).withTimeout(.75) // because I couldn't get smarter approaches to work
            , new WaitCommand(0.5) // FIXME excessive for testing 
            , new ArmExtendToArg(armExtSub, ()->(Constants.ARM_EXTENT_LIMIT*(1/2))).withTimeout(1)
            , new WaitCommand(0.5) // FIXME excessive for testing
            
            , new WristToArg(wristSub, Constants.WRIST_POS_MID)

            , new WaitCommand(0.25) // FIXME excessive for testing
            , new SetIntakeCube(intakeSub).withTimeout(1) // deploys a cone)
            , new WaitCommand(0.5) // FIXME excessive for testing 
        );
    }
}