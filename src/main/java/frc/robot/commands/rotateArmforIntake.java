/*--------------------------------------------------------* 
 * 
 * 2023 Charged Up
 * 
--------------------------------------------------------*/
package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ArmSubsystem;

public class rotateArmforIntake extends CommandBase {

    private final ArmSubsystem m_armSubsystem;
   
    public rotateArmforIntake(ArmSubsystem subsytem) {
     m_armSubsystem = subsytem;
     addRequirements(m_armSubsystem);
    }
}
