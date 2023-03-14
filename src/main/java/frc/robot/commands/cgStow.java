/*--------------------------------------------------------
*
*
*--------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Constants;
import frc.robot.Constants.Arm;
import frc.robot.subsystems.ArmExtensionSubsystem;
import frc.robot.subsystems.ArmSubsystem;
import frc.robot.subsystems.IntakeSubsystemV2;
import frc.robot.subsystems.WristSubsystem;

public class cgStow extends SequentialCommandGroup {
    
    public cgStow
    (ArmSubsystem armSub, ArmExtensionSubsystem armExtSub, WristSubsystem wristSub, IntakeSubsystemV2 intakeSub) {
        addCommands( 
        // , new ArmExtendToArg(armExtSub, 0)
        // , new WaitCommand(0.5) // FIXME excessive for testing                 
        // , new WristToArg(wristSub, Constants.WRIST_POS_LEVEL)
        // , new WaitCommand(0.5) // FIXME excessive for testing
        // , new RotateArmArg(armSub, 10) // FIXME excessive for testing
            
         );
    
    }

}