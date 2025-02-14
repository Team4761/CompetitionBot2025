package frc.robot.auto;

/**
 * All of these are oriented by looking at the opposite alliance wall, and figuring out where the robot is.
 * Additionally, LEFT and RIGHT are both centered on the line, looking forwards (for now), and centered with the Barge April Tag
 * CENTER is the same, except that the robot is centered in between the two barges.
 */
public enum StartingPosition {
    LEFT,   // Theoretically, this would be (7.5565, 6.03885)
    CENTER, // Theoretically, this would be (7.5565, 4.02590)
    RIGHT   // Theoretically, this would be (7.5565, 2.01295)
}
