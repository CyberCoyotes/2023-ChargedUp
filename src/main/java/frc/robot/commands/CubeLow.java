/*--------------------------------------------------------
*
* Run Time: Original            2.5 = 1.0 + 1.5
* Run Time: TODO Test times     1.0 = 0.5 + 0.5
*--------------------------------------------------------*/

package frc.robot.commands;

import frc.robot.subsystems.ArmExtensionSubsystem;
import frc.robot.subsystems.ArmRotationSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.ArmWristSubsystem;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class CubeLow extends SequentialCommandGroup{

    public CubeLow
    (ArmRotationSubsystem armSub, ArmExtensionSubsystem armExtSub, ArmWristSubsystem wristSub, IntakeSubsystem intakeSub) {
        addCommands(
            new RotateArmToArg(armSub, 30).withTimeout(0.5) // TODO Determine experimentally. Previous (1.0)
            , new SetIntakeCone(intakeSub).withTimeout(0.5) // TODO Experimentally determine optimal timeout. Previous (1.5) // Deposits a cube low
            // , new WaitCommand(0.2) 
        );
    }
}