/*--------------------------------------------------------* 
 * 
 * 2023 Charged Up
 * 
--------------------------------------------------------*/
package frc.robot.commands;

import frc.robot.subsystems.IntakeWheelsSubsystem;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class SetIntakeWheelsIn extends CommandBase {

   private final IntakeWheelsSubsystem m_intakeWheelsSubsystem;

   public SetIntakeWheelsIn(IntakeWheelsSubsystem subsystem) {
      // super();

      m_intakeWheelsSubsystem = subsystem;
      //addRequirements(m_intakeWheelsSubsystem);
      addRequirements(m_intakeWheelsSubsystem);

   }

   // Called when the command is initially scheduled.
   @Override
   public void initialize() {
   }

   @Override
   public void execute() {
      m_intakeWheelsSubsystem.intakeWheelsOn();
   }

   @Override
   public void end(boolean interuppted) {
      // see above :)
      m_intakeWheelsSubsystem.intakeWheelsOff();
   }

   // Returns true when the command should end.
   @Override
   public boolean isFinished() {
      return false;
   }

}
