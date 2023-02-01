package frc.robot.commands;

import com.pathplanner.lib.PathConstraints;
import com.pathplanner.lib.PathPlanner;
import com.pathplanner.lib.PathPlannerTrajectory;
import com.pathplanner.lib.PathPlannerTrajectory.PathPlannerState;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.Constants.Swerve;

public class PathTrial_Basic {
    // This will load the file "Example Path.path" and generate it with a max
    // velocity of 2 m/s and a max acceleration of 1 m/s^2
    PathPlannerTrajectory PathTrial_1 = PathPlanner.loadPath("PathTrial_1", new PathConstraints(2, 1));

    // This trajectory can then be passed to a path follower such as a
    // PPSwerveControllerCommand
    // Or the path can be sampled at a given point in time for custom path following

    // Sample the state of the path at 1.2 seconds
    PathPlannerState exampleState = (PathPlannerState) PathTrial_1.sample(1.2);

    /* 
    public PathTrial_Basic (Swerve Swerve) {
        addCommands(
            new InstantCommand(() -> 
        )
    }
    */

    // FIXME Print the velocity at the sampled time
    // System.out.println(exampleState.velocityMetersPerSecond);

}
