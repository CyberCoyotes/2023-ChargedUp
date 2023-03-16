/*--------------------------------------------------------
*
* Stow Command Group
* Intake arm extension to home position 0
* Wrist rotates to Home position 0
* Arm rotates to Home position 0
*--------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Constants;
import frc.robot.subsystems.ArmExtensionSubsystem;
import frc.robot.subsystems.ArmSubsystem;
import frc.robot.subsystems.IntakeSubsystemV2;
import frc.robot.subsystems.WristSubsystem;

public class cgStow extends SequentialCommandGroup {
    
    public cgStow
    (ArmSubsystem armSub, ArmExtensionSubsystem armExtSub, WristSubsystem wristSub, IntakeSubsystemV2 intakeSub) {
        addCommands( 
        // new ArmExtendToArg(armExtSub, 0) //.withTimeout(2)
        // , new WaitCommand(2) // FIXME excessive for testing                 
         new WristToArg(wristSub, Constants.WRIST_POS_HOME).withTimeout(2) // Combine in parallel group with extension in to speed up
        , new WaitCommand(2) // FIXME excessive time for testing
        , new RotateArmArg(armSub, 5).withTimeout(2) // Tuck the arm into robot
            
         );
    
    }

}
