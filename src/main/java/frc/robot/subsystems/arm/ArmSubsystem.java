package frc.robot.subsystems.arm;

import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.wpilibj.DutyCycleEncoder;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

/**
 * <p> For the arm, +x represents forwards and +y represents up. Why not +z? Well, Pose2d uses x,y and I'm too lazy to make my own.
 * <p> This includes both the pivot motor AND the extension motor as well as 1 absolute encoder on each motor.
 */
public class ArmSubsystem extends SubsystemBase {

    /**
     * Sadly, there is no easy way to zero the encoders. Therefore, the best we can do is have an offset.
     */
    /** To determine this, move the arm into the (0,0) state which is [...] and record the value of the absolute encoder read in the dashboard. */
    private static final Rotation2d PIVOT_ENCODER_OFFSET = new Rotation2d(0);
    /** Meters. To determine this, move the arm into the (0,0) state which is [...] and record the value of the absolute encoder read in the dashboard. */
    private static final double EXTENSION_ENCODER_OFFSET = 0.0;

    // This is the conversion factor from encoder units to radians for the pivot absolute encoder.
    // I believe the encoders are 1 unit per rotation, but because of physics (curse you Newton), it's not exact.
    // Therefore, after measuring the actual rotation versus the measured rotation, I found the black magic number below (currently TBD)
    private static final double PIVOT_ENCODER_UNITS_TO_RADIANS = (Math.PI*2) * 1.000;

    // This is the conversion factor from encoder units to meters for the extend absolute encoder.
    // I believe the encoders are 1 unit per rotation, but because of physics (curse you Newton), it's not exact.
    // Therefore, after measuring the actual rotation versus the measured rotation, I found the black magic number below (currently TBD)
    // The math for this is [...]
    private static final double EXTENSION_ENCODER_UNITS_TO_METERS = 1.000;


    // +x represents forwards and +y represents up.
    // 0,0 is currently undecided (we need the design of the arm before we decide.)
    private Pose2d desiredPosition;
    
    // Both are Krakens
    private TalonFX pivotMotor = new TalonFX(Constants.ARM_PIVOT_MOTOR_PORT);
    private TalonFX extendMotor = new TalonFX(Constants.ARM_EXTEND_MOTOR_PORT);
    
    // Absolute Encoders (their readings persist even after turning the robot off)
    // Both encoders are REV Through Bore Encoders (https://www.revrobotics.com/rev-11-1271/)
    // WPILib counts these as DutyCycleEncoders (https://docs.wpilib.org/en/stable/docs/software/hardware-apis/sensors/encoders-software.html)
    private DutyCycleEncoder pivotEncoder = new DutyCycleEncoder(Constants.ARM_PIVOT_ENCODER_PORT);
    private DutyCycleEncoder extendEncoder = new DutyCycleEncoder(Constants.ARM_EXTEND_ENCODER_PORT);

    // PID controllers for the arm
    // TODO: Tune these controllers!
    private ProfiledPIDController pivotPID = new ProfiledPIDController(
        3,
        0,
        0,
        new TrapezoidProfile.Constraints(Constants.ARM_MAX_ANGULAR_VELOCITY, Constants.ARM_MAX_ANGULAR_ACCELERATION)
    );

    private ProfiledPIDController extensionPID = new ProfiledPIDController(
        3,
        0,
        0,
        new TrapezoidProfile.Constraints(Constants.ARM_MAX_EXT_VELOCITY, Constants.ARM_MAX_EXT_ACCELERATION)
    );


    /**
     * <p> This will constantly try to get the arm to the desired position.
     */
    @Override
    public void periodic(){
        // [pivotDirection, extensionLength]
        double[] setPoint = getRotationExtensionFromSetPoint(desiredPosition.getX(), desiredPosition.getY());

        double pivotSpeed = pivotPID.calculate(pivotEncoder.get(), setPoint[0]);
        double extensionSpeed = extensionPID.calculate(extendEncoder.get(), setPoint[1]);

        pivotMotor.set(pivotSpeed);
        extendMotor.set(extensionSpeed);
    }
    

    /**
     * <p> Takes in a point in 2D space and returns the arm configuration to reach it. Outputs {-1, -1} if it is unreachable.
     * <p> (0,0) represents the base of the arm.
     * @param x The X value of the point to be reached where +x is the forwards direction (of the robot). Meters.
     * @param y The Y value of the point to be reached where +y is the up direction. Meters.
     * @return double[pivotDirection, extensionPercent] OR {-1,-1} if the point is unreachable:
     *          <p> - pivotDirection = The direction of the pivot in radians where 0 radians represents parallel to the ground (pointing forwards) and the positive direction is upwards
     *          <p> - extensionLength = The length to extend the arm to reach the point in meters.
     */
    public static double[] getRotationExtensionFromSetPoint(double x, double y) {
        double directionTowardsPoint = Math.atan2(y,x);
        double distanceToPoint = Math.sqrt((x*x)+(y*y));
        double lengthOfExtension = distanceToPoint - Constants.ARM_PIVOT_LENGTH;
        double percentOfExtension = lengthOfExtension/Constants.ARM_EXTEND_LENGTH;
        // Angle Boundaries: 0 radians to PI radians (range of motion: 180 degrees) 
        // Extension Boundaries: 0% to 100%
        if ((0 < percentOfExtension && percentOfExtension < 1) && (0 < directionTowardsPoint && directionTowardsPoint < Math.PI)) {
            return new double[]{directionTowardsPoint, lengthOfExtension};
        }
        return new double[]{-1,-1};
    }

    /**
     * <p> Rotates the pivot of the arm at the specified speed.
     * @param rotationalVelocity Power of the motor between -1 and 1 where +1 represents full rotation upwards.
     */
    public void rotate(double rotationalVelocity)
    {
        pivotMotor.set(rotationalVelocity);
    }

    /**
     * <p> Runs the extension motor for the arm at the specified speed.
     * @param extensionVelocity Power of the motor between -1 and 1 where +1 represents full extension and -1 represents full retraction.
     */
    public void extend(double extensionVelocity)
    {
        extendMotor.set(extensionVelocity);
    }


    /**
     * <p> This gets the rotation determined by the pivot absolute encoder after applying the conversion factor.
     * @return The rotation of the arm where 0 radians/degrees represents [...] <-- NEED TO DETERMINE THIS
     */
    public Rotation2d getPivotRotation() {
        return new Rotation2d(pivotEncoder.get() * PIVOT_ENCODER_UNITS_TO_RADIANS).minus(PIVOT_ENCODER_OFFSET);
        // TODO: Maybe implement encoder.isConnected() for doomsday code?
    }


    /**
     * <p> This gets the length of the current extension based on the absolute encoder attached after applying the conversion factor.
     * @return The length of the arm's extension in meters where 0 represents no extension.
     */
    public double getExtensionLength() {
        return extendEncoder.get() * EXTENSION_ENCODER_UNITS_TO_METERS - EXTENSION_ENCODER_OFFSET;
        // TODO: Maybe implement encoder.isConnected() for doomsday code?
    }


    /**
     * Just some testing for the arm math.
     * Check out the desmos graph linked below for the math in more detail:
     * https://www.desmos.com/calculator/acillm6yyc
     */
    public static void main(String[] args) {
        double[] output = getRotationExtensionFromSetPoint(20,20);
        System.out.println(output[0]);
        System.out.println(output[1]);
    }
}