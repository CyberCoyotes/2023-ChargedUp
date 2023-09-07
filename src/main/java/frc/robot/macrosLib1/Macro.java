package frc.robot.macrosLib1;

import edu.wpi.first.wpilibj.Joystick;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 * Allows recording during teleop, and playback of those recordings during autonomous.
 *
 * @author Nicholas DeLello
 * @see simulatedJoystick
 * @see JoystickEvent
 * @see MacroHelper
 */
public class Macro {
    private Long startTime = null, stopTime = null;
    private final Joystick[] sticks;
    private final HashMap<Integer, simulatedJoystick> previousStateSticks;
    private final HashMap<Integer, simulatedJoystick> initialStateSticks;
    private final int[] ids;
    private Long playbackStart;
    private boolean playing;
    private boolean recording;
    private final int maxLength = 15000; //15 seconds for autonomous.
    //In case the format changes and you want to convert...
    private final byte macroFormatVersion;
    public static final byte currentMacroFormatVersion = 1;

    /**
     * All joystick events that occurred during the macro, in chronological order.
     */
    private final ArrayList<JoystickEvent> events;

    /**
     * Creates a macro for recording given the sticks being used.
     *
     * @param sticks An array containing all joysticks being used in the recording.
     */
    public Macro(Joystick[] sticks) {
        events = new ArrayList<>();
        this.sticks = sticks;
        //! haha silly LINQ SHAMELESS RIPOFF!
        //! Converted to a stream for the sake of mapping to ints
        this.ids = Arrays.stream(sticks).mapToInt(Joystick::getPort).toArray();
        previousStateSticks = new HashMap<>();
        initialStateSticks = new HashMap<>();
        for (Joystick stick : this.sticks) {
            //! So simulated joysticks is a class of this library that takes after another real stick.
            previousStateSticks.put(stick.getPort(), new simulatedJoystick(stick).update(stick));
            // initialStateSticks cannot have pointers to previousStateSticks in it. Each simulatedJoystick must be new.
            initialStateSticks .put(stick.getPort(), new simulatedJoystick(stick).update(stick));
        }
        macroFormatVersion = currentMacroFormatVersion;
    }

    /**
     * Loads a saved macro. (given the output from .toString())
     *
     * @param loadedMacro The output from Macro.toString()
     * @param sticks      The joysticks used in the recording
     */

    public Macro(String loadedMacro, Joystick[] sticks) {
        this(loadedMacro.split("\n"), sticks);
    }

    /**
     * Loads a saved macro. (given the output from .toString())
     *
     * @param lines  The output from Macro.toString()
     * @param sticks The joysticks used in the recording
     */
    public Macro(String[] lines, Joystick[] sticks) {
        this.sticks = sticks;
        this.ids = Arrays.stream(sticks).mapToInt(Joystick::getPort).toArray();
        previousStateSticks = new HashMap<>();
        for (Joystick stick : this.sticks)
            previousStateSticks.put(stick.getPort(), new simulatedJoystick(stick).update(stick));
        events = new ArrayList<>();
        startTime = Long.parseLong(lines[0].substring(1));
        // Read the initial state of each stick
        initialStateSticks = new HashMap<>();
        for (int i = 1; i <= this.sticks.length; i++)
            initialStateSticks.put(ids[i - 1], new simulatedJoystick(lines[i]));

        // Read each event, adding it to the event list in chronological order.
        for (int i = this.sticks.length + 1; i < lines.length - 2; i++) {
            long time = Long.parseLong(lines[i].substring(0, lines[i].indexOf(':')));
            String[] values = lines[i].substring(lines[i].indexOf(':') + 1).split(",");
            switch (values[0]) {
                case "press":
                    events.add(new JoystickEvent(JoystickEvent.eventType.PRESS, time, Integer.parseInt(values[1]), Integer.parseInt(values[2])));
                    break;
                case "release":
                    events.add(new JoystickEvent(JoystickEvent.eventType.RELEASE, time, Integer.parseInt(values[1]), Integer.parseInt(values[2])));
                    break;
                case "axis":
                    events.add(new JoystickEvent(JoystickEvent.eventType.AXIS, time, Integer.parseInt(values[1]), Integer.parseInt(values[2]), Double.parseDouble(values[3])));
                    break;
                case "POV":
                    events.add(new JoystickEvent(JoystickEvent.eventType.POV, time, Integer.parseInt(values[1]), Integer.parseInt(values[2]), (double) Integer.parseInt(values[3])));
                    break;
            }
        }
        stopTime = Long.parseLong(lines[lines.length - 2].substring(1));
        macroFormatVersion = Byte.parseByte(lines[lines.length - 1]);
    }

    /**
     * Starts recording a macro.
     *
     * @return If this macro has not already been recorded.
     */
    public boolean startRecording() {
        if (this.startTime == null) {
            recording = true;
            this.startTime = System.currentTimeMillis();
            for (int i = 0; i < sticks.length; i++) {
                previousStateSticks.get(ids[i]).update(sticks[i]);
                initialStateSticks.get(ids[i]).update(sticks[i]);
            }
            return true;
        }
        return false;
    }

    /**
     * Stops recording a macro.
     *
     * @return If this macro has a recording to stop.
     */
    public boolean stopRecording() {
        if (this.startTime != null) {
            recording = false;
            this.stopTime = System.currentTimeMillis();
            return true;
        }
        return false;
    }

    /**
     * @return The length, in milliseconds, of the recording, or null if the recording is unfinished or not started.
     */
    public Long length() {
        return (this.stopTime != null && this.startTime != null) ? this.stopTime - this.startTime: null;
    }

    /**
     * Plays back any events that are ready to be played. Put this in the beginning of teleop, but after checking for the button to start/stop recording.
     *
     * @param sticks The joysticks to be changed by the recording.
     * @return If it is currently isPlaying.
     */
    public boolean playback(simulatedJoystick[] sticks) {
        if (System.currentTimeMillis() - playbackStart >= Math.min(this.length(), maxLength)) { //If the macro ended or exceeded maximum length
            this.stopPlaying();
            return false;
        }
        if (this.stopTime == null || !playing) //If it's in mid-recording or startPlaying() hasn't been called yet
            return false;
        if (playbackStart == null) { //If it's the beginning of the playback.
            playbackStart = System.currentTimeMillis();
            for (int i = 0; i < ids.length; i++)
                sticks[i].update(initialStateSticks.get(ids[i]));
        }
        JoystickEvent event;
        //If 15 seconds (or the length of the macro) has passed or there are no events left.
        while (System.currentTimeMillis() - playbackStart <= Math.max(this.length(), maxLength) && !events.isEmpty()) {
            event = events.get(0);
            // If the current event is ready to be run, run it.
            if (event.getTime() - this.getStartTime() <= System.currentTimeMillis() - playbackStart)
                switch (event.getEventType()) {
                    case PRESS:
                        sticks[event.getStickId()].setButton(event.getID(), true);
                        break;
                    case RELEASE:
                        sticks[event.getStickId()].setButton(event.getID(), false);
                        break;
                    case POV:
                        sticks[event.getStickId()].setPOV(event.getID(), event.getPOVValue());
                        break;
                    case AXIS:
                        sticks[event.getStickId()].setAxis(event.getID(), event.getVal());
                        break;
                }
            else
                break;
            events.remove(0);
        }
        return true;
    }

    /**
     * @return If the macro is playing.
     */
    public boolean isPlaying() {
        return playing;
    }

    /**
     * @return If the macro is recording.
     */

    public boolean isRecording() {
        return recording;
    }

    /**
     * Starts recording the current change in the joysticks.
     */
    public void record() {
        if (System.currentTimeMillis() - startTime > maxLength) { //Stop it at 15 seconds.
            this.stopRecording();
            return;
        }
        recording = true;
        for (int i = 0; i < sticks.length; i++) { //Check all of the sticks for changes
            for (int j = 1; j <= sticks[i].getButtonCount(); j++) //Check if any buttons were pressed or released
                if (previousStateSticks.get(ids[i]).getRawButton(j) != sticks[i].getRawButton(j)) //Button was pressed or released
                    events.add(new JoystickEvent(previousStateSticks.get(ids[i]).getRawButton(i) ? JoystickEvent.eventType.RELEASE: JoystickEvent.eventType.PRESS, i, j));

            for (int j = 0; j < sticks[i].getAxisCount(); j++) //Check if any axes moved
                if (previousStateSticks.get(ids[i]).getRawAxis(j) != sticks[i].getRawAxis(j)) //Axis moved
                    events.add(new JoystickEvent(JoystickEvent.eventType.AXIS, i, j, sticks[i].getRawAxis(j)));

            for (int j = 0; j < sticks[i].getPOVCount(); j++) //Check if any POV switch was moved
                if (previousStateSticks.get(ids[i]).getPOV(j) != sticks[i].getPOV(j))
                    events.add(new JoystickEvent(JoystickEvent.eventType.POV, i, j, (double) sticks[i].getPOV(j)));
            previousStateSticks.get(ids[i]).update(sticks[i]); //Update the last state for next time
        }
    }

    /**
     * @return When the recording was started (using output from System.currentTimeMillis())
     */
    public long getStartTime() {
        return startTime;
    }

    /**
     * @return When the recording was started (using output from System.currentTimeMillis())
     */

    public long getStopTime() {
        return stopTime;
    }

    /**
     * Sets the playbackStart time so .playback() works.
     */
    public void startPlaying() {
        playing = true;
        playbackStart = System.currentTimeMillis();
    }

    /**
     * Stops playback, preventing .playback() from working.
     */
    public void stopPlaying() {
        playing = false;
        System.out.println("Macro finished.");
    }

    /**
     * Converts the macro into a string which can be loaded later using either the String or String[] constructor.
     *
     * @return The macro serialized into a string.
     */
    public String toString() {
        //There may be a lot of events, so a StringBuilder is better.
        StringBuilder str = new StringBuilder("{").append(this.startTime).append('\n');
        for (int j: ids)
            str.append(initialStateSticks.get(j)).append('\n');
        events.forEach(str::append); //If you know what a for-each loop does, you can guess what this does.
        return str.append('}').append(this.stopTime).append('\n').append(macroFormatVersion).toString();
    }

    /**
     * Converts this into a human-readable string. For logging purposes, mostly.
     *
     * @return This as a human-readable string.
     */
    public String toReadableString() {
        StringBuilder str = new StringBuilder("{ Start time: ").append(this.startTime).append('\n');
        for (int j: ids)
            str.append(initialStateSticks.get(j).toReadableString()).append('\n');
        events.forEach(event->str.append(event.toReadableString())); //There's a lot of these, which is why I use a StringBuilder.
        return str.append("} Stop time: ").append(this.stopTime).append("\nMacro Format Version: ").append(macroFormatVersion).toString();
    }
}
