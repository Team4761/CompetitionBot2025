package frc.robot;

import java.util.Map;

import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInLayouts;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardLayout;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;

/**
 * This is to have a nice and straightforward way of displaying data/settings on the dashboard during competition and debugging.
 * For on the fly debugging and logging, use SmartDashboard.put... It's just easier to work with.
 * This is for long term logging and settings.
 */
public class RobocketsShuffleboard {

    ShuffleboardTab settings = null;
    ShuffleboardTab swerve = null;

    /**
     * ALL ENTRIES
     */
    // Swerve
    GenericEntry swerveFLDistanceTraveled;
    GenericEntry swerveFLCurrentRotation;
    GenericEntry swerveFLDesiredDriveSpeed;
    GenericEntry swerveFLDesiredTurnSpeed;
    public GenericEntry swerveFLEnabled;
    public GenericEntry swerveFLManualControl;

    GenericEntry swerveFRDistanceTraveled;
    GenericEntry swerveFRCurrentRotation;
    GenericEntry swerveFRDesiredDriveSpeed;
    GenericEntry swerveFRDesiredTurnSpeed;
    public GenericEntry swerveFREnabled;
    public GenericEntry swerveFRManualControl;

    GenericEntry swerveBLDistanceTraveled;
    GenericEntry swerveBLCurrentRotation;
    GenericEntry swerveBLDesiredDriveSpeed;
    GenericEntry swerveBLDesiredTurnSpeed;
    public GenericEntry swerveBLEnabled;
    public GenericEntry swerveBLManualControl;

    GenericEntry swerveBRDistanceTraveled;
    GenericEntry swerveBRCurrentRotation;
    GenericEntry swerveBRDesiredDriveSpeed;
    GenericEntry swerveBRDesiredTurnSpeed;
    public GenericEntry swerveBREnabled;
    public GenericEntry swerveBRManualControl;

    GenericEntry swerveXPosition;
    GenericEntry swerveYPosition;
    GenericEntry swerveRotation;
    GenericEntry swerveGyroRotation;

    // Settings
    public GenericEntry settingsSwerveDriveSpeed;
    public GenericEntry settingsSwerveTurnSpeed;
    public GenericEntry settingsSwerveFieldOriented;
    

    /**
     * INITIALIZATION
     */
    public RobocketsShuffleboard() {
        initializeTabs();
        fillSettingsTab();
        if (swerve != null) { fillSwerveTab(); }
    }


    /**
     * INITIALIZING TABS
     */

    public void initializeTabs() {
        settings = Shuffleboard.getTab("Settings");
        if (Robot.map.swerve != null) { swerve = Shuffleboard.getTab("Swerve"); }
    }

    public void fillSettingsTab() {
        // Swerve settings
        // ShuffleboardLayout swerveSettings = settings.getLayout("Swerve", BuiltInLayouts.kList).withSize(2,3);
        settingsSwerveDriveSpeed = settings.add("Swerve Drive Speed", 0.5).withWidget(BuiltInWidgets.kNumberSlider).withProperties(Map.of("min",0,"max",1)).withPosition(0, 0).getEntry();
        settingsSwerveTurnSpeed = settings.add("Swerve Turn Speed", 0.5).withWidget(BuiltInWidgets.kNumberSlider).withProperties(Map.of("min",0,"max",1)).withPosition(0, 1).getEntry();
        settingsSwerveFieldOriented = settings.add("Swerve Field Oriented", true).withWidget(BuiltInWidgets.kToggleSwitch).withPosition(0, 2).getEntry();
    }

    public void fillSwerveTab() {
        ShuffleboardLayout frontLeft = swerve.getLayout("Front Left Module", BuiltInLayouts.kList).withSize(2, 5).withPosition(2, 0);
        swerveFLDistanceTraveled = frontLeft.add("FL: Distance Traveled", 0).withPosition(0, 0).getEntry();
        swerveFLCurrentRotation = frontLeft.add("FL: Current Rotation", 0).withPosition(1, 0).getEntry();
        swerveFLDesiredDriveSpeed = frontLeft.add("FL: Desired Drive Speed", 0).withPosition(2, 0).getEntry();
        swerveFLDesiredTurnSpeed = frontLeft.add("FL: Desired Turn Speed", 0).withPosition(3, 0).getEntry();
        swerveFLEnabled = frontLeft.add("FL: Enabled?", true).withWidget(BuiltInWidgets.kToggleSwitch).withPosition(4, 0).getEntry();
        swerveFLEnabled = frontLeft.add("FL: Manual Control?", true).withWidget(BuiltInWidgets.kToggleSwitch).withPosition(5, 0).getEntry();

        ShuffleboardLayout frontRight = swerve.getLayout("Front Right Module", BuiltInLayouts.kList).withSize(2, 5).withPosition(4, 0);
        swerveFRDistanceTraveled = frontRight.add("FR: Distance Traveled", 0).withPosition(0, 0).getEntry();
        swerveFRCurrentRotation = frontRight.add("FR: Current Rotation", 0).withPosition(1, 0).getEntry();
        swerveFRDesiredDriveSpeed = frontRight.add("FR: Desired Drive Speed", 0).withPosition(2, 0).getEntry();
        swerveFRDesiredTurnSpeed = frontRight.add("FR: Desired Turn Speed", 0).withPosition(3, 0).getEntry();
        swerveFREnabled = frontRight.add("FR: Enabled?", true).withWidget(BuiltInWidgets.kToggleSwitch).withPosition(4, 0).getEntry();
        swerveFREnabled = frontRight.add("FR: Manual Control?", true).withWidget(BuiltInWidgets.kToggleSwitch).withPosition(5, 0).getEntry();

        ShuffleboardLayout backLeft = swerve.getLayout("Back Left Module", BuiltInLayouts.kList).withSize(2, 5).withPosition(6, 0);
        swerveBLDistanceTraveled = backLeft.add("BL: Distance Traveled", 0).withPosition(0, 0).getEntry();
        swerveBLCurrentRotation = backLeft.add("BL: Current Rotation", 0).withPosition(1, 0).getEntry();
        swerveBLDesiredDriveSpeed = backLeft.add("BL: Desired Drive Speed", 0).withPosition(2, 0).getEntry();
        swerveBLDesiredTurnSpeed = backLeft.add("BL: Desired Turn Speed", 0).withPosition(3, 0).getEntry();
        swerveBLEnabled = backLeft.add("BL: Enabled?", true).withWidget(BuiltInWidgets.kToggleSwitch).withPosition(4, 0).getEntry();
        swerveBLEnabled = backLeft.add("BL: Manual Control?", true).withWidget(BuiltInWidgets.kToggleSwitch).withPosition(5, 0).getEntry();

        ShuffleboardLayout backRight = swerve.getLayout("Back Right Module", BuiltInLayouts.kList).withSize(2, 5).withPosition(8, 0);
        swerveBRDistanceTraveled = backRight.add("BR: Distance Traveled", 0).withPosition(0, 0).getEntry();
        swerveBRCurrentRotation = backRight.add("BR: Current Rotation", 0).withPosition(1, 0).getEntry();
        swerveBRDesiredDriveSpeed = backRight.add("BR: Desired Drive Speed", 0).withPosition(2, 0).getEntry();
        swerveBRDesiredTurnSpeed = backRight.add("BR: Desired Turn Speed", 0).withPosition(3, 0).getEntry();
        swerveBREnabled = backRight.add("BR: Enabled?", true).withWidget(BuiltInWidgets.kToggleSwitch).withPosition(4, 0).getEntry();
        swerveBREnabled = backRight.add("BR: Manual Control?", true).withWidget(BuiltInWidgets.kToggleSwitch).withPosition(5, 0).getEntry();

        ShuffleboardLayout general = swerve.getLayout("General", BuiltInLayouts.kList).withSize(2, 3).withPosition(0, 0);
        swerveRotation = general.add("Rotation", 0).withPosition(0, 0).getEntry();
        swerveGyroRotation = general.add("Gyro Rotation", 0).withPosition(1, 0).getEntry();
        swerveXPosition = general.add("X Position", 0).withPosition(2, 0).getEntry();
        swerveYPosition = general.add("Y Position", 0).withPosition(3, 0).getEntry();
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

        swerveBLDistanceTraveled.setDouble(Robot.map.swerve.backLeft.getDrivePosition());
        swerveBLCurrentRotation.setDouble(Robot.map.swerve.backLeft.getWheelRotation().getDegrees());

        swerveBRDistanceTraveled.setDouble(Robot.map.swerve.backRight.getDrivePosition());
        swerveBRCurrentRotation.setDouble(Robot.map.swerve.backRight.getWheelRotation().getDegrees());

        swerveGyroRotation.setDouble(Robot.map.swerve.getGyroRotation().getDegrees());
        swerveRotation.setDouble(Robot.map.swerve.getPosition().getRotation().getDegrees());
        swerveXPosition.setDouble(Robot.map.swerve.getPosition().getX());
        swerveYPosition.setDouble(Robot.map.swerve.getPosition().getY());

        Robot.map.swerve.frontLeft.enabled = swerveFLEnabled.getBoolean(true);
        Robot.map.swerve.frontRight.enabled = swerveFREnabled.getBoolean(true);
        Robot.map.swerve.backLeft.enabled = swerveBLEnabled.getBoolean(true);
        Robot.map.swerve.backRight.enabled = swerveBREnabled.getBoolean(true);
        Robot.map.swerve.speedDriveModifier = settingsSwerveDriveSpeed.getDouble(0.5);
        Robot.map.swerve.speedTurnModifier = settingsSwerveTurnSpeed.getDouble(0.5);
    }
}
