/*
 * "CubeMidTaxi.java"
 */
package frc.robot.autos;

import java.util.function.BooleanSupplier;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.TeleopSwerve;
import frc.robot.commands.cgCubeMid_ver3;
import frc.robot.subsystems.ArmExtensionSubsystem;
import frc.robot.subsystems.ArmSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.Swerve;
import frc.robot.subsystems.WristSubsystem;


public class CubeMidTaxi_version1 extends SequentialCommandGroup
{


    private Swerve m_swerve;
    private ArmSubsystem m_arm;
    private ArmExtensionSubsystem m_extend;
    private IntakeSubsystem m_intake;
    private WristSubsystem m_wrist;



    public CubeMidTaxi_version1(Swerve s_Swerve, ArmExtensionSubsystem extend, ArmSubsystem arm, IntakeSubsystem intake, WristSubsystem wrist, BooleanSupplier robotCentric) {

            this.m_swerve = s_Swerve; 
            this.m_arm = arm; 
            this.m_extend = extend; 
            this.m_wrist = wrist;
            this.m_intake = intake; 
        // addRequirements(null);


        short polarity = 1;
        double power = .4;
        double seconds = 3;
        // : 40% in a single direction for 1 second: ~51 inches        
        final float input = (float) (polarity * power);
        Command driveCommand;
    
            addRequirements(m_swerve, m_arm, m_extend, m_intake);
            
            driveCommand = new TeleopSwerve(
                m_swerve,
                () -> input,
                () -> 0,
                () -> 0,
                () -> robotCentric.getAsBoolean(),
                () -> false);
        

        addCommands(
            //just in case
            new cgCubeMid_ver3(m_arm, m_extend, m_wrist, m_intake ).withTimeout(7),
            driveCommand

        );
    }
}

//! TODO THE AUTO TUNER 