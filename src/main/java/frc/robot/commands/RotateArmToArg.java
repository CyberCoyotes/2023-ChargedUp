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
import frc.robot.subsystems.ArmRotationSubsystem;

public class RotateArmToArg extends CommandBase {
      
    private final ArmRotationSubsystem m_armSubsystem;
    private final int deg;
    public RotateArmToArg(ArmRotationSubsystem subsystem, int deg) {
     m_armSubsystem = subsystem;
     this.deg =deg;
     addRequirements(m_armSubsystem);
    }

    @Override
    public void initialize() {
        super.initialize();
    }
    
    @Override
    public void execute() {
        m_armSubsystem.RotateArmToDeg(this.deg);
    }
    @Override
    public boolean isFinished() {
        return false;
    }

}
