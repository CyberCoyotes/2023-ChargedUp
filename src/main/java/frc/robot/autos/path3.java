/* 
 * 
 * PathPlanner based Auton, deploys low cube
 * 
*/
package frc.robot.autos;

import java.util.List;
// import java.util.function.BooleanSupplier;

import com.pathplanner.lib.PathConstraints;
import com.pathplanner.lib.PathPlanner;
import com.pathplanner.lib.PathPlannerTrajectory;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.RobotContainer;

public class path3 extends SequentialCommandGroup
{

    public path3() {

        List<PathPlannerTrajectory> path2 = PathPlanner.loadPathGroup("Cube2_p2", new PathConstraints(4, 2));
    
        addRequirements();

        addCommands(
            RobotContainer.buildAuton(path2) // mini path to cube
        );
    }
}
