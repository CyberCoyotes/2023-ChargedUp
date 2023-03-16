package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.RepeatCommand;
import frc.robot.subsystems.ArmExtensionSubsystem;
import frc.robot.subsystems.ArmSubsystem;
import frc.robot.subsystems.WristSubsystem;

/**
 * Stows the Arm Totally
 * 
 * 
 */
public class StowWristCommand extends RepeatCommand
{
    private WristSubsystem wrist;
    ArmSubsystem armRot;
    ArmExtensionSubsystem extend;
 StowWristCommand(WristSubsystem wrist, ArmSubsystem armRot, ArmExtensionSubsystem extend)
 {
    this.wrist = wrist;
    this.armRot = armRot;
    this.extend = extend;
    addRequirements(wrist, armRot, extend);
 }   



 @Override
 public void execute() {
     // TODO Auto-generated method stub
     wrist.setWristToPosition(2000);
 }
}
