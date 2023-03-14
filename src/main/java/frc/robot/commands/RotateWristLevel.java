/*--------------------------------------------------------* 
 * 
 * 2023 Charged Up
 * commands/rotateArm
 * 
 * When button is pressed:
 ** Rotates the arm rotates "x" encoder marks to an almost deployed arm position
 ** Operator will deploy arm to final position
 * 
 * Single button will be a toggle between deploy position and intake position
 * 
--------------------------------------------------------*/
package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.WristSubsystem;

public class RotateWristLevel extends CommandBase {
      
    private final WristSubsystem m_wristSub;
   
    public RotateWristLevel(WristSubsystem subsystem) {
     m_wristSub = subsystem;
     addRequirements(m_wristSub); // references the variable above
    }

    @Override
    public void initialize() {
        // TODO Auto-generated method stub
        // super.initialize();
    }
    
    @Override
    public void execute() {
        m_wristSub.setWristPosLevel(Constants.WRIST_POS_LEVEL);
    }
    @Override
    public boolean isFinished() {
        return false;
    }

}