package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ArmRotationSubsystem;
import frc.robot.subsystems.SensorsSubsystem;

public class ArmLimitReached extends CommandBase{

    private final ArmRotationSubsystem m_ArmSubsystem;
    private final SensorsSubsystem m_SensorsSubsystem;

    public ArmLimitReached(ArmRotationSubsystem subsystem, SensorsSubsystem subsystem2) {
        m_ArmSubsystem = subsystem;
        m_SensorsSubsystem = subsystem2;
    
        addRequirements(m_ArmSubsystem, m_SensorsSubsystem);
    }

    /*
     * If the magnetic limit switch triggered on arm, reset the encoder and stop the motor temporarily 
     */
    @Override
    public void initialize() {
        
          boolean limit =  m_SensorsSubsystem.armSwitch.get();

        if(limit == false) {  //need to figure out correct check
            m_ArmSubsystem.ZeroArmEncoder();
             // reset right arm motor encoder to zero AND stop arm
            // WaitCommand();
// Set motor to zero for 300 ms
        } 
    }

    @Override
    public boolean isFinished() {
        return true;
    }
    
}
