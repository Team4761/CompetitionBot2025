package frc.robot.subsystems.swerve.io;

import edu.wpi.first.math.geometry.Rotation2d;

public interface SwerveGyroIO {
    /**
     * This is meant to get the current rotation after applying the offset and necessary rotations.
     * @return The rotation of the gyro (and therefore robot) where positive is the counterclockwise direction (to the left of the front)
     */
    public Rotation2d getRotation();

    /**
     * This should set the gyro's rotation to zero.
     */
    public void resetGyro();

    /**
     * This sets the offset to be used by the gyro;
     */
    public void setOffset(Rotation2d offset);

}
