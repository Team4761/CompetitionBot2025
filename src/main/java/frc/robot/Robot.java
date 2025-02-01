// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.net.WebServer;
import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.auto.AutoHandler;
import frc.robot.controllers.ArmController;
import frc.robot.controllers.DriveController;
import frc.robot.dashboard.RobocketsDashboard;

/**
 * The methods in this class are called automatically corresponding to each mode, as described in
 * the TimedRobot documentation. If you change the name of this class or the package after creating
 * this project, you must also update the Main.java file in the project.
 */
public class Robot extends TimedRobot {
  /**
   * We're gonna win cause this is here!
   */
  public static boolean win = true;

  public static final RobotMap map = new RobotMap();
  public static final RobocketsDashboard dashboard = new RobocketsDashboard();

  public static final DriveController driveController = new DriveController(0);
  public static final ArmController armController = new ArmController(1);

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
    // Make sure our autos can be properly selected
    AutoHandler.setupAutoSelector();
    AutoHandler.setupPathPlanner();

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
  public void autonomousInit() {}

  /** This function is called periodically during autonomous. */
  @Override
  public void autonomousPeriodic() {
    CommandScheduler.getInstance().run();
  }

  /** This function is called once when teleop is enabled. */
  @Override
  public void teleopInit() {}

  /** This function is called periodically during operator control. */
  @Override
  public void teleopPeriodic() {
    CommandScheduler.getInstance().run();
    
    if (map.swerve != null) {
      map.swerve.setDesiredSpeeds(
        -driveController.getLeftY(),   // Negative to make up the positive direction
        driveController.getLeftX(),
        -driveController.getRightX()   // Negative to make left (counterclockwise) the positive direction.
      );
    }
  }

  /** This function is called once when the robot is disabled. */
  @Override
  public void disabledInit() {}

  /** This function is called periodically when disabled. */
  @Override
  public void disabledPeriodic() {}

  /** This function is called once when test mode is enabled. */
  @Override
  public void testInit() {}

  /** This function is called periodically during test mode. */
  @Override
  public void testPeriodic() {}

  /** This function is called once when the robot is first started up. */
  @Override
  public void simulationInit() {}

  /** This function is called periodically whilst in simulation. */
  @Override
  public void simulationPeriodic() {}





  public byte publicStaticVoidMainStringArgs = (byte) 0011010;
}
