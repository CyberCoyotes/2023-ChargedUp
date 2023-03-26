package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ArmExtensionSubsystem;
import frc.robot.subsystems.ArmSubsystem;
import frc.robot.subsystems.WristSubsystem;

public class StowArmCommand extends CommandBase {

    ArmExtensionSubsystem extendSubsystem;
    ArmSubsystem rotateSubsystem;
    WristSubsystem wristSubsystem;
    

    private int wrist, rot;

    public boolean IsRunningStage1()
    {
        return (!((extendSubsystem.ReadExtension() <= 2000)) || !(wristSubsystem.getWristPos() <= 6000 )); 
    }
    public void GetData()
    {    
        System.out.println("extend safe " + ((extendSubsystem.ReadExtension() <= 2000)));
        System.out.println("wrist safe "  + (wristSubsystem.getWristPos() <= 6000 ));
    }

    public StowArmCommand(ArmExtensionSubsystem m_extend, ArmSubsystem m_rotate, WristSubsystem m_wrist) 
    {
        this.rotateSubsystem = m_rotate;
        this.extendSubsystem = m_extend;
        this.wristSubsystem = m_wrist;
        addRequirements(m_extend, m_rotate, m_wrist);

     //wrist.setWristToPosition(2000);
     
       
    
    }
    
    @Override
    public void execute() {


        boolean extendSafe = (extendSubsystem.ReadExtension() <= 2000);
        boolean wristSafe = wristSubsystem.getWristPos() <= 6000 ;




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

        this.wristSubsystem.SetWristToTickPosition(wrist);
        this.rotateSubsystem.RotateArmToDeg(rot);
        // this.wristSubsystem.setWristHome();
        this.extendSubsystem.SetArmToTickPosition(2000);
        System.out.println("RUNNING STAGE ONE");

    }
    
    /**
     * The second stage of the stow command; Once the arm is retracted well enough, the arm will finish rotating into place.
     */
    private void Stage2()
    {

        wrist = 2000;
        rot = 25;

        this.wristSubsystem.SetWristToTickPosition(wrist);
        this.rotateSubsystem.RotateArmToDeg(rot);
        System.out.println("RUNNING STAGE TWO TWO TWO");

    }
    @Override
    public boolean isFinished() {
        return 
            rotateSubsystem.GetRotationInDeg() < 22 
        &&  wristSubsystem.getWristPos() < 2200
        &&  extendSubsystem.ReadExtension() < 2200;
             

    }

}
