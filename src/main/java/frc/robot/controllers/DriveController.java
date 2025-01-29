package frc.robot.controllers;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.filter.SlewRateLimiter;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.wpilibj.XboxController;
import frc.robot.Robot;
import frc.robot.RobotMap;

/**
 * This just controls swerve.
 */
public class DriveController extends XboxController {

    // Slew rate limiters caps the transition between different speeds. (ex, a limiter of 3 means that the max change in speed is 3 per second.)
    // A rateLimit of 3 means that it will take ~1/3 second to go from 0 -> 1.
    private final SlewRateLimiter leftXLimiter = new SlewRateLimiter(3);
    private final SlewRateLimiter leftYLimiter = new SlewRateLimiter(3);
    private final SlewRateLimiter rightXLimiter = new SlewRateLimiter(3);
    private final SlewRateLimiter rightYLimiter = new SlewRateLimiter(3);


    // Just a reference to the RobotMap stored in Robot.java so I don't have to keep typing Robot.map
    private RobotMap map;

    public DriveController (int port) {
        super(port);
        map = Robot.map;
    }


    /**
     * Call this function during teleop to actually run!
     * This involves both joysticks and buttons.
     */
    public void teleopPeriodic() {
        // Swerve
        if (map.swerve != null) {

            if (getXButtonPressed()) {
                map.swerve.orientForwardsControllingDirection();
            }
            if (getYButtonPressed()) {
                map.swerve.resetPosition(new Pose2d());
            }

            // Joystick control
            map.swerve.setDesiredSpeeds(
                -getLeftY(),   // Negative to make up the positive direction
                -getLeftX(),   // Negative to make left the positive direction
                -getRightX()   // Negative to make left (counterclockwise) the positive direction.
            );
        }
    }

    
    // Applying slew limiters and deadzone (deadband) to every single axis.
    @Override
    public double getLeftX() {
        return leftXLimiter.calculate(MathUtil.applyDeadband(super.getLeftX(), 0.02));
    }
    @Override
    public double getLeftY() {
        return leftYLimiter.calculate(MathUtil.applyDeadband(super.getLeftY(), 0.02));
    }
    @Override
    public double getRightX() {
        return rightXLimiter.calculate(MathUtil.applyDeadband(super.getRightX(), 0.02));
    }
    @Override
    public double getRightY() {
        return rightYLimiter.calculate(MathUtil.applyDeadband(super.getRightY(), 0.02));
    }
}
