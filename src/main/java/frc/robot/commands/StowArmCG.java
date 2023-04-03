package frc.robot.commands;

import java.util.ArrayList;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.ArmExtensionSubsystem;
import frc.robot.subsystems.ArmRotationSubsystem;
import frc.robot.subsystems.ArmWristSubsystem;

public class StowArmCG extends SequentialCommandGroup {

    StowArmStage stageOne;
StowArmStage stageTwo;
    ArmExtensionSubsystem extendSubsystem;
    ArmRotationSubsystem rotateSubsystem;
    ArmWristSubsystem wristSubsystem;

    public String DebugUsefulInfo()
    {
        var string1 = stageOne.isScheduled();
        var string2 = stageTwo.isScheduled();
        return string1 + " " + string2;
    }


    public StowArmCG(ArmExtensionSubsystem m_extend, ArmRotationSubsystem m_rotate, ArmWristSubsystem m_wrist, StowArmStage... stages) 
    {
        this.rotateSubsystem = m_rotate;
        this.extendSubsystem = m_extend;
        this.wristSubsystem = m_wrist;

        //holy constructor batman
         stageOne = new StowArmStage(m_extend, m_rotate, m_wrist, 2000, 50, 500); //Can make it one stage if it makes mentors happy (though i still really don't recommend even trying)
         stageTwo = new StowArmStage(m_extend, m_rotate, m_wrist, 2000, 30, 500); //Can make it one stage if it makes mentors happy (though i still really don't recommend even trying)

        addRequirements(m_extend, m_rotate, m_wrist);
       addCommands(stages);
    
        
    }
    
}
