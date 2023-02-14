/*--------------------------------------------------------* 
 * 
 * 2023 Charged Up
 * commands/rotateArm
 * 
 * When button is pressed:
 ** Rotates the arm rotates "x" encoder marks to an almost deployed arm position
 ** Operator will deploy arm to final position
 * 
--------------------------------------------------------*/
package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ArmSubsystem;

public class RotateArmDeploy extends CommandBase {
      
    private final ArmSubsystem m_armSubsystem;
   
    public RotateArmDeploy(ArmSubsystem subsystem) {
     m_armSubsystem = subsystem;
     addRequirements(m_armSubsystem);
    }
}
