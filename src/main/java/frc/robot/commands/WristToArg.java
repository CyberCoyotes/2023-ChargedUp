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
import frc.robot.subsystems.WristSubsystem;

public class WristToArg extends CommandBase {
   
    private final WristSubsystem m_wristSubsystem;
    private final double target;
    
    public WristToArg(WristSubsystem subsystem, double target) {

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
        //// m_armExtensionSubsystem.Setup();
    }
    
    @Override
    public void execute() {
        m_wristSubsystem.SetWristToTickPosition(target);
    }
    @Override
    public InterruptionBehavior getInterruptionBehavior() {
        return InterruptionBehavior.kCancelSelf;
    }

}
