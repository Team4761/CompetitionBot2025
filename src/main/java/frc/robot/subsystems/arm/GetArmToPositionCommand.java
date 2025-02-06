package frc.robot.subsystems.arm;

import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Robot;

/**
 * <p> This controls the arm to get to the setpoint and ends when it gets within (TBD) meters of the desired location.
 * <p> TODO: Determine what to do if we tell it to go to an impossible location
 * <p> TODO: Change (0,0) to a description of where the origin actually is.
 * <p> TODO: Actually implement this.
 */
public class GetArmToPositionCommand extends Command {

    // This is in meters and is the acceptable margin of error for how far the current point can be from the set point to end this command.
    private static final double ACCEPTABLE_MARGIN_OF_ERROR = 0.02;

    // The target x position where +x is the forwards direction of the robot in meters
    private double targetX;
    // The target y position where +y is the up direction in meters
    private double targetY;
    
    /**
     * DO NOT USE THE CONSTRUCTOR. Please use GetArmToPositionCommand.create() instead.
     */
    private GetArmToPositionCommand() {}

    /**
     * DO NOT USE THE CONSTRUCTOR. Please use GetArmToPositionCommand.create() instead.
     */
    private GetArmToPositionCommand(double x, double y) {
        this.targetX = x;
        this.targetY = y;
    }


    /**
     * This sets the setpoints in the ArmSubsystem and ends when the arm is within the margin of error of that position.
     * @param x The distance from (0,0) horizontally. +x is the front side of the Robot.
     * @param y The distance from (0,0) vertically. +y is the upwards direction.
     */
    public static Command create(double x, double y) {
        return new GetArmToPositionCommand(x, y);
    }


    /**
     * This should set the arm setpoint.
     */
    @Override
    public void initialize() {
        Robot.map.arm.setDesiredPosition(targetX, targetY);
    }

    /**
     * This should only end when the current position is within MARGIN_OF_ERROR of the target position.
     */
    @Override
    public boolean isFinished() {
        Translation2d currentPosition = Robot.map.arm.getSetPointFromRotationAndExtension(Robot.map.arm.getPivotRotation(), Robot.map.arm.getExtensionLength());

        // If within ACCEPTABLE_MARGIN_OF_ERROR meters of the target location, end the command.
        if (Math.pow((currentPosition.getX() - targetX),2) + Math.pow((currentPosition.getX() - targetY),2) < Math.pow(ACCEPTABLE_MARGIN_OF_ERROR,2)) {
            return true;
        }
        return false;
    }
}
