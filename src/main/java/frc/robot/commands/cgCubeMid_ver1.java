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


public class cgCubeMid_ver1 extends SequentialCommandGroup{

    public cgCubeMid_ver1
    (ArmSubsystem armSub, ArmExtensionSubsystem armExtSub, WristSubsystem wristSub, IntakeSubsystem intakeSub) {
        addCommands(
            new RotateArmMid(armSub).withTimeout(.75)
            , new WaitCommand(0.5) 
            , new ArmExtendToArg(armExtSub, ()->(Constants.ARM_EXTENT_LIMIT * 1/2))
            , new WaitCommand(0.5)           
            , new WristToArg(wristSub, Constants.WRIST_POS_MID)
            , new WaitCommand(0.25)
            , new SetIntakeCone(intakeSub).withTimeout(1) // deploys a cube
            , new WaitCommand(0.5)
        );
    }
}