/*--------------------------------------------------------* 
 * 
 * 2023 Charged Up
 * IO.java
 * Separating Input and Output constants from drivetrain  
 * 
--------------------------------------------------------*/

package frc.robot;

public final class IO {

    public final double ARM_POSITION_DEPLOY = 1000; // TODO TBD experimentally
    double ARM_POSITION_INTAKE; // TODO TBD experimentally
    double ARM_CURRENT_POSITION;
    
    double EXTENSION_POSITION_OUT; // TODO TBD experimentally
    double EXENTSION_POSITION_IN; // TODO TBD experimentally

    double CLAW_OPEN;
    double CLAW_CLOSED;

    boolean LED_CONE_LOCK; 
    // double LED_CONE_MISSING;
    double LED_CUBE_LOCK;

    public static final int VISION_LED_OFF = 1;
    public static final int VISION_LED_ON = 2;

     // Needed for LED
     public static final class PWMPorts {
        public static final int kBlinkin = 0;
    }
}