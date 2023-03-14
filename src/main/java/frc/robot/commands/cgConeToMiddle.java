/*--------------------------------------------------------
*
* Deploy Cone to Mid Level
* Wrist Pos = 72250;
* Arm Rot = -90;
* Arm Extension = full;
* 
*--------------------------------------------------------*/


package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.ArmExtensionSubsystem;
import frc.robot.subsystems.ArmSubsystem;
import frc.robot.subsystems.IntakeSubsystemV2;
import frc.robot.subsystems.WristSubsystem;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;


public class cgConeToMiddle extends SequentialCommandGroup{

    public cgConeToMiddle
    (ArmSubsystem armSub, ArmExtensionSubsystem armExtSub, WristSubsystem wristSub, IntakeSubsystemV2 intakeSub) {
        addCommands(
            new RotateArmArg(armSub, -90)
                , new WaitCommand(0.5) // FIXME excessive for testing
            /*     
            , new ArmExtendToArg(armExtSub, Constants.ARM_EXTENT_LIMIT)
                , new WaitCommand(0.5) // FIXME excessive for testing
            , new WristToArg(wristSub, Constants.WRIST_POS_MID)
                , new WaitCommand(0.5) // FIXME excessive for testing
            , new SetIntakeCube(intakeSub) // deploys a cone
            , new WaitCommand(0.5)
            , new WristToArg(wristSub, Constants.WRIST_POS_LEVEL)
                , new WaitCommand(0.5) // FIXME excessive for testing
            , new RotateArmArg(armSub, 10)
            */
        );
    }
}