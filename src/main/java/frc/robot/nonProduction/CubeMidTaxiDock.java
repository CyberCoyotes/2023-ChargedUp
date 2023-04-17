/* 
 * Original "CubeLowTaxiDock.java"
*/
package frc.robot.nonProduction;

import java.util.function.BooleanSupplier;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.TeleopSwerve;
import frc.robot.subsystems.ArmExtensionSubsystem;
import frc.robot.subsystems.ArmRotationSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.Swerve;
import frc.robot.subsystems.ArmWristSubsystem;


public class CubeMidTaxiDock extends SequentialCommandGroup
{


    private Swerve m_swerve;
    private ArmRotationSubsystem m_arm;
    private ArmExtensionSubsystem m_extend;
    private IntakeSubsystem m_intake;
    private ArmWristSubsystem m_wrist;



    public CubeMidTaxiDock(Swerve s_Swerve, ArmExtensionSubsystem extend, ArmRotationSubsystem arm, IntakeSubsystem intake, ArmWristSubsystem wrist, BooleanSupplier robotCentric) {

            this.m_swerve = s_Swerve; 
            this.m_arm = arm; 
            this.m_extend = extend; 
            this.m_intake = intake; 



        short polarity = 1;
        double power = .4;
        double seconds = 2.12;
        // : 40% in a single direction for 1 second: ~51 inches        
        final float input = (float) (polarity * power);
        Command driveCommand;
    
            addRequirements(m_swerve);
            
            driveCommand = new TeleopSwerve(
                m_swerve,
                () -> input,
                () -> 0,
                () -> 0,
                () -> robotCentric.getAsBoolean(),
                () -> false);
         
                //Now trying to dock
                Command driveCommandReverse = new TeleopSwerve(
                    m_swerve,
                    () -> input,
                    () -> 0,
                    () -> 0,
                    () -> robotCentric.getAsBoolean(),
                    () -> false);
            

        addCommands(
            //just in case
            // new cgCubeLow(m_arm, m_extend, m_wrist, m_intake ).withTimeout(5),
            driveCommand.withTimeout(seconds),
            driveCommandReverse.withTimeout(seconds -2)


        );
    }
}

//! THE AUTO TUNER 