package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.subsystems.WristSubsystem;

public class ReadyForCargoCommand extends SequentialCommandGroup {
   
    private WristSubsystem wristSub;

   public ReadyForCargoCommand(WristSubsystem wrist) {
    this.wristSub = wrist;
    addRequirements(wristSub);
    addCommands(new WaitCommand(.25), new WristToArg(wristSub, 6000));
   }
   @Override
   public InterruptionBehavior getInterruptionBehavior() {
       return InterruptionBehavior.kCancelSelf;
   }
    
}
