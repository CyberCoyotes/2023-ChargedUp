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

public class ArmExtendToArg extends CommandBase {
   
    private final ArmExtensionSubsystem m_armExtensionSubsystem;
   
    private int position;
    public ArmExtendToArg(ArmExtensionSubsystem subsystem) {
     m_armExtensionSubsystem = subsystem;
     
    //  this.position = position;
     addRequirements(m_armExtensionSubsystem);
    }
    @Override
    public boolean isFinished() {
        return false;
    }
    @Override
    public void initialize() {
        // TODO Auto-generated method stub
        super.initialize();
        m_armExtensionSubsystem.Setup();
    }
    
    @Override
    public void execute() {
                 
        m_armExtensionSubsystem.SetArmToTickPosition(3500);
    }
    @Override
    public InterruptionBehavior getInterruptionBehavior() {
        return InterruptionBehavior.kCancelIncoming;
    }

}
