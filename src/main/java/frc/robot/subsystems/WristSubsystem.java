package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Constants.*;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.TalonFXControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;

/*
 * DATA COLLECTION
 * Rot  | Wrist     | Notes
 * -------------------------------------
 * 0    | 00,000    | Home position
 * 0    | 11,000    | Wrist for Loading
 * 0    | 19,000    | Wrist is level
 * 0    | 44,000    | Wrist is resting, flipped down (bad position)
 * 10   | 22,000    | Wrist is level
 * -90  | 72,250    | Position to deploy mid level cone. Also needs full extension
 */

public class WristSubsystem extends SubsystemBase {

    private TalonFX m_motorController = new TalonFX(Arm.WRIST_TALONFX_ID);

    /* If ARM ROTATION = 10 or less, then setWristHome() */
    /*
     * 
     * TODO test encoder values 
     * (direction of sensors relative to positive motor input), limits
     * TODO Find comfortable input value, create virtual speed limiter
     * 
     */
    public WristSubsystem() {

        m_motorController.config_kP(0,1);
        m_motorController.config_kI(0,0);
        m_motorController.config_kD(0,0);

        m_motorController.setNeutralMode(NeutralMode.Brake);
        m_motorController.setSelectedSensorPosition(0);

        m_motorController.configPeakOutputForward(0.5);
        m_motorController.configPeakOutputReverse(-0.5);

    }

    public void PercentOutputSupplierDrive(double input) {
        m_motorController.set(ControlMode.PercentOutput, input);
    }

    public void setWristHome() {

        m_motorController.set(ControlMode.Position, 0);

    }

    /* Set the wrist position to level i.e. parallel to the ground 
     * when the arm rotation is approximately zero
    */

    public void setWristToPosition(int ticks) {
        m_motorController.set(TalonFXControlMode.Position, ticks);

    }

    /*
     * Set the wrist position to using encoder values
     * that is optimize for loading.
     * Wrist must be pointed slightly up or it will hit the hall when loading
     */
    public void setWristPositionLoad() {
        m_motorController.set(ControlMode.Position, Constants.WRIST_POS_LOAD);
        // m_motorController.set(ControlMode.Position, 11000);

    }

    /*
     * Set the wrist position to using encoder values
     * Optimize for deploying a cone to mid level.
     * Wrist must be pointed down
     */
    public void setWristMidCone(double input) {
        m_motorController.set(ControlMode.Position, Constants.WRIST_POS_MID);
    }

    // Return the current encoder position of the Wrist
    public double getWristPos() {
        return m_motorController.getSelectedSensorPosition();
        // return 0; // leaving for the LOLs. It wasn't returning an encoder value :P
    }

       /**
     * @param input the encoder degrees to set the arm at. Note the arm extends to roughly 0 at rest, and 14500 units maximum. 
     */
    public void SetWristToTickPosition(double input)
    {
        System.out.println("Internal method being called; position control");
        System.out.println();
        // m_motorController.configPeakOutputForward(0.8);
        // m_motorController.configPeakOutputReverse(-0.8);
        m_motorController.set(ControlMode.Position, (double)input);

    }
}
