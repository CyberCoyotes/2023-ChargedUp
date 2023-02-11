/*--------------------------------------------------------* 
 * 
 * 2023 Charged Up
 * commands/extendArm
 * 
 * Function: retracts arm
 * 
--------------------------------------------------------*/
package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ArmExtensionSubsystem;

public class retractArm extends CommandBase {

    private final ArmExtensionSubsystem m_armExtensionSubsystem;
   
    public retractArm(ArmExtensionSubsystem subsytem) {
     m_armExtensionSubsystem = subsytem;
     addRequirements(m_armExtensionSubsystem);
    }
}
