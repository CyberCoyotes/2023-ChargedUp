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

import frc.robot.subsystems.IntakeSubsystemV2;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class SetIntakeCube extends CommandBase {

   private final IntakeSubsystemV2 m_intakeSubsystem; // made into a final

   public SetIntakeCube(IntakeSubsystemV2 subsystem) {
      m_intakeSubsystem = subsystem;
      addRequirements(m_intakeSubsystem);
   }

   // Called when the command is initially scheduled.
   @Override
   public void initialize() {
      m_intakeSubsystem.SetDriveOutake(); // moved from execute

   }

   @Override
   public void execute() {
   }

   @Override
   public void end(boolean interuppted) {
      m_intakeSubsystem.ShutUp();
   }

   // Returns true when the command should end.
   /* Commented out
   @Override
   public boolean isFinished() {
      return false;
   }
   */

}
