/*--------------------------------------------------------
*
* 
*--------------------------------------------------------*/

package frc.robot.commands;

import frc.robot.subsystems.ArmExtensionSubsystem;
import frc.robot.subsystems.ArmRotationSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.ArmWristSubsystem;
import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

import edu.wpi.first.wpilibj2.command.WaitCommand;


public class ConeLow extends SequentialCommandGroup{

    public ConeLow
    (ArmRotationSubsystem armSub, ArmExtensionSubsystem armExtSub, ArmWristSubsystem wristSub, IntakeSubsystem intakeSub) {
        addCommands(
            new RotateArmToArg(armSub, -30).withTimeout(1)
            , new WaitCommand(0.25)
            // Ideally the dead condition would be when the wrist achieves its angle
            , new ParallelDeadlineGroup( // Use ParallelRaceGroup if this doesn't work 
                new WristToArg(wristSub, 19000)
                , new SetIntakeCube(intakeSub).withTimeout(1)) // deploys a cone (trust me)
                , new WaitCommand(0.5) 
        );
    }
}