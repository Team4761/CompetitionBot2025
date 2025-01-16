package frc.robot.shuffleboard.reactive;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.util.function.BooleanConsumer;

/**
 * This allows you to store a boolean entry on the shuffleboard and call a function automatically whenever that value is updated.
 * The update() function must be called in order to check for the updates, but this allows for almost all the shuffleboard code to be contained to RobocketsShuffleboard.java
 * This is useful for updating PID values, swerve values, etc. without having to constantly check the shuffleboard values in the main robot code.
 */
public class ReactiveBooleanEntry {

    /** This entry should be taken directly from shuffleboard */
    private NetworkTableEntry entry;

    /** This stores the last value from the shuffleboard so that it only calls the update function when necessary. */
    private boolean lastValue;

    /** 
     * This is a bit more complicated. An DoubleConsumer stores a function that is passed a double as a parameter.
     * For example, I could tell this DoubleConsumer to be a swerve function that updates the P value in PID by assigning it to SwerveSubsystem::setP
     * This way, when the value is updated, it will call the function that I passed to it.
     */
    private BooleanConsumer functionToCallOnUpdate;


    /**
     * This will put a boolean entry on the shuffleboard and call the function "functionToCallOnUpdate" when the shuffleboard value is updated.
     * @param functionToCallOnUpdate The function to call when the shuffleboard value is updated. Takes the form of OBJECT::FUNCTION (or a lambda expression if you're interested in googling)
     * @param entry The shuffleboard entry to update. You can see how to get this by looking at RobocketsShuffleboard.java
     */
    public ReactiveBooleanEntry(BooleanConsumer functionToCallOnUpdate, NetworkTableEntry entry) {
        this.functionToCallOnUpdate = functionToCallOnUpdate;
        this.entry = entry;
        this.lastValue = entry.getBoolean(false);
    }


    /**
     * This will call the function that was stored in functionToCallOnUpdate IF the value on the shuffleboard has changed.
     */
    public void update() {
        boolean newValue = entry.getBoolean(false);
        if (newValue != lastValue) {
            // functionToCallOnUpdate.accept(newValue) is the same as saying functionToCallOnUpdate(newValue)
            functionToCallOnUpdate.accept(newValue);
            lastValue = newValue;
        }
    }
}
