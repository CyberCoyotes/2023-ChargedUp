package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import frc.robot.Constants.Arm;
import frc.robot.subsystems.ArmExtensionSubsystem;
import frc.robot.subsystems.ArmSubsystem;
import frc.robot.subsystems.WristSubsystem;

public class StowArmCommand extends CommandBase {

    ArmExtensionSubsystem extendSubsystem;
    ArmSubsystem rotateSubsystem;
    WristSubsystem wristSubsystem;
    
    public StowArmCommand(ArmExtensionSubsystem m_extend, ArmSubsystem m_rotate, WristSubsystem m_wrist) 
    {
        addRequirements(m_extend, m_rotate, m_wrist);
        this.rotateSubsystem = rotateSubsystem;
        this.extendSubsystem = extendSubsystem;
        this.wristSubsystem = wristSubsystem;

    
    
    }

}
