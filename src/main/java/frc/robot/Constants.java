package frc.robot;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.util.Units;

/**
 * A port of 0 means that it is currently unknown/undecided.
 */
public class Constants {

    /*
     * ARM
     */
    public static final int ARM_PIVOT_MOTOR_PORT = 18;      // CAN Port.
    public static final int ARM_PIVOT_ENCODER_PORT = 0;    // DIO Port.
    public static final int ARM_EXTEND_MOTOR_PORT = 21;     // CAN Port.
    public static final int ARM_EXTEND_ENCODER_PORT = 1;   // DIO Port. (1 IS PLACEHOLDER)

    public static final double ARM_EXTEND_LENGTH = Units.inchesToMeters(58); // Current temporary value, will be measured in meters
    public static final double ARM_PIVOT_LENGTH = Units.inchesToMeters(16); // TEMPORARY VALUE
    public static final double ARM_PIVOT_TO_BASE_DISTANCE = Units.inchesToMeters(16.5); // Distance between the pivot point of the arm and the robot base.
    public static final double ARM_MAX_ANGULAR_VELOCITY = 0.25; // (speed as in percent) Max angular velocity of the arm
    public static final double ARM_MAX_ANGULAR_ACCELERATION = 0.25; // (speed as in percent / second) Max angular acceleration of the arm
    public static final double ARM_MAX_EXT_VELOCITY = 0.15; // (speed as in percent) Max velocity of the arm extension
    public static final double ARM_MAX_EXT_ACCELERATION = 0.15; // (speed as in percent / second) Max acceleration of the arm extension
    public static final double L1_X = Units.inchesToMeters(15);     // arm x-coordinate for L1
    public static final double L1_Y = Units.inchesToMeters(-2.0);          // arm y-coordinate for L1
    public static final double L2_X = Units.inchesToMeters(16.625); // arm x-coordinate for L2
    public static final double L2_Y = Units.inchesToMeters(11.875); // arm y-coordinate for L2
    public static final double L3_X = Units.inchesToMeters(16.625); // arm x-coordinate for L3
    public static final double L3_Y = Units.inchesToMeters(27.625); // arm y-coordinate for L3
    public static final double L4_X = Units.inchesToMeters(16.125); // arm x-coordinate for L4
    public static final double L4_Y = Units.inchesToMeters(52.0);   // arm y-coordinate for L4
    /*
     * LEDS
     */
    public static final int LEDS_PORT = 0;                 // PWM Port.
    public static final int LEDS_NUMBER_OF_LEDS = 32;      // 32x1

    /*
     * MUNCHER
     */
    public static final int MUNCHER_INTAKE_MOTOR_PORT = 19;        // CAN Port.
    public static final int MUNCHER_YEET_MOTOR_PORT = 20;      // CAN Port.
   


     /*
      * SWERVE
      */
    // FL = "front left", FR = "front right", BL = "back left", BR = "back right"
    public static final int SWERVE_FL_DRIVE_MOTOR_PORT = 14;    // CAN Port.
    public static final int SWERVE_FL_TURN_MOTOR_PORT = 6;      // CAN Port.
    public static final int SWERVE_FL_ENCODER_PORT = 10;        // CAN Port.

    public static final int SWERVE_FR_DRIVE_MOTOR_PORT = 17;    // CAN Port.
    public static final int SWERVE_FR_TURN_MOTOR_PORT = 9;      // CAN Port.
    public static final int SWERVE_FR_ENCODER_PORT = 13;        // CAN Port.

    public static final int SWERVE_BL_DRIVE_MOTOR_PORT = 15;    // CAN Port.
    public static final int SWERVE_BL_TURN_MOTOR_PORT = 7;      // CAN Port.
    public static final int SWERVE_BL_ENCODER_PORT = 11;        // CAN Port.

    public static final int SWERVE_BR_DRIVE_MOTOR_PORT = 16;    // CAN Port.
    public static final int SWERVE_BR_TURN_MOTOR_PORT = 8;      // CAN Port.
    public static final int SWERVE_BR_ENCODER_PORT = 12;        // CAN Port.

    public static final double SWERVE_MAX_ANGULAR_VELOCITY = Units.degreesToRadians(360);           // Radians per second.
    public static final double SWERVE_MAX_ANGULAR_ACCELERATION = Units.degreesToRadians(1080);       // Radians per second squared.
    public static final double SWERVE_MAX_DRIVE_SPEED = 3.0;                                                // Meters per second?
    public static final double SWERVE_MAX_ACCELERATION = 100.0;                                             // Meters per second?
    public static final double SWERVE_MAX_DRIVE_VOLTAGE = 4.0;                                              // In Volts

    public static final Rotation2d SWERVE_GYRO_OFFSET = new Rotation2d(Units.degreesToRadians(0));  // To find this, set it to 0 and record the value you get on startup.

    public static final double WHEEL_RADIUS = 0.04955;                  // Meters. Double check this; it's based on the 2024 Competition code.
    public static final double SWERVE_DRIVE_MOTOR_GEAR_RATIO = 6.12;    // Double check this; it's based on the 2024 Competition code!

    // PHYSICAL CONSTANTS
    public static final double ROBOT_SIDE_LENGTH = Units.inchesToMeters(30); // Width & Length of the robot.
    public static final double ROBOT_HEIGHT = Units.inchesToMeters(3); // temporary value, height of the robot.-

    /*
     * VISION
    */
    // April codes: p. 4; https://firstfrc.blob.core.windows.net/frc2025/FieldAssets/2025FieldDrawings-FieldLayoutAndMarking.pdf
    public static final boolean ON_BLUE_ALLIANCE = true; // true if blue (left), false if red (right)
    public static int REEF_FRONT() { return ON_BLUE_ALLIANCE ? 21 : 10; }
    public static int REEF_FRONT_LEFT() { return ON_BLUE_ALLIANCE ? 22 : 9; }
    public static int REEF_FRONT_RIGHT() { return ON_BLUE_ALLIANCE ? 20 : 11; }
    public static int REEF_BACK() { return ON_BLUE_ALLIANCE ? 18 : 7; }
    public static int REEF_BACK_LEFT() { return ON_BLUE_ALLIANCE ? 17 : 8; }
    public static int REEF_BACK_RIGHT() { return ON_BLUE_ALLIANCE ? 19 : 6; }

    public static int CORAL_STATION_LEFT() { return ON_BLUE_ALLIANCE ? 12 : 2; }
    public static int CORAL_STATION_RIGHT() { return ON_BLUE_ALLIANCE ? 13 : 1; }

    public static int CORAL_DROPOFF() { return ON_BLUE_ALLIANCE ? 16 : 3; }

    public static int BARGE_RIGHT() { return ON_BLUE_ALLIANCE ? 14 : 5; }
    public static int BARGE_LEFT() { return ON_BLUE_ALLIANCE ? 15 : 4; }

    // April tag aligment strategies
    public class AprilTagAlignment {
      public static final int CENTER = 0;
      public static final int LEFT = 1;
      public static final int RIGHT = 2;
    }
}
