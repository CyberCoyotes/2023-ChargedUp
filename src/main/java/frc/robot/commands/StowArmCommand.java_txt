package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ArmExtensionSubsystem;
import frc.robot.subsystems.ArmRotationSubsystem;
import frc.robot.subsystems.ArmWristSubsystem;



/**
 * Stows the arm asyncronously. <b>No built-in timeout</b>, so an arm cancel or decorating timeout is recommended. 
 * @deprecated Doesn't play well with WPILib, and should be replaced by {@link StowArmCG}.
 */
@Deprecated
public class StowArmCommand extends CommandBase {

    ArmExtensionSubsystem extendSubsystem;
    ArmRotationSubsystem rotateSubsystem;
    ArmWristSubsystem wristSubsystem;
    

    private int wrist, rot;

    public boolean IsRunningStage1()
    {
        return (!((extendSubsystem.GetPosition() <= 2000)) || !(wristSubsystem.GetPosition() <= 6000 )); 
    }
    public void GetData()
    {    
        System.out.println("extend safe " + ((extendSubsystem.GetPosition() <= 2000)));
        System.out.println("wrist safe "  + (wristSubsystem.GetPosition() <= 6000 ));
    }

    public StowArmCommand(ArmExtensionSubsystem m_extend, ArmRotationSubsystem m_rotate, ArmWristSubsystem m_wrist) 
    {
        this.rotateSubsystem = m_rotate;
        this.extendSubsystem = m_extend;
        this.wristSubsystem = m_wrist;
        addRequirements(m_extend, m_rotate, m_wrist);

     //wrist.setWristToPosition(2000);
     
       
    
    }
    
    @Override
    public void execute() {


        boolean extendSafe = (extendSubsystem.GetPosition() <= 2000);
        boolean wristSafe = wristSubsystem.GetPosition() <= 6000 ;




        //if stage 1 isnt done
        if (!extendSafe || !wristSafe )
        {
            Stage1();
        }
        else
        {
            Stage2();
        }
    }
    /**
     * The first stage of the stow command; runs until the extent of the arm is sufficently stowed
     */
    private void Stage1()
    {
        wrist = 2000;
        rot = 50;

        this.wristSubsystem.SetToPosition(wrist);
        this.rotateSubsystem.RotateArmToDeg(rot);
        // this.wristSubsystem.setWristHome();
        this.extendSubsystem.SetToPosition(2000);
        System.out.println("RUNNING STAGE ONE");

    }
    
    /**
     * The second stage of the stow command; Once the arm is retracted well enough, the arm will finish rotating into place.
     */
    private void Stage2()
    {

        wrist = 2000;
        rot = 25;

        this.wristSubsystem.SetToPosition(wrist);
        this.rotateSubsystem.RotateArmToDeg(rot);
        System.out.println("RUNNING STAGE TWO TWO TWO");

    }
    @Override
    public boolean isFinished() {
        return 
            rotateSubsystem.GetRotationInDeg() < 22 
        &&  wristSubsystem.GetPosition() < 2200
        &&  extendSubsystem.GetPosition() < 2200;
             

    }

}
