package frc.robot.subsystems.swerve;

import com.ctre.phoenix6.hardware.CANcoder;
import com.ctre.phoenix6.hardware.TalonFX;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Constants;

/**
 * A swerve module involves...
 * - 1 Kraken for driving
 * - 1 NEO for rotating the wheel
 * - 1 CANcoder for reading which direction the wheel is pointed in.
 */
public class SwerveModule {

    // This would be 2pi in an ideal world, or 6.283185, but it's not because of physics :(
    // Instead, I manually spun the front right wheel 20 times and recorded the rotation of that, and then divided by 20. The more rotations, the more accurate.
    private static final double TURN_UNITS_PER_ROTATION = (Math.PI * 2);

    // This determines if the motor should be trying to get to the desired state.
    public boolean enabled = true;

    // This determines if the wheels can be manually controlled rather than PID controlled.
    public boolean isManualControl = false;

    // The turn motor is a NEO. The drive motor is a Kraken.
    // They changed CANSparkMax to SparkMAX... :(  -https://docs.revrobotics.com/brushless/spark-max/overview
    private SparkMax turnMotor;
    private TalonFX driveMotor;

    /** We have a separate encoder attached directly to the wheel to more accurately determine rotation. */
    private CANcoder turnEncoder;
    // No drive encoder needed because you can do driveMotor.getPosition()

    // Need an offset for the turn encoder
    // To find this value, set the offset to 0, manually rotate the wheel to face forwards, and then record the outputted rotation of the wheel from shuffleboard.
    private Rotation2d turnOffset;  // PLACEHOLDER! DO NOT CHANGE HERE, CHANGE IN SwerveSubsystem

    
    // This determines the speed of the drive motor based on...
    // kP = proportional: This changes the speed based on how far away the motor is from its desired position.
    // kI = integral: This changes the speed based on how long the program has been running for (highly recommended to keep this at 0)
    // kD = derivative: This changes the speed based on the current speed (no need to get faster if you're already going fast).
    private final PIDController drivePIDController = new PIDController(2, 0, 0);

    // A ProfiledPIDController is the same as above but also includes a max speed and max acceleration.
    private final ProfiledPIDController turningPIDController = new ProfiledPIDController(
        4,
        0,
        0,
        new TrapezoidProfile.Constraints(Constants.SWERVE_MAX_ANGULAR_VELOCITY, Constants.SWERVE_MAX_ANGULAR_ACCELERATION)
    );

    // Feed forward literally predicts the future and determines a MINIMUM speed to maintain the current position.
    // Typically this isn't needed, so the values (ks and kv) are set to 0 for now (Jan 11, 2025).
    private SimpleMotorFeedforward driveFeedforward = new SimpleMotorFeedforward(0, 0);
    private SimpleMotorFeedforward turnFeedforward = new SimpleMotorFeedforward(0, 0);

    
    /**
     * Represents an entire Swerve Module including it's drive motor, turn motor, and turn encoder.
     * @param driveMotorID CAN bus: Kraken motor ID. Find using Phoenix Tuner X.
     * @param turnMotorID CAN bus: SparkMAX motor controller ID for a NEO. Find using Rev Hardware Client.
     * @param turnEncoderID CAN bus: CANcoder id. Find using Phoenix Tuner X.
     * @param turnOffset Find by setting this to 0.0, rotating the wheel to face forwards, and use the value recorded on Shuffleboard.
     */
    public SwerveModule(int driveMotorID, int turnMotorID, int turnEncoderID, Rotation2d turnOffset) {
        this.driveMotor = new TalonFX(driveMotorID);
        this.turnMotor = new SparkMax(turnMotorID, MotorType.kBrushless);
        this.turnEncoder = new CANcoder(turnEncoderID);

        this.turnOffset = turnOffset;
    }


    /**
     * Actually runs the motors to get the speed and rotation dictated by the SwerveSubsystem.
     * @param desiredState This is figured out by the SwerveSubsystem depending on the desired speeds.
     * @param manualSpeeds If the module is in manual control, this will be used instead of the desiredState. [0] = drive, [1] = turn.
     */
    public void getToDesiredState(SwerveModuleState desiredState, double... manualSpeeds) {
        if (this.enabled) {
            // Just turn motors at desired speeds.
            if (this.isManualControl && manualSpeeds != null) {
                driveMotor.setVoltage(manualSpeeds[0]);
                turnMotor.setVoltage(manualSpeeds[1]);
            }
            // Use PID control
            else {
                Rotation2d wheelRotation = getWheelRotation();

                // Optimize the desired state to avoid spinning further than 90 degrees
                desiredState.optimize(wheelRotation);

                // Scale speed by cosine of angle error. This scales down movement perpendicular to the desired
                // direction of travel that can occur when modules change directions.
                // This results in smoother driving because the wheel won't try to go at 100% speed WHILE also rotating itself.
                // desiredState.speedMetersPerSecond = desiredState.speedMetersPerSecond * Math.cos(desiredState.angle.getRadians() - wheelRotation.getRadians());

                // Calculate the voltage sent to the driving motor using the drive PID controller.
                final double driveOutput = drivePIDController.calculate(getDriveVelocity(), desiredState.speedMetersPerSecond);
                final double driveFF = driveFeedforward.calculate(desiredState.speedMetersPerSecond);

                // Calculate the turning motor output using the turning PID controller.
                final double turnOutput = turningPIDController.calculate(getWheelRotation().getRadians(), desiredState.angle.getRadians());
                final double turnFF = turnFeedforward.calculate(turningPIDController.getSetpoint().velocity);

                // Scale the drive voltage proportionally to the max voltage and speed value from shuffleboard.
                SmartDashboard.putNumber("Drive Voltage", driveOutput+driveFF);
                driveMotor.setVoltage((driveOutput + driveFF));
                // Turn motor reversed because of gears *dies inside more than is physically possible*
                turnMotor.setVoltage(-(turnOutput + turnFF));
            }
        }
        // If not enabled
        else {
            driveMotor.setVoltage(0);
            turnMotor.setVoltage(0);
        }
    }


    /**
     * Gets the current state of the entire module in a form that WPILib can understand.
     * This includes the speed of the wheel and the current rotation.
     * @return The current SwerveModuleState of the module.
     */
    public SwerveModuleState getState() {
        return new SwerveModuleState(
            getDriveVelocity(), 
            getWheelRotation()
        );
    }

    /**
     * Returns the current position of the module in a form that WPILib can understand.
     * This includes the current distance travelled by the motor and its rotation.
     * @return The current SwerveModulePosition of the module.
     */
    public SwerveModulePosition getPosition() {
        return new SwerveModulePosition(
            getDrivePosition(), 
            getWheelRotation()
        );
    }
    

    /**
     * Get the expected distance traveled by the wheel in meters after applying nthe conversion factor.
     * @return The distance traveled by the wheel in meters.
     */
    public double getDrivePosition() {
        // getPosition() gives you the "mechanism rotations", so 1 should equal a 360 degree rotation.
        // Therefore, (rotations) * (wheel_circumference) / (gear_ratio) = (distance_traveled)
        return (driveMotor.getPosition().getValueAsDouble()) * (Math.PI * 2 * Constants.WHEEL_RADIUS) / (Constants.SWERVE_DRIVE_MOTOR_GEAR_RATIO);
    }

    /**
     * Get the expected velocity of the wheel in meters per second after applying nthe conversion factor.
     * @return The velocity of the wheel in meters per second.
     */
    public double getDriveVelocity() {
        // Uses the same math at getDrivePosition()
        return (driveMotor.getVelocity().getValueAsDouble()) * (Math.PI * 2 * Constants.WHEEL_RADIUS) / (Constants.SWERVE_DRIVE_MOTOR_GEAR_RATIO);
    }


    /**
     * Gets the rotation of the wheel.
     * @return The rotation of the wheel.
     */
    public Rotation2d getWheelRotation() {
        // getAbsolutePosition() counts up by full rotations as well (I think). So 1 equals a 360 degree rotation.
        // Therefore, (rotations) * (radians_per_rotation) = (radians)
        // Also apply the offset here.
        // However, because physics is a pain, (radians_per_rotation) is not actually 2pi, it's a different value.
        // The explanation for that can be found above the TURN_UNITS_PER_ROTATION constant at the top of this file.
        return new Rotation2d((turnEncoder.getAbsolutePosition().getValueAsDouble()) * TURN_UNITS_PER_ROTATION).minus(turnOffset);
    }

    /**
     * Gets the angular velocity of the wheel in radians per second.
     * @return The angular velocity of the wheel in radians per second.
     */
    public Rotation2d getAngularVelocity() {
        // Uses the same math as getWheelRotation() without the offset
        return new Rotation2d((turnEncoder.getVelocity().getValueAsDouble()) * TURN_UNITS_PER_ROTATION);
    }


    /**
     * Mainly for debugging purposes. This gets the exact reading of the turn encoder in its native units of "rotations".
     * @return The encoder units that describe the rotation of the wheel. It should be "rotations", but it's slightly off, so pain.
     */
    public double getTurnEncoderReading() {
        return turnEncoder.getAbsolutePosition().getValueAsDouble();
    }


    /**
     * This literally just sets the distance traveled to 0.
     */
    public void resetPosition() {
        driveMotor.setPosition(0);
    }


    /**
     * Determines if the motor should run at all.
     * @param enabled If true, the motor should run. If false, no.
     */
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }


    /**
     * Determines if the motor should be completely manually controlled. As in forward runs the drive motor and rotate just rotates the wheel.
     * @param isManualControl True if it should be fully manual.
     */
    public void setManualControl(boolean isManualControl) {
        this.isManualControl = isManualControl;
    }


    /**
     * The following 10 methods are for tuning PID and feedforward exclusively.
     */
    public void updateDriveP(double p) {
        drivePIDController.setP(p);
    }
    public void updateDriveI(double i) {
        drivePIDController.setI(i);
    }
    public void updateDriveD(double d) {
        drivePIDController.setD(d);
    }
    public void updateTurnP(double p) {
        turningPIDController.setP(p);
    }
    public void updateTurnI(double i) {
        turningPIDController.setI(i);
    }
    public void updateTurnD(double d) {
        turningPIDController.setD(d);
    }
    public void updateDriveFFs(double ks) {
        driveFeedforward = new SimpleMotorFeedforward(ks, driveFeedforward.getKv());
    }
    public void updateDriveFFv(double kv) {
        driveFeedforward = new SimpleMotorFeedforward(driveFeedforward.getKs(), kv);
    }
    public void updateTurnFFs(double ks) {
        turnFeedforward = new SimpleMotorFeedforward(ks, turnFeedforward.getKv());
    }
    public void updateTurnFFv(double kv) {
        turnFeedforward = new SimpleMotorFeedforward(turnFeedforward.getKs(), kv);
    }
}
