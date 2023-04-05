/* 
 * 
 * PathPlanner based Auton, deploys low cube
 * 
*/
package frc.robot.autos;

import java.util.List;
import java.util.function.BooleanSupplier;

import com.pathplanner.lib.PathConstraints;
import com.pathplanner.lib.PathPlanner;
import com.pathplanner.lib.PathPlannerTrajectory;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.RobotContainer;
import frc.robot.commands.CubeLowCG;
import frc.robot.commands.SetIntakeCone;
import frc.robot.subsystems.ArmExtensionSubsystem;
import frc.robot.subsystems.ArmRotationSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.ArmWristSubsystem;

public class path2 extends SequentialCommandGroup
{
    public path2() {

        
        List<PathPlannerTrajectory> path2 = PathPlanner.loadPathGroup("Cube2_p2", new PathConstraints(4, 3));
    
        addRequirements();

        addCommands(
            RobotContainer.buildAuton(path2)
        );
    }
}
