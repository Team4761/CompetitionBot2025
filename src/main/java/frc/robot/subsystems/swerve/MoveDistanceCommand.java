package frc.robot.subsystems.swerve;

import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.Robot;
import frc.robot.auto.CommandCenter;


//Command to move a set distance.
public class MoveDistanceCommand extends Command{


    private double deltaX;
    private double deltaY;
    private Rotation2d deltaRot;

    private final ProfiledPIDController DistanceXPID = new ProfiledPIDController(
        1,
        0.05,
        0.01,
        new TrapezoidProfile.Constraints(Constants.SWERVE_MAX_DRIVE_SPEED, Constants.SWERVE_MAX_ACCELERATION)
    );


    private final ProfiledPIDController DistanceYPID = new ProfiledPIDController(
        1.4,
        0.08,
        0.01,
        new TrapezoidProfile.Constraints(Constants.SWERVE_MAX_DRIVE_SPEED, Constants.SWERVE_MAX_ACCELERATION)
    );


    private final ProfiledPIDController RotationPID = new ProfiledPIDController(
        1,
        0.05,
        0.01,
        // Radians per second, radians per second squared
        new TrapezoidProfile.Constraints(Units.degreesToRadians(120), Units.degreesToRadians(360))
    );

    private Pose2d targetPosition = new Pose2d();


    /**
     * DO NOT USE THE CONSTRUCTOR!!! USE MoveDistanceCommand.create()!!
     * @param deltaX How far the x value is from where the robot is now in meters. Positive x is forward and negative is backward.
     * @param deltaY How far the y value is from where the robot is now in meters. Positive y is left and negative is right.
     * @param deltaRot How much the robot should rotate
     */
    private MoveDistanceCommand(double deltaX, double deltaY, Rotation2d deltaRot){
        this.deltaX = deltaX;
        this.deltaY = deltaY;
        this.deltaRot = deltaRot;
    }


    /**
     * <p> All that needs to be initialized.
     * @param deltaX How far the x value is from where the robot is now in meters. Positive x is forward and negative is backward.
     * @param deltaY How far the y value is from where the robot is now in meters. Positive y is left and negative is right.
     * @param deltaRot How much the robot should rotate
     */
    public static Command create(double deltaX, double deltaY, Rotation2d deltaRot)
    {
        return new MoveDistanceCommand(deltaX, deltaY, deltaRot);
    }


    @Override
    public void initialize() {
        CommandCenter.addRequirements(this, Robot.map.swerve);
        Pose2d currentPosition = Robot.map.swerve.getPosition();
        targetPosition = new Pose2d(currentPosition.getX() + deltaX, currentPosition.getY() + deltaY, Robot.map.swerve.getGyroRotation().plus(deltaRot));
        DistanceXPID.reset(currentPosition.getX());
        DistanceYPID.reset(currentPosition.getY());
        RotationPID.reset(Robot.map.swerve.getGyroRotation().getRadians());
    }
    
    @Override
    public void execute() {
        Pose2d currentPosition = Robot.map.swerve.getPosition();
        
        double driveSpeedX = DistanceXPID.calculate(currentPosition.getX(), targetPosition.getX());
        double driveSpeedY = DistanceYPID.calculate(currentPosition.getY(), targetPosition.getY());
        double driveSpeedRot = RotationPID.calculate(Robot.map.swerve.getGyroRotation().getRadians(), targetPosition.getRotation().getRadians());

        Robot.map.swerve.setDesiredSpeeds(driveSpeedX, driveSpeedY, driveSpeedRot);
    }

    @Override
    public boolean isFinished() {
        Pose2d currentPosition = Robot.map.swerve.getPosition();
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
