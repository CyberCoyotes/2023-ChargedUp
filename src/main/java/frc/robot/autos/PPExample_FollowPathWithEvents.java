package frc.robot.autos;

import java.util.HashMap;

import com.pathplanner.lib.PathConstraints;
import com.pathplanner.lib.PathPlanner;
import com.pathplanner.lib.PathPlannerTrajectory;
import com.pathplanner.lib.commands.FollowPathWithEvents;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class PPExample_FollowPathWithEvents extends SequentialCommandGroup{

    // This will load the file "CableCube2.path" and generate it with a max velocity of 4 m/s and a max acceleration of 3 m/s^2
PathPlannerTrajectory cableCube2 = PathPlanner.loadPath("CableCube2", new PathConstraints(4, 3));

// This is just an example event map. It would be better to have a constant, global event map
// in your code that will be used by all path following commands.
HashMap<String, Command> eventMap = new HashMap<>();

eventMap.put("Waypoint 2", new PrintCommand("Passed marker Cube 2"));
// eventMap.put("intakeDown", new IntakeDown());

FollowPathWithEvents command = new FollowPathWithEvents(
    getPathFollowingCommand(CableCube2),
    CableCube2.getMarkers(),
    eventMap
);
}