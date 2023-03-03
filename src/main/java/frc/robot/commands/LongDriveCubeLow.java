package frc.robot.commands;

import java.util.function.BooleanSupplier;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.subsystems.ArmExtensionSubsystem;
import frc.robot.subsystems.ArmSubsystem;
import frc.robot.subsystems.ClawSubsystem;
import frc.robot.subsystems.Swerve;

public class LongDriveCubeLow extends SequentialCommandGroup {
    public LongDriveCubeLow(ArmSubsystem armSubsystem, ArmExtensionSubsystem m_extend, ClawSubsystem m_claw, Swerve s_Swerve, BooleanSupplier robotCentric) {

        addRequirements(armSubsystem, m_extend, m_claw, s_Swerve);

        short polarity = 1;
        double power = .2;
        double seconds = 2;// double seconds = inches * Constants.AutoConstants.AUTON_40_PERCENT_MULTIPLIER;
        final float input = (float) (polarity * power);
        var driveCommand = new TeleopSwerve(
            s_Swerve,
            () -> input,
            () -> 0,
            () -> 0,
            () -> robotCentric.getAsBoolean(),
            () -> false);
        // return new ParallelDeadlineGroup(new WaitCommand(seconds), driveCommand);

        // return new ParallelDeadlineGroup(new WaitCommand(seconds), driveCommand);
        

        addCommands(
        new cgCubeDeployLow(armSubsystem, m_extend, m_claw), 
        new ParallelDeadlineGroup(new WaitCommand(2.6), driveCommand ));
        ;
    }
}
