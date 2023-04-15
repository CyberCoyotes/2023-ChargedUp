package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.ArmExtensionSubsystem;
import frc.robot.subsystems.ArmRotationSubsystem;
import frc.robot.subsystems.ArmWristSubsystem;
import frc.robot.subsystems.IntakeSubsystem;

public class whyme extends SequentialCommandGroup
{
    
    private ArmRotationSubsystem m_arm;
    private ArmExtensionSubsystem m_extend;
    private IntakeSubsystem m_intake;
    private ArmWristSubsystem m_wrist;


 public whyme(ArmExtensionSubsystem extend, ArmRotationSubsystem arm, IntakeSubsystem intake, ArmWristSubsystem wrist) {



    this.m_arm = arm; 
    this.m_extend = extend; 
    this.m_intake = intake;
    this.m_wrist = wrist;

    addRequirements();

   addCommands
   (
    //effectively covers putting the arm into position and then bringing it back into stow
    new PickupGroundCubeV2(arm, wrist, intake, extend), // 3 (s) is current run time
    new ArmSetpoint(m_extend, m_arm, wrist, 2000, 50, 500),
    new ArmSetpoint(m_extend, m_arm, wrist, 2000, 30, 500) 
   );
 }   



}
