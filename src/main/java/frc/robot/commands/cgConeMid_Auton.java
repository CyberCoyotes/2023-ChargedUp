/*--------------------------------------------------------
*
* Same as "cgConeMid" but automatically outtakes the cone.
* 
*--------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Constants;
import frc.robot.subsystems.ArmExtensionSubsystem;
import frc.robot.subsystems.ArmSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.WristSubsystem;


public class cgConeMid_Auton extends SequentialCommandGroup{

    public cgConeMid_Auton
    (ArmSubsystem armSub, ArmExtensionSubsystem armExtSub, WristSubsystem wristSub, IntakeSubsystem intakeSub) {
        addCommands(
            new cgConeMid(armSub, armExtSub, wristSub, intakeSub)
            // These are for Autonomous
            , new WaitCommand(0.25)
            // Outtake a Cone which is the same as Intake a Cube
            , new SetIntakeCube(intakeSub).withTimeout(0.75)
            , new WaitCommand(0.25)
        );
    }
}