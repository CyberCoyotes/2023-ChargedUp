/* 
 * 
 * PathPlanner based Auton, deploys low cube
 * 
*/
package frc.robot.nonProduction;

import java.util.List;
// import java.util.function.BooleanSupplier;

import com.pathplanner.lib.PathConstraints;
import com.pathplanner.lib.PathPlanner;
import com.pathplanner.lib.PathPlannerTrajectory;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.RobotContainer;

public class ppCube2_p3 extends SequentialCommandGroup
{


    public ppCube2_p3() {
        
        List<PathPlannerTrajectory> path3 = PathPlanner.loadPathGroup("Cube2_p3", new PathConstraints(4, 2));
    
        addRequirements();

        addCommands(
            RobotContainer.buildAuton(path3) // Path back to station
        );
    }
}
