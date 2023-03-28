/*--------------------------------------------------------
*
* "cgCubeTop.java"
* 
*--------------------------------------------------------*/

package frc.robot.commands;

import frc.robot.subsystems.ArmExtensionSubsystem;
import frc.robot.subsystems.ArmSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.WristSubsystem;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

import edu.wpi.first.wpilibj2.command.WaitCommand;


public class CubeMidAuton extends SequentialCommandGroup{

    public CubeMidAuton
    (ArmSubsystem armSub, WristSubsystem wristSub, IntakeSubsystem intakeSub) {
        addCommands(
            new RotateArmToArg(armSub, 80).withTimeout(.75)
            , new WaitCommand(0.25)    
            , new WristToArg(wristSub, 21000).withTimeout(.75)
            , new SetIntakeCone(intakeSub).withTimeout(.75)
            , new WaitCommand(0.25) 
        );
    }
    @Override
    public InterruptionBehavior getInterruptionBehavior() {
        return InterruptionBehavior.kCancelSelf;
    }
}