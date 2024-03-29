package frc.robot.commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ArmWristSubsystem;

public class MoveWristManual extends CommandBase {

    private final ArmWristSubsystem m_Wrist;
    private final DoubleSupplier input;
    public MoveWristManual(ArmWristSubsystem subsystem, DoubleSupplier supplier) {
     m_Wrist = subsystem;
     addRequirements(m_Wrist);
     this.input = supplier;
    }    
    @Override
    public void execute() {
        m_Wrist.PercentOutputSupplierDrive(input.getAsDouble());
    }
    

    
}
