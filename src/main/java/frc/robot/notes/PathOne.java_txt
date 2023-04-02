package frc.robot.commands;

import com.pathplanner.lib.PathPlanner;
import com.pathplanner.lib.PathPlannerTrajectory;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.Swerve;

public class PathPlannerTestOne extends SequentialCommandGroup {

  // typically a max velocity of 2, but reduced to stop the robot from killing
  // itself
  PathPlannerTrajectory thePath = PathPlanner.loadPath("PathTestOne", 1.0, 3.0);

  public PathPlannerTestOne(Swerve m_drive) {
    addCommands(
        new InstantCommand(() -> m_drive.dt.setKnownPose(thePath.getInitialPose())),
        m_drive.dt.createCommandForTrajectory(thePath, m_drive));
  }

} // end of class
