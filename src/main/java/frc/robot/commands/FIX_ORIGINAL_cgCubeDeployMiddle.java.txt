package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.ScheduleCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Constants.Arm;
import frc.robot.subsystems.ArmExtensionSubsystem;
import frc.robot.subsystems.ArmSubsystem;
import frc.robot.subsystems.ClawSubsystem;

public class cgCubeDeployMiddle extends SequentialCommandGroup {



    public cgCubeDeployMiddle(ArmSubsystem m_ArmSub, ArmExtensionSubsystem m_ArmExtSub, ClawSubsystem m_ClawSub) {

        // FIXME Needs testing and some actual numbers
        addCommands(
                new SetClawClose2(m_ClawSub),
                //:Sets the rotation
                
                //ScheduleCommand also finishes instantly(to not "clog" the group and halt progress) while also scheduling the other command.
                //? new ScheduleCommand( new SetArmRotationCommand(90, m_ArmSub)), // Rotate the arm to approximately 90 degrees. 
                //:wait for a bit before we begin extending
                //? new WaitCommand(2),
                
                //Same as before; ScheduleCommand also finishes instantly(to not "clog" the group and halt progress) while also scheduling the other command.
                //:Sets the Extent
                new ScheduleCommand(new SetArmExtentCommand(9500, m_ArmExtSub)),
                // This isn't a command, its a method in ArmSubstem
                // Rotate the arm to approximately 90 degrees (again to compensate for
                                               // sag) . Determine experimentally

                                               //todo arm auto open and full deposit
                new SetClawOpen2(m_ClawSub) // open the claw
                // new WaitCommand(.35), // short pause
                //todo make this a better stow command
                //? new ScheduleCommand(new SetArmExtentCommand(200, m_ArmExtSub)), // Retract the arm same as extension ticks
                //wait for a bit, so the robot doesn't die try to retract
                // new WaitCommand(2),
                //? new ScheduleCommand( new SetArmRotationCommand(Arm.ARM_STOW_ROTATION_DEG, m_ArmSub)) // Rotate the arm to approximately 90 degrees. 
                // In auton, this would move on to drive phase
                //for stowing, we'll wait for the extent read to be low enough                //todo shuffleboard time thingy
//TODO: test our stow command
        ); // end of commands
    }
}