package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.subsystems.ArmWristSubsystem;

public class ReadyForCargoCommand extends SequentialCommandGroup {
   
    private ArmWristSubsystem wristSub;

   public ReadyForCargoCommand(ArmWristSubsystem wrist) {
    this.wristSub = wrist;
    addRequirements(wristSub);
    addCommands(new WaitCommand(.25), new WristToArg(wristSub, 6000));
   }
   @Override
   public InterruptionBehavior getInterruptionBehavior() {
       return InterruptionBehavior.kCancelSelf;
   }
    
}
