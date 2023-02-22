public class ArmLimitSwitch {

    private DigitalInput limitSwitch = new DigitalInput(DIGITAL_INPUT_PORT);

public boolean getLimitSwitchState() {
    return limitSwitch.get();
}
    
}

/*
 * Encoder encoder = new Encoder(0, 1);
Spark spark = new Spark(0);
// Limit switch on DIO 2
DigitalInput limit = new DigitalInput(2);
public void autonomousPeriodic() {
// Runs the motor backwards at half speed until the limit switch is pressed
// then turn off the motor and reset the encoder
if(!limit.get()) {
spark.set(-0.5);
} else {
spark.set(0);
encoder.reset();
}
}
 */

/*
 * Magnetic Limit Switch
 * https://www.revrobotics.com/rev-31-1462/
 */
