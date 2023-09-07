package frc.robot.macrosLib1;

import edu.wpi.first.wpilibj.Joystick;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.IntStream;

/**
 * A class acting as an abstraction layer between a {@link Joystick} and a {@link Macro}.
 * @author Nicholas DeLello
 */
class simulatedJoystick {
    private final boolean[] buttons;
    private final double[] axes;
    private int[] POVs;
    private final int id;
    private final ArrayList<JoystickEvent> events = new ArrayList<>();
    public final byte simulatedJoystickFormatVersion = 1; //In case the format changes and you want to convert...

    /**
     * Creates a simulated joystick from the given joystick ID.
     *
     * @param id The id of the real joystick this is based on.
     */
    public simulatedJoystick(int id) {
        this(new Joystick(id));
    }

    /**
     * Creates a simulated joystick from the given joystick.
     *
     * @param j The real joystick this is based on.
     */
    public simulatedJoystick(Joystick j) {
        buttons = new boolean[j.getButtonCount()];
        axes = new double[j.getAxisCount()];
        POVs = new int[j.getPOVCount()];
        this.id = j.getPort();
    }

    /**
     * Creates a joystick with the given number of buttons and axes (assumes 1 POVs)
     *
     * @param numButtons How many buttons the simulatedJoystick should have
     * @param numAxes    How many axes the simulatedJoystick should have
     */
    public simulatedJoystick(int numButtons, int numAxes, int numPOV, int id) {
        buttons = new boolean[numButtons];
        axes = new double[numAxes];
        POVs = new int[numPOV];
        this.id = id;
    }

    /**
     * Creates a joystick from a serialized string. (From the output of .toString())
     *
     * @param serializedJoystick The serialized joystick in string form.
     */
    public simulatedJoystick(String serializedJoystick) {
        String[] values = serializedJoystick.split("|");
        id = Integer.parseInt(values[0]);

        String[] buttons = values[1].split(",");
        this.buttons = new boolean[buttons.length];
        for (int i = 0; i < buttons.length; i++)
            this.buttons[i] = Boolean.parseBoolean(buttons[i]);

        String[] axes = values[2].split(",");
        this.axes = new double[axes.length];
        for (int i = 0; i < axes.length; i++)
            this.axes[i] = Integer.parseInt(axes[i]);

        String[] POVs = values[3].split(",");
        for (int i = 0; i < POVs.length; i++) {
            this.POVs[i] = Integer.parseInt(POVs[i]);
        }
    }

    /**
     * Returns the value of the given button in this joystick.
     *
     * @param buttonNum The button ID to get the value of
     * @return If the given button is being pressed.
     */
    public boolean getRawButton(int buttonNum) {
        return buttons[buttonNum - 1];
    }

    /**
     * Returns the value of the given axis in this joystick.
     *
     * @param axisNum The axis ID to get the value of
     * @return The current value of the given axis
     */
    public double getRawAxis(int axisNum) {
        return axes[axisNum];
    }

    /**
     * Returns the value of the trigger in this joystick.
     *
     * @return If the trigger is being pressed.
     */
    public boolean getTrigger() {
        return buttons[0];
    }

    /**
     * Returns the value of the top button in this joystick.
     *
     * @return If the top button is being pressed.
     */
    @SuppressWarnings("unused")
    public boolean getTop() {
        return buttons[1];
    }

    /**
     * @return The current value of this joystick's X (left-right) axis.
     */
    public double getX() {
        return axes[0];
    }

    /**
     * @return The current value of this joystick's Y (forward-back) axis.
     */
    public double getY() {
        return axes[1];
    }

    /**
     * @return The current value of this joystick's Z (twist) axis.
     */
    public double getZ() {
        return this.getTwist();
    }

    /**
     * @return How much the stick is being twisted.
     */
    public double getTwist() {
        return axes[2];
    }

    /**
     * @return The current value of the throttle.
     */
    public double getThrottle() {
        return axes[3];
    }

    /**
     * Sets the given button to the given state.
     *
     * @param button The ID of the button to set
     * @param val    the value of the button
     */
    public void setButton(int button, boolean val) {
        buttons[button - 1] = val;
    }

    /**
     * Sets the given axis to the given value.
     *
     * @param axis The ID of the axis to change
     * @param val  The value to change the axis to
     */
    public void setAxis(int axis, double val) {
        axes[axis] = val;
    }

    /**
     * Sets the twist of the stick to the given value.
     *
     * @param val The value to set the twist to.
     */
    public void setTwist(int val) {
        axes[2] = val;
    }

    /**
     * Sets the first POVs switch to the given value.
     *
     * @param POVs The value to set the POVs switch to.
     */
    public void setPOV(int POVs) {
        this.POVs[0] = POVs;
    }

    /**
     * Sets the given POVs switch to the given value.
     *
     * @param i   The index of the POVs switch to change
     * @param POV The value to set the POVs switch to.
     */
    public void setPOV(int i, int POV) {
        this.POVs[i] = POV;
    }

    /**
     * @return How many buttons are on this joystick.
     */
    public int getButtonCount() {
        return buttons.length;
    }

    /**
     * @return How many axes are on this joystick.
     */
    public int getAxisCount() {
        return axes.length;
    }

    /**
     * @return How many POV switches are on this joystick.
     */
    public int getPOVCount() {
        return POVs.length;
    }

    /**
     * @return The current value of the first POVs switch on this stick.
     */
    public int getPOVs() {
        return POVs[0];
    }

    /**
     * The value of the given POVs switch on this stick.
     *
     * @param i The index of the POVs switch to check
     * @return The state of it as an int.
     */
    public int getPOV(int i) {
        return POVs[i];
    }

    /**
     * Sets the values of the current stick to those in the one given.
     *
     * @param j The joystick to set the values from
     * @return This, for convenience.
     */
    public simulatedJoystick update(Joystick j) {
        IntStream.range(0, j.getPOVCount()).forEach(i -> this.setPOV(i, j.getPOV(i)));
        IntStream.range(0, j.getButtonCount()).forEach(i -> this.setButton(i, j.getRawButton(i)));
        IntStream.range(0, j.getAxisCount()).forEach(i -> this.setAxis(i, j.getRawAxis(i)));
        return this;
    }

    /**
     * Sets the values of the current stick to those in the one given.
     *
     * @param j The joystick to set the values from
     * @return This, for convenience.
     */
    //! Seems to be used with past stick states
    public simulatedJoystick update(simulatedJoystick j) {
        IntStream.range(0, j.getPOVCount()).forEach(i -> this.setPOV(i, j.getPOV(i)));
        IntStream.range(0, j.getButtonCount()).forEach(i -> this.setButton(i, j.getRawButton(i)));
        IntStream.range(0, j.getAxisCount()).forEach(i -> this.setAxis(i, j.getRawAxis(i)));
        return this;
    }

    /**
     * Returns the ID of the joystick
     *
     * @return The ID of the joystick
     */
    public int getID() {
        return id;
    }


    /**
     * Returns the ID of the joystick
     *
     * @return The ID of the joystick
     */
    public int getPort() {
        return id;
    }

    /**
     * Updates the joystick, returning events (whose times are all 0 for convenience) for any changes that occur.
     *
     * @param j  The joystick to update from
     * @param id The joystick's ID
     * @return A list of all events that were generated from the update.
     */
    public ArrayList<JoystickEvent> updateWithEvents(Joystick j, int id) {
        boolean currentState;
        double currentPosition;
        events.clear();
        for (int i = 1; i <= j.getButtonCount(); i++) {
            currentState = j.getRawButton(i);
            if (this.buttons[i-1] != currentState) {
                this.setButton(i, currentState);
                events.add(new JoystickEvent(currentState ? JoystickEvent.eventType.PRESS : JoystickEvent.eventType.RELEASE, 0L, id, i));
            }
        }
        for (int i = 0; i < j.getAxisCount(); i++) {
            currentPosition = j.getRawAxis(i);
            if (this.axes[i] != currentPosition) {
                this.setAxis(i, currentPosition);
                events.add(new JoystickEvent(JoystickEvent.eventType.AXIS, 0L, id, i, currentPosition));
            }
        }
        for (int i = 0; i < j.getPOVCount(); i++) {
            currentPosition = j.getPOV(i);
            if (this.POVs[i] != currentPosition) {
                this.setPOV(i, (int) Math.round(currentPosition));
                events.add(new JoystickEvent(JoystickEvent.eventType.POV, 0L, id, i, currentPosition));
            }
        }
        return events;
    }

    /**
     * Sets all of the values on this joystick to their defaults.
     */
    public void reset() {
        IntStream.range(0, this.getButtonCount()).forEach(i -> this.setButton(i, false));
        IntStream.range(0, this.getAxisCount()).forEach(i -> this.setAxis(i, 0));
        IntStream.range(0, this.getPOVCount()).forEach(i -> this.setPOV(i, -1));
    }

    /**
     * Converts this into a string which can be easily split and turned back into a simulatedJoystick.
     *
     * @return This serialized into a string.
     */
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append(id).append('|');
        for (int i = 0; i < buttons.length; i++) {
            str.append(buttons[i]);
            if (i != buttons.length - 1)
                str.append(',');
        }
        str.append('|');
        for (int i = 0; i < axes.length; i++) {
            str.append(axes[i]);
            if (i != axes.length - 1)
                str.append(',');
        }
        str.append('|');
        for (int i = 0; i < POVs.length; i++) {
            str.append(POVs[i]);
            if (i != axes.length - 1)
                str.append(',');
        }
        return str.append(simulatedJoystickFormatVersion).toString();
    }

    /**
     * Converts this into a human-readable string. For logging purposes, mostly.
     *
     * @return This event as a human-readable string.
     */
    public String toReadableString() {
        StringBuilder str = new StringBuilder();
        str.append("POVs switch angles: ");
        Arrays.stream(POVs).forEachOrdered(POV -> str.append(POV).append(", "));
        str.append("; ");
        for (int i = 0; i < buttons.length; i++) {
            str.append("Button ").append(i + 1).append(" is ").append(buttons[i] ? "on" : "off");
            if (i != buttons.length - 1)
                str.append(", ");
        }
        str.append("; ");
        for (int i = 0; i < axes.length; i++) {
            str.append("Axis ").append(i).append("'s position: ").append(axes[i]);
            if (i != axes.length - 1)
                str.append(", ");
        }
        return str.toString();
    }
}
