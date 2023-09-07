package frc.robot.macrosLib1;


import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * An object representing a change in the state of a joystick, and when it occurred.
 *
 * @author Nicholas DeLello
 * @see simulatedJoystick
 * @see Macro
 */
class JoystickEvent {
    /**
     * The eventType of event, whether a button was pressed, a button was released, an axis was moved, or a pov switch moved.
     */
    public enum eventType {
        PRESS, RELEASE, AXIS, POV
    }

    private final JoystickEvent.eventType eventType;
    private final int stickId;
    private final int id;
    private Double val;
    private final long time;
    private static SimpleDateFormat fmt;

    /**
     * Creates a joystick event. (JoystickEvent.eventType.PRESS/RELEASE)
     *
     * @param type  The eventType of event (JoystickEvent.eventType.PRESS/RELEASE)
     * @param id    The id of the joystick being used.
     * @param btnId The id of the button being pressed/released.
     */
    public JoystickEvent(JoystickEvent.eventType type, int id, int btnId) {
        this.eventType = type;
        this.stickId = id;
        this.id = btnId;
        this.time = System.currentTimeMillis();
    }

    /**
     * Creates a joystick event. (JoystickEvent.eventType.AXIS/POV)
     *
     * @param type   The eventType of event (JoystickEvent.eventType.AXIS)
     * @param id     The id of the joystick being used.
     * @param axisId The id of the axis being changed.
     * @param val    The current value of the axis.
     */
    public JoystickEvent(JoystickEvent.eventType type, int id, int axisId, double val) {
        this.eventType = type;
        this.stickId = id;
        this.id = axisId;
        this.val = val;
        this.time = System.currentTimeMillis();
    }

    /**
     * Creates a joystick event. (Press/Release)
     *
     * @param type  The eventType of event (JoystickEvent.eventType.PRESS/RELEASE)
     * @param time  The time, in milliseconds, the event occurred. (from System.currentTimeMillis())
     * @param id    The id of the joystick being used.
     * @param btnId The id of the button being pressed/released.
     */
    public JoystickEvent(JoystickEvent.eventType type, long time, int id, int btnId) {
        this.eventType = type;
        this.stickId = id;
        this.id = btnId;
        this.time = time;
    }

    /**
     * Creates a joystick event. (JoystickEvent.eventType.AXIS/POV)
     *
     * @param type   The eventType of event (JoystickEvent.eventType.AXIS/POV)
     * @param time   The time, in milliseconds, the event occurred. (from System.currentTimeMillis())
     * @param id     The id of the joystick being used.
     * @param axisId The id of the axis being changed.
     * @param val    The current value of the axis.
     */
    public JoystickEvent(JoystickEvent.eventType type, long time, int id, int axisId, double val) {
        this.eventType = type;
        this.stickId = id;
        this.id = axisId;
        this.val = val;
        this.time = time;
    }

    /**
     * @return The time at which this event occurred.
     */
    public long getTime() {
        return time;
    }

    /**
     * @return The current value of the axis, if it is a AXIS event, and null otherwise.
     */
    public Double getVal() {
        return val != null ? val: null;
    }

    /**
     * @return The eventType of event this event is.
     */
    public JoystickEvent.eventType getEventType() {
        return eventType;
    }

    /**
     * @return The id of the button if it is not a POV event, and null otherwise.
     */
    public Integer getID() {
        return id;
    }

    /**
     * @return The value of the POV switch, if it is a POV event, and null otherwise.
     */
    public Integer getPOVValue() {
        return this.eventType == eventType.POV ? val.intValue(): null;
    }

    /**
     * @return The ID of the stick this event is coming from.
     */
    public int getStickId() {
        return stickId;
    }

    /**
     * Converts this into a string which can be easily split and turned back into a JoystickEvent.
     *
     * @return This serialized into a string.
     */
    public String toString() {
        String str = time + ":";
        switch (eventType) {
            case PRESS:
                str += "press";
                break;
            case RELEASE:
                str += "release";
                break;
            case AXIS:
                str += "axis";
                break;
            case POV:
                str += "POV";
                break;
        }
        return str + ',' + stickId + ',' + id + ',' +
               (eventType == eventType.AXIS ? String.valueOf(val) + ',':
                eventType == eventType.POV ? String.valueOf(val.intValue()): "") + '\n';
    }

    /**
     * Checks if the given JoystickEvent has the same {@link #id}, {@link eventType}, and {@link #stickId}.<br>
     * NOTE: Only checks equality for JoystickEvents, not even subclasses! See method body for subclass implementation.
     *
     * @param o The object to check for equality
     * @return If the two objects have the same {@link #id}, {@link eventType}, and {@link #stickId}.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        //Equivalent to an instanceof call, but no subclasses.
        //NOTE: If you want to subclass this, change "getClass() != o.getClass()" to "o instanceof JoystickEvent".
        //The below implementation is normal behavior for .equals(), but in case you want to change it, that's how.
        if (o == null || getClass() != o.getClass())
            return false;

        JoystickEvent that = (JoystickEvent) o;

        return this.stickId == that.stickId && this.id == that.id && this.eventType == that.eventType;
    }

    /**
     * Returns the hashcode for this object, looking at {@link #id}, {@link eventType}, and {@link #stickId}.
     *
     * @return The hashcode for this object, looking at {@link #id}, {@link eventType}, and {@link #stickId}.
     */
    @Override
    public int hashCode() {
        int result = getEventType().hashCode();
        result = 31 * result + getStickId();
        result = 31 * result + id;
        return result;
    }

    /**
     * Converts this into a human-readable string. For logging purposes, mostly.
     *
     * @return This event as a human-readable string.
     */
    public String toReadableString() {
        fmt = fmt == null ? new SimpleDateFormat("MM/dd/yyyy hh:mm:ss"): fmt; //Create the date formatter if it does not exist.
        return String.format(Locale.ENGLISH, "%s: Joystick %d's %s %s\n", fmt.format(time), stickId,
                             eventType == eventType.PRESS || eventType == eventType.RELEASE ? "Button ": eventType == eventType.AXIS ? "Axis": "POV",
                             eventType == eventType.PRESS ? "pressed.": eventType == eventType.RELEASE ? "released.": ("set to " + val));
    }

}