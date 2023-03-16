/*--------------------------------------------------------
*
* Deploy Cube to Mid Level
* 
*--------------------------------------------------------*/

package frc.robot.commands;

import frc.robot.Constants;
import frc.robot.subsystems.ArmExtensionSubsystem;
import frc.robot.subsystems.ArmSubsystem;
import frc.robot.subsystems.IntakeSubsystemV2;
import frc.robot.subsystems.WristSubsystem;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

import edu.wpi.first.wpilibj2.command.WaitCommand;


public class cgCubeMiddle extends SequentialCommandGroup{

    public cgCubeMiddle
    (ArmSubsystem armSub, ArmExtensionSubsystem armExtSub, WristSubsystem wristSub, IntakeSubsystemV2 intakeSub) {
        addCommands(
            new RotateArmArg(armSub, -75).withTimeout(1)
            , new SetIntakeCone(intakeSub).withTimeout(1) // deploys a cube  (trust me)
            , new WaitCommand(0.5)
            , new RotateArmArg(armSub, -5).withTimeout(1)
        );
    }
}