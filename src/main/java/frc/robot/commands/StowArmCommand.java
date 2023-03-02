package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import frc.robot.Constants.Arm;
import frc.robot.subsystems.ArmExtensionSubsystem;
import frc.robot.subsystems.ArmSubsystem;

public class StowArmCommand extends ParallelCommandGroup {

    ArmExtensionSubsystem extendSubsystem;
    ArmSubsystem rotateSubsystem;
    public StowArmCommand(ArmExtensionSubsystem m_extend, ArmSubsystem m_rotate) 
    {
        addRequirements(m_extend, m_rotate);
        this.rotateSubsystem = rotateSubsystem;
        this.extendSubsystem = extendSubsystem;
        addCommands(new SetArmExtentCommand(Arm.ARM_STOW_EXTENT_ENCODER,  m_extend), new SetArmRotationCommand(Arm.ARM_STOW_ROTATION_DEG, m_rotate));
    
    
    }

}
