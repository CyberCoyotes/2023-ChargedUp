package frc.robot.autos;

import java.util.ArrayList;

import com.pathplanner.lib.PathConstraints;
import com.pathplanner.lib.PathPlanner;
import com.pathplanner.lib.PathPlannerTrajectory;

public class PPExample_AutoBuilder {
    // This will load the file "CableCube2.path" and generate it with a max velocity of 4 m/s and a max acceleration of 3 m/s^2
// for every path in the group
ArrayList<PathPlannerTrajectory> pathGroup1 = PathPlanner.loadPathGroup("CableCube2", new PathConstraints(4, 3));

// This will load the file "CableCube2.path" and generate it with different path constraints for each segment
ArrayList<PathPlannerTrajectory> pathGroup2 = PathPlanner.loadPathGroup(
    "CableCube2", 
    new PathConstraints(4, 3), 
    new PathConstraints(2, 2), 
    new PathConstraints(3, 3));
    
}
