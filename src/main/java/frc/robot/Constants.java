package frc.robot;

import edu.wpi.first.math.kinematics.DifferentialDriveKinematics;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.util.Units;

// Note from Ben Clark: looking at some other people's code strucutre, perhaps making classes within the class constants
// would be a good idea. For example, having a class called "Arm" and having all the constants related to the arm. 
// This would make it easier to find constants and keep the code organized.

/**
 * A port of 0 means that it is currently unknown/undecided.
 */
public class Constants {

    /*
     * ARM
     */
    public static final int ARM_PIVOT_MOTOR_PORT = 18;      // CAN Port.
    public static final int ARM_PIVOT_ENCODER_PORT = 30;    // DIO Port. (30 IS PLACEHOLDER)
    public static final int ARM_EXTEND_MOTOR_PORT = 31;     // CAN Port. (31 IS PLACEHOLDER)
    public static final int ARM_EXTEND_ENCODER_PORT = 31;   // DIO Port. (32 IS PLACEHOLDER)

    public static final double ARM_EXTEND_LENGTH = Units.inchesToMeters(58); // Current temporary value, will be measured in meters
    public static final double ARM_PIVOT_TO_BASE_DISTANCE = Units.inchesToMeters(16.5); // Distance between the pivot point of the arm and the robot base.
    public static final double ARM_MAX_ANGULAR_VELOCITY = 0.3; // Max angular velocity of the arm (percent where 1 is 100% speed)
    public static final double ARM_MAX_ANGULAR_ACCELERATION = 1.0; // Max angular acceleration of the arm (change in percent where 1 is 100% speed)
    public static final double ARM_MAX_EXT_VELOCITY = 1.0; // (percent where 1 is 100% speed) Max velocity of the arm extension
    public static final double ARM_MAX_EXT_ACCELERATION = 1.0; // (percent where 1 is 100% speed) Max acceleration of the arm extension
    public static final double L1_X = Units.inchesToMeters(15);
    public static final double L1_Y = Units.inchesToMeters(-2.0);
    public static final double L2_X = Units.inchesToMeters(16.625);
    public static final double L2_Y = Units.inchesToMeters(11.875);
    public static final double L3_X = Units.inchesToMeters(16.625);
    public static final double L3_Y = Units.inchesToMeters(27.625);
    public static final double L4_X = Units.inchesToMeters(16.125);
    public static final double L4_Y = Units.inchesToMeters(52.0);

    // arm pid constants
    public static final double ARM_PID_KS = 0.0; // Ks Volts
    public static final double ARM_PID_KV = 0.0; // Kv VoltSecondsPerMeter
    public static final double ARM_PID_KA = 0.0; // Ka VoltSecondsSquaredPerMeter
    public static final double ARM_PID_KP = 0.0; // Kp Drive Velocity

    /*
     * LEDS
     */
    public static final int LEDS_PORT = 0;                 // PWM Port.
    public static final int LEDS_NUMBER_OF_LEDS = 32;      // 32x1

    /*
     * MUNCHER
     */
    public static final int MUNCHER_INTAKE_MOTOR_PORT = 19;        // CAN Port.
    public static final int MUNCHER_EJECT_MOTOR_PORT = 20;      // CAN Port.

    /*
     * SWERVE
     */
    // FL = "front left", FR = "front right", BL = "back left", BR = "back right"
    public static final int SWERVE_FL_DRIVE_MOTOR_PORT = 16;    // CAN Port.
    public static final int SWERVE_FL_TURN_MOTOR_PORT = 8;      // CAN Port.
    public static final int SWERVE_FL_ENCODER_PORT = 12;        // CAN Port.

    public static final int SWERVE_FR_DRIVE_MOTOR_PORT = 15;    // CAN Port.
    public static final int SWERVE_FR_TURN_MOTOR_PORT = 7;      // CAN Port.
    public static final int SWERVE_FR_ENCODER_PORT = 11;        // CAN Port.

    public static final int SWERVE_BL_DRIVE_MOTOR_PORT = 17;    // CAN Port.
    public static final int SWERVE_BL_TURN_MOTOR_PORT = 9;      // CAN Port.
    public static final int SWERVE_BL_ENCODER_PORT = 13;        // CAN Port.

    public static final int SWERVE_BR_DRIVE_MOTOR_PORT = 14;    // CAN Port.
    public static final int SWERVE_BR_TURN_MOTOR_PORT = 6;      // CAN Port.
    public static final int SWERVE_BR_ENCODER_PORT = 10;        // CAN Port.

    public static final double SWERVE_MAX_ANGULAR_VELOCITY = Units.degreesToRadians(180);           // Radians per second.
    public static final double SWERVE_MAX_ANGULAR_ACCELERATION = Units.degreesToRadians(360);       // Radians per second squared.
    public static final double SWERVE_MAX_DRIVE_SPEED = 3.0;                                                // Meters per second?
    public static final double SWERVE_MAX_ACCELERATION = 100.0;                                             // Meters per second?
    public static final double SWERVE_MAX_DRIVE_VOLTAGE = 4.0;                                              // In Volts

    public static final Rotation2d SWERVE_GYRO_OFFSET = new Rotation2d(Units.degreesToRadians(0));  // To find this, set it to 0 and record the value you get on startup.

    public static final double WHEEL_RADIUS = 0.04955;                  // Meters. Double check this; it's based on the 2024 Competition code.
    public static final double SWERVE_DRIVE_MOTOR_GEAR_RATIO = 6.12;    // Double check this; it's based on the 2024 Competition code!

    // swerve pid constants
    public static final double SWERVE_PID_KS = 0.0; // Ks Volts
    public static final double SWERVE_PID_KV = 0.0; // Kv Volts Seconds Per Meter
    public static final double SWERVE_PID_KA = 0.0; // Ka Volts Seconds SQUARED Per Meter
    public static final double SWERVE_PID_KP = 0.0; // Kp Drive Velocity

    public static final double SWERVE_PID_KMAXSPEED = 0.0; // KMaxSpeed Meters Per Second
    public static final double SWERVE_PID_KMAXACCELERATION = 0.0; // KMaxSpeed Meters Per Second SQUARED
    public static final double SWERVE_PID_KTRACKWIDTH = 0.0; // In meters
    public static final DifferentialDriveKinematics SWERVE_PID_DRIVE_KINEMATICS = new DifferentialDriveKinematics(SWERVE_PID_KTRACKWIDTH);


    // PHYSICAL CONSTANTS
    public static final double ROBOT_SIDE_LENGTH = Units.inchesToMeters(30); // Width & Length of the robot.
    public static final double ROBOT_HEIGHT = Units.inchesToMeters(3); // temporary value, height of the robot.-
      /*
       * VISION
       */
    // a lot of nothing
}
