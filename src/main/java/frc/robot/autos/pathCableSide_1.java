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

public class pathCableSide_1 extends SequentialCommandGroup
{

    // public path1(ArmExtensionSubsystem extend, ArmRotationSubsystem arm, IntakeSubsystem intake, ArmWristSubsystem wrist) {

    public pathCableSide_1() {

        List<PathPlannerTrajectory> path1 = PathPlanner.loadPathGroup("Cube2_p1", new PathConstraints(4, 2));
        
        addRequirements();

        addCommands(
            RobotContainer.buildAuton(path1) // Path to Cube 2
        );
    }
}