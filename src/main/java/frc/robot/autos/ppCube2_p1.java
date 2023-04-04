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
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.RobotContainer;
import frc.robot.commands.CubeMid;
import frc.robot.commands.LowCubePickup;
import frc.robot.commands.SetIntakeCone;
import frc.robot.commands.SetIntakeCube;
import frc.robot.subsystems.ArmExtensionSubsystem;
import frc.robot.subsystems.ArmRotationSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.ArmWristSubsystem;

public class ppCube2_p1 extends SequentialCommandGroup
{
    private ArmRotationSubsystem m_arm;
    private ArmExtensionSubsystem m_extend;
    private IntakeSubsystem m_intake;
    private ArmWristSubsystem m_wrist;


    public ppCube2_p1(ArmExtensionSubsystem extend, ArmRotationSubsystem arm, IntakeSubsystem intake, ArmWristSubsystem wrist) {

            this.m_arm = arm; 
            this.m_extend = extend; 
            this.m_intake = intake;
            this.m_wrist = wrist;


        List<PathPlannerTrajectory> path1Out = PathPlanner.loadPathGroup("Cube2_p1", new PathConstraints(4, 3));
        
        addRequirements();

        addCommands(
            new SetIntakeCone(m_intake ).withTimeout(1) // TODO Replace with Cube Mid Auton
            , new WaitCommand(0.25) // TODO Replace with Stow command
            
            , RobotContainer.buildAuton(path1Out) // Path to Cube 2
            
            // new SetIntakeCube(m_intake).withTimeout(1), // Place holder
            , new LowCubePickup(arm, wrist, intake, extend)

            , new WaitCommand(0.25) // TODO Replace woth Stow command
        );
    }
}