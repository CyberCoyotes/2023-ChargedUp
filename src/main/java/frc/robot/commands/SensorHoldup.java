package frc.robot.commands;

import java.util.function.DoubleConsumer;
import java.util.function.DoubleSupplier;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj2.command.CommandBase;

/**
 * 
 *3370? 3317?
 * 
 */
public class SensorHoldup extends CommandBase {
    //TODO get this to work    
    private DoubleSupplier positionSupplier;
    
    private double desiredPosition;

    private double inputStep;
    private DoubleConsumer inputGlutton;
    public SensorHoldup(DoubleSupplier sensorPositionSupplier, double desiredPosition) {
        
        
        this.positionSupplier = sensorPositionSupplier;    
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
