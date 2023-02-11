/*--------------------------------------------------------* 
 * 
 * 2023 Charged Up
 * Blinkin.java
 *  
--------------------------------------------------------*/
package frc.robot.subsystems;

import frc.robot.Robot;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.motorcontrol.Spark;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Blinkin extends SubsystemBase {

    /*
     * Rev Robotics Blinkin takes a PWM signal from 1000-2000us
     * This is identical to a SparkMax motor.
     * -1 corresponds to 1000us
     * 0 corresponds to 1500us
     * +1 corresponds to 2000us
     */
    public static Spark m_blinkin = null;
    // private Vision m_limelight;

    /**
     * Creates a new Blinkin LED controller.
     * 
     * @param pwmPort The PWM port the Blinkin is connected to.
     */
    public Blinkin(int pwmPort) {
        m_blinkin = new Spark(pwmPort);

    }

    /*
     * Set the color and blink pattern of the LED strip.
     * Consult the Rev Robotics Blinkin manual Table 5 for a mapping of values to
     * patterns.
     * 
     * @param val The LED blink color and patern value [-1,1]
     * 
     */

    public void set(double val) {
        if ((val >= -1.0) && (val <= 1.0)) {
            m_blinkin.set(val);
        }
    }

    public void rainbow() {
        set(-0.99);
    }

    public void oceanPallete() {
        set(-0.75);
    }

    public void waveOcean() {
        set(-0.41);
    }

    // Color Waves , Forest
    public void waveForest() {
        set(-0.37);
    }

    public void chaseRed() {
        set(-0.31);
    }

    public void chaseBlue() {
        set(-0.29);
    }

    public void blend_to_black() {
        set(-0.03);
    }

    public void darkRed() {
        set(0.59);
    }

    public void gold() {
        set(0.67);
    }

    public void yellow() {
        set(0.69);
    }

    public void lawnGreen() {
        set(0.71);
    }

    public void darkGreen() {
        set(0.75);
    }

    public void darkBlue() {
        set(0.85);
    }

    public void violet() {
        set(0.91);
    }

    public void allianceColor() {
        boolean isRed = NetworkTableInstance.getDefault().getTable("FMSInfo").getEntry("IsRedAlliance")
                .getBoolean(true);
        if (isRed == true) {
            this.chaseRed();// WAS RobotContainer.m_blinkin
            System.out.println("Red Alliance");
        } else {
            this.chaseBlue();// WAS RobotContainer.m_blinkin
            System.out.println("Blue Alliance");
        }
    }

} // end of class

/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved. */
/* Open Source Software - may be modified and shared by FRC teams. The code */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project. */
/*----------------------------------------------------------------------------*/
