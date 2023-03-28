/*--------------------------------------------------------
*
* 
*--------------------------------------------------------*/

package frc.robot.commands;

import frc.robot.subsystems.ArmExtensionSubsystem;
import frc.robot.subsystems.ArmSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.WristSubsystem;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

import edu.wpi.first.wpilibj2.command.WaitCommand;


public class cgCubeLow extends SequentialCommandGroup{

    public cgCubeLow
    (ArmSubsystem armSub, ArmExtensionSubsystem armExtSub, WristSubsystem wristSub, IntakeSubsystem intakeSub) {
        addCommands(
            new RotateArmToArg(armSub, 30).withTimeout(1)
            , new SetIntakeCone(intakeSub).withTimeout(1.5) // deploys a cube
            , new WaitCommand(0.2) 
        );
    }
}