package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.RepeatCommand;
import frc.robot.Constants.Arm;
import frc.robot.subsystems.ArmExtensionSubsystem;
import frc.robot.subsystems.ArmSubsystem;
import frc.robot.subsystems.WristSubsystem;

public class StowArmCommand extends CommandBase {

    ArmExtensionSubsystem extendSubsystem;
    ArmSubsystem rotateSubsystem;
    WristSubsystem wristSubsystem;
    

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
        //if stage 1 isnt done
        if (!(extendSubsystem.ReadExtension() <= 2000) && wristSubsystem.getWristPos() <= 6000 )
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
        this.wristSubsystem.SetWristToTickPosition(2000);
        this.rotateSubsystem.RotateArmToDeg(50);
        this.wristSubsystem.setWristHome();

    }
    
    /**
     * The second stage of the stow command; Once the arm is retracted well enough, the arm will finish rotating into place.
     */
    private void Stage2()
    {

        this.wristSubsystem.SetWristToTickPosition(2000);
        this.rotateSubsystem.RotateArmToDeg(20);

    }
    @Override
    public boolean isFinished() {
        return 
            rotateSubsystem.GetRotationInDeg() < 22 
        &&  wristSubsystem.getWristPos() < 2200
        &&  extendSubsystem.ReadExtension() < 2200;
             

    }

}
