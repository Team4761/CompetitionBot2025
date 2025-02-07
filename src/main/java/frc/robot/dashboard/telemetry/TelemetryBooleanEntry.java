package frc.robot.dashboard.telemetry;

import java.util.function.BooleanSupplier;

import edu.wpi.first.networktables.NetworkTableEntry;
import frc.robot.dashboard.DashboardHandler;

/**
 * This allows you to store a number entry on the dashboard that auto updates every time the value in code has changed.
 * The update() function must be called in order to check for the updates, but this allows for almost all the dashboard code to be contained to RobocketsDashboard.java
 * This is useful for logging data that we want to see while running the code (rotations, time, speeds, joystick input, etc.)
 */
public class TelemetryBooleanEntry implements TelemetryEntry {

    /** This entry should be taken directly from SmartDashboard */
    private NetworkTableEntry entry;

    /** This stores the last value from the dashboard so that it only calls the update function when necessary. */
    private boolean lastValue;

    /** 
     * This is a bit more complicated. An DoubleSupplier stores a function that returns a double.
     * For example, I could tell this DoubleSupplier to be a swerve function that gets the current robot rotation by assigning it to SwerveSubsystem::getGyroRotation
     * This way, when the value in code changes, I can figure out what it is.
     */
    private BooleanSupplier functionToCallOnUpdate;


    /**
     * This will put a number entry on the dashboard and call the function "functionToCallOnUpdate" to get the latest value to put on the dashboard.
     * @param functionToCallOnUpdate The function to call to get the new value. Takes the form of OBJECT::FUNCTION (or a lambda expression if you're interested in googling)
     * @param entry The dashboard entry to update. You can see how to get this by looking at RobocketsDashboard.java
     */
    public TelemetryBooleanEntry(BooleanSupplier functionToCallOnUpdate, NetworkTableEntry entry) {
        DashboardHandler.addTelemetryEntry(this);
        
        this.functionToCallOnUpdate = functionToCallOnUpdate;
        this.entry = entry;
        this.lastValue = entry.getBoolean(false);
    }


    /**
     * This will call the function that was stored in functionToCallOnUpdate IF the value on the dashboard has changed.
     */
    public void update() {
        boolean newValue = functionToCallOnUpdate.getAsBoolean();
        if (newValue != lastValue) {
            entry.setBoolean(newValue);
            lastValue = newValue;
        }
    }
}
