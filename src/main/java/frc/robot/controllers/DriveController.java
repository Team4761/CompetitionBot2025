package frc.robot.controllers;

// import edu.wpi.first.math.filter.SlewRateLimiter;
import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.Constants;
import frc.robot.Robot;
import frc.robot.RobotMap;
import frc.robot.auto.AlignWithAprilTag;
import frc.robot.auto.SmartAlignWithAprilTag;

/**
 * This just controls swerve.
 */
public class DriveController extends XboxController {

    private Command currentAutoSwerveCommand = null;

    /** This stores which direction should be considered forwards. On robot initialization, this is towards the front of the robot. */
    private Rotation2d currentForwardsDirection = new Rotation2d();

    private double driveModifier = 1.0;
    private double turnModifier = 1.0;

    private boolean driveInverted = true;
    private boolean strafeInverted = true;
    private boolean turnInverted = true;

    // Slew rate limiters caps the transition between different speeds. (ex, a limiter of 3 means that the max change in speed is 3 per second.)
    // A rateLimit of 3 means that it will take ~1/3 second to go from 0 -> 1.
    // private final SlewRateLimiter leftXLimiter = new SlewRateLimiter(3);
    // private final SlewRateLimiter leftYLimiter = new SlewRateLimiter(3);
    // private final SlewRateLimiter rightXLimiter = new SlewRateLimiter(3);
    // private final SlewRateLimiter rightYLimiter = new SlewRateLimiter(3);


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

        // Criteria for canceling commands! Funsies
        if (currentAutoSwerveCommand != null) {
            if (getLeftX() != 0 || getLeftY() != 0 || getRightX() != 0) {
                if (Robot.map.swerve != null) {
                    Robot.map.swerve.setFieldOriented(true);
                }
                cancelCurrentAutoSwerveCommand();
            }
        }

        // Swerve
        if (map.swerve != null) {
            if (getRightBumperButton()) {
                driveModifier = 0.3;
                turnModifier = 0.3;
            }
            else {
                driveModifier = 1.0;
                turnModifier = 1.0;
            }
            // Buttons
            if (getXButtonPressed()) {
                this.orientForwardsControllingDirection();
            }
            if (getYButtonPressed()) {
                map.swerve.resetPosition(new Pose2d());
            }

            // Swerve + Vision
            if (Robot.map.vision != null) {
                if (getLeftTriggerAxis() > 0.4) {
                    cancelCurrentAutoSwerveCommand();
                    currentAutoSwerveCommand = AlignWithAprilTag.create(Constants.AprilTagAlignment.LEFT);
                    CommandScheduler.getInstance().schedule(currentAutoSwerveCommand);
                }
                else if (getRightTriggerAxis() > 0.4) {
                    cancelCurrentAutoSwerveCommand();
                    currentAutoSwerveCommand = AlignWithAprilTag.create(Constants.AprilTagAlignment.RIGHT);
                    CommandScheduler.getInstance().schedule(currentAutoSwerveCommand);
                }
                else if (getAButtonPressed()) {
                    cancelCurrentAutoSwerveCommand();
                    currentAutoSwerveCommand = AlignWithAprilTag.create(Constants.AprilTagAlignment.CENTER);
                    CommandScheduler.getInstance().schedule(currentAutoSwerveCommand);
                }
                else if (getBButtonPressed()) {
                    cancelCurrentAutoSwerveCommand();
                    currentAutoSwerveCommand = SmartAlignWithAprilTag.create(Constants.AprilTagAlignment.CENTER, true, 10);
                    CommandScheduler.getInstance().schedule(currentAutoSwerveCommand);
                }
            }

            if (currentAutoSwerveCommand == null) {
                // Joystick control
                // Check out this desmos graph to see how the math works: https://www.desmos.com/calculator/6sio2uwvi1
                map.swerve.setDesiredSpeeds(
                    driveModifier * ((strafeInverted ? -1 : 1) * -getLeftX()*Math.sin(currentForwardsDirection.getRadians()) + (driveInverted ? -1 : 1) * getLeftY()*Math.cos(currentForwardsDirection.getRadians())),   // Negative to make up the positive direction
                    driveModifier * ((driveInverted ? -1 : 1) * getLeftY()*Math.sin(currentForwardsDirection.getRadians()) + (strafeInverted ? -1 : 1) * getLeftX()*Math.cos(currentForwardsDirection.getRadians())),   // Negative to make left the positive direction
                    turnModifier * ((turnInverted ? -1 : 1) * getRightX())   // Negative to make left (counterclockwise) the positive direction.
                );
            }
        }
    }


    private void cancelCurrentAutoSwerveCommand() {
        if (currentAutoSwerveCommand != null)
            CommandScheduler.getInstance().cancel(currentAutoSwerveCommand);
        currentAutoSwerveCommand = null;
    }

    
    // Applying slew limiters and deadzone (deadband) to every single axis.
    @Override
    public double getLeftX() {
        // return leftXLimiter.calculate(MathUtil.applyDeadband(super.getLeftX(), 0.02));
        return MathUtil.applyDeadband(super.getLeftX(), 0.08);
    }
    @Override
    public double getLeftY() {
        // return leftYLimiter.calculate(MathUtil.applyDeadband(super.getLeftY(), 0.02));
        return MathUtil.applyDeadband(super.getLeftY(), 0.08);
    }
    @Override
    public double getRightX() {
        // return rightXLimiter.calculate(MathUtil.applyDeadband(super.getRightX(), 0.02));
        return MathUtil.applyDeadband(super.getRightX(), 0.08);
    }
    @Override
    public double getRightY() {
        // return rightYLimiter.calculate(MathUtil.applyDeadband(super.getRightY(), 0.02));
        return MathUtil.applyDeadband(super.getRightY(), 0.08);
    }


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

    /**
     * 
     * @param forwardsDirection The offset from the field-oriented forwards direction. CCW = positive.
     */
    public void orientForwardsControllingDirection(Rotation2d forwardsDirection) {
        if (Robot.map.swerve != null) {
            currentForwardsDirection = forwardsDirection;
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
