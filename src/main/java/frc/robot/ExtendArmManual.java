package frc.robot;

import java.util.function.BooleanSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ArmExtensionSubsystem;

public class ExtendArmManual extends CommandBase {

    
    private BooleanSupplier retract;
    private BooleanSupplier extend;
    private ArmExtensionSubsystem subsystem;
    public ExtendArmManual(ArmExtensionSubsystem subsystem, BooleanSupplier extend, BooleanSupplier retract) {
        this.subsystem = subsystem;
        this.extend = extend;
        this.retract = retract;
        this.addRequirements(subsystem);
    }
    @Override
    public void execute() {
        double input = (extend.getAsBoolean() ? 1 : 0 ) + (retract.getAsBoolean() ? -1: 0); 
        subsystem.PercentOutputSupplierDrive(input * .3);
    }

}
