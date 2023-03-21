package frc.robot.commands;

import java.util.function.BooleanSupplier;

import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
// import frc.robot.commands.cgCubeMiddle;
import frc.robot.subsystems.ArmExtensionSubsystem;
import frc.robot.subsystems.ArmSubsystem;
import frc.robot.subsystems.Swerve;

public class cgCubeMid_ver2 extends SequentialCommandGroup {
    public cgCubeMid_ver2(ArmSubsystem armSubsystem, ArmExtensionSubsystem m_extend, Swerve s_Swerve, BooleanSupplier robotCentric) {

        addRequirements(armSubsystem, m_extend, s_Swerve);

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

        addCommands(
        new cgCubeMid_ver2(armSubsystem, m_extend, null, null), 
        new ParallelDeadlineGroup(new WaitCommand(2.6), driveCommand ));
        ;
    }
}
