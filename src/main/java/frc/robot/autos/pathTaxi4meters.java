/* Taxi out 4 meters in a straight line, no game element deposits; PathPlanner based drive */

package frc.robot.autos;

import java.util.List;

import com.pathplanner.lib.PathConstraints;
import com.pathplanner.lib.PathPlanner;
import com.pathplanner.lib.PathPlannerTrajectory;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.RobotContainer;

public class pathTaxi4meters extends SequentialCommandGroup {

    public pathTaxi4meters() {
        List<PathPlannerTrajectory> pptList = PathPlanner.loadPathGroup(
            "Taxi4meters", new PathConstraints(4, 3)
            );
        
        addCommands(
            RobotContainer.buildAuton(pptList)
        );
    }
}