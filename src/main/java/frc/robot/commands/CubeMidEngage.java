package frc.robot.commands;

import java.util.function.BooleanSupplier;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.ArmExtensionSubsystem;
import frc.robot.subsystems.ArmRotationSubsystem;
import frc.robot.subsystems.ArmWristSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.Swerve;

public class CubeMidEngage extends SequentialCommandGroup {

    private Swerve m_swerve;
    private ArmRotationSubsystem m_arm;
    private ArmExtensionSubsystem m_extend;
    private IntakeSubsystem m_intake;
    private ArmWristSubsystem m_wrist;

    public CubeMidEngage(Swerve s_Swerve, ArmExtensionSubsystem extend, ArmRotationSubsystem arm,
            IntakeSubsystem intake, ArmWristSubsystem wrist, BooleanSupplier robotCentric) {

        this.m_swerve = s_Swerve;
        this.m_arm = arm;
        this.m_extend = extend;
        this.m_intake = intake;

        short polarity = 1;
        double power = .4;
        double seconds = 2.12;
        // : 40% in a single direction for 1 second: ~51 inches
        final float input = (float) (polarity * power);
        Command driveCommand;

        addRequirements(m_swerve, m_arm, m_extend, m_intake);

        driveCommand = new TeleopSwerve(
                m_swerve,
                () -> input,
                () -> 0,
                () -> 0,
                () -> robotCentric.getAsBoolean(),
                () -> false);

        // Now trying to dock

        addCommands(
                // just in case
                // new cgCubeLow(m_arm, m_extend, m_wrist, m_intake ).withTimeout(5),
                new CubeLowCG(arm, extend, wrist, intake).withTimeout(1),
                driveCommand.withTimeout(seconds)
        );
    }
}