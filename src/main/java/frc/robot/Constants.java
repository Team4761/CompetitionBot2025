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
    public static final int ARM_PIVOT_MOTOR_PORT = 0;      // CAN Port.
    public static final int ARM_PIVOT_ENCODER_PORT = 0;    // DIO Port.
    public static final int ARM_EXTEND_MOTOR_PORT = 0;     // CAN Port.
    public static final int ARM_EXTEND_ENCODER_PORT = 0;   // DIO Port.


    /*
     * LEDS
     */
    public static final int LEDS_PORT = 0;                 // PWM Port.
    public static final int LEDS_NUMBER_OF_LEDS = 256;      // I counted them. 32x8


    /*
     * MUNCHER
     */
    public static final int MUNCHER_MOTOR_PORT = 0;        // CAN Port.
    public static final int MUNCHER_OUTTAKE_PORT = 0;      // CAN Port.
    public static final int MUNCHER_BREAKBEAM_PORT = 0;    // DIO Port. Don't know if this will actually exist or not...


     /*
      * SWERVE
      */
    // FL = "front left", FR = "front right", BL = "back left", BR = "back right"
    public static final int SWERVE_FL_DRIVE_MOTOR_PORT = 20;    // CAN Port.
    public static final int SWERVE_FL_TURN_MOTOR_PORT = 5;      // CAN Port.
    public static final int SWERVE_FL_ENCODER_PORT = 27;        // CAN Port.

    public static final int SWERVE_FR_DRIVE_MOTOR_PORT = 22;    // CAN Port.
    public static final int SWERVE_FR_TURN_MOTOR_PORT = 7;      // CAN Port.
    public static final int SWERVE_FR_ENCODER_PORT = 26;        // CAN Port.

    public static final int SWERVE_BL_DRIVE_MOTOR_PORT = 23;    // CAN Port.
    public static final int SWERVE_BL_TURN_MOTOR_PORT = 8;      // CAN Port.
    public static final int SWERVE_BL_ENCODER_PORT = 24;        // CAN Port.

    public static final int SWERVE_BR_DRIVE_MOTOR_PORT = 21;    // CAN Port.
    public static final int SWERVE_BR_TURN_MOTOR_PORT = 6;      // CAN Port.
    public static final int SWERVE_BR_ENCODER_PORT = 25;        // CAN Port.

    public static final double SWERVE_MAX_ANGULAR_VELOCITY = Units.degreesToRadians(180);           // Radians per second.
    public static final double SWERVE_MAX_ANGULAR_ACCELERATION = Units.degreesToRadians(360);       // Radians per second squared.
    public static final double SWERVE_MAX_DRIVE_SPEED = 3.0;                                                // Meters per second?
    public static final double SWERVE_MAX_DRIVE_VOLTAGE = 4.0;

    public static final Rotation2d SWERVE_GYRO_OFFSET = new Rotation2d(Units.degreesToRadians(0));  // To find this, set it to 0 and record the value you get on startup.

    public static final double WHEEL_RADIUS = 0.04955;                  // Meters. Double check this; it's based on the 2024 Competition code.
    public static final double SWERVE_DRIVE_MOTOR_GEAR_RATIO = 6.12;    // Double check this; it's based on the 2024 Competition code!

      /*
       * VISION
       */
}
