/*--------------------------------------------------------* 
 * 
 * 2023 Charged Up
 * 
--------------------------------------------------------*/
package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ArmSubsystem;

public class RotateArmIntake extends CommandBase {

    private final ArmSubsystem m_armSubsystem;
   
    public RotateArmIntake(ArmSubsystem subsystem) {
     m_armSubsystem = subsystem;
     addRequirements(m_armSubsystem);
    }

    @Override
    public void execute() {
        m_armSubsystem.rotateArmforIntake();

    }
    @Override
    public boolean isFinished() {
        return m_armSubsystem.ConvertFXEncodertoDeg(m_armSubsystem.GetRotation()) >= 90;
    }
    @Override
    public void end(boolean interrupted) {
        m_armSubsystem.PercentOutputSupplierDrive(0);
    }
}
