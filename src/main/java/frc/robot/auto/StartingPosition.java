package frc.robot.auto;

/**
 * All of these are oriented by looking at the opposite alliance wall, and figuring out where the robot is.
 * Additionally, LEFT and RIGHT are both centered on the line, looking forwards (for now), and centered with the Barge April Tag
 * CENTER is the same, except that the robot is centered in between the two barges.
 */
public enum StartingPosition {
    BLUE_LEFT,   // Theoretically, this would be (7.5565, 6.03885)
    BLUE_CENTER, // Theoretically, this would be (7.5565, 4.02590)
    BLUE_RIGHT,  // Theoretically, this would be (7.5565, 2.01295)
    RED_LEFT,    // Theoretically, this would be (???, 6.03885)
    RED_CENTER,  // Theoretically, this would be (???, 4.02590)
    RED_RIGHT,   // Theoretically, this would be (???, 2.01295)
}