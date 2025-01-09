package frc.robot;

/**
 * A port of -1 means that it is currently unknown/undecided.
 */
public class Constants {
    
    /*
     * ARM
     */
    public static final int ARM_PIVOT_MOTOR_PORT = -1;      // CAN Port.
    public static final int ARM_PIVOT_ENCODER_PORT = -1;    // DIO Port.
    public static final int ARM_EXTEND_MOTOR_PORT = -1;     // CAN Port.
    public static final int ARM_EXTEND_ENCODER_PORT = -1;   // DIO Port.


    /*
     * LEDS
     */
    public static final int LEDS_PORT = -1;                 // PWM Port.
    public static final int LEDS_NUMBER_OF_LEDS = 256;      // I counted them. 32x8


    /*
     * MUNCHER
     */
    public static final int MUNCHER_MOTOR_PORT = -1;        // CAN Port.
    public static final int MUNCHER_OUTTAKE_PORT = -1;      // CAN Port.
    public static final int MUNCHER_BREAKBEAM_PORT = -1;    // DIO Port. Don't know if this will actually exist or not...


     /*
      * SWERVE
      */
    public static final int SWERVE_FL_DRIVE_MOTOR_PORT = -1;    // CAN Port.
    public static final int SWERVE_FL_ROTATE_MOTOR_PORT = -1;   // CAN Port.
    public static final int SWERVE_FL_ENCODER_PORT = -1;        // CAN Port.

    public static final int SWERVE_FR_DRIVE_MOTOR_PORT = -1;    // CAN Port.
    public static final int SWERVE_FR_ROTATE_MOTOR_PORT = -1;   // CAN Port.
    public static final int SWERVE_FR_ENCODER_PORT = -1;        // CAN Port.

    public static final int SWERVE_BL_DRIVE_MOTOR_PORT = -1;    // CAN Port.
    public static final int SWERVE_BL_ROTATE_MOTOR_PORT = -1;   // CAN Port.
    public static final int SWERVE_BL_ENCODER_PORT = -1;        // CAN Port.

    public static final int SWERVE_BR_DRIVE_MOTOR_PORT = -1;    // CAN Port.
    public static final int SWERVE_BR_ROTATE_MOTOR_PORT = -1;   // CAN Port.
    public static final int SWERVE_BR_ENCODER_PORT = -1;        // CAN Port.


      /*
       * VISION
       */
}
