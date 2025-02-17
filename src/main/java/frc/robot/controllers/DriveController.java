package frc.robot.controllers;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.filter.SlewRateLimiter;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.XboxController;
import frc.robot.Robot;
import frc.robot.RobotMap;

/**
 * This just controls swerve.
 */
public class DriveController extends XboxController {

    /** This stores which direction should be considered forwards. On robot initialization, this is towards the front of the robot. */
    private Rotation2d currentForwardsDirection = new Rotation2d();

    private boolean driveInverted = true;
    private boolean strafeInverted = true;
    private boolean turnInverted = true;

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
            // Buttons
            if (getXButtonPressed()) {
                this.orientForwardsControllingDirection();
            }
            if (getYButtonPressed()) {
                // map.swerve.resetPosition(new Pose2d());
            }

            // Joystick control
            // Check out this desmos graph to see how the math works: https://www.desmos.com/calculator/6sio2uwvi1
            map.swerve.setDesiredSpeeds(
                (driveInverted ? -1 : 1) * getLeftX()*Math.sin(currentForwardsDirection.getRadians()) + (driveInverted ? -1 : 1) * getLeftY()*Math.cos(currentForwardsDirection.getRadians()),   // Negative to make up the positive direction
                (strafeInverted ? 1 : -1) * getLeftY()*Math.sin(currentForwardsDirection.getRadians()) + (strafeInverted ? -1 : 1) * getLeftX()*Math.cos(currentForwardsDirection.getRadians()),   // Negative to make left the positive direction
                (turnInverted ? -1 : 1) * getRightX()   // Negative to make left (counterclockwise) the positive direction.
            );
        }
    }

    
    // Applying slew limiters and deadzone (deadband) to every single axis.
    // @Override
    // public double getLeftX() {
    //     return leftXLimiter.calculate(MathUtil.applyDeadband(super.getLeftX(), 0.02));
    // }
    // @Override
    // public double getLeftY() {
    //     return leftYLimiter.calculate(MathUtil.applyDeadband(super.getLeftY(), 0.02));
    // }
    // @Override
    // public double getRightX() {
    //     return rightXLimiter.calculate(MathUtil.applyDeadband(super.getRightX(), 0.02));
    // }
    // @Override
    // public double getRightY() {
    //     return rightYLimiter.calculate(MathUtil.applyDeadband(super.getRightY(), 0.02));
    // }


    /**
     * This will take the current orientation of the robot and make it the forwards direction.
     */
    public void orientForwardsControllingDirection() {
        if (Robot.map.swerve != null) {
            currentForwardsDirection = Robot.map.swerve.getGyroRotation();
        }
        else {
            System.out.println("Tried to orient the forwards direction for controlling... but swerve is turned off in RobotMap (or something else has gone terribly wrong).");
        }
    }

    public void setDriveInverted(boolean isInverted) {
        this.driveInverted = isInverted;
    }
    public void setStrafeInverted(boolean isInverted) {
        this.strafeInverted = isInverted;
    }
    public void setTurnInverted(boolean isInverted) {
        this.turnInverted = isInverted;
    }


    public boolean getSwerveDriveInverted() {
        return this.driveInverted;
    }
    public boolean getSwerveStrafeInverted() {
        return this.strafeInverted;
    }
    public boolean getSwerveTurnInverted() {
        return this.turnInverted;
    }
}
