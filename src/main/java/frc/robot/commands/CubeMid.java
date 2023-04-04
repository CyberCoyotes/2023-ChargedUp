/*--------------------------------------------------------
*
* Run Time: Original            2.75 = 0.75 + 0.25 + 0.75 + 0.75 + 0.25
* Run Time:TODO Test Times      1.50 = 0.50 + 0.50 + 0.50
* 
*--------------------------------------------------------*/

package frc.robot.commands;

import frc.robot.subsystems.ArmRotationSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.ArmWristSubsystem;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

import edu.wpi.first.wpilibj2.command.WaitCommand;


public class CubeMid extends SequentialCommandGroup{

    public CubeMid
    (ArmRotationSubsystem armSub, ArmWristSubsystem wristSub, IntakeSubsystem intakeSub) {
        addCommands(
            new RotateArmToArg(armSub, 80).withTimeout(0.50) // TODO 0.75 Test this time
            // , new WaitCommand(0.10) // TODO Test this time    
            , new WristToArg(wristSub, 21000).withTimeout(0.50) // TODO Test this time
            , new SetIntakeCone(intakeSub).withTimeout(0.50) // TODO Test this time
            // , new WaitCommand(0.25) 
        );
    }
    @Override
    public InterruptionBehavior getInterruptionBehavior() {
        return InterruptionBehavior.kCancelSelf;
    }
}