package frc.robot.autos;

import java.util.function.BooleanSupplier;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.TeleopSwerve;
import frc.robot.subsystems.Swerve;

public class Taxi extends SequentialCommandGroup {
    public Taxi(Swerve s_Swerve, BooleanSupplier robotCentric) {

        addRequirements(s_Swerve);

        short polarity = 1;
        double power = .2;
        double seconds = 2;// double seconds = inches * Constants.AutoConstants.AUTON_40_PERCENT_MULTIPLIER;
        final float input = (float) (polarity * power);
        Command driveCommand = new TeleopSwerve(
            s_Swerve,
            () -> input,
            () -> 0,
            () -> 0,
            () -> robotCentric.getAsBoolean(),
            () -> false);
        
        addCommands(
            // This originally had 2.0 seconds, the Taxi had 2.6 seconds
            driveCommand.withTimeout(seconds));
        

        // addCommands(
        // new cgCubeDeployLow(armSubsystem, m_extend, m_claw), 
        // new ParallelDeadlineGroup(new WaitCommand(2.6), driveCommand ));
    }
}
