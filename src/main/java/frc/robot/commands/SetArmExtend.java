/*--------------------------------------------------------* 
 * 
 * 2023 Charged Up
 * commands/extendArm
 * 
 * Function: Extends the arm for a high score
 * 
--------------------------------------------------------*/
package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ArmExtensionSubsystem;

public class SetArmExtend extends CommandBase {
   
    private final ArmExtensionSubsystem m_armExtensionSubsystem;
   
    public SetArmExtend(ArmExtensionSubsystem subsystem) {
     m_armExtensionSubsystem = subsystem;
     addRequirements(m_armExtensionSubsystem);
    }

}