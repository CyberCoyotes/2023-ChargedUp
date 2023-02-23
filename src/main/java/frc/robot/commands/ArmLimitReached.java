package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.subsystems.ArmSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.SensorsSubsystem;

public class ArmLimitReached extends CommandBase{

    private final ArmSubsystem m_ArmSub;
    private final SensorsSubsystem m_SensorsSub;
    private final IntakeSubsystem m_IntakeSub;

    public ArmLimitReached(ArmSubsystem subsystem1, SensorsSubsystem subsystem2, IntakeSubsystem subsystem3) {
        m_ArmSub = subsystem1;
        m_SensorsSub = subsystem2;
        m_IntakeSub = subsystem3;
    
        addRequirements(m_ArmSub, m_SensorsSub, m_IntakeSub);
    }

    /*
     * If the magnetic limit switch triggered on arm, reset the encoder and stop the motor temporarily 
     */
    @Override
    public void initialize() {
        
          boolean limit =  m_SensorsSub.armSwitch.get();

        if(limit == false) {  //need to figure out correct check
            m_IntakeSub.intakeWheelsReverse();
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
