package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ArmExtensionSubsystem;
import frc.robot.subsystems.ArmRotationSubsystem;
import frc.robot.subsystems.ArmWristSubsystem;

public class ArmSetpoint extends CommandBase
{
    
    ArmExtensionSubsystem extendSubsystem;
    ArmRotationSubsystem rotateSubsystem;
    ArmWristSubsystem wristSubsystem;
    
    int wristPoint, extendPoint, rotatePoint;
    private int wristAllowedError = 750, extendAllowedError = 100, rotateAllowedError = 3;

    public void SetWristError(int arg)
    {
        this.wristAllowedError = arg;
    }
    public void SetExtendError(int arg)
    {
        this.extendAllowedError = arg;
    }
    public void SetRotationError(int arg)
    {
        this.rotateAllowedError = arg;
    }
    public ArmSetpoint WithAllowedErrors(int extend, int rotation, int wrist)
    {
        SetExtendError(extend);
        SetWristError(wrist);
        SetRotationError(rotation);
        return this;
    }


    public ArmSetpoint(ArmExtensionSubsystem m_extend, ArmRotationSubsystem m_rotate, ArmWristSubsystem m_wrist, int extendPoint, int rotatePoint, int wristPoint) 
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

        //V2 as of 4-10 5-ish; now judges actual error, rather than "if really less then its done"

        boolean fin = 
        Math.abs(wristPoint  - wristSubsystem.getWristPos() )<=wristAllowedError  &&
        Math.abs(rotatePoint -  rotateSubsystem.GetRotationInDeg()) <=rotateAllowedError &&
        Math.abs(extendPoint - extendSubsystem.ReadExtension() ) <=extendAllowedError ;

        if(fin)
        {
            System.out.println("ITS DONE");
        }

        return fin; 
     
    }

}
