package frc.robot.dashboard;


import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.util.sendable.Sendable;
import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;
import frc.robot.dashboard.reactive.ReactiveBooleanEntry;
import frc.robot.dashboard.reactive.ReactiveNumberEntry;

/**
 * This is to have a nice and straightforward way of displaying data/settings on the dashboard during competition and debugging.
 * You might be thinking to yourself: why am I not using Shuffleboard?
 * Well, the WPILib gods have determined that Shuffleboard is blasphemy, and for some reason, it has forever stopped working.
 * Instead, we now use Elastic for most things and Glass when Elastic doesn't work.
 * This is for long term logging and settings.
 */
public class RobocketsDashboard {

    Field2d field;

    /**
     * SWERVE
     */
    // Modules
    NetworkTableEntry swerveFLDistanceTraveled;
    NetworkTableEntry swerveFLCurrentRotation;
    NetworkTableEntry swerveFLDesiredDriveSpeed;
    NetworkTableEntry swerveFLDesiredTurnSpeed;
    ReactiveBooleanEntry swerveFLEnabled;
    ReactiveBooleanEntry swerveFLManualControl;

    NetworkTableEntry swerveFRDistanceTraveled;
    NetworkTableEntry swerveFRCurrentRotation;
    NetworkTableEntry swerveFRTurnEncoderReading;
    NetworkTableEntry swerveFRDesiredDriveSpeed;
    NetworkTableEntry swerveFRDesiredTurnSpeed;
    ReactiveBooleanEntry swerveFREnabled;
    ReactiveBooleanEntry swerveFRManualControl;

    NetworkTableEntry swerveBLDistanceTraveled;
    NetworkTableEntry swerveBLCurrentRotation;
    NetworkTableEntry swerveBLDesiredDriveSpeed;
    NetworkTableEntry swerveBLDesiredTurnSpeed;
    ReactiveBooleanEntry swerveBLEnabled;
    ReactiveBooleanEntry swerveBLManualControl;

    NetworkTableEntry swerveBRDistanceTraveled;
    NetworkTableEntry swerveBRCurrentRotation;
    NetworkTableEntry swerveBRDesiredDriveSpeed;
    NetworkTableEntry swerveBRDesiredTurnSpeed;
    ReactiveBooleanEntry swerveBREnabled;
    ReactiveBooleanEntry swerveBRManualControl;

    // Entire Drivetrain
    NetworkTableEntry swerveXPosition;
    NetworkTableEntry swerveYPosition;
    NetworkTableEntry swerveRotation;
    NetworkTableEntry swerveGyroRotation;

    // Tuning
    ReactiveNumberEntry swerveDriveP;
    ReactiveNumberEntry swerveDriveI;
    ReactiveNumberEntry swerveDriveD;
    ReactiveNumberEntry swerveDriveFFs;
    ReactiveNumberEntry swerveDriveFFv;

    ReactiveNumberEntry swerveTurnP;
    ReactiveNumberEntry swerveTurnI;
    ReactiveNumberEntry swerveTurnD;
    ReactiveNumberEntry swerveTurnFFs;
    ReactiveNumberEntry swerveTurnFFv;

    ReactiveBooleanEntry swerveDriveInverted;
    ReactiveBooleanEntry swerveStrafeInverted;
    ReactiveBooleanEntry swerveTurnInverted;

    // Settings
    ReactiveNumberEntry swerveDriveSpeed;
    ReactiveNumberEntry swerveTurnSpeed;
    ReactiveBooleanEntry swerveFieldOriented;


    /**
     * ARM
     */
    ReactiveBooleanEntry armManualControlEnabled;
    // Pivot
    NetworkTableEntry armPivotAngle;
    NetworkTableEntry armPivotRaw;
    ReactiveBooleanEntry rotateArmMotorEnabled;
 

    // Extension
    NetworkTableEntry armExtensionLength;
    NetworkTableEntry armExtensionRaw;
    ReactiveBooleanEntry extendArmMotorEnabled;

    
    // Tuning
    ReactiveNumberEntry armPivotP;
    ReactiveNumberEntry armPivotI;
    ReactiveNumberEntry armPivotD;
    ReactiveNumberEntry armPivotKs;
    ReactiveNumberEntry armPivotKv;

    ReactiveNumberEntry armExtensionP;
    ReactiveNumberEntry armExtensionI;
    ReactiveNumberEntry armExtensionD;
    ReactiveNumberEntry armExtensionKs;
    ReactiveNumberEntry armExtensionKv;

    // Visualization?
    // I don't know what to do here yet...


    /**
     * MUNCHER
     */
    // Intake/Outtake
    ReactiveNumberEntry muncherIntakeSpeed;
    ReactiveNumberEntry muncherOuttakeSpeed;

    // Yeeter
    ReactiveNumberEntry muncherYeetSpeed;


    /**
     * LEDs
     */
    // No idea what to put here...


    /**
     * VISION
     */
    // April Tag Info
    NetworkTableEntry visionAprilTagID;
    NetworkTableEntry visionX;
    NetworkTableEntry visionY;
    NetworkTableEntry visionZ;
    NetworkTableEntry visionYaw;
    NetworkTableEntry visionPitch;
    

    /**
     * INITIALIZATION
     */
    public RobocketsDashboard() {

        field = new Field2d();

        initializeGeneralInfo();
        if (Robot.map.swerve != null) { initializeSwerveInfo(); }
    }


    /**
     * INITIALIZING INFO
     */

    /**
     * This is for any information that is not subsystem specific.
     */
    public void initializeGeneralInfo() {
        // Currently nothing here...
    }


    /**
     * There's a lot of stuff here, so buckle up.
     */
    public void initializeSwerveInfo() {
        // Settings
        swerveDriveSpeed = new ReactiveNumberEntry(Robot.map.swerve::setDriveMultiplier, putNumber("Swerve Drive Speed", 0.5));
        swerveTurnSpeed = new ReactiveNumberEntry(Robot.map.swerve::setTurnMultiplier, putNumber("Swerve Turn Speed", 0.5));
        swerveFieldOriented = new ReactiveBooleanEntry(Robot.map.swerve::setFieldOriented, putBoolean("Swerve Field Oriented", true));

        // This puts swerve onto the shuffleboard in a really nice way.
        // Here's the documentation for it: https://frc-elastic.gitbook.io/docs/additional-features-and-references/custom-widget-examples
        // All the () -> Robot.map... stuff is something called a lambda expression. In this case, that's just a fancy way of saying the dashboard will call the method after the arrow (->) every time it wants to get the current value.
        SmartDashboard.putData("Swerve Drive", new Sendable() {
            @Override
            public void initSendable(SendableBuilder builder) {
                builder.setSmartDashboardType("SwerveDrive");

                builder.addDoubleProperty("Front Left Angle", () -> Robot.map.swerve.frontLeft.getWheelRotation().getRadians(), null);
                builder.addDoubleProperty("Front Left Velocity", () -> Robot.map.swerve.frontLeft.getDriveVelocity(), null);

                builder.addDoubleProperty("Front Right Angle", () -> Robot.map.swerve.frontRight.getWheelRotation().getRadians(), null);
                builder.addDoubleProperty("Front Right Velocity", () -> Robot.map.swerve.frontRight.getDriveVelocity(), null);

                builder.addDoubleProperty("Back Left Angle", () -> Robot.map.swerve.backLeft.getWheelRotation().getRadians(), null);
                builder.addDoubleProperty("Back Left Velocity", () -> Robot.map.swerve.backLeft.getDriveVelocity(), null);

                builder.addDoubleProperty("Back Right Angle", () -> Robot.map.swerve.backRight.getWheelRotation().getRadians(), null);
                builder.addDoubleProperty("Back Right Velocity", () -> Robot.map.swerve.backRight.getDriveVelocity(), null);

                builder.addDoubleProperty("Robot Angle", () -> Robot.map.swerve.getGyroRotation().getRadians(), null);
            }
        });

        swerveFLDistanceTraveled = putNumber("FL: Distance Traveled", 0);
        swerveFLCurrentRotation = putNumber("FL: Current Rotation", 0);
        swerveFLDesiredDriveSpeed = putNumber("FL: Desired Drive Speed", 0);
        swerveFLDesiredTurnSpeed = putNumber("FL: Desired Turn Speed", 0);
        swerveFLEnabled = new ReactiveBooleanEntry(Robot.map.swerve.frontLeft::setEnabled, putBoolean("FL: Enabled?", true));
        swerveFLManualControl = new ReactiveBooleanEntry(Robot.map.swerve.frontLeft::setManualControl, putBoolean("FL: Manual Control?", false));

        swerveFRDistanceTraveled = putNumber("FR: Distance Traveled", 0);
        swerveFRCurrentRotation = putNumber("FR: Current Rotation", 0);
        swerveFRTurnEncoderReading = putNumber("FR: Turn Encoder Reading", 0);
        swerveFRDesiredDriveSpeed = putNumber("FR: Desired Drive Speed", 0);
        swerveFRDesiredTurnSpeed = putNumber("FR: Desired Turn Speed", 0);
        swerveFREnabled = new ReactiveBooleanEntry(Robot.map.swerve.frontRight::setEnabled, putBoolean("FR: Enabled?", true));
        swerveFRManualControl = new ReactiveBooleanEntry(Robot.map.swerve.frontRight::setManualControl, putBoolean("FR: Manual Control?", false));

        swerveBLDistanceTraveled = putNumber("BL: Distance Traveled", 0);
        swerveBLCurrentRotation = putNumber("BL: Current Rotation", 0);
        swerveBLDesiredDriveSpeed = putNumber("BL: Desired Drive Speed", 0);
        swerveBLDesiredTurnSpeed = putNumber("BL: Desired Turn Speed", 0);
        swerveBLEnabled = new ReactiveBooleanEntry(Robot.map.swerve.backLeft::setEnabled, putBoolean("BL: Enabled?", true));
        swerveBLManualControl = new ReactiveBooleanEntry(Robot.map.swerve.backLeft::setManualControl, putBoolean("BL: Manual Control?", false));

        swerveBRDistanceTraveled = putNumber("BR: Distance Traveled", 0);
        swerveBRCurrentRotation = putNumber("BR: Current Rotation", 0);
        swerveBRDesiredDriveSpeed = putNumber("BR: Desired Drive Speed", 0);
        swerveBRDesiredTurnSpeed = putNumber("BR: Desired Turn Speed", 0);
        swerveBREnabled = new ReactiveBooleanEntry(Robot.map.swerve.backRight::setEnabled, putBoolean("BR: Enabled?", true));
        swerveBRManualControl = new ReactiveBooleanEntry(Robot.map.swerve.backRight::setManualControl, putBoolean("BR: Manual Control?", false));

        swerveRotation = putNumber("Rotation", 0);
        swerveGyroRotation = putNumber("Gyro Rotation", 0);
        swerveXPosition = putNumber("X Position", 0);
        swerveYPosition = putNumber("Y Position", 0);

        swerveDriveP = new ReactiveNumberEntry(Robot.map.swerve::updateDriveP, putNumber("Drive P", Robot.map.swerve.frontRight.getDrivePIDController().getP()));
        swerveDriveI = new ReactiveNumberEntry(Robot.map.swerve::updateDriveI, putNumber("Drive I", Robot.map.swerve.frontRight.getDrivePIDController().getI()));
        swerveDriveD = new ReactiveNumberEntry(Robot.map.swerve::updateDriveD, putNumber("Drive D", Robot.map.swerve.frontRight.getDrivePIDController().getD()));
        swerveDriveFFs = new ReactiveNumberEntry(Robot.map.swerve::updateDriveFFs, putNumber("Drive FFs", Robot.map.swerve.frontRight.getDriveFeedforward().getKs()));
        swerveDriveFFv = new ReactiveNumberEntry(Robot.map.swerve::updateDriveFFv, putNumber("Drive FFv", Robot.map.swerve.frontRight.getDriveFeedforward().getKv()));
        swerveTurnP = new ReactiveNumberEntry(Robot.map.swerve::updateTurnP, putNumber("Turn P", Robot.map.swerve.frontRight.getTurningPIDController().getP()));
        swerveTurnI = new ReactiveNumberEntry(Robot.map.swerve::updateTurnI, putNumber("Turn I", Robot.map.swerve.frontRight.getTurningPIDController().getI()));
        swerveTurnD = new ReactiveNumberEntry(Robot.map.swerve::updateTurnD, putNumber("Turn D", Robot.map.swerve.frontRight.getTurningPIDController().getD()));
        swerveTurnFFs = new ReactiveNumberEntry(Robot.map.swerve::updateTurnFFs, putNumber("Turn FFs", Robot.map.swerve.frontRight.getTurnFeedforward().getKs()));
        swerveTurnFFv = new ReactiveNumberEntry(Robot.map.swerve::updateTurnFFv, putNumber("Turn FFv", Robot.map.swerve.frontRight.getTurnFeedforward().getKv()));

        // if (Robot.driveController != null) {
        //     swerveDriveInverted = new ReactiveBooleanEntry(Robot.driveController::setDriveInverted, putBoolean("Is Drive Inverted", Robot.driveController.getSwerveDriveInverted()));
        //     swerveStrafeInverted = new ReactiveBooleanEntry(Robot.driveController::setStrafeInverted, putBoolean("Is Strafe Inverted", Robot.driveController.getSwerveStrafeInverted()));
        //     swerveTurnInverted = new ReactiveBooleanEntry(Robot.driveController::setTurnInverted, putBoolean("Is Turn Inverted", Robot.driveController.getSwerveTurnInverted()));
        // }
    }

    public void setUpArm()
    {
        extendArmMotorEnabled= new ReactiveBooleanEntry(Robot.armController::armManualControl, putBoolean("Extend Arm Motor Enabled", true));
        rotateArmMotorEnabled= new ReactiveBooleanEntry(Robot.armController::armManualControl, putBoolean("Rotate Arm Motor Enabled", true));
        armManualControlEnabled = new ReactiveBooleanEntry(Robot.armController::armManualControl, putBoolean("Arm Manual Control Enabled", false));
    }
    /**
     * UPDATING TABS
     */
    
    /**
     * Called in the SwerveSubsystem's periodic method.
     */
    public void updateSwerve() {
        // Currently no desired speed data yet.
        swerveFLDistanceTraveled.setDouble(Robot.map.swerve.frontLeft.getDrivePosition());
        swerveFLCurrentRotation.setDouble(Robot.map.swerve.frontLeft.getWheelRotation().getDegrees());

        swerveFRDistanceTraveled.setDouble(Robot.map.swerve.frontRight.getDrivePosition());
        swerveFRCurrentRotation.setDouble(Robot.map.swerve.frontRight.getWheelRotation().getDegrees());
        swerveFRTurnEncoderReading.setDouble(Robot.map.swerve.frontRight.getTurnEncoderReading());

        swerveBLDistanceTraveled.setDouble(Robot.map.swerve.backLeft.getDrivePosition());
        swerveBLCurrentRotation.setDouble(Robot.map.swerve.backLeft.getWheelRotation().getDegrees());

        swerveBRDistanceTraveled.setDouble(Robot.map.swerve.backRight.getDrivePosition());
        swerveBRCurrentRotation.setDouble(Robot.map.swerve.backRight.getWheelRotation().getDegrees());

        swerveGyroRotation.setDouble(Robot.map.swerve.getGyroRotation().getDegrees());
        swerveRotation.setDouble(Robot.map.swerve.getPosition().getRotation().getDegrees());
        swerveXPosition.setDouble(Robot.map.swerve.getPosition().getX());
        swerveYPosition.setDouble(Robot.map.swerve.getPosition().getY());

        // REACTIVE ELEMENTS

        swerveFLEnabled.update();
        swerveFREnabled.update();
        swerveBLEnabled.update();
        swerveBREnabled.update();

        swerveFLManualControl.update();
        swerveFRManualControl.update();
        swerveBLManualControl.update();
        swerveBRManualControl.update();

        swerveDriveSpeed.update();
        swerveTurnSpeed.update();
        swerveFieldOriented.update();

        swerveDriveP.update();
        swerveDriveI.update();
        swerveDriveD.update();
        swerveDriveFFs.update();
        swerveDriveFFv.update();
        swerveTurnP.update();
        swerveTurnI.update();
        swerveTurnD.update();
        swerveTurnFFs.update();
        swerveTurnFFv.update();

        field.setRobotPose(Robot.map.swerve.getPosition());
    }

    
    /**
     * HELPER FUNCTIONS
     */

    /**
     * This puts a number onto the SmartDashboard.
     * @param topic The name of the data.
     * @param defaultValue The default value.
     * @return A reference to the entry of that number in the SmartDashboard.
     */
    public static NetworkTableEntry putNumber(String topic, double defaultValue) {
        SmartDashboard.putNumber(topic, defaultValue);
        return SmartDashboard.getEntry(topic);
    }


    /**
     * This puts a boolean onto the SmartDashboard.
     * @param topic The name of the data.
     * @param defaultValue The default value.
     * @return A reference to the entry of that number in the SmartDashboard.
     */
    public static NetworkTableEntry putBoolean(String topic, boolean defaultValue) {
        SmartDashboard.putBoolean(topic, defaultValue);
        return SmartDashboard.getEntry(topic);
    }
}
