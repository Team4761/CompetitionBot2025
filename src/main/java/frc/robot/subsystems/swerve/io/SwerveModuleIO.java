package frc.robot.subsystems.swerve.io;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.math.kinematics.SwerveModuleState;

/**
 * This is the blueprint for everything a swerve module needs to be able to do.
 */
public interface SwerveModuleIO {
    
    /**
     * Actually runs the motors to get the speed and rotation dictated by the SwerveSubsystem.
     * @param desiredState This is figured out by the SwerveSubsystem depending on the desired speeds.
     * @param manualSpeeds If the module is in manual control, this will be used instead of the desiredState. [0] = drive, [1] = turn.
     */
    public void getToDesiredState(SwerveModuleState desiredState, double... manualSpeeds);

    /**
     * Gets the current state of the entire module in a form that WPILib can understand.
     * This includes the speed of the wheel and the current rotation.
     * @return The current SwerveModuleState of the module.
     */
    public SwerveModuleState getState();

    /**
     * Returns the current position of the module in a form that WPILib can understand.
     * This includes the current distance travelled by the motor and its rotation.
     * @return The current SwerveModulePosition of the module.
     */
    public SwerveModulePosition getPosition();

    /**
     * Get the expected distance traveled by the wheel in meters after applying nthe conversion factor.
     * @return The distance traveled by the wheel in meters.
     */
    public double getDrivePosition();

    /**
     * Get the expected velocity of the wheel in meters per second after applying nthe conversion factor.
     * @return The velocity of the wheel in meters per second.
     */
    public double getDriveVelocity();

    /**
     * Gets the rotation of the wheel.
     * @return The rotation of the wheel.
     */
    public Rotation2d getWheelRotation();

    /**
     * Gets the angular velocity of the wheel in radians per second.
     * @return The angular velocity of the wheel in radians per second.
     */
    public Rotation2d getAngularVelocity();

    /**
     * Mainly for debugging purposes. This gets the exact reading of the turn encoder in its native units of "rotations".
     * @return The encoder units that describe the rotation of the wheel. It should be "rotations", but it's slightly off, so pain.
     */
    public double getTurnEncoderReading();

    /**
     * This literally just sets the distance traveled to 0.
     */
    public void resetPosition();

    /**
     * Determines if the motor should be completely manually controlled. As in forward runs the drive motor and rotate just rotates the wheel.
     * @param isManualControl True if it should be fully manual.
     */
    public void setManualControl(boolean isManualControl);

    /**
     * @return The PID controller of the swerve module for running the wheel.
     */
    public PIDController getDrivePIDController();

    /**
     * @return The PID controller of the swerve module for turning the direction of the wheel.
     */
    public ProfiledPIDController getTurningPIDController();

    /**
     * @return The Feedforward for the driving.
     */
    public SimpleMotorFeedforward getDriveFeedforward();

    /**
     * @return The Feedforward for the turning of the direction of the wheels.
     */
    public SimpleMotorFeedforward getTurnFeedforward();

    /** 
     * @return True if this module is currently in manual mode.
    */
    public boolean isManualControl();

    /**
     * Determines if the motor should run at all.
     * @param enabled If true, the motor should run. If false, no.
     */
    public void setEnabled(boolean enabled);

    // I'm sorry.
    public void updateDriveP(double p);
    public void updateDriveI(double i);
    public void updateDriveD(double d);
    public void updateTurnP(double p);
    public void updateTurnI(double i);
    public void updateTurnD(double d);
    public void updateDriveFFs(double ks);
    public void updateDriveFFv(double kv);
    public void updateTurnFFs(double ks);
    public void updateTurnFFv(double kv);
}
