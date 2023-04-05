/* Deposits Cone 1 Mid, pickups up Cone 2, deposits low; PathPlanner based drive */

package frc.robot.autos;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.CubeLowCG;
import frc.robot.commands.PickupGroundCube;
import frc.robot.subsystems.ArmExtensionSubsystem;
import frc.robot.subsystems.ArmRotationSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.ArmWristSubsystem;

public class ppCube2 extends SequentialCommandGroup
{
    private ArmRotationSubsystem m_arm;
    private ArmExtensionSubsystem m_extend;
    private IntakeSubsystem m_intake;
    private ArmWristSubsystem m_wrist;


    public ppCube2(ArmExtensionSubsystem extend, ArmRotationSubsystem arm, IntakeSubsystem intake, ArmWristSubsystem wrist) {

            this.m_arm = arm; 
            this.m_extend = extend; 
            this.m_intake = intake;
            this.m_wrist = wrist;
    
            addRequirements();

        addCommands(
            new CubeLowCG(arm, extend, wrist, intake).withTimeout(1), // time = 1 (s)
            new ppCube2_p1(),                                               // If v = 4, a = 2: time = 3.34 (s)
            new ppCube2_p2(),                                               // If v = 4, a = 1: time = 1.89 (s)
            new PickupGroundCube(arm, wrist, intake, extend),               // time = 3.00 (s)
            new ppCube2_p3(),                                               // If v = 4,a = 2: time = 3.26 (s)
            new CubeLowCG(arm, extend, wrist, m_intake).withTimeout(1) // time = 1 (s)
             
        );
    }
}
