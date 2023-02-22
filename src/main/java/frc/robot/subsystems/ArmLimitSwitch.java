public class ArmLimitSwitch {

    private DigitalInput limitSwitch = new DigitalInput(DIGITAL_INPUT_PORT);

public boolean getLimitSwitchState() {
    return limitSwitch.get();
}
    
}

/*
 * Magnetic Limit Switch
 * https://www.revrobotics.com/rev-31-1462/
 */