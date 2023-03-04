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
   private final int target;
    public ArmExtendToArg(ArmExtensionSubsystem subsystem, int target) {

     m_armExtensionSubsystem = subsystem;
     this.target = target;
     addRequirements(m_armExtensionSubsystem);
    }
    @Override
    public boolean isFinished() {
        return false;
    }
    @Override
    public void initialize() {
        super.initialize();
        //// m_armExtensionSubsystem.Setup();
    }
    
    @Override
    public void execute() {
        m_armExtensionSubsystem.SetArmToTickPosition(target);
    }
    @Override
    public InterruptionBehavior getInterruptionBehavior() {
        return InterruptionBehavior.kCancelSelf;
    }

}
