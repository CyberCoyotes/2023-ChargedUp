/* 
 * Original "DriveOutAndChargeStation.java"
*/

package frc.robot.nonProduction;

import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.SeekBalanceCommand;
import frc.robot.commands.SeekBeginofChargeStation;
import frc.robot.commands.TeleopSwerve;
import frc.robot.subsystems.Swerve;

public class CubeTaxiEngage extends SequentialCommandGroup {

    private double pTerm;
    private double dTerm;

        
    public void incrementPIDs(DoubleSupplier pIncrementer, DoubleSupplier dIncrementer)
    {
        this.pTerm += pIncrementer.getAsDouble();
        this.dTerm += dIncrementer.getAsDouble();
    }


    private DoubleSupplier pSup = new DoubleSupplier() {
        public double getAsDouble() {return pTerm;};}; //bro what are these semicolons
        
    private DoubleSupplier dSup = new DoubleSupplier() {
        public double getAsDouble() {return dTerm;};}; //bro what are these semicolons
        
    short polarity = 1;
    double power = .35;
    double seconds = 4;
    // : 40% in a single direction for 1 second: ~51 inches        
    final float input = (float) (polarity * power);
    Command driveCommand;
    public CubeTaxiEngage(Swerve s_Swerve, BooleanSupplier robotCentric) 
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
           
            driveCommand.withTimeout(seconds),
            new SeekBeginofChargeStation(s_Swerve),
            new SeekBalanceCommand(s_Swerve, pSup, dSup)
        );
      

    }

    
}
