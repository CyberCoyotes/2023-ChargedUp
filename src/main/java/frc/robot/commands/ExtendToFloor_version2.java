/*--------------------------------------------------------* 
 * 
 * 2023 Charged Up
 * commands/extendArm
 * 
 * Function: BETA 
 * 
--------------------------------------------------------*/
package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants.Arm;
import frc.robot.subsystems.ArmExtensionSubsystem;


public class ExtendToFloor_version2 extends CommandBase {

    private final ArmExtensionSubsystem m_armExtensionSubsystem;


    private Boolean position = false;

    public ExtendToFloor_version2(ArmExtensionSubsystem subsystem) {

     m_armExtensionSubsystem = subsystem;
     
     addRequirements(m_armExtensionSubsystem);
    
    }
  
    @Override
    public void initialize() {
        super.initialize();
    }
    
    @Override
    public void execute() {
        // TODO VanScoyoc shot in the dark #2
        m_armExtensionSubsystem.ReadExtension(); // Reads the current extension encoder position
        m_armExtensionSubsystem.extendToFloorCube(); // Extends to a set encoder value. Testing with a shorter value and slower speed
        
        if ((m_armExtensionSubsystem.ReadExtension()  >= (Arm.EXTENSION_FLOOR_POS*0.95) && (m_armExtensionSubsystem.ReadExtension() <= (Arm.EXTENSION_FLOOR_POS*1.05)))) { 
        if ((m_armExtensionSubsystem.ReadExtension()  <= -700) && (m_armExtensionSubsystem.ReadExtension() >= -1500)) { 
            // FIXME Should be a constant around 9500 but using short extension for testing
            position = true;
        }
        }
    }


    @Override
    public boolean isFinished() {
        // TODO VanScoyoc shot in the dark
        // Attempting to use a conditional check against encoder value vs desired value w/o using fancy PID
            return position;    
    }

}
