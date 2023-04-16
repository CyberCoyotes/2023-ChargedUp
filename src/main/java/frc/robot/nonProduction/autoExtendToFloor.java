/* Deposits low cube and taxi out; PathPlanner based drive */

package frc.robot.nonProduction;

import java.util.List;

import com.pathplanner.lib.PathConstraints;
import com.pathplanner.lib.PathPlanner;
import com.pathplanner.lib.PathPlannerTrajectory;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.ArmExtensionSubsystem;
import frc.robot.subsystems.ArmRotationSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.ArmWristSubsystem;

public class autoExtendToFloor extends SequentialCommandGroup
{
    private ArmRotationSubsystem m_arm;
    private ArmExtensionSubsystem m_extend;
    private IntakeSubsystem m_intake;
    private ArmWristSubsystem m_wrist;


    public autoExtendToFloor(ArmExtensionSubsystem extend, ArmRotationSubsystem arm, IntakeSubsystem intake, ArmWristSubsystem wrist) {

            this.m_arm = arm; 
            this.m_extend = extend; 
            this.m_intake = intake; 

        List<PathPlannerTrajectory> taxi4Meters = PathPlanner.loadPathGroup("Taxi4meters", new PathConstraints(4, 3));
    
            addRequirements();

        addCommands(
            new ExtendToFloor(extend)
            //RobotContainer.buildAuton(taxi4Meters)

        );
    }
}
