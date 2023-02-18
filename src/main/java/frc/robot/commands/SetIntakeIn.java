/*--------------------------------------------------------* 
 * 
 * 2023 Charged Up
 * 
 * operate when the button is pressed,
 * stop when released
 * 
 * 
--------------------------------------------------------*/
package frc.robot.commands;

import frc.robot.subsystems.IntakeSubsystem;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class SetIntakeIn extends CommandBase {

   private IntakeSubsystem m_intakeSubsystem;

   public SetIntakeIn(IntakeSubsystem subsystem) {
      m_intakeSubsystem = subsystem;
      addRequirements(m_intakeSubsystem);
      addRequirements(subsystem);

   }

   // Called when the command is initially scheduled.
   @Override
   public void initialize() {
   }

   @Override
   public void execute() {
      m_intakeSubsystem.intakeWheelsIn();
   }

   @Override
   public void end(boolean interuppted) {
      m_intakeSubsystem.intakeWheelsOff();
   }

   // Returns true when the command should end.
   @Override
   public boolean isFinished() {
      return false;
   }

}