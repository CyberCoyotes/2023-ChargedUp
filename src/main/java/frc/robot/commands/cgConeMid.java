/*--------------------------------------------------------
*
* Original "cgConeMiddle.java"
* Deploy Cone to Mid Level
* Wrist Pos = 72250;
* Arm Rot = -90;
* Arm Extension = full;
* 
*--------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Constants;
import frc.robot.subsystems.ArmExtensionSubsystem;
import frc.robot.subsystems.ArmSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.WristSubsystem;


public class cgConeMid extends SequentialCommandGroup{

    public cgConeMid
    (ArmSubsystem armSub, ArmExtensionSubsystem armExtSub, WristSubsystem wristSub, IntakeSubsystem intakeSub) {
        addCommands(
            new RotateArmMid(armSub).withTimeout(.75)
            , new WaitCommand(0.5) 
            , new ArmExtendToArg(armExtSub, ()->(Constants.ARM_EXTENT_LIMIT*(1/2))).withTimeout(1)
            , new WaitCommand(0.5)
            , new WristToArg(wristSub, Constants.WRIST_POS_MID)
            , new WaitCommand(0.25)
            , new SetIntakeCube(intakeSub).withTimeout(1) // deploys a cone)
            , new WaitCommand(0.5)
        );
    }
}