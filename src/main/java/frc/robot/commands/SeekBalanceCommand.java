package frc.robot.commands;

import frc.lib.math.CLAWTransform;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.filter.Debouncer;
import edu.wpi.first.math.filter.Debouncer.DebounceType;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Swerve;

public class SeekBalanceCommand extends CommandBase {
    
    /**
     * TESTING:
     * 1. Negating yaw offset correction
     * 2. Adjusting drivePID
     */
    
    
    
    private static final double PITCH_SETPOINT_ERROR_DEG = 1;
    
    private Swerve swerveDrive;
    
    private final PIDController drivePID = new PIDController(0,0,0);
    
    private final Debouncer balancedDebouncer = new Debouncer(1, DebounceType.kRising);
    
    private Rotation2d initialRobotYaw;
    
    private final CLAWTransform yawOffsetToCorrectionTurn =
        // Wrap degrees from -180 to +180
        ((CLAWTransform)(deg -> {
            while (deg > 180) deg -= 360;
            while (deg < -180) deg += 360;
            return deg;
        }))
        
        .then(CLAWTransform.NEGATE)
        
        // Apply the corrective turn
        //basically gets the current rotation and demolishes its size, resulting in a direction to go to negate offset rotation.
        .then(offsetDeg -> offsetDeg / 30.)
        .then(CLAWTransform.clamp(-1, 1))
        .then(v -> 3.5*v);
    
    public SeekBalanceCommand (Swerve swerveDrive) {
        this.swerveDrive = swerveDrive;
        addRequirements(swerveDrive);
    }
    
    @Override
    public void initialize () {
        swerveDrive.StopModules();
        initialRobotYaw = swerveDrive.getYaw();
        
        drivePID.reset();
        
        drivePID.setTolerance(1);
    }
    
    @Override
    public void execute () {
     
        double pitch = swerveDrive.GetPitch();
        
        double speed = drivePID.calculate(pitch);
        if (Math.abs(pitch) < PITCH_SETPOINT_ERROR_DEG) speed = 0;
        
        double turnSpeed = yawOffsetToCorrectionTurn.apply(swerveDrive.getYaw().minus(initialRobotYaw).getDegrees());
        
        swerveDrive.setModuleStates(new ChassisSpeeds(speed, 0, turnSpeed));
    }
    
    @Override
    public void end (boolean interrupted) {
        swerveDrive.StopModules();
    }
    
    @Override
    public boolean isFinished () {
        return balancedDebouncer.calculate(Math.abs(swerveDrive.GetPitch()) < PITCH_SETPOINT_ERROR_DEG);
    }
    
}