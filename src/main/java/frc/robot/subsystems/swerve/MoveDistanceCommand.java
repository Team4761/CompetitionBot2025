package frc.robot.subsystems.swerve;

import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.Robot;


//Command to move a set distance.
public class MoveDistanceCommand extends Command{


    private double deltaX;
    private double deltaY;
    private Rotation2d deltaRot;

    private static final ProfiledPIDController DistanceXPID = new ProfiledPIDController(
        1,
        0,
        0,
        new TrapezoidProfile.Constraints(Constants.SWERVE_MAX_DRIVE_SPEED, Constants.SWERVE_MAX_ACCELERATION)
    );

    private static final ProfiledPIDController DistanceYPID = new ProfiledPIDController(
        1,
        0,
        0,
        new TrapezoidProfile.Constraints(Constants.SWERVE_MAX_DRIVE_SPEED, Constants.SWERVE_MAX_ACCELERATION)
    );


    private static final ProfiledPIDController RotationPID = new ProfiledPIDController(
        3,
        0,
        0,
        new TrapezoidProfile.Constraints(Constants.SWERVE_MAX_DRIVE_SPEED, Constants.SWERVE_MAX_ANGULAR_ACCELERATION)
    );

    private Pose2d targetPosition = new Pose2d();


    /**
     * DO NOT USE THE CONSTRUCTOR!!! USE MoveDistanceCommand.create()!!
     */
    private MoveDistanceCommand() {}

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
        addRequirements(Robot.map.swerve); //first come, first swerve (-Slim Jim from 2024 competition bot)
        Pose2d currentPosition = Robot.map.swerve.getPosition();
        targetPosition = new Pose2d(currentPosition.getX() + deltaX, currentPosition.getY() + deltaY, currentPosition.getRotation().plus(deltaRot));
    }
    
    @Override
    public void execute() {
        Pose2d currentPosition = Robot.map.swerve.getPosition();
        
        double driveSpeedX = DistanceXPID.calculate(currentPosition.getX(), targetPosition.getX());
        double driveSpeedY = DistanceYPID.calculate(currentPosition.getY(), targetPosition.getY());
        double driveSpeedRot = RotationPID.calculate(currentPosition.getRotation().getRadians(), targetPosition.getRotation().getRadians());

        Robot.map.swerve.setDesiredSpeeds(driveSpeedX, driveSpeedY, driveSpeedRot);
    }

    @Override
    public boolean isFinished() {
        Pose2d currentPosition = Robot.map.swerve.getPosition();
        double differenceX = Math.abs(currentPosition.getX() - targetPosition.getX());
        double differenceY = Math.abs(currentPosition.getY() - targetPosition.getY());
        double differenceRot = Math.abs(currentPosition.getRotation().getRadians() - targetPosition.getRotation().getRadians());
        // If within the acceptable margin of error (1cm & 5 degrees), then the command is finished.
        if (differenceX + differenceY <= 0.01 && differenceRot <= Math.toRadians(5)) {
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
