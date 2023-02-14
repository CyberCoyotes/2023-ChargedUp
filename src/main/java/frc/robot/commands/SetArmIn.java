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

public class SetArmIn extends CommandBase {

    private final ArmExtensionSubsystem m_armExtensionSubsystem;
   
    public SetArmIn(ArmExtensionSubsystem subsystem) {
     m_armExtensionSubsystem = subsystem;
     addRequirements(m_armExtensionSubsystem);
    }
}
