/*--------------------------------------------------------* 
 * 
 * 2023 Charged Up
 * 
 * Sensors.java
 * 
 * --------------------------------------------------------*/
package frc.robot.subsystems;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class SensorsSubsystem extends SubsystemBase {

    public DigitalInput armSwitch = new DigitalInput(1);

    public SensorsSubsystem() {
    }

    /* SmartDashboard calls this from robot container 
     * Maybe also needed for other methods and commands?
    */

    public boolean getLimitSwitchState() {
        return !armSwitch.get();//Pull-up; naturally returns false when touching, so we invert for user-friendly-ness
    }

    


}


/*
 * Magnetic Limit Switch
 * https://www.revrobotics.com/rev-31-1462/
 * 
 * Hardware
 * https://docs.wpilib.org/en/stable/docs/hardware/sensors/digital-inputs-hardware.html 
 * https://docs.wpilib.org/en/stable/docs/software/hardware-apis/sensors/limit-switch.html
 * 
 * https://www.chiefdelphi.com/t/rev-magnetic-limit-switches/365147
 * 
 * Software
 * https://docs.wpilib.org/en/stable/docs/software/hardware-apis/sensors/limit-switch.html
 * https://docs.wpilib.org/en/stable/docs/software/dashboards/smartdashboard/displaying-expressions.html 
 * 
 */
