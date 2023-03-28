package frc.robot.autos;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import edu.wpi.first.wpilibj2.command.RepeatCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.commands.RotateArmArg;
import frc.robot.commands.WristToArg;
import frc.robot.subsystems.ArmSubsystem;
import frc.robot.subsystems.WristSubsystem;

public class ConeMidAuto extends SequentialCommandGroup {
    

    // 54719 is wrist for cone mid 
    // and 102 is arm rotation
    

    public ConeMidAuto(WristSubsystem wrist, ArmSubsystem armRot) {

        // WaitCommand deadline = new WaitCommand(5);
        Command armCommand = new RotateArmArg(armRot, 95).withTimeout(1.5);

        Command wristCommand = new WristToArg(wrist, 41558).withTimeout(1);

        addCommands( armCommand, wristCommand);
    }

}
