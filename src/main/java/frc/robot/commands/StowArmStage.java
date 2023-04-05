package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ArmExtensionSubsystem;
import frc.robot.subsystems.ArmRotationSubsystem;
import frc.robot.subsystems.ArmWristSubsystem;

public class StowArmStage extends CommandBase
{
    
    ArmExtensionSubsystem extendSubsystem;
    ArmRotationSubsystem rotateSubsystem;
    ArmWristSubsystem wristSubsystem;
    
    int wristPoint, extendPoint, rotatePoint;
    private int wristAllowedError = 750, extendAllowedError = 100, rotateAllowedError = 3;



    public StowArmStage(ArmExtensionSubsystem m_extend, ArmRotationSubsystem m_rotate, ArmWristSubsystem m_wrist, int extendPoint, int rotatePoint, int wristPoint) 
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
        

        this.wristSubsystem.setWristToPosition(wristPoint);
        this.rotateSubsystem.RotateArmToDeg(rotatePoint);
        this.extendSubsystem.SetArmToTickPosition(extendPoint);
        System.out.println("RUNNING STAGE ONE");

    }
    @Override
    public boolean isFinished() {

        boolean fin = 
        this.wristSubsystem.getWristPos() <= wristPoint + wristAllowedError &&
        this.rotateSubsystem.GetRotationInDeg() <= rotatePoint + rotateAllowedError &&
        this.extendSubsystem.ReadExtension() <= extendPoint + extendAllowedError;

        if(fin)
        {
            System.out.println("ITS DONE");
        }

        return fin; 
     
    }

}
