/*--------------------------------------------------------
*
* 
*--------------------------------------------------------*/

package frc.robot.commands;

import frc.robot.Constants;
import frc.robot.subsystems.ArmExtensionSubsystem;
import frc.robot.subsystems.ArmSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.WristSubsystem;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

import edu.wpi.first.wpilibj2.command.WaitCommand;


public class cgConeLow extends SequentialCommandGroup{

    public cgConeLow
    (ArmSubsystem armSub, ArmExtensionSubsystem armExtSub, WristSubsystem wristSub, IntakeSubsystem intakeSub) {
        addCommands(
            new RotateArmArg(armSub, -30).withTimeout(1)
            , new WaitCommand(0.25) // FIXME excessive for testing

            // Ideally the dead condition would be when the wrist achieves its angle
            , new ParallelDeadlineGroup( // Use ParallelRaceGroup if this doesn't work 
                new WristToArg(wristSub, 19000)
                , new SetIntakeCube(intakeSub).withTimeout(1)) // deploys a cone (trust me)
            
            , new WaitCommand(0.5) // FIXME excessive for testing 
                    //,  new cgStow(armSub, armExtSub, wristSub, intakeSub)
            // , new WristToArg(wristSub, Constants.WRIST_POS_LEVEL)
            // , new WaitCommand(0.5) // FIXME excessive for testing
            // , new RotateArmArg(armSub, -5)
        );
    }
}