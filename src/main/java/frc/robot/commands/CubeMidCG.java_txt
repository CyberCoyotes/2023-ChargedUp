/*--------------------------------------------------------
*
* Run Time: Original            2.75 = 0.75 + 0.25 + 0.75 + 0.75 + 0.25
* Run Time: Test Times      1.50 = 0.50 + 0.50 + 0.50
* 
*--------------------------------------------------------*/

package frc.robot.commands;

import frc.robot.subsystems.ArmRotationSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.ArmWristSubsystem;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class CubeMidCG extends SequentialCommandGroup{

    public CubeMidCG
    (ArmRotationSubsystem armSub, ArmWristSubsystem wristSub, IntakeSubsystem intakeSub) {
        addCommands(
            new RotateArmToArg(armSub, 80).withTimeout(.5)
            // , new WaitCommand(0.25)    
            , new WristToArg(wristSub, 21000).withTimeout(.5)
            , new SetIntakeCone(intakeSub).withTimeout(.5)
            // , new WaitCommand(0.25) 
        );
    }
    @Override
    public InterruptionBehavior getInterruptionBehavior() {
        return InterruptionBehavior.kCancelSelf;
    }
}