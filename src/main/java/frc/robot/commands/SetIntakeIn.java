/*--------------------------------------------------------* 
 * 
 * 2023 Charged Up
 * 
--------------------------------------------------------*/
package frc.robot.commands;

import frc.robot.subsystems.IntakeSubsystem;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class SetIntakeIn extends CommandBase {

   private IntakeSubsystem m_intakeSubsystem;

   public SetIntakeIn(IntakeSubsystem subsystem) {
      m_intakeSubsystem = subsystem;
      //addRequirements(m_intakeWheelsSubsystem);
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
      // see above :)
      m_intakeSubsystem.intakeWheelsOff();
   }

   // Returns true when the command should end.
   @Override
   public boolean isFinished() {
      return false;
   }

}