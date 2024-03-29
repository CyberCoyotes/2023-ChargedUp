/*--------------------------------------------------------* 
 * 
 * 2023 Charged Up
 * commands/extendArm
 * 
 * Function: BETA 
 * 
--------------------------------------------------------*/
package frc.robot.nonProduction;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants.Arm;
import frc.robot.subsystems.ArmExtensionSubsystem;


public class ExtendToFloor extends CommandBase {

    private final ArmExtensionSubsystem m_armExtensionSubsystem;

    
    public ExtendToFloor(ArmExtensionSubsystem subsystem) {

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
        m_armExtensionSubsystem.GetPosition(); // Reads the current extension encoder position
        m_armExtensionSubsystem.extendToFloorCube(); // Extends to a set encoder value. Testing with a shorter value and slower speed
    }


    @Override
    public boolean isFinished() {
        // TODO VanScoyoc shot in the dark
        // Attempting to use a conditional check against encoder value vs desired value w/o using fancy PID
        if (m_armExtensionSubsystem.GetPosition()  <= Arm.EXTENSION_FLOOR_POS) { // FIXME Should be a constant around 9500 but using short extension for testing
            return true;
        } else {
            return false; // Runs without stopping
        }
    
    }

}
