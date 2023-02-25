package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Vision;

public class GetTagID extends CommandBase {  

  private final Vision m_vision; // made into a final

  public GetTagID(Vision subsystem) {
     m_vision = subsystem;
     addRequirements(m_vision);
  }
  @Override
  public void initialize() {
     m_vision.getTID();

  }

  @Override
  public void execute() {
  }

  
}
