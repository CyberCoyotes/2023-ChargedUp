/*--------------------------------------------------------* 
 * 
 * 2023 Charged Up
 * 
 * subsystems/ArmSubsystem.java
 * 
 * Uses 2 Falcon 500 subsytem for arm movement
 * 
 * Documentation and references
 * https://docs.wpilib.org/en/stable/docs/software/advanced-controls/introduction/tuning-vertical-arm.html
 *  a combined feedforward-feedback strategy is needed.
 * 
 * https://www.chiefdelphi.com/t/smoothly-controlling-an-arm/343880/
 * 
 * Math stuff from 5 yrs ago
 * https://youtu.be/RLrZzSpHP4E
--------------------------------------------------------*/
package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ArmSubsystem extends SubsystemBase {
    
    /** Rotates arm to deploment side of robot */ 
    public void rotateArmDeploy(){
        
    }

    /** Rotates arm to intake side of robot */
    public void rotateArmforIntake(){
        
    }

    /** 
     * Use case scenario 1: Allow for a command to use any values for encoder
     * Use case scenarior 2: Use for incremental rotation, e.g. 10 ticks for every button press
    **/
    public void rotateArmManual(){
        
    }
}