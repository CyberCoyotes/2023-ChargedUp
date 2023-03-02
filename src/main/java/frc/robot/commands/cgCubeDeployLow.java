package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.subsystems.ArmExtensionSubsystem;
import frc.robot.subsystems.ArmSubsystem;
import frc.robot.subsystems.ClawSubsystem;

public class cgCubeDeployLow extends SequentialCommandGroup {

    public cgCubeDeployLow(ArmSubsystem m_ArmSub, ArmExtensionSubsystem m_ArmExtSub, ClawSubsystem m_ClawSub) {

        // FIXME Needs testing and some actual numbers
        addCommands(
                new RotateArmManual(m_ArmSub, null), // Pop out arm 20 degrees
                new WaitCommand(.15), // short pause
                new SetClawOpen2(m_ClawSub), // open the claw
                new WaitCommand(.15), // short pause
                new SetClawClose2(m_ClawSub) // open the claw
                // move on to drive phase

        ); // end of commands
    }
}