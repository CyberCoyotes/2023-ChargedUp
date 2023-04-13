/* 
 * 
 * PathPlanner based Auton, deploys low cube
 * 
*/
package frc.robot.autos;

import java.util.List;

import com.pathplanner.lib.PathConstraints;
import com.pathplanner.lib.PathPlanner;
import com.pathplanner.lib.PathPlannerTrajectory;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.RobotContainer;

public class pathNonCableSide_A extends SequentialCommandGroup
{

    public pathNonCableSide_A() {

        List<PathPlannerTrajectory> pathA = PathPlanner.loadPathGroup("Cube2_pA", new PathConstraints(4, 3));
       
        addRequirements();

        addCommands(
            RobotContainer.buildAuton(pathA) // Path to Cube 2 on Non-Cable Side
        );
    }
}