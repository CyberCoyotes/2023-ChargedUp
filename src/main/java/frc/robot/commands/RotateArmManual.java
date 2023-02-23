/*--------------------------------------------------------* 
 * 
 * 2023 Charged Up
 * commands/deployArm
 * 
 * When button is pressed
 * (1) the arm rotates "x" encoder marks to an almost deployed arm position
 * (2) adapts the wrist to a correct position
 * ()
 * Operator will deploy arm to final position
 * 
--------------------------------------------------------*/
package frc.robot.commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ArmSubsystem;

public class RotateArmManual extends CommandBase {

    private final ArmSubsystem m_armSubsystem;
    private final DoubleSupplier input;
    public RotateArmManual(ArmSubsystem subsystem, DoubleSupplier supplier) {
     m_armSubsystem = subsystem;
     addRequirements(m_armSubsystem);
     this.input = supplier;
    }    
    @Override
    public void execute() {
        m_armSubsystem.PercentOutputSupplierDrive(input.getAsDouble());
    }
   
    
}
