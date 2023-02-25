package frc.robot;

import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ArmExtensionSubsystem;

public class ExtendArmManual extends CommandBase {

    
    // private BooleanSupplier retract;  for use with bumpers, rather than triggers
    // private BooleanSupplier retract;  for use with bumpers, rather than triggers
    private DoubleSupplier extend; //for use with bumpers, rather than triggers
    private DoubleSupplier retract; //for use with bumpers, rather than triggers
    private ArmExtensionSubsystem subsystem;
    public ExtendArmManual(ArmExtensionSubsystem subsystem, DoubleSupplier extend, DoubleSupplier retract) {
        this.subsystem = subsystem;
        this.extend = extend;
        this.retract = retract;
        this.addRequirements(subsystem);
    }
    // public ExtendArmManual(ArmExtensionSubsystem subsystem, BooleanSupplier extend, BooleanSupplier retract) {
    //     this.subsystem = subsystem;
    //     this.extend = extend;
    //     this.retract = retract;
    //     this.addRequirements(subsystem);
    // }
    @Override
    public void execute() {
        double input = extend.getAsDouble() + retract.getAsDouble() * -1; 
        subsystem.PercentOutputSupplierDrive(input * .3);
    }
    // @Override //for use with bumpers, rather than triggers
    // public void execute() {
    //     double input = (extend.getAsBoolean() ? 1 : 0 ) + (retract.getAsBoolean() ? -1: 0); 
    //     subsystem.PercentOutputSupplierDrive(input * .3);
    // }

}
