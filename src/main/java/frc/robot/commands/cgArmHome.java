/*---------------------------------------------------------------
 * 
 * Functions:
 * Open Claw - should already be open
 * Retract arm extension - only after mid or high level
 * Rotate arm to intake position
 * Set intake position
 * Reset arm encoder
 * Set Motor to zero
 * Wait?
 * 
 */
package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;

import frc.robot.subsystems.*;
import frc.robot.commands.*;


public class cgArmHome extends SequentialCommandGroup{
    
    public cgArmHome(ArmSubsystem m_ArmSub, SensorsSubsystem m_SensorsSub) {

        // Needed for Sequential, not Parralel
        addCommands(
        
        new ParallelCommandGroup(    
            new RotateArmIntake(m_ArmSub),
            new ArmLimitReached(m_ArmSub, m_SensorsSub))
        
        

        ); //end of commands

    }

}
