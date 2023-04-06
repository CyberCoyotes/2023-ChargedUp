/* Deposits Cone 1 Mid, pickups up Cone 2, deposits low; PathPlanner based drive */

package frc.robot.autos;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.CubeLowCG;
import frc.robot.commands.PickupGroundCube;
import frc.robot.commands.StowArmStage;
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
            new CubeLowCG(arm, extend, wrist, intake).withTimeout(1),
            new path1(), // Path to Cube 2
            new PickupGroundCube(arm, wrist, intake, extend), // 3 (s) is current run time
            new StowArmStage(m_extend, m_arm, wrist, 2000, 50, 500),
            new StowArmStage(m_extend, m_arm, wrist, 2000, 30, 500),
            new path3(), // Path back to deposit Cube 2
            new CubeLowCG(arm, extend, wrist, intake).withTimeout(1)
            
        );
    }
}
