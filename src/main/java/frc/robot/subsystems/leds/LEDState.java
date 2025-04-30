package frc.robot.subsystems.leds;

/**
 * Represents what state the LEDs are currently in (being controlled by the robot).
 * The default is IDLE.
 */
public enum LEDState {
    IDLE,       // When no other patterns should be applied
    AUTO_ARM,   // When the arm is trying to get to a set point
    AUTO_ALIGN, // When the bot is trying to align with an april tag
}
