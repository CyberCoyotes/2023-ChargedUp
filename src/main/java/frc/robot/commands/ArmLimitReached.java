package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.subsystems.ArmSubsystem;
import frc.robot.subsystems.SensorsSubsystem;

public class ArmLimitReached extends CommandBase{

    private final ArmSubsystem m_ArmSub;
    private final SensorsSubsystem m_SensorsSub;

    public ArmLimitReached(ArmSubsystem subsystem1, SensorsSubsystem subsystem2) {
        m_ArmSub = subsystem1;
        m_SensorsSub = subsystem2;
        addRequirements(m_ArmSub, m_SensorsSub);
    }

    /*
     * If the magnetic limit switch triggered on arm, reset the encoder and stop the motor temporarily 
     */
    @Override
    public void initialize() {
        if(limit.get( )) {  //need to figure out correct check
            m_ArmSub.ZeroArmEncoder();
             // reset right arm motor encoder to zero AND stop arm
            // WaitCommand();
// Set motor to zero for 300 ms
        } 

    @Override
    public boolean isFinished() {
        return true;
    }
    
}
