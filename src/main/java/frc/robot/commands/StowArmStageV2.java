package frc.robot.commands;

import java.io.NotActiveException;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ArmExtensionSubsystem;
import frc.robot.subsystems.ArmRotationSubsystem;
import frc.robot.subsystems.ArmWristSubsystem;

@Deprecated
public class StowArmStageV2 extends CommandBase
{
    
    ArmExtensionSubsystem extendSubsystem;
    ArmRotationSubsystem rotateSubsystem;
    ArmWristSubsystem wristSubsystem;
    
    int wristPoint, extendPoint, rotatePoint;
    private int wristAllowedError = 750, extendAllowedError = 100, rotateAllowedError = 3;

    public void WithSetpoint()
    {
        return;
    }

    public StowArmStageV2(ArmExtensionSubsystem m_extend, ArmRotationSubsystem m_rotate, ArmWristSubsystem m_wrist, int extendPoint, int rotatePoint, int wristPoint) 
    {
        this.rotateSubsystem = m_rotate;
        this.extendSubsystem = m_extend;
        this.wristSubsystem = m_wrist;
        addRequirements(m_extend, m_rotate, m_wrist);
        this.wristPoint = wristPoint;
        this.rotatePoint = rotatePoint;
        this.extendPoint = extendPoint;


     //wrist.setWristToPosition(2000);
     
       
    
    }

    /* 
    private void ConfigureAllowedErrors()
    {
        return; //lazy
    }
    */
    
    @Override
    public void execute() {
        

        this.wristSubsystem.SetToPosition(wristPoint);
        this.rotateSubsystem.RotateArmToDeg(rotatePoint);
        this.extendSubsystem.SetToPosition(extendPoint);
        System.out.println("RUNNING STAGE ONE");

    }
    @Override
    public boolean isFinished() {

        boolean fin = 
        this.wristSubsystem.GetPosition() <= wristPoint + wristAllowedError &&
        this.rotateSubsystem.GetRotationInDeg() <= rotatePoint + rotateAllowedError &&
        this.extendSubsystem.GetPosition() <= extendPoint + extendAllowedError;

        if(fin)
        {
            System.out.println("ITS DONE");
        }

        return fin; 
     
    }

}
