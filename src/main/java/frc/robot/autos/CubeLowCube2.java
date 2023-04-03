package frc.robot.autos;

import java.util.List;

import com.pathplanner.lib.PathConstraints;
import com.pathplanner.lib.PathPlanner;
import com.pathplanner.lib.PathPlannerTrajectory;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.RobotContainer;

public class CubeLowCube2 extends SequentialCommandGroup {
    public CubeLowCube2() {
        List<PathPlannerTrajectory> pptList = PathPlanner.loadPathGroup(
            "CableCube3", new PathConstraints(4, 3)
            );
        
        addCommands(
            RobotContainer.buildAuton(pptList)
        );
    }
}