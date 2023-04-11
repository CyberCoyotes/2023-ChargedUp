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

    
    public ArmExtendToFloor(ArmExtensionSubsystem subsystem) {

     m_armExtensionSubsystem = subsystem;
     

    
     addRequirements(m_armExtensionSubsystem);
    
    }
  
    @Override
    public void initialize() {
        super.initialize();
    }
    
    @Override
    public void execute() {
        // TODO VanScoyoc shot in the dark
        m_armExtensionSubsystem.getExtensionPosition(); // Reads the current extension encoder position
        m_armExtensionSubsystem.pickupFloorCube();
    }


    @Override
    public boolean isFinished() {
        // TODO VanScoyoc shot in the dark
        if (m_armExtensionSubsystem.getExtensionPosition() > 2000) { // FIXME Should be a constant around 9500 but using short extension for testing
            return true;
        } else {
            return false;
        }
    
    }

}
