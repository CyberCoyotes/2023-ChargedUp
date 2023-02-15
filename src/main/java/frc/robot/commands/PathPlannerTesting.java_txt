/* 
 * 2023 TAZ
 * PathPlannerTesting.java 
*/

package frc.robot.commands;

import java.util.function.Consumer;
import java.util.function.Supplier;

import com.pathplanner.lib.PathPlanner;
import com.pathplanner.lib.PathPlannerTrajectory;
import com.pathplanner.lib.PathPlannerTrajectory.PathPlannerState;
import com.pathplanner.lib.commands.PPSwerveControllerCommand;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.Constants;
import frc.robot.subsystems.Swerve;;

public class PathPlannerTesting {
    private Swerve swerve;

    public PathPlannerTesting(Swerve swerve) {
        this.swerve = swerve;
    }
public Command Generate()
{
    PathPlannerTrajectory trajectory = PathPlanner.loadPath("New Path", 2, 1);
    
    Consumer<SwerveModuleState[]> consumah =  (swerve::setModuleStates);
    Supplier<Pose2d> suppliah = () -> swerve.getPose();
    var command =  new PPSwerveControllerCommand(
                trajectory,
                suppliah, // Functional interface to feed supplier
                Constants.Swerve.swerveKinematics,
        
                // Position controllers
                Constants.Swerve.xPIDController,
                Constants.Swerve.yPIDController,
                Constants.AutoConstants.thetaProfiledPID,
                consumah,
                swerve);
    return command;

}
}
