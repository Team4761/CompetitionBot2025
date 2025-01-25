package frc.robot.subsystems.swerve.io;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.ADIS16470_IMU;
import frc.robot.Constants;

public class SwerveCompetitionGyro implements SwerveGyroIO {

    private Rotation2d offset;

    private final ADIS16470_IMU gyro;


    public SwerveCompetitionGyro(Rotation2d offset) {
        this.offset = offset;
        // The gyro doesn't need an ID because it's literally on the robot.
        gyro = new ADIS16470_IMU();
    }
    
    public Rotation2d getRotation() {
        // Negative because gyro's are dumb.
        return new Rotation2d(-(new Rotation2d(Units.degreesToRadians(gyro.getAngle())).minus(offset)).getRadians());
    }


    @Override
    public void setOffset(Rotation2d offset) {
        this.offset = offset;
    }


    @Override
    public void resetGyro() {
        gyro.reset();
    }
}
