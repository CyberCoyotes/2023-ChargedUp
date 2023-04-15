package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants.Arm;
import frc.robot.subsystems.ArmExtensionSubsystem;

public class ExtendToFloor_v4 extends CommandBase {
    private final ArmExtensionSubsystem extensionSubsystem;
    
    private int target = Arm.EXTENSION_FLOOR_POS;
    private double target_min = target*.90;
    private double target_max = target*1.10;
    



    public ExtendToFloor_v4(ArmExtensionSubsystem extensionSubsystem, int encoderPosition) {
        this.extensionSubsystem = extensionSubsystem;
        this.target = target;
        addRequirements(extensionSubsystem);
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        extensionSubsystem.extendToFloor_v4();
    }

    @Override
    public boolean isFinished() {
        // Check later
        // if ( (extensionSubsystem.ReadExtension() >= target_min) && (extensionSubsystem.ReadExtension() <= target_max) ) {

        if (extensionSubsystem.ReadExtension() >= target_min) {
        return true;
        } else {
            return false;
        }

    }

    @Override
    public void end(boolean interrupted) {
        extensionSubsystem.stopExtension();
    }
}

