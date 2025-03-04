package frc.robot.subsystems.arm;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.util.Units;

/**
 * Guess what? This defines the current state of the arm!
 * This includes both the current rotation and length of extension.
 */
public class ArmState {

    public static final Rotation2d MAX_ROTATION = new Rotation2d(Units.degreesToRadians(95));
    public static final Rotation2d MIN_ROTATION = new Rotation2d(Units.degreesToRadians(-12));

    public static final double MIN_ARM_LENGTH = Units.inchesToMeters(16);  // TODO: Set this number
    public static final double MAX_ARM_LENGTH = Units.inchesToMeters(58);  // TODO: Set this number
    public static final double MAX_EXTENSION_LENGTH = MAX_ARM_LENGTH - MIN_ARM_LENGTH;  // TODO: Set this number

    /** This is the rotation of the arm assuming that 0 degrees is forwards, parallel to the ground and the positive direction is rotating upwards. */
    private Rotation2d pivotRotation;
    /** This is the length of the extension of the arm in meters (not including the base length of the arm when not extended). 0 = not extended. */
    private double extensionLength;
    
    /**
     * This defines a state of the arm (either the current state or target state).
     * By having no parameters, this sets the arm to being not outstretched and facing forwards parallel to the ground (0 degrees).
     */
    public ArmState() {
        this(new Rotation2d(0), 0);
    }

    /**
     * This defines a state of the arm (either the current state or target state)
     * @param pivotRotation The current rotation of the arm. 0 = facing forwards, parallel to the ground. The positive direction is pivoting upwards.
     * @param extensionLength The current extension length of the arm in meters. 0 = not extended at all.
     */
    public ArmState(Rotation2d pivotRotation, double extensionLength) {
        this.pivotRotation = pivotRotation;
        this.extensionLength = extensionLength;
    }


    /**
     * @return the rotation of the arm (0 = forwards, parallel to the ground, positive = upwards)
     */
    public Rotation2d getPivotRotation() {
        return pivotRotation;
    }

    /**
     * Sets the rotation of this arm state to a new rotation.
     * @param pivotRotation The new rotation.
     */
    public void setPivotRotation(Rotation2d pivotRotation) {
        this.pivotRotation = pivotRotation;
    }

    /**
     * @return The extension length of the arm in meters (0 = not extended).
     */
    public double getExtensionLength() {
        return extensionLength;
    }

    /**
     * @return The percentage of the full extension. 0.0 = not extended; 1.0 = fully extended
     */
    public double getExtensionPercent() {
        return getExtensionLength() / MAX_EXTENSION_LENGTH;
    }

    /**
     * Sets the extension length of this arm state to a new length.
     * @param extensionLength The new extension length in meters.
     */
    public void setExtensionLength(double extensionLength) {
        this.extensionLength = extensionLength;
    }
}
