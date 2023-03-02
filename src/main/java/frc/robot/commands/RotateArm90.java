/*--------------------------------------------------------* 
 * 
 * 2023 Charged Up
 * commands/rotateArm
 * 
 * When button is pressed:
 ** Rotates the arm rotates "x" encoder marks to an almost deployed arm position
 ** Operator will deploy arm to final position
 * 
 * Single button will be a toggle between deploy position and intake position
 * 
--------------------------------------------------------*/
package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ArmSubsystem;

public class RotateArm90 extends CommandBase {
      
    private final ArmSubsystem m_armSubsystem;
   
    public RotateArm90(ArmSubsystem subsystem) {
     m_armSubsystem = subsystem;
     addRequirements(m_armSubsystem);
    }

    @Override
    public void initialize() {
        // TODO Auto-generated method stub
        super.initialize();
    }
    
    @Override
    public void execute() {
        m_armSubsystem.RotateArmToDeg(90);
    }
    @Override
    public boolean isFinished() {
        return false;
    }

}