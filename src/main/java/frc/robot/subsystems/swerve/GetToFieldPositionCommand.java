package frc.robot.subsystems.swerve;

import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.PrintCommand;
import frc.robot.Constants;
import frc.robot.Robot;
import frc.robot.auto.CommandCenter;

/**
 * <p> This uses vision to get to a specific x,y position on the field.
 * <p> +x is the meters across in the direction from the blue alliance wall to the red alliance wall.
 * <p> +y is the meters across from the right side of the blue alliance wall to the left side of the blue alliance wall.
 */
public class GetToFieldPositionCommand extends Command {

    private final ProfiledPIDController xPID = new ProfiledPIDController(
        1,
        0.05,
        0.01,
        new TrapezoidProfile.Constraints(Constants.SWERVE_MAX_DRIVE_SPEED, Constants.SWERVE_MAX_ACCELERATION)
    );


    private final ProfiledPIDController yPID = new ProfiledPIDController(
        1.4,
        0.08,
        0.01,
        new TrapezoidProfile.Constraints(Constants.SWERVE_MAX_DRIVE_SPEED, Constants.SWERVE_MAX_ACCELERATION)
    );


    private final ProfiledPIDController rotationPID = new ProfiledPIDController(
        1,
        0.05,
        0.01,
        // Radians per second, radians per second squared
        new TrapezoidProfile.Constraints(Units.degreesToRadians(120), Units.degreesToRadians(360))
    );

    /** The... target position... on the field... (in meters!) +x = "blue to red alliance wall". +y = "right side to left side of blue alliance wall". +rotation = "CCW". (0,0,0) = bottom left corner, facing red alliance wall. */
    private Pose2d targetPosition;


    /**
     * DO NOT USE THE CONSTRUCTOR!!! USE GetToFieldPositionCommand.create()!!
     */
    private GetToFieldPositionCommand(Pose2d targetPosition){

        this.targetPosition = targetPosition;
    }


    /**
     * Will get the robot to the target position on the field. I would highly recommend using PathPlanner to get these numbers!
     * @param x The desired distance from the blue alliance wall to the red alliance wall in meters. 0 = at blue alliance wall.
     * @param y The desired distance from the right side of the blue alliance wall to the left side (facing the red alliance wall) in meters. 0 = at right side of blue alliance wall.
     * @param desiredRotation The desired rotation, counterclockwise looking from above. 0 = facing the red alliance wall.
     * @return
     */
    public static Command create(double x, double y, Rotation2d desiredRotation)
    {
        if (Robot.map.vision != null)
            return new GetToFieldPositionCommand(new Pose2d(x, y, desiredRotation));
        else
            return new PrintCommand("Vision is not activated! Activate vision in the RobotMap!");
    }


    /**
     * Will get the robot to the target position on the field. I would highly recommend using PathPlanner to get these numbers!
     * @param targetPosition All in meters. (0,0) = bottom left corner of the field (blue alliance). +x = "blue alliance to red alliance". +y = "right side of blue alliance to left side of blue alliance". 0 rotation = "facing red alliance wall" where +rotation = CCW.
     * @return
     */
    public static Command create(Pose2d targetPosition) {
        if (Robot.map.vision != null)
            return new GetToFieldPositionCommand(targetPosition);
        else
            return new PrintCommand("Vision is not activated! Activate vision in the RobotMap!");
    }

    @Override
    public void initialize() {
        CommandCenter.addRequirements(this, Robot.map.swerve);
        Pose2d currentPosition = Robot.map.vision.getFieldPose().toPose2d();

        xPID.reset(currentPosition.getX());
        yPID.reset(currentPosition.getY());
        rotationPID.reset(Robot.map.swerve.getGyroRotation().getRadians());
    }


    @Override
    public void execute() {
        Pose2d currentPosition = Robot.map.vision.getFieldPose().toPose2d();
        
        double driveSpeedX = xPID.calculate(currentPosition.getX(), targetPosition.getX());
        double driveSpeedY = yPID.calculate(currentPosition.getY(), targetPosition.getY());
        double driveSpeedRot = rotationPID.calculate(Robot.map.swerve.getGyroRotation().getRadians(), targetPosition.getRotation().getRadians());

        Robot.map.swerve.setDesiredSpeeds(driveSpeedX, driveSpeedY, driveSpeedRot);
    }

    @Override
    public boolean isFinished() {
        Pose2d currentPosition = Robot.map.vision.getFieldPose().toPose2d();
        double differenceX = Math.abs(currentPosition.getX() - targetPosition.getX());
        double differenceY = Math.abs(currentPosition.getY() - targetPosition.getY());
        double differenceRot = Math.abs(Robot.map.swerve.getGyroRotation().getRadians() - targetPosition.getRotation().getRadians());
        // If within the acceptable margin of error (1cm & 5 degrees), then the command is finished.
        if (differenceX + differenceY <= 0.01 && differenceRot <= Math.toRadians(2)) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void end(boolean isInterrupted) {
        Robot.map.swerve.setDesiredSpeeds(0,0,0);
    }
}
