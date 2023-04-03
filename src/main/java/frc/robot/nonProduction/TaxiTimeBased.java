package frc.robot.nonProduction;

import java.util.function.BooleanSupplier;

import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.commands.TeleopSwerve;
import frc.robot.subsystems.Swerve;

public class TaxiTimeBased extends SequentialCommandGroup {
    public TaxiTimeBased(Swerve s_Swerve, BooleanSupplier robotCentric) {

        addRequirements(s_Swerve);

        short polarity = 1;
        double power = .2;
        double seconds = 2.6;// double seconds = inches * Constants.AutoConstants.AUTON_40_PERCENT_MULTIPLIER;
        final float input = (float) (polarity * power);
        var driveCommand = new TeleopSwerve(
            s_Swerve,
            () -> input,
            () -> 0,
            () -> 0,
            () -> robotCentric.getAsBoolean(),
            () -> false);

        addCommands(
        new ParallelDeadlineGroup(new WaitCommand(seconds), driveCommand ));
        ;
    }
}
