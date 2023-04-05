package frc.robot.autos;

import java.util.List;

import com.pathplanner.lib.PathConstraints;
import com.pathplanner.lib.PathPlanner;
import com.pathplanner.lib.PathPlannerTrajectory;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.RobotContainer;

public class ppCube2 extends SequentialCommandGroup {
    public ppCube2() {
        List<PathPlannerTrajectory> pptList = PathPlanner.loadPathGroup(
            "path1", new PathConstraints(4, 2)
            );
        
        addCommands(
            RobotContainer.buildAuton(pptList)
        );
    }
}