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
import frc.robot.commands.SetIntakeCube;
import frc.robot.subsystems.ArmExtensionSubsystem;
import frc.robot.subsystems.ArmRotationSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.ArmWristSubsystem;

public class ppCube3_p3 extends SequentialCommandGroup
{
    private ArmRotationSubsystem m_arm;
    private ArmExtensionSubsystem m_extend;
    private IntakeSubsystem m_intake;
    private ArmWristSubsystem m_wrist;


    public ppCube3_p3(ArmExtensionSubsystem extend, ArmRotationSubsystem arm, IntakeSubsystem intake, ArmWristSubsystem wrist, BooleanSupplier robotCentric) {

            this.m_arm = arm; 
            this.m_extend = extend; 
            this.m_intake = intake; 

        List<PathPlannerTrajectory> path3Out = PathPlanner.loadPathGroup("Cube3_p3", new PathConstraints(4, 3));
        
        addRequirements();

        addCommands(
            RobotContainer.buildAuton(path3Out), // Path to Cube 3
            new SetIntakeCube(m_intake).withTimeout(1), // TODO Replace with Cube Pickup
            new WaitCommand(0.25) // TODO Relace with Stow Cube
        );
    }
}
