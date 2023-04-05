package frc.robot.commands;

import java.util.function.DoubleConsumer;
import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;

/**
 * 
 *3370? 3317?
 * 
 */
public class MoveUntilSensor extends CommandBase {
    // get this to work    
    private DoubleSupplier positionSupplier;
    
    private double desiredPosition;

    private double inputStep;
    private DoubleConsumer inputGlutton;
    public MoveUntilSensor(DoubleSupplier sensorPositionSupplier, DoubleConsumer InputHere, double desiredPosition, double input) {
        
        this.inputGlutton = InputHere;
        this.inputStep = input;
        this.positionSupplier = sensorPositionSupplier;    
    }
    @Override
    public void execute() {
        //inputs our desisred power into the thing
        inputGlutton.accept(inputStep);
    }
    @Override
    public boolean isFinished() {


        //trial: moving angle to 90 degrees; on this cycle, we get 85 degrees.
        //360 / 100 = 3.6
        // (90 - 85) = 5 > 3.6
        //not close enough


        double nearness = desiredPosition / 100;
        return Math.abs(desiredPosition - positionSupplier.getAsDouble()) < nearness;
    }
}
