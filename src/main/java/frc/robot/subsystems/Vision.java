/*--------------------------------------------------------* 
 * 
 * 2023 Charged Up
 * subsystems/Vision.java 
 * 
--------------------------------------------------------*/

package frc.robot.subsystems;

import java.util.ArrayList;
import static frc.robot.IO.VISION_LED_OFF;
import static frc.robot.IO.VISION_LED_ON;

import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

public class Vision extends SubsystemBase {
    
    private final NetworkTable limelight;// Table for the limelight
    private final NetworkTableEntry led_mode;// Table for the limelight

    public double tx;
    private double ty;
    private double ta;
    private double tv;
    private double tid;

    double areaMin = 5; // Smaller number means farther away
    boolean checkTagID;
    boolean checkArea;
    boolean checkSkew;
    boolean nominalTarget; // Area + Tag ID

    /**
     * Creates a new Vision.
     */
    public Vision() {

        limelight = NetworkTableInstance.getDefault().getTable("limelight");// Instantiate the tables
        // m_targetList = new ArrayList<Double>(MAX_ENTRIES);
        led_mode = limelight.getEntry("ledMode"); //

    }

    public void periodic() {
        // This method will be called once per scheduler run
        // Lambda Corp calls in a periodic

        tx = limelight.getEntry("tx").getDouble(0);
        ty = limelight.getEntry("ty").getDouble(0);
        tv = limelight.getEntry("tv").getDouble(0);
        ta = limelight.getEntry("ta").getDouble(0);
        tid = limelight.getEntry("tid").getDouble(0);

        // post to smart dashboard periodically
        SmartDashboard.putNumber("Limelight X", tx);
        SmartDashboard.putNumber("Limelight Y", ty);
        SmartDashboard.putNumber("Limelight Area", ta);
        SmartDashboard.putNumber("April Tag", tid);
        SmartDashboard.putBoolean("V Nominal Tag Sequence", checkTagID); // Works
        SmartDashboard.putBoolean("V Nominal Area", checkArea); // Works
        SmartDashboard.putBoolean("V Nominal Target", nominalTarget); // Works

    }

    public double getTID() {
        return tid;
    }

    /**
     * This function returns the target's side-to-side angle from the center of the
     * Limelight's field of view
     * 
     * @return x-angle of the target in degrees
     */
    public double getX() {
        return tx;
    }

    /**
     * This function returns the target's up-down angle from the center of the
     * Limelight's field of view
     * 
     * @return y-angle of the target in degrees
     */
    public double getY() {
        return ty;
    }

    public double getV() {
        return tv;
    }

    /**
     * This function returns the target's area in the image
     * 
     * @return Area of the target in pixels
     */
    public double getArea() {
        return ta;
    }

    // Example from Limelight Docs
    // NetworkTableInstance.getDefault().getTable("limelight").getEntry("<variablename>").setNumber(<value>);

    public void offLEDMode() {
        NetworkTableInstance.getDefault().getTable("limelight").getEntry("ledMode").setNumber(1);

        /*
         * ledMode Sets limelight’s LED state
         * 0 use the LED Mode set in the current pipeline
         * 1 force off
         * 2 force blink
         * 3 force on
         */
    }

    public void setLEDmode(int mode) {
        led_mode.setDouble(mode); // changed from setForceDouble
    }

    /**
     * Check for Tag ID for range 1 to 8 except 4 & 5
     **/
    public boolean checkTagID() {
        if (tid == 1 || tid == 2 || tid == 3 || tid == 6 || tid == 7 || tid == 8) {
            checkTagID = true; // Table is returning correcting but visual is not
        } else {
            checkTagID = false;
        }
        return checkTagID;
    }

    /**
     * Check for tArea minimum of target.
     * Consider using other distances (Y or Z)
     **/
    public boolean checkArea() {
        if (ta > areaMin) {
            checkArea = true;
        } else {
            checkArea = false;
        }
        return checkArea; // returns true or false, combine with Tag ID
    }

    /* 
     * Doesn't seem to be returning properly from subsystem
     * Errors may have been battery related
     * @return nominalTarget
    */
    public boolean nominalTarget() {
        if ((checkArea = true) && (checkTagID = true)) {
            nominalTarget = true;
        } else {
            nominalTarget = false;
        }
        return nominalTarget;
    }

    

} // end of class