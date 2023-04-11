/*--------------------------------------------------------* 
 * 
 * 2023 Charged Up
 * commands/extendArm
 * 
 * Function: 
 * 
--------------------------------------------------------*/
package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ArmExtensionSubsystem;


public class ArmExtendToFloor extends CommandBase {
   
    private final ArmExtensionSubsystem m_armExtensionSubsystem;
    private final double position;
    
    public ArmExtendToFloor(ArmExtensionSubsystem subsystem, double position) {

     m_armExtensionSubsystem = subsystem;
     
     this.position = position;
    
     addRequirements(m_armExtensionSubsystem);
    
    }
  
    @Override
    public void initialize() {
        super.initialize();
    }
    
    @Override
    public void execute() {
        // VanScoyoc shot in the dark
        m_armExtensionSubsystem.getExtensionPosition();
        m_armExtensionSubsystem.pickupFloorCube();
    }


    @Override
    public boolean isFinished() {
        // VanScoyoc shot in the dark
        if (m_armExtensionSubsystem.getExtensionPosition() > 2000) { // FIXME Should be a constant around 9500 but using short extension for testing
            return true;
        } else {
            return false;
        }
    
    }

}
