package frc.robot.autos;

import java.util.ArrayList;
import java.util.List;

import com.pathplanner.lib.PathConstraints;
import com.pathplanner.lib.PathPlanner;
import com.pathplanner.lib.PathPlannerTrajectory;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.RobotContainer;
import frc.robot.subsystems.Swerve;

public class cgCubeLow_PPCube2 extends SequentialCommandGroup {
    public cgCubeLow_PPCube2() {
        List<PathPlannerTrajectory> pptList = PathPlanner.loadPathGroup(
            "CableCube2", new PathConstraints(4, 3)
            );
        
        addCommands(
            RobotContainer.buildAuton(pptList)
        );
    }
}