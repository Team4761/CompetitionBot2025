// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import org.littletonrobotics.urcl.URCL;

import edu.wpi.first.net.WebServer;
import edu.wpi.first.wpilibj.DataLogManager;
import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.auto.AutoHandler;
import frc.robot.controllers.ArmController;
import frc.robot.controllers.DriveController;
import frc.robot.dashboard.DashboardHandler;
import frc.robot.dashboard.RobocketsDashboard;
// import edu.wpi.first.wpilibj.Timer;
import frc.robot.subsystems.swerve.ZeroGyroCommand;


/**
 * The methods in this class are called automatically corresponding to each mode, as described in
 * the TimedRobot documentation. If you change the name of this class or the package after creating
 * this project, you must also update the Main.java file in the project.
 */

public class Robot extends TimedRobot {
  /**
   * We're gonna win cause this is here!
   */
  public static final boolean win = true;
  // Check winSubsystem

  public static final RobotMap map = new RobotMap();

  public static final DriveController driveController = new DriveController(0);
  public static final ArmController armController = new ArmController(1);

  public static RobocketsDashboard dashboard;

  // The time between periodic() calls.
  // This gets updated in the robotPeriodic() function.
  public static double currentPeriod = 0.050;


  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */
  public Robot() {}

  /**
   * This is called once when the robot code is first initialized (while the robot is on).
   */
  @Override
  public void robotInit() {
    // This prevents swerve from starting and trying to rotate
    if (Robot.map.swerve != null)
      Robot.map.swerve.updateLastRotation();
    CommandScheduler.getInstance().cancelAll();
    dashboard = new RobocketsDashboard();
    
    // Setup logger
    DataLogManager.start();
    URCL.start();

    // Make sure our autos can be properly selected
    AutoHandler.setupAutoSelector();
    AutoHandler.setupPathPlanner();

    // Start the URCL logger
    URCL.start(DataLogManager.getLog());

    // Added so that you can copy the dashboard config put in the deploy folder.
    WebServer.start(5800, Filesystem.getDeployDirectory().getPath());
  }

  /**
   * This function is called every 20 ms, no matter the mode. Use this for items like diagnostics
   * that you want ran during disabled, autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before LiveWindow and
   * SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
    currentPeriod = getPeriod();

    DashboardHandler.updateDashboard();
  }

  /**
   * This autonomous (along with the chooser code above) shows how to select between different
   * autonomous modes using the dashboard. The sendable chooser code works with the Java
   * SmartDashboard. If you prefer the LabVIEW Dashboard, remove all of the chooser code and
   * uncomment the getString line to get the auto name from the text box below the Gyro
   *
   * <p>You can add additional auto modes by adding additional comparisons to the switch structure
   * below with additional strings. If using the SendableChooser make sure to add them to the
   * chooser code above as well.
   */
  @Override
  public void autonomousInit() {
    CommandScheduler.getInstance().cancelAll();
    CommandScheduler.getInstance().schedule(ZeroGyroCommand.create());
    CommandScheduler.getInstance().schedule(AutoHandler.getSelectedAuto());
  }

  /** This function is called periodically during autonomous. */
  @Override
  public void autonomousPeriodic() {
    CommandScheduler.getInstance().run();
  }

  /** This function is called once when teleop is enabled. */
  @Override
  public void teleopInit() {
    CommandScheduler.getInstance().cancelAll();
  }

  /** This function is called periodically during operator control. */
  @Override
  public void teleopPeriodic() {
    CommandScheduler.getInstance().run();
    
    driveController.teleopPeriodic();
    armController.teleopPeriodic();
  }

  /** This function is called once when the robot is disabled. */
  @Override
  public void disabledInit() {
    CommandScheduler.getInstance().cancelAll();
    if (Robot.map.leds != null) {
      Robot.map.leds.stopLEDs();
    }
    if (Robot.map.swerve != null) {
      Robot.map.swerve.setDesiredSpeeds(0, 0, 0);
    }
    if (Robot.map.arm != null) {
      Robot.map.arm.extend(0);
      Robot.map.arm.rotate(0);
    }
  }

  /** This function is called periodically when disabled. */
  @Override
  public void disabledPeriodic() {}


  /** This function is called periodically during test mode. */
  @Override
  public void testPeriodic() {
    CommandScheduler.getInstance().run();

    // double currentTime = Timer.getFPGATimestamp();
  }

  /** This function is called once when the robot is first started up. */
  @Override
  public void simulationInit() {}

  /** This function is called periodically whilst in simulation. */
  @Override
  public void simulationPeriodic() {}





  public byte publicStaticVoidMainStringArgs = (byte) 00000100;
}
