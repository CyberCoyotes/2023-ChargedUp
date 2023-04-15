/*--------------------------------------------------------* 
 * 
 * 2023 Charged Up
 * commands/extendArm
 * 
 * Function: Extends the arm for a high score
 * 
--------------------------------------------------------*/
package frc.robot.commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ArmExtensionSubsystem;

public class ArmExtendToArg extends CommandBase {
   
    private final ArmExtensionSubsystem m_armExtensionSubsystem;
   private final DoubleSupplier target;
    public ArmExtendToArg(ArmExtensionSubsystem subsystem, DoubleSupplier target) {

     m_armExtensionSubsystem = subsystem;
     this.target = target;
     addRequirements(m_armExtensionSubsystem);
    }
  
    @Override
    public void initialize() {
        super.initialize();
    }
    
    @Override
    public void execute() {
        m_armExtensionSubsystem.SetToPosition((int)this.target.getAsDouble());
    }
    @Override
    public boolean isFinished() {
        return false;
    }

}
