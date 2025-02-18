package frc.robot.dashboard;


import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.util.sendable.Sendable;
import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;
import frc.robot.dashboard.reactive.ReactiveBooleanEntry;
import frc.robot.dashboard.reactive.ReactiveNumberEntry;
import frc.robot.dashboard.telemetry.TelemetryBooleanEntry;
import frc.robot.dashboard.telemetry.TelemetryNumberEntry;

/**
 * This is to have a nice and straightforward way of displaying data/settings on the dashboard during competition and debugging.
 * You might be thinking to yourself: why am I not using Shuffleboard?
 * Well, the WPILib gods have determined that Shuffleboard is blasphemy, and for some reason, it has forever stopped working.
 * Instead, we now use Elastic for most things and Glass when Elastic doesn't work.
 * This is for long term logging and settings.
 */
public class RobocketsDashboard {

    /** Literally represents the field with the robot on it (tis a pretty cool visualization) */
    private Field2d field;
    

    /**
     * There should only be one RobocketsDashboard initialized in Robot.java
     */
    public RobocketsDashboard() {
        field = new Field2d();

        setupArmController();
        setupDriveController();
        setupSwerve();
        setupArm();
        setupVision();
    }


    /**
     * Only call this if swerve is initialized!
     */
    public void setupSwerve() {
        if (Robot.map.swerve != null) {
            // This puts swerve onto the shuffleboard in a really nice way.
            // Here's the documentation for it: https://frc-elastic.gitbook.io/docs/additional-features-and-references/custom-widget-examples
            // All the () -> Robot.map... stuff is something called a lambda expression. In this case, that's just a fancy way of saying the dashboard will call the method after the arrow (->) every time it wants to get the current value.
            SmartDashboard.putData("Swerve Drive Representation", new Sendable() {
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

            // Settings
            new ReactiveNumberEntry(Robot.map.swerve::setDriveMultiplier, putNumber("Swerve", "Swerve Drive Speed", 0.5));
            new ReactiveNumberEntry(Robot.map.swerve::setTurnMultiplier, putNumber("Swerve", "Swerve Turn Speed", 0.5));
            new ReactiveBooleanEntry(Robot.map.swerve::setFieldOriented, putBoolean("Swerve", "Swerve Field Oriented", true));

            // Module Specific Info
            new TelemetryNumberEntry(() -> Robot.map.swerve.frontLeft.getDrivePosition(), putNumber("Swerve", "FL: Distance Traveled", 0));
            new TelemetryNumberEntry(() -> Robot.map.swerve.frontLeft.getWheelRotation().getDegrees(), putNumber("Swerve", "FL: Current Rotation", 0));
            new TelemetryNumberEntry(() -> Robot.map.swerve.frontLeft.getDriveVelocity(), putNumber("Swerve", "FL: Desired Drive Speed", 0));
            new TelemetryNumberEntry(() -> Robot.map.swerve.frontLeft.getAngularVelocity().getDegrees(), putNumber("Swerve", "FL: Desired Turn Speed", 0));
            new ReactiveBooleanEntry(Robot.map.swerve.frontLeft::setEnabled, putBoolean("Swerve", "FL: Enabled?", true));
            new ReactiveBooleanEntry(Robot.map.swerve.frontLeft::setManualControl, putBoolean("Swerve", "FL: Manual Control?", false));

            new TelemetryNumberEntry(() -> Robot.map.swerve.frontRight.getDrivePosition(), putNumber("Swerve", "FR: Distance Traveled", 0));
            new TelemetryNumberEntry(() -> Robot.map.swerve.frontRight.getWheelRotation().getDegrees(), putNumber("Swerve", "FR: Current Rotation", 0));
            new TelemetryNumberEntry(() -> Robot.map.swerve.frontRight.getDriveVelocity(), putNumber("Swerve", "FR: Desired Drive Speed", 0));
            new TelemetryNumberEntry(() -> Robot.map.swerve.frontRight.getAngularVelocity().getDegrees(), putNumber("Swerve", "FR: Desired Turn Speed", 0));
            new ReactiveBooleanEntry(Robot.map.swerve.frontRight::setEnabled, putBoolean("Swerve", "FR: Enabled?", true));
            new ReactiveBooleanEntry(Robot.map.swerve.frontRight::setManualControl, putBoolean("Swerve", "FR: Manual Control?", false));
            new TelemetryNumberEntry(() -> Robot.map.swerve.frontRight.getTurnEncoderReading(), putNumber("Swerve", "FR: Distance Traveled", 0));

            new TelemetryNumberEntry(() -> Robot.map.swerve.backLeft.getDrivePosition(), putNumber("Swerve", "BL: Distance Traveled", 0));
            new TelemetryNumberEntry(() -> Robot.map.swerve.backLeft.getWheelRotation().getDegrees(), putNumber("Swerve", "BL: Current Rotation", 0));
            new TelemetryNumberEntry(() -> Robot.map.swerve.backLeft.getDriveVelocity(), putNumber("Swerve", "BL: Desired Drive Speed", 0));
            new TelemetryNumberEntry(() -> Robot.map.swerve.backLeft.getAngularVelocity().getDegrees(), putNumber("Swerve", "BL: Desired Turn Speed", 0));
            new ReactiveBooleanEntry(Robot.map.swerve.backLeft::setEnabled, putBoolean("Swerve", "BL: Enabled?", true));
            new ReactiveBooleanEntry(Robot.map.swerve.backLeft::setManualControl, putBoolean("Swerve", "BL: Manual Control?", false));

            new TelemetryNumberEntry(() -> Robot.map.swerve.backRight.getDrivePosition(), putNumber("Swerve", "BR: Distance Traveled", 0));
            new TelemetryNumberEntry(() -> Robot.map.swerve.backRight.getWheelRotation().getDegrees(), putNumber("Swerve", "BR: Current Rotation", 0));
            new TelemetryNumberEntry(() -> Robot.map.swerve.backRight.getDriveVelocity(), putNumber("Swerve", "BR: Desired Drive Speed", 0));
            new TelemetryNumberEntry(() -> Robot.map.swerve.backRight.getAngularVelocity().getDegrees(), putNumber("Swerve", "BR: Desired Turn Speed", 0));
            new ReactiveBooleanEntry(Robot.map.swerve.backRight::setEnabled, putBoolean("Swerve", "BR: Enabled?", true));
            new ReactiveBooleanEntry(Robot.map.swerve.backRight::setManualControl, putBoolean("Swerve", "BR: Manual Control?", false));

            // Swerve General Info
            new TelemetryNumberEntry(() -> Robot.map.swerve.getPosition().getRotation().getDegrees(), putNumber("Swerve", "Rotation", 0));
            new TelemetryNumberEntry(() -> Robot.map.swerve.getGyroRotation().getDegrees(), putNumber("Swerve", "Gyro Rotation", 0));
            new TelemetryNumberEntry(() -> Robot.map.swerve.getPosition().getX(), putNumber("Swerve", "X Position", 0));
            new TelemetryNumberEntry(() -> Robot.map.swerve.getPosition().getY(), putNumber("Swerve", "Y Position", 0));

            // Tuning Info
            new ReactiveNumberEntry(Robot.map.swerve::updateDriveP, putNumber("Swerve", "Drive P", Robot.map.swerve.frontRight.getDrivePIDController().getP()));
            new ReactiveNumberEntry(Robot.map.swerve::updateDriveI, putNumber("Swerve", "Drive I", Robot.map.swerve.frontRight.getDrivePIDController().getI()));
            new ReactiveNumberEntry(Robot.map.swerve::updateDriveD, putNumber("Swerve", "Drive D", Robot.map.swerve.frontRight.getDrivePIDController().getD()));
            new ReactiveNumberEntry(Robot.map.swerve::updateDriveFFs, putNumber("Swerve", "Drive FFs", Robot.map.swerve.frontRight.getDriveFeedforward().getKs()));
            new ReactiveNumberEntry(Robot.map.swerve::updateDriveFFv, putNumber("Swerve", "Drive FFv", Robot.map.swerve.frontRight.getDriveFeedforward().getKv()));
            new ReactiveNumberEntry(Robot.map.swerve::updateTurnP, putNumber("Swerve", "Turn P", Robot.map.swerve.frontRight.getTurningPIDController().getP()));
            new ReactiveNumberEntry(Robot.map.swerve::updateTurnI, putNumber("Swerve", "Turn I", Robot.map.swerve.frontRight.getTurningPIDController().getI()));
            new ReactiveNumberEntry(Robot.map.swerve::updateTurnD, putNumber("Swerve", "Turn D", Robot.map.swerve.frontRight.getTurningPIDController().getD()));
            new ReactiveNumberEntry(Robot.map.swerve::updateTurnFFs, putNumber("Swerve", "Turn FFs", Robot.map.swerve.frontRight.getTurnFeedforward().getKs()));
            new ReactiveNumberEntry(Robot.map.swerve::updateTurnFFv, putNumber("Swerve", "Turn FFv", Robot.map.swerve.frontRight.getTurnFeedforward().getKv()));
        }
    }


    public void setupArm()
    {
        if (Robot.map.arm != null) {
            // Arm Info
            new TelemetryNumberEntry(() -> Robot.map.arm.getPivotRotation().getDegrees(), putNumber("Arm", "Pivot Angle", 0));
            new TelemetryNumberEntry(() -> Robot.map.arm.getExtensionLength(), putNumber("Arm", "Extension Length", 0));
            new TelemetryNumberEntry(() -> Robot.map.arm.getTargetPoint().getX(), putNumber("Arm", "Target X", 0));
            new TelemetryNumberEntry(() -> Robot.map.arm.getTargetPoint().getY(), putNumber("Arm", "Target Y", 0));
            new TelemetryNumberEntry(() -> Robot.map.arm.getCurrentPoint().getX(), putNumber("Arm", "Current X", 0));
            new TelemetryNumberEntry(() -> Robot.map.arm.getCurrentPoint().getY(), putNumber("Arm", "Current Y", 0));

            new TelemetryBooleanEntry(() -> Robot.map.arm.isPivotEncoderConnected(), putBoolean("Arm", "Is Pivot Encoder Connected?", false));
            new TelemetryBooleanEntry(() -> Robot.map.arm.isExtensionEncoderConnected(), putBoolean("Arm", "Is Extension Encoder Connected?", false));

            // Tuning
            new ReactiveNumberEntry(Robot.map.arm::setExtendP, putNumber("Arm", "Extend P", Robot.map.arm.getExtensionPID().getP()));
            new ReactiveNumberEntry(Robot.map.arm::setExtendI, putNumber("Arm", "Extend I", Robot.map.arm.getExtensionPID().getI()));
            new ReactiveNumberEntry(Robot.map.arm::setExtendD, putNumber("Arm", "Extend D", Robot.map.arm.getExtensionPID().getD()));
            new ReactiveNumberEntry(Robot.map.arm::setRotateP, putNumber("Arm", "Pivot P", Robot.map.arm.getPivotPID().getP()));
            new ReactiveNumberEntry(Robot.map.arm::setRotateI, putNumber("Arm", "Pivot I", Robot.map.arm.getPivotPID().getI()));
            new ReactiveNumberEntry(Robot.map.arm::setRotateD, putNumber("Arm", "Pivot D", Robot.map.arm.getPivotPID().getD()));

            // Settings
            new ReactiveBooleanEntry(Robot.map.arm::setUsingSetpointSystem, putBoolean("Arm", "Using Setpoint System", Robot.map.arm.usingSetpointSystem()));
            new ReactiveNumberEntry((degrees) -> Robot.map.arm.setForcedRotation(new Rotation2d(Units.degreesToRadians(degrees))), putNumber("Arm", "Forced Rotation", Robot.map.arm.getForcedRotation().getDegrees()));
            new ReactiveNumberEntry(Robot.map.arm::setForcedExtension, putNumber("Arm", "Forced Extension", Robot.map.arm.getForcedExtension()));
        }
    }


    public void setupArmController() {
        if (Robot.driveController != null) {
            new ReactiveBooleanEntry(Robot.armController::setPivotArmMotorEnabled, putBoolean("Arm Controller", "Pivot Motor Enabled", Robot.armController.isPivotEnabled()));
            new ReactiveBooleanEntry(Robot.armController::setExtendArmMotorEnabled, putBoolean("Arm Controller", "Extend Motor Enabled", Robot.armController.isExtendEnabled()));

            new ReactiveBooleanEntry(Robot.armController::setPivotInverted, putBoolean("Arm Controller", "Is Pivot Inverted", Robot.armController.isPivotInverted()));
            new ReactiveBooleanEntry(Robot.armController::setExtendInverted, putBoolean("Arm Controller", "Is Extend Inverted", Robot.armController.isExtendInverted()));

            new ReactiveBooleanEntry(Robot.armController::setArmManualControl, putBoolean("Arm Controller", "Manual Control Enabled", Robot.armController.isArmManualControl()));

            new ReactiveNumberEntry(Robot.armController::setIntakeSpeed, putNumber("Arm Controller", "Intake Speed", Robot.armController.getIntakeSpeed()));
            new ReactiveNumberEntry(Robot.armController::setOuttakeSpeed, putNumber("Arm Controller", "Outtake Speed", Robot.armController.getOuttakeSpeed()));
            new ReactiveNumberEntry(Robot.armController::setYeetSpeed, putNumber("Arm Controller", "Yeet Speed", Robot.armController.getYeetSpeed()));
            new ReactiveNumberEntry(Robot.armController::setPivotSpeed, putNumber("Arm Controller", "Pivot Speed", Robot.armController.getPivotSpeed()));
            new ReactiveNumberEntry(Robot.armController::setExtendSpeed, putNumber("Arm Controller", "Extend Speed", Robot.armController.getExtendSpeed()));
        }
    }


    public void setupDriveController() {
        if (Robot.driveController != null) {
            new ReactiveBooleanEntry(Robot.driveController::setDriveInverted, putBoolean("Swerve Controller", "Is Drive Inverted", Robot.driveController.getSwerveDriveInverted()));
            new ReactiveBooleanEntry(Robot.driveController::setStrafeInverted, putBoolean("Swerve Controller", "Is Strafe Inverted", Robot.driveController.getSwerveStrafeInverted()));
            new ReactiveBooleanEntry(Robot.driveController::setTurnInverted, putBoolean("Swerve Controller", "Is Turn Inverted", Robot.driveController.getSwerveTurnInverted()));
        }
    }


    public void setupVision() {
        if (Robot.map.vision != null) {
            // April Tag Info
            new TelemetryNumberEntry(() -> Robot.map.vision.getFieldPose().getX(), putNumber("Vision", "X", 0));
            new TelemetryNumberEntry(() -> Robot.map.vision.getFieldPose().getY(), putNumber("Vision", "Y", 0));
            new TelemetryNumberEntry(() -> Robot.map.vision.getFieldPose().getZ(), putNumber("Vision", "Z", 0));
            new TelemetryNumberEntry(() -> Robot.map.vision.getFieldPose().getRotation().getX(), putNumber("Vision", "Roll", 0));
            new TelemetryNumberEntry(() -> Robot.map.vision.getFieldPose().getRotation().getY(), putNumber("Vision", "Pitch", 0));
            new TelemetryNumberEntry(() -> Robot.map.vision.getFieldPose().getRotation().getZ(), putNumber("Vision", "Yaw", 0));
            new TelemetryNumberEntry(() -> Robot.map.vision.getLastAprilTagID(), putNumber("Vision", "April Tag ID", 0));
            new TelemetryNumberEntry(() -> Robot.map.vision.getLatency(), putNumber("Vision", "Latency", 0));
            new TelemetryBooleanEntry(() -> Robot.map.vision.isSeeingAprilTag(), putBoolean("Vision", "Is Seeing April Tag?", false));
            
        }
    }


    /**
     * This is only needed for things that can't be easily fit into a reactive/telemetry entry
     * This method is called by the DashboardHandler
     */
    public void update()
    {
        if (Robot.map.swerve != null)
        field.setRobotPose(Robot.map.swerve.getPosition());
    }


    /**
     * This puts a number onto the SmartDashboard.
     * @param group The folder/group to put the value inside of.
     * @param name The name of the data.
     * @param defaultValue The default value.
     * @return A reference to the entry of that number in the SmartDashboard.
     */
    public static NetworkTableEntry putNumber(String group, String name, double defaultValue) {
        SmartDashboard.putNumber(group+"/"+name, defaultValue);
        return SmartDashboard.getEntry(group+"/"+name);
    }


    /**
     * This puts a boolean onto the SmartDashboard.
     * @param group The folder/group to put the value inside of.
     * @param name The name of the data.
     * @param defaultValue The default value.
     * @return A reference to the entry of that number in the SmartDashboard.
     */
    public static NetworkTableEntry putBoolean(String group, String name, boolean defaultValue) {
        SmartDashboard.putBoolean(group+"/"+name, defaultValue);
        return SmartDashboard.getEntry(group+"/"+name);
    }
}
