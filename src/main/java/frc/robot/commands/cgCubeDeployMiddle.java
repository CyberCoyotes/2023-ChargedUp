package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.subsystems.ArmExtensionSubsystem;
import frc.robot.subsystems.ArmSubsystem;
import frc.robot.subsystems.ClawSubsystem;

public class cgCubeDeployMiddle extends SequentialCommandGroup {

    public cgCubeDeployMiddle(ArmSubsystem m_ArmSub, ArmExtensionSubsystem m_ArmExtSub, ClawSubsystem m_ClawSub) {

        // FIXME Needs testing and some actual numbers
        addCommands(
                new SetClawClose2(m_ClawSub),
                new RotateArmDeploy(m_ArmSub), // Rotate the arm to approximately 90 degrees. Determine experimentally
                new SetArmExtend(m_ArmExtSub), // Extend the arm 6000 ticks?. Determine experimentally
                new RotateArmDeploy(m_ArmSub), // Rotate the arm to approximately 90 degrees (again to compensate for sag) . Determine experimentally
                new SetClawOpen2(m_ClawSub), // open the claw
                new WaitCommand(.15), // short pause
                new SetClawClose2(m_ClawSub), // open the claw
                new SetArmIn(m_ArmExtSub), // Retract the arm same as extension ticks
                new RotateArmIntake(m_ArmSub) // Rotate arm back to intake position 0 degrees or shorter 20 degrees
                // move on to drive phase

        ); // end of commands
    }
}