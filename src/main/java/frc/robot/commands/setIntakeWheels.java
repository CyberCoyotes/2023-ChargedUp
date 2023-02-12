/*--------------------------------------------------------* 
 * 
 * 2023 Charged Up
 * 
--------------------------------------------------------*/
package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ArmSubsystem;
import frc.robot.subsystems.IntakeWheelsSubsystem;

public class setIntakeWheels extends CommandBase {

    private final IntakeWheelsSubsystem m_intakeWheelsSubsystem;
   
    public setIntakeWheels(IntakeWheelsSubsystem subsytem) {
     m_intakeWheelsSubsystem = subsytem;
     addRequirements(m_intakeWheelsSubsystem);
    }
}
