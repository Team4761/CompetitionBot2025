package frc.robot.controllers;

import edu.wpi.first.wpilibj.DataLogManager;
import edu.wpi.first.wpilibj.DataLog;
import edu.wpi.first.wpilibj.DataLogEntry;
import frc.robot.Robot;
import frc.robot.RobotMap;

public class DriveTestController {
    private RobotMap map;
    private DataLog log;
    private DataLogEntry forwardTestLog;
    private DataLogEntry strafeTestLog;
    private DataLogEntry turnTestLog;

    // Define constants for test parameters
    private int testStage = 0; // Current stage of testing
    private double testStartTime = 0;

    // Testing parameters, i.e. speed, time, etc.
    private double testSpeed = 0.5;
    private double testTime = 2.0;

    public DriveTestController() {
        map = Robot.map;

        // Initialize log manager
        DataLogManager.start();
        log = DataLogManager.getLog();

        // Create logs for each test
        forwardTestLog = new DataLogEntry(log, "Test/forward");
        strafeTestLog = new DataLogEntry(log, "Test/strafe");
        turnTestLog = new DataLogEntry(log, "Test/turn");
    }

    public void runTestPeriodic(double currentTime) {
        switch (testStage) {
            case 0:
                startForwardTest(currentTime);
                break;
            case 1:
                startStrafeTest(currentTime);
                break;
            case 2:
                startTurnTest(currentTime);
                break;
            default:
                System.out.println("Test stages complete");
                break;
        }
    }

    private void startForwardTest(double currentTime) {
        if (testStartTime == 0) {
            testStartTime = currentTime;
            System.out.println("Starting forwards test.....");
        }

        double elapsedTime = currentTime - testStartTime;
        double input;
        if (elapsedTime < testTime) {
            input = testSpeed;
        } else {
            input = 0.0;
        }

        forwardTestLog.append(input);
        map.swerve.setDesiredSpeeds(input, 0, 0);

        if (elapsedTime > 3.0) {  // Wait 1 second after stopping
            testStage++;
            testStartTime = 0;
        }
    }

    private void startStrafeTest(double currentTime) {
        if (testStartTime == 0) {
            testStartTime = currentTime;
            System.out.println("Starting strafe test.....");
        }

        double elapsedTime = currentTime - testStartTime;
        double input;
        if (elapsedTime < testTime) {
            input = testSpeed;
        } else {
            input = 0.0;
        }

        strafeTestLog.append(input);
        map.swerve.setDesiredSpeeds(0, input, 0);

        if (elapsedTime > 3.0) {  // Wait 1 second after stopping
            testStage++;
            testStartTime = 0;
        }
    }

    private void startTurnTest(double currentTime) {
        if (testStartTime == 0) {
            testStartTime = currentTime;
            System.out.println("Starting turn test.....");
        }

        double elapsedTime = currentTime - testStartTime;
        double input;
        if (elapsedTime < testTime) {
            input = testSpeed;
        } else {
            input = 0.0;
        }

        turnTestLog.append(input);
        map.swerve.setDesiredSpeeds(0, 0, input);

        if (elapsedTime > 3.0) {  // Test complete
            testStage++;
            testStartTime = 0;
        }
    }
}
