package frc.robot.subsystems.swerve;

import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.config.PIDConstants;
import com.pathplanner.lib.config.RobotConfig;
import com.pathplanner.lib.controllers.PPHolonomicDriveController;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.kinematics.SwerveDriveOdometry;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Robot;
import frc.robot.subsystems.swerve.io.SwerveCompetitionGyro;
import frc.robot.subsystems.swerve.io.SwerveGyroIO;
import frc.robot.subsystems.swerve.io.SwerveModuleIO;
import frc.robot.subsystems.swerve.io.SwerveModuleKraken;
import frc.robot.subsystems.swerve.io.SwerveModuleNeo;
import frc.robot.subsystems.swerve.io.SwerveTestGyro;

/**
 * For swerve, +x represents the forward direction and +y represents left.
 */
public class SwerveSubsystem extends SubsystemBase {

    // These get updated via shuffleboard
    // Drive for forward/backward/strafing speed
    // Turn for, well, turning speeds...
    public double speedDriveModifier = 0.5;
    public double speedTurnModifier = 0.5;

    /** This controls which direction is the forwards direction for joystick control. */
    public Rotation2d forwardsControllingRotation;

    // Used for PathPlanner
    private ChassisSpeeds pathPlannerChassisSpeeds;
    private boolean isPathPlannerRunning;

    // The module offsets from the CENTER of the robot to the CENTER of the wheel on each module.
    // All in meters. +x = forwards. +y = left.
    private final Translation2d frontLeftLocation = new Translation2d(+0.32, +0.32);
    private final Translation2d frontRightLocation = new Translation2d(+0.32, -0.32);
    private final Translation2d backLeftLocation = new Translation2d(-0.32, +0.32);
    private final Translation2d backRightLocation = new Translation2d(-0.32, -0.32);

    // For Kraken swerve (competition bot)
    public final SwerveModuleIO frontLeft = new SwerveModuleKraken(Constants.SWERVE_FL_DRIVE_MOTOR_PORT, Constants.SWERVE_FL_TURN_MOTOR_PORT, Constants.SWERVE_FL_ENCODER_PORT, new Rotation2d(Units.degreesToRadians(115.444)));
    public final SwerveModuleIO frontRight = new SwerveModuleKraken(Constants.SWERVE_FR_DRIVE_MOTOR_PORT, Constants.SWERVE_FR_TURN_MOTOR_PORT, Constants.SWERVE_FR_ENCODER_PORT, new Rotation2d(Units.degreesToRadians(-61.988)));
    public final SwerveModuleIO backLeft = new SwerveModuleKraken(Constants.SWERVE_BL_DRIVE_MOTOR_PORT, Constants.SWERVE_BL_TURN_MOTOR_PORT, Constants.SWERVE_BL_ENCODER_PORT, new Rotation2d(Units.degreesToRadians(-28.151)));
    public final SwerveModuleIO backRight = new SwerveModuleKraken(Constants.SWERVE_BR_DRIVE_MOTOR_PORT, Constants.SWERVE_BR_TURN_MOTOR_PORT, Constants.SWERVE_BR_ENCODER_PORT, new Rotation2d(Units.degreesToRadians(-118.562)));
    
    // For Neo swerve (test bot)
    // public final SwerveModuleIO frontLeft = new SwerveModuleNeo(10, 6, 1, new Rotation2d(Units.degreesToRadians(-41.081)), false);
    // public final SwerveModuleIO frontRight = new SwerveModuleNeo(8, 5, 4, new Rotation2d(Units.degreesToRadians(-87.939)), false);
    // public final SwerveModuleIO backLeft = new SwerveModuleNeo(7, 9, 3, new Rotation2d(Units.degreesToRadians(-14.3)), true);
    // public final SwerveModuleIO backRight = new SwerveModuleNeo(12, 11, 2, new Rotation2d(Units.degreesToRadians(10.637)), false);
    
    
    /** This is just the type of gyro we have. */
    private final SwerveGyroIO gyro = new SwerveCompetitionGyro(Constants.SWERVE_GYRO_OFFSET);
    // private final SwerveGyroIO gyro = new SwerveTestGyro(Constants.SWERVE_GYRO_OFFSET);

    /** The kinematics is used for doing the math required for going from desired positions to actual speeds and vice versa. */
    private final SwerveDriveKinematics kinematics = new SwerveDriveKinematics(frontLeftLocation, frontRightLocation, backLeftLocation, backRightLocation);

    /** The odometry does the math for us to calculate current/expected position. */
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
    
    /** Meters per second. +x is the forwards direction. */
    private double desiredSpeedX = 0.0;
    /** Meters per second. +y is the left direction. */
    private double desiredSpeedY = 0.0;
    /** Radians per second. Positive is the counterclockwise direction (the front of the robot turning left) */
    private double desiredSpeedRotation = 0.0;
    /** This should be true if the robot is actively being rotated, false if otherwise */
    private boolean isBeingRotated = false;
    /** This stores the rotation of the robot the last time it was being rotated */
    private Rotation2d lastRotation = new Rotation2d();

    /** Determines if the forwards direction depends on the robot's rotation or not. If true, forwards is NOT dependent on the robot's rotation. If false, forwards IS dependent on the robot's rotation. */
    private boolean isFieldOriented = true;


    /**
     * I honestly don't know if this constructor is needed yet.
     */
    public SwerveSubsystem() {
        gyro.resetGyro();

        if (Robot.driveController != null) {
            Robot.driveController.orientForwardsControllingDirection();
        }

        try {
        RobotConfig config = RobotConfig.fromGUISettings();
            // RobotConfig config = new RobotConfig(
            //     3, 
            //     null, 
            //     new ModuleConfig(
            //         0.048, 
            //         5.450, 
            //         1.200, 
            //         null, 
            //         null, 
            //         1
            //     ), 
            // 0.273
            // );

        // Configure AutoBuilder
        AutoBuilder.configure(
            this::getPosition, 
            this::resetPosition, 
            this::getSpeeds, 
            this::driveRobotRelativePathPlanner, 
            new PPHolonomicDriveController(
                new PIDConstants(5.0, 0.0, 0.0),    // for driving
                new PIDConstants(5.0, 0.0, 0.0)     // for turning
            ),
            config,
            () -> {
                // Boolean supplier that controls when the path will be mirrored for the red alliance
                // This will flip the path being followed to the red side of the field.
                // THE ORIGIN WILL REMAIN ON THE BLUE SIDE

                var alliance = DriverStation.getAlliance();
                if (alliance.isPresent()) {
                    return alliance.get() == DriverStation.Alliance.Red;
                }
                return false;
            },
            this
        );
        }catch(Exception e){
            DriverStation.reportError("Failed to load PathPlanner config and configure AutoBuilder", e.getStackTrace());
        }
    }


    /**
     * <p> This is going to make it constantly try to get to the desired set speeds.
     */
    @Override
    public void periodic() {
        updateOdometry();

        // If not actively rotating, then we should be correcting the rotation to stop "drifting"
        // Math.signum(number) returns -1 if the number is negative and +1 if the number is positive (or 0 if it is 0)
        if (!isBeingRotated && Math.abs(lastRotation.getDegrees() - getGyroRotation().getDegrees()) > 3) {
            this.desiredSpeedRotation = Math.signum(lastRotation.getDegrees() - getGyroRotation().getDegrees());
        }

        // Represents the desired speeds of the entire robot essentially
        ChassisSpeeds chassisSpeeds = new ChassisSpeeds(this.desiredSpeedX, this.desiredSpeedY, this.desiredSpeedRotation);
        // Makes the speeds relative to the field if true
        if (isFieldOriented) {
            chassisSpeeds = ChassisSpeeds.fromRobotRelativeSpeeds(chassisSpeeds, getGyroRotation());
        }
        // PathPlanner gives its own chassis speeds to use so...
        if (isPathPlannerRunning) {
            chassisSpeeds = pathPlannerChassisSpeeds;
        }
        // Essentially discretizing it makes it so that each direction (x, y, rotation) work independently based on time rather than each other (I think).
        chassisSpeeds = ChassisSpeeds.discretize(chassisSpeeds, Robot.currentPeriod);
        // Converts the desired chassis speeds into speeds for each swerve module.
        SwerveModuleState[] swerveModuleStates = kinematics.toWheelSpeeds(chassisSpeeds);
        // Max the speeds
        SwerveDriveKinematics.desaturateWheelSpeeds(swerveModuleStates, Constants.SWERVE_MAX_DRIVE_SPEED);
        // Is the below code ugly? Yes. However, it works. It's used mainly for testing.
        if (frontLeft.isManualControl()) { frontLeft.getToDesiredState(null, this.desiredSpeedX, this.desiredSpeedRotation); }
        else { frontLeft.getToDesiredState(swerveModuleStates[0]); }
        if (frontRight.isManualControl()) { frontRight.getToDesiredState(null, this.desiredSpeedX, this.desiredSpeedRotation); }
        else { frontRight.getToDesiredState(swerveModuleStates[1]); }
        if (backLeft.isManualControl()) { backLeft.getToDesiredState(null, this.desiredSpeedX, this.desiredSpeedRotation); }
        else { backLeft.getToDesiredState(swerveModuleStates[2]); }
        if (backRight.isManualControl()) { backRight.getToDesiredState(null, this.desiredSpeedX, this.desiredSpeedRotation); }
        else { backRight.getToDesiredState(swerveModuleStates[3]); }
    }


    /**
     * This is mainly used for PathPlanner
     */
    public void driveRobotRelativePathPlanner(ChassisSpeeds speeds) {
        this.isFieldOriented = false;
        this.pathPlannerChassisSpeeds = speeds;
        setUsingPathPlanner(true);
    }


    /**
     * Sets the setpoints that will be used to calculate swerve module speeds in the periodic method.
     * @param speedX A value between -1 to 1 where +1 represents 100% in the forwards direction.
     * @param speedY A value between -1 to 1 where +1 represents 100% in the left direction.
     * @param speedRotation A value between -1 to 1 where =1 represents 100% in the counterclockwise direction (the front of the robot turns left).
     * @param isFieldOriented Determines if the math should be calculated with a coordinate system relative to the field or to the robot. If true, forwards is the same direction regardless of robot rotation. If false, forwards is dependent on the robot's rotation.
     */
    public void setDesiredSpeeds(double speedX, double speedY, double speedRotation) {
        // The actual speeds are in meters/second, not percentages. Currently, the max speed in 5 meters per second
        this.desiredSpeedX = speedX * speedDriveModifier * 5;
        this.desiredSpeedY = speedY * speedDriveModifier * 5;
        // Except this one. Rotation is radians/second.
        this.desiredSpeedRotation = speedRotation * speedTurnModifier * 5;
        setUsingPathPlanner(false);

        if (desiredSpeedRotation != 0) {
            this.isBeingRotated = true;
            this.lastRotation = getGyroRotation();
        }
        else {
            this.isBeingRotated = false;
        }
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
        // Not negative because our gyro isn't dumb!
        return gyro.getRotation();
    }


    /**
     * Gets the current position based on the odometry.
     * @return The current position where +x is forwards and +y is left in meters.
     */
    public Pose2d getPosition() {
        return odometry.getPoseMeters();
    }


    /**
     * Get the speeds in a form that swerve code can understand (mostly used for path planner)
     * @return The speeds of the modules.
     */
    public ChassisSpeeds getSpeeds() {
        return kinematics.toChassisSpeeds(new SwerveModuleState[]{
           frontLeft.getState(),
           frontRight.getState(),
           backLeft.getState(),
           backRight.getState() 
        });
    }


    /**
     * This changes how the forward direction position of the robot is decided.
     * @param isFieldOriented True if forward should be the same regardless of robot rotation. False if forward should be dependent on which direction the robot is facing.
     */
    public void setFieldOriented(boolean isFieldOriented) {
        this.isFieldOriented = isFieldOriented;
    }


    /**
     * This determines if path planner is running or not.
     * @param usingPathPlanner True if path planner is actively running. False if otherwise.
     */
    public void setUsingPathPlanner(boolean usingPathPlanner) {
        this.isPathPlannerRunning = usingPathPlanner;
    }


    /**
     * This resets the position of the robot and reorients the forward's direction to be the same as the robot's rotation.
     */
    public void resetPosition(Pose2d startingPosition) {
        frontLeft.resetPosition();
        frontRight.resetPosition();
        backLeft.resetPosition();
        backRight.resetPosition();

        odometry.resetPosition(
            getGyroRotation(), 
            new SwerveModulePosition[] {
                frontLeft.getPosition(),
                frontRight.getPosition(),
                backLeft.getPosition(),
                backRight.getPosition()
            }, 
            startingPosition
        );
    }
    

    /** 
     * The following functions are mainly for the shuffleboard and tuning purposes.
     */
    public void setDriveMultiplier(double multiplier) {
        this.speedDriveModifier = multiplier;
    }
    public void setTurnMultiplier(double multiplier) {
        this.speedTurnModifier = multiplier;
    }


    /**
     * The following 10 functions are used to tune the PID and FeedForward.
     */
    public void updateDriveP(double p) {
        frontLeft.updateDriveP(p);
        frontRight.updateDriveP(p);
        backLeft.updateDriveP(p);
        backRight.updateDriveP(p);
    }
    public void updateDriveI(double i) {
        frontLeft.updateDriveI(i);
        frontRight.updateDriveI(i);
        backLeft.updateDriveI(i);
        backRight.updateDriveI(i);
    }
    public void updateDriveD(double d) {
        frontLeft.updateDriveD(d);
        frontRight.updateDriveD(d);
        backLeft.updateDriveD(d);
        backRight.updateDriveD(d);
    }
    public void updateTurnP(double p) {
        frontLeft.updateTurnP(p);
        frontRight.updateTurnP(p);
        backLeft.updateTurnP(p);
        backRight.updateTurnP(p);
    }
    public void updateTurnI(double i) {
        frontLeft.updateTurnI(i);
        frontRight.updateTurnI(i);
        backLeft.updateTurnI(i);
        backRight.updateTurnI(i);
    }
    public void updateTurnD(double d) {
        frontLeft.updateTurnD(d);
        frontRight.updateTurnD(d);
        backLeft.updateTurnD(d);
        backRight.updateTurnD(d);
    }
    public void updateDriveFFs(double ks) {
        frontLeft.updateDriveFFs(ks);
        frontRight.updateDriveFFs(ks);
        backLeft.updateDriveFFs(ks);
        backRight.updateDriveFFs(ks);
    }
    public void updateDriveFFv(double kv) {
        frontLeft.updateDriveFFv(kv);
        frontRight.updateDriveFFv(kv);
        backLeft.updateDriveFFv(kv);
        backRight.updateDriveFFv(kv);
    }
    public void updateTurnFFs(double ks) {
        frontLeft.updateTurnFFs(ks);
        frontRight.updateTurnFFs(ks);
        backLeft.updateTurnFFs(ks);
        backRight.updateTurnFFs(ks);
    }
    public void updateTurnFFv(double kv) {
        frontLeft.updateTurnFFv(kv);
        frontRight.updateTurnFFv(kv);
        backLeft.updateTurnFFv(kv);
        backRight.updateTurnFFv(kv);
    }
}
