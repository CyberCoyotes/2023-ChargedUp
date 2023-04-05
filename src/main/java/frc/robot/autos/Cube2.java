/* Deposits Cone 1 Mid, pickups up Cone 2, deposits low; PathPlanner based drive */

package frc.robot.autos;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.CubeLow;
import frc.robot.commands.GroundCubePickup;
import frc.robot.subsystems.ArmExtensionSubsystem;
import frc.robot.subsystems.ArmRotationSubsystem;
import frc.robot.subsystems.ArmWristSubsystem;
import frc.robot.subsystems.IntakeSubsystem;

public class Cube2 extends SequentialCommandGroup
{
    private ArmRotationSubsystem m_arm;
    private ArmExtensionSubsystem m_extend;
    private IntakeSubsystem m_intake;
    private ArmWristSubsystem m_wrist;


    public Cube2(ArmExtensionSubsystem extend, ArmRotationSubsystem arm, IntakeSubsystem intake, ArmWristSubsystem wrist) {

            this.m_arm = arm; 
            this.m_extend = extend; 
            this.m_intake = intake;
            this.m_wrist = wrist;
    
            addRequirements();

        addCommands(
            new CubeLow(arm, extend, wrist, intake).withTimeout(1),
            new path1(),
            new path2(),
            new GroundCubePickup(arm, wrist, intake, extend),
            new path3(),
            new CubeLow(arm, extend, wrist, intake).withTimeout(1) // TODO Replace with Cube Low

            
        );
    }
}
