package frc.robot.commands;

import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Constants.Arm;
import frc.robot.subsystems.ArmExtensionSubsystem;
import frc.robot.subsystems.ArmSubsystem;
import frc.robot.subsystems.IntakeSubsystemV2;
import frc.robot.subsystems.Swerve;
import frc.robot.subsystems.WristSubsystem;


public class CubeMidTaxi extends SequentialCommandGroup
{


    private Swerve m_swerve;
    private ArmSubsystem m_arm;
    private ArmExtensionSubsystem m_extend;
    private IntakeSubsystemV2 m_intake;
    private WristSubsystem m_wrist;



    public CubeMidTaxi(Swerve s_Swerve, ArmExtensionSubsystem extend, ArmSubsystem arm, IntakeSubsystemV2 intake, WristSubsystem wrist, BooleanSupplier robotCentric) {

            this.m_swerve = s_Swerve; 
            this.m_arm = arm; 
            this.m_extend = extend; 
            this.m_intake = intake; 
        // addRequirements(null);


        short polarity = 1;
        double power = .4;
        double seconds = 3;
        // : 40% in a single direction for 1 second: ~51 inches        
        final float input = (float) (polarity * power);
        Command driveCommand;
    
            // addRequirements(m_swerve, m_arm, m_extend, m_intake);
            
            driveCommand = new TeleopSwerve(
                m_swerve,
                () -> input,
                () -> 0,
                () -> 0,
                () -> robotCentric.getAsBoolean(),
                () -> false);
        

        addCommands(
            //just in case
            new cgCubeTop(m_arm, m_extend, m_wrist, m_intake ).withTimeout(7),
            driveCommand

        );
    }
}

//! TODO THE AUTO TUNER 