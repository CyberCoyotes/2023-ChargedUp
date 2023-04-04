/*
 * "CubeMidTaxi.java"
 */
package frc.robot.autos;

import java.util.function.BooleanSupplier;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.TeleopSwerve;
import frc.robot.commands.CubeMid;
import frc.robot.commands.CubeMidOld;
import frc.robot.subsystems.ArmExtensionSubsystem;
import frc.robot.subsystems.ArmRotationSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.Swerve;
import frc.robot.subsystems.ArmWristSubsystem;


public class CubeMidTaxi_version1 extends SequentialCommandGroup
{


    private Swerve m_swerve;
    private ArmRotationSubsystem m_arm;
    private IntakeSubsystem m_intake;
    private ArmWristSubsystem m_wrist;



    public CubeMidTaxi_version1(Swerve s_Swerve, ArmExtensionSubsystem extend, ArmRotationSubsystem arm, IntakeSubsystem intake, ArmWristSubsystem wrist, BooleanSupplier robotCentric) {

            this.m_swerve = s_Swerve; 
            this.m_arm = arm; 
            this.m_wrist = wrist;
            this.m_intake = intake; 
        // addRequirements(null);


        short polarity = 1;
        double power = .4;
        double seconds = 3;
        // : 40% in a single direction for 1 second: ~51 inches        
        final float input = (float) (polarity * power);
        Command driveCommand;
    
            addRequirements(m_swerve, m_arm, m_intake);
            
            driveCommand = new TeleopSwerve(
                m_swerve,
                () -> input,
                () -> 0,
                () -> 0,
                () -> robotCentric.getAsBoolean(),
                () -> false);
        

        addCommands(
            //just in case
            new CubeMid(arm, wrist, intake).withTimeout(7),
            driveCommand

        );
    }
}

//! TODO THE AUTO TUNER 