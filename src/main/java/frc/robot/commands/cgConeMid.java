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

    // Current run time is 3.65 seconds with overly long pauses for testing purposes.
    public cgConeMid
    (ArmSubsystem armSub, ArmExtensionSubsystem armExtSub, WristSubsystem wristSub, IntakeSubsystem intakeSub) {
        addCommands(
            // Rotate arm to an experimentally determined position. Approximately 90 degrees absolute position
            new RotateArmMid(armSub).withTimeout(.75) 
            , new WaitCommand(0.2)
            // Extend arm to the an experimentally determined position.
            // FIXME This is an underestimate for testing purposes.
            , new ArmExtendToArg(armExtSub, ()->(Constants.ARM_EXTENT_LIMIT*(4/4))).withTimeout(2)
            , new WaitCommand(0.2)
            // Set wrist to an experimentally determinded position. See "WRIST_POS_MID"
            , new WristToArg(wristSub, Constants.WRIST_POS_MID).withTimeout(0.5)
            // Robot and arm position can now be adjusted for out take of the cone
        );
    }
}