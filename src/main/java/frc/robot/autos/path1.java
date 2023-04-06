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
import frc.robot.subsystems.ArmExtensionSubsystem;
import frc.robot.subsystems.ArmRotationSubsystem;
import frc.robot.subsystems.ArmWristSubsystem;
import frc.robot.subsystems.IntakeSubsystem;


public class path1 extends SequentialCommandGroup
{
    private ArmRotationSubsystem m_arm;
    private ArmExtensionSubsystem m_extend;
    private IntakeSubsystem m_intake;
    private ArmWristSubsystem m_wrist;

    // public path1(ArmExtensionSubsystem extend, ArmRotationSubsystem arm, IntakeSubsystem intake, ArmWristSubsystem wrist) {

    public path1() {

        List<PathPlannerTrajectory> path1 = PathPlanner.loadPathGroup("Cube2_p1", new PathConstraints(4, 2));
        
        addRequirements();

        addCommands(
            RobotContainer.buildAuton(path1) // Path to Cube 2
        );
    }
}