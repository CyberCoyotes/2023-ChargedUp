/* 
 * 
 * PathPlanner based Auton
 * 
*/
package frc.robot.autos;

import java.util.List;

import com.pathplanner.lib.PathConstraints;
import com.pathplanner.lib.PathPlanner;
import com.pathplanner.lib.PathPlannerTrajectory;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.RobotContainer;

public class pathNonCableSide_B extends SequentialCommandGroup
{

    public pathNonCableSide_B() {

        List<PathPlannerTrajectory> pathB = PathPlanner.loadPathGroup("Cube2_pB", new PathConstraints(4, 3));
    
        addRequirements();

        addCommands(
            RobotContainer.buildAuton(pathB) // Path back to station on non-cable side

    
        );
    }
}
