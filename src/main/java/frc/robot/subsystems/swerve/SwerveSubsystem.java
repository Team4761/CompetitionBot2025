package frc.robot.subsystems.swerve;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.kinematics.SwerveDriveOdometry;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.ADIS16470_IMU;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Robot;

/**
 * For swerve, +x represents the forward direction and +y represents left.
 */
public class SwerveSubsystem extends SubsystemBase {

    // The module offsets from the CENTER of the robot to the CENTER of the wheel on each module.
    // All in meters. +x = forwards. +y = left.
    private final Translation2d frontLeftLocation = new Translation2d(+0.32, +0.32);
    private final Translation2d frontRightLocation = new Translation2d(+0.32, -0.32);
    private final Translation2d backLeftLocation = new Translation2d(-0.32, +0.32);
    private final Translation2d backRightLocation = new Translation2d(-0.32, -0.32);

    public final SwerveModule frontLeft = new SwerveModule(Constants.SWERVE_FL_DRIVE_MOTOR_PORT, Constants.SWERVE_FL_TURN_MOTOR_PORT, Constants.SWERVE_FL_ENCODER_PORT, new Rotation2d(Units.degreesToRadians(-30.41)));
    public final SwerveModule frontRight = new SwerveModule(Constants.SWERVE_FR_DRIVE_MOTOR_PORT, Constants.SWERVE_FR_TURN_MOTOR_PORT, Constants.SWERVE_FR_ENCODER_PORT, new Rotation2d(Units.degreesToRadians(23.20)));
    public final SwerveModule backLeft = new SwerveModule(Constants.SWERVE_BL_DRIVE_MOTOR_PORT, Constants.SWERVE_BL_TURN_MOTOR_PORT, Constants.SWERVE_BL_ENCODER_PORT, new Rotation2d(Units.degreesToRadians(211.55)));
    public final SwerveModule backRight = new SwerveModule(Constants.SWERVE_BR_DRIVE_MOTOR_PORT, Constants.SWERVE_BR_TURN_MOTOR_PORT, Constants.SWERVE_BR_ENCODER_PORT, new Rotation2d(Units.degreesToRadians(240.82)));

    // This is just the type of gyro we have.
    private final ADIS16470_IMU gyro = new ADIS16470_IMU();

    // The kinematics is used for doing the math required for going from desired positions to actual speeds and vice versa.
    private final SwerveDriveKinematics kinematics = new SwerveDriveKinematics(frontLeftLocation, frontRightLocation, backLeftLocation, backRightLocation);

    // The odometry does the math for us to calculate current/expected position.
    private final SwerveDriveOdometry odometry = new SwerveDriveOdometry(
            kinematics,
            getGyroRotation(),
            new SwerveModulePosition[] {
                frontLeft.getPosition(),
                frontRight.getPosition(),
                backLeft.getPosition(),
                backRight.getPosition()
        }
    );
    
    // All as a number between -1 to 1 where 1 represents 100% speed forwards.
    private double desiredSpeedX = 0.0;
    private double desiredSpeedY = 0.0;
    private double desiredSpeedRotation = 0.0;
    // Determines if the forwards direction depends on the robot's rotation or not. If true, forwards is NOT dependent on the robot's rotation. If false, forwards IS dependent on the robot's rotation.
    private boolean isFieldOriented;


    /**
     * I honestly don't know if this constructor is needed yet.
     */
    public SwerveSubsystem() {
        gyro.reset();
    }


    /**
     * <p> This is going to make it constantly try to get to the desired set speeds.
     */
    @Override
    public void periodic() {
        updateOdometry();

        // Represents the desired speeds of the entire robot essentially
        ChassisSpeeds chassisSpeeds = new ChassisSpeeds(this.desiredSpeedX, this.desiredSpeedY, this.desiredSpeedRotation);
        // Makes the speeds relative to the field if true
        if (isFieldOriented) {
            chassisSpeeds = ChassisSpeeds.fromRobotRelativeSpeeds(chassisSpeeds, getGyroRotation());
        }
        // Essentially discretizing it makes it so that each direction (x, y, rotation) work independently based on time rather than each other (I think).
        chassisSpeeds = ChassisSpeeds.discretize(chassisSpeeds, Robot.currentPeriod);
        // Converts the desired chassis speeds into speeds for each swerve module.
        SwerveModuleState[] swerveModuleStates = kinematics.toWheelSpeeds(chassisSpeeds);
        // Max the speeds
        SwerveDriveKinematics.desaturateWheelSpeeds(swerveModuleStates, Constants.SWERVE_MAX_DRIVE_SPEED);
        frontLeft.getToDesiredState(swerveModuleStates[0]);
        frontRight.getToDesiredState(swerveModuleStates[1]);
        backLeft.getToDesiredState(swerveModuleStates[2]);
        backRight.getToDesiredState(swerveModuleStates[3]);

        if (Robot.shuffleboard != null)
            Robot.shuffleboard.updateSwerve();
    }


    /**
     * Sets the setpoints that will be used to calculate swerve module speeds in the periodic method.
     * @param speedX A value between -1 to 1 where +1 represents 100% in the forwards direction.
     * @param speedY A value between -1 to 1 where +1 represents 100% in the left direction.
     * @param speedRotation A value between -1 to 1 where =1 represents 100% in the counterclockwise direction (the front of the robot turns left).
     * @param isFieldOriented Determines if the math should be calculated with a coordinate system relative to the field or to the robot. If true, forwards is the same direction regardless of robot rotation. If false, forwards is dependent on the robot's rotation.
     */
    public void setDesiredSpeeds(double speedX, double speedY, double speedRotation, boolean isFieldOriented) {
        this.desiredSpeedX = speedX;
        this.desiredSpeedY = speedY;
        this.desiredSpeedRotation = speedRotation;
        this.isFieldOriented = isFieldOriented;
    }


    /**
     * Updates the field relative position (odometry) of the robot. 
     */
    public void updateOdometry() {
        odometry.update(
            getGyroRotation(),
            new SwerveModulePosition[] {
                frontLeft.getPosition(),
                frontRight.getPosition(),
                backLeft.getPosition(),
                backRight.getPosition()
            }
        );
    }


    /**
     * Converts the gyro's rotation into a Rotation2d after applying an offset.
     * @return The gyro's rotation after accounting for the offset.
     */
    public Rotation2d getGyroRotation() {
        return new Rotation2d(Units.degreesToRadians(gyro.getAngle())).minus(Constants.SWERVE_GYRO_OFFSET);
    }


    /**
     * Gets the current position based on the odometry.
     * @return The current position where +x is forwards and +y is left in meters.
     */
    public Pose2d getPosition() {
        return odometry.getPoseMeters();
    }
}
