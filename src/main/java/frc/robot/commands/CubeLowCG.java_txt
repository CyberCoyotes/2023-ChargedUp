/*--------------------------------------------------------
*
* Run Time: Original            2.5 = 1.0 + 1.5
* Run Time: Test times     1.0 = 0.5 + 0.5
*--------------------------------------------------------*/

package frc.robot.commands;

import frc.robot.subsystems.ArmExtensionSubsystem;
import frc.robot.subsystems.ArmRotationSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.ArmWristSubsystem;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class CubeLowCG extends SequentialCommandGroup{

    public CubeLowCG
    (ArmRotationSubsystem armSub, ArmExtensionSubsystem armExtSub, ArmWristSubsystem wristSub, IntakeSubsystem intakeSub) {
        addCommands(
            new RotateArmToArg(armSub, 30).withTimeout(0.5) // Determine experimentally. Previous (1.0) Seemed to work at 4/4 practice
            , new SetIntakeCone(intakeSub).withTimeout(0.5) // Experimentally determine optimal timeout. Previous (1.5) Seemed to work at 4/4 practice 
        );
    }
}