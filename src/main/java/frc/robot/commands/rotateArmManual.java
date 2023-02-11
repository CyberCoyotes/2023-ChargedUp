/*--------------------------------------------------------* 
 * 
 * 2023 Charged Up
 * commands/deployArm
 * 
 * When button is pressed
 * (1) the arm rotates "x" encoder marks to an almost deployed arm position
 * (2) adapts the wrist to a correct position
 * ()
 * Operator will deploy arm to final position
 * 
--------------------------------------------------------*/
package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ArmSubsystem;

public class rotateArmManual extends CommandBase {

    private final ArmSubsystem m_armSubsystem;
   
    public rotateArmManual(ArmSubsystem subsytem) {
     m_armSubsystem = subsytem;
     addRequirements(m_armSubsystem);
    }    
    
}
