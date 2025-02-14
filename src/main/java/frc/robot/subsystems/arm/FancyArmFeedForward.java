package frc.robot.subsystems.arm;

import edu.wpi.first.math.geometry.Rotation2d;

/**
 * <p> Buckle your eyes and get ready to behold MATH.
 * 
 * <p> This essentially predicts the future given a whole bunch of physical constants about the arm to figure out the minimum speed we need to run our motors to hold the arm at a position.
 * <p> This takes into account that the center of mass of the arm moves (somewhat) linearly along its extension path.
 * <p> This allows me to figure our the approximate voltage required along any path of that extension.
 * 
 * <p> To be clear, this does NOT do any physics calculations! It merely applies the principle of torque to account for both rotation and extension.
 */
public class FancyArmFeedForward {
    
    /** distance from pivot of arm in the meters. */
    private static double unextendedCenterOfMass = 0.0;
    /** distance from pivot of arm in the meters. */
    private static double extendedCenterOfMass = 0.0;

    // In math terms, these conditions make cos(angle) = 1.0 which is handy for the math we have to do...
    /** Tune this value to become the minimum *speed* required to keep the arm upright when unextended and parallel to the ground. */
    private static double kS = 0.0;

    // P.S. I'm not implementing kV or kA unless we can prove that it's necessary.

    /**
     * Don't feel scared about the fact that this takes no parameters! All of the params are constants in the FancyArmFeedForward.java file (for now)
     */
    public FancyArmFeedForward() {}


    /**
     * This takes the current rotation and extension length into account and gives the minimum voltage required to keep the motor at the setpoint.
     * @param targetRotation The desired rotation of the arm (where 0 is parallel to the ground, extended in the front of the robot).
     * @param targetExtension Meters. The desired extension of the arm (where 0 represents no extension)
     * @return A speed value that should be added to any other speed values that can maintain the arm at the given setpoint. (currently between -1 and 1 where +1 represents 100% power upwards.)
     */
    public double calculate(Rotation2d targetRotation, double targetExtension) {
        // Step 1: Figure out where the center of mass currently is as a percentage of its full range of motion.
        // Example: the '+' represents the center of mass: ---+--- would give 0.5(50%), -+---- would give 0.25(25%)
        double currentCOMPercent = targetExtension / (extendedCenterOfMass - unextendedCenterOfMass);
        // Step 2: Figure out the minimum speed to set the motor to (as if the arm was perfectly parallel with the ground)
        // There is (1.0 + ...) because the kS value was tuned when the arm was NOT extended!
        double minimumSpeed = kS * (1.0 + currentCOMPercent);
        // Step 3: Decrease this speed depending on how rotated the arm is (think about it logically, if the arm is perfectly upright, it is balanced and doesn't need any additional force to keep it upright.)
        return minimumSpeed * Math.abs(Math.cos(targetRotation.getRadians()));
    }
}
