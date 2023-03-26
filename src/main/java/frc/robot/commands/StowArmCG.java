package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.ArmExtensionSubsystem;
import frc.robot.subsystems.ArmSubsystem;
import frc.robot.subsystems.WristSubsystem;

public class StowArmCG extends SequentialCommandGroup {

    ArmExtensionSubsystem extendSubsystem;
    ArmSubsystem rotateSubsystem;
    WristSubsystem wristSubsystem;

    public StowArmCG(ArmExtensionSubsystem m_extend, ArmSubsystem m_rotate, WristSubsystem m_wrist) 
    {
        this.rotateSubsystem = m_rotate;
        this.extendSubsystem = m_extend;
        this.wristSubsystem = m_wrist;

        //holy constructor batman
        StowArmStage stageOne = new StowArmStage(m_extend, m_rotate, m_wrist, 2000, 50, 500); //Can make it one stage if it makes mentors happy (though i still really don't recommend even trying)
        StowArmStage stageTwo = new StowArmStage(m_extend, m_rotate, m_wrist, 2000, 30, 500); //Can make it one stage if it makes mentors happy (though i still really don't recommend even trying)


        addCommands
        (
            stageOne, 
            stageTwo
        );
    }
}
