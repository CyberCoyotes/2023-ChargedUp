package frc.robot.autos;

import frc.robot.Constants;
import frc.robot.subsystems.Swerve;

import java.util.List;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.math.trajectory.TrajectoryConfig;
import edu.wpi.first.math.trajectory.TrajectoryGenerator;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.SwerveControllerCommand;

public class cgCubeMid_Taxi_ver2 extends SequentialCommandGroup {
        // Move these to constants if it makes sense
        double DRIVE_RATE = 52; // inches per second at 0.40 output
        double STATION_DEPTH = 54; // approx inches
        // COMMUNITY Depth ranges from 11 ft to 16 ft, 1 inch
        double COMMUNITY_DEPTH = 193; // 16 ft *12 inches + 1 inch = 193 inches
        double DRIVE_DISTANCE = (COMMUNITY_DEPTH - STATION_DEPTH); // approx 139 inches
    
        double DRIVE_TIME_CLEAR_ZONE = (DRIVE_DISTANCE / DRIVE_RATE); // should be approximately 2.67 seconds
    
    
        /*
         * Subtracting half the width of the CHARGE STATION and assumes no slippage.
         * Should be 24 without slippage and ideal (?)
         * Go shorter to engage and play safe?
         */
        double CHARGE_STATION_ADJ = 12;
        double DRIVE_TIME_ENGAGE = ((DRIVE_DISTANCE - CHARGE_STATION_ADJ) / DRIVE_RATE);

    public cgCubeMid_Taxi_ver2(Swerve s_Swerve){
        TrajectoryConfig config =
            new TrajectoryConfig(
                    Constants.AutoConstants.kMaxSpeedMetersPerSecond,
                    Constants.AutoConstants.kMaxAccelerationMetersPerSecondSquared)
                .setKinematics(Constants.Swerve.swerveKinematics);

        // An example trajectory to follow.  All units in meters.
        Trajectory exampleTrajectory =
            TrajectoryGenerator.generateTrajectory(
                // Start at the origin facing the +X direction
                new Pose2d(0, 0, new Rotation2d(0)),
                // Pass through these two interior waypoints, making an 's' curve path
                List.of(new Translation2d(1, 1), new Translation2d(2, -1)),
                // End 3 meters straight ahead of where we started, facing forward
                new Pose2d(3, 0, new Rotation2d(0)),
                config);

        var thetaController =
            new ProfiledPIDController(
                Constants.AutoConstants.kPThetaController, 0, 0, Constants.AutoConstants.kThetaControllerConstraints);
        thetaController.enableContinuousInput(-Math.PI, Math.PI);

        SwerveControllerCommand swerveControllerCommand =
            new SwerveControllerCommand(
                exampleTrajectory,
                s_Swerve::getPose,
                Constants.Swerve.swerveKinematics,
                new PIDController(Constants.AutoConstants.kPXController, 0, 0),
                new PIDController(Constants.AutoConstants.kPYController, 0, 0),
                thetaController,
                s_Swerve::setModuleStates,
                s_Swerve);


        addCommands(
            // new cgCubeDeployMiddle(null, null, null),
            new InstantCommand(() -> s_Swerve.resetOdometry(exampleTrajectory.getInitialPose())),
            swerveControllerCommand
        );
    }
}