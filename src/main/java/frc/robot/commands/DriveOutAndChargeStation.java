package frc.robot.commands;

import java.util.function.BooleanSupplier;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.Swerve;

public class DriveOutAndChargeStation extends SequentialCommandGroup {
    short polarity = 1;
    double power = .4;
    double seconds = 3;
    // : 40% in a single direction for 1 second: ~51 inches        
    final float input = (float) (polarity * power);
    Command driveCommand;
    public DriveOutAndChargeStation(Swerve s_Swerve, BooleanSupplier robotCentric) 
    {
        
        addRequirements(s_Swerve);
        
        driveCommand = new TeleopSwerve(
            s_Swerve,
            () -> input,
            () -> 0,
            () -> 0,
            () -> robotCentric.getAsBoolean(),
            () -> false);
    
        
        addCommands
        (
           
            driveCommand,
            new SeekBeginofChargeStation(s_Swerve),
            new SeekBalanceCommand(s_Swerve, )
        );
    }
}
