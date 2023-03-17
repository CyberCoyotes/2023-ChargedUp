package frc.robot.commands;

import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Swerve;

public class SeekBeginofChargeStation extends CommandBase 
{
    private Swerve s_swerve;

    /**A required error of 30 degrees on the pitch */
    private double errorRequirement = -19;
    public SeekBeginofChargeStation(Swerve swerve) {
        super();
        this.s_swerve = swerve;
        
    }
    
    @Override
    public void execute() {
        
        
        double speed = -.6;
        
        
        s_swerve.setModuleStates(new ChassisSpeeds(speed, 0, 0));
    
    }
    @Override
    public boolean isFinished() {
        return s_swerve.GetPitch() - errorRequirement < 3; //If the pitch is more than 5, we're done here
    }
}
