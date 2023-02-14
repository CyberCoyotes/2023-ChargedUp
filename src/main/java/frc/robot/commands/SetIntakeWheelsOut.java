/*--------------------------------------------------------* 
 * 
 * 2023 Charged Up
 * 
--------------------------------------------------------*/
package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.Subsystem;
import frc.robot.subsystems.IntakeWheelsSubsystem;

public class SetIntakeWheelsOut extends CommandBase {

    private final IntakeWheelsSubsystem m_intakeWheelsSubsystem;

    public SetIntakeWheelsOut(IntakeWheelsSubsystem subsystem) {
        // super();
        m_intakeWheelsSubsystem = subsystem;
        addRequirements(m_intakeWheelsSubsystem);
    }

    @Override
    public void execute() {
        m_intakeWheelsSubsystem.intakeWheelsReverse();
    }

    @Override
    public boolean isFinished() {
        return false;
    }

}
