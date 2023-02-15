/*--------------------------------------------------------* 
 * 
 * 2023 Charged Up
 * 
--------------------------------------------------------*/
package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.Subsystem;
import frc.robot.subsystems.IntakeSubsystem;

public class SetIntakeOut extends CommandBase {

    private final IntakeSubsystem m_intakeSubsystem;

    public SetIntakeOut(IntakeSubsystem subsystem) {
        // super();
        m_intakeSubsystem = subsystem;
        addRequirements(m_intakeSubsystem);
    }

    @Override
    public void execute() {
        m_intakeSubsystem.intakeWheelsReverse();
    }

    @Override
    public boolean isFinished() {
        return false;
    }

}
