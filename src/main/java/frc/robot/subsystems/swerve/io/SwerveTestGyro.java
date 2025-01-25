package frc.robot.subsystems.swerve.io;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.ADXRS450_Gyro;

public class SwerveTestGyro implements SwerveGyroIO {

    private Rotation2d offset;

    private ADXRS450_Gyro gyro;

    public SwerveTestGyro(Rotation2d offset) {
        this.offset = offset;
        gyro = new ADXRS450_Gyro();
    }
    
    @Override
    public Rotation2d getRotation() {
        // Not negated because the gyro is upside down... I'm confused now xD
        // Positive direction should be counterclockwise
        return new Rotation2d(Units.degreesToRadians(gyro.getAngle())).minus(offset);
    }


    @Override
    public void setOffset(Rotation2d offset) {

    }


    @Override
    public void resetGyro() {
        gyro.reset();
    }
}
