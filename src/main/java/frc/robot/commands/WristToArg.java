/*--------------------------------------------------------* 
 * 
 * 2023 Charged Up
 * commands/extendArm
 * 
 * Function: Extends the arm for a high score
 * 
--------------------------------------------------------*/
package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ArmWristSubsystem;

public class WristToArg extends CommandBase {
   
    private final ArmWristSubsystem m_wristSubsystem;
    private final int target;
    
    public WristToArg(ArmWristSubsystem subsystem, int target) {

     m_wristSubsystem = subsystem;
     this.target = target;
     addRequirements(m_wristSubsystem);
    }

    @Override
    public boolean isFinished() {
        return false;
    }
    @Override
    public void initialize() {
        super.initialize();
    }
    
    @Override
    public void execute() {
        m_wristSubsystem.setWristToPosition(target);
    }
    @Override
    public InterruptionBehavior getInterruptionBehavior() {
        return InterruptionBehavior.kCancelSelf;
    }

}
