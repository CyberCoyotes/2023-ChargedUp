/* Taxi and Dock; PathPlanner based drive */

package frc.robot.autos;

import java.util.List;

import com.pathplanner.lib.PathConstraints;
import com.pathplanner.lib.PathPlanner;
import com.pathplanner.lib.PathPlannerTrajectory;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.RobotContainer;
import frc.robot.commands.CubeLowCG;
import frc.robot.subsystems.ArmExtensionSubsystem;
import frc.robot.subsystems.ArmRotationSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.ArmWristSubsystem;

public class ppCubeTaxiDock extends SequentialCommandGroup
{
    private ArmRotationSubsystem m_arm;
    private ArmExtensionSubsystem m_extend;
    private IntakeSubsystem m_intake;
    private ArmWristSubsystem m_wrist;


    public ppCubeTaxiDock(ArmExtensionSubsystem extend, ArmRotationSubsystem arm, IntakeSubsystem intake, ArmWristSubsystem wrist) {

            this.m_arm = arm; 
            this.m_extend = extend; 
            this.m_intake = intake; 
            this.m_wrist = wrist; // Was missing at practice field and in GR!


        List<PathPlannerTrajectory> taxiDock = PathPlanner.loadPathGroup("TaxiDock", new PathConstraints(4, 1));
    
            addRequirements();

        addCommands(
            new CubeLowCG(arm, extend, wrist, intake).withTimeout(1.0),
            
            RobotContainer.buildAuton(taxiDock)

        );
    }
}
