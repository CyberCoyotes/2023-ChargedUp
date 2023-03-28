package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.Constants.Arm;
import frc.robot.subsystems.ArmExtensionSubsystem;
import frc.robot.subsystems.ArmRotationSubsystem;
import frc.robot.subsystems.ArmWristSubsystem;

public class ResetArmCommand extends InstantCommand{
    
    ArmRotationSubsystem arm;
    ArmWristSubsystem wrist; 
    ArmExtensionSubsystem extend;
    @Override
    public void execute() {

arm.getCurrentCommand().cancel();
wrist.getCurrentCommand().cancel();
        extend.getCurrentCommand().cancel();

    }
    public ResetArmCommand(ArmRotationSubsystem arm, ArmWristSubsystem wrist, ArmExtensionSubsystem extend) {
        this.arm = arm;
this.wrist = wrist;
        this.extend = extend;
        addRequirements(this.arm, this.extend, this.wrist);
    }
}
