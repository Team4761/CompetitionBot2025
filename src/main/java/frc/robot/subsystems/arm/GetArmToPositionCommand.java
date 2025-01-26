package frc.robot.subsystems.arm;

import edu.wpi.first.wpilibj2.command.Command;

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
     * This sets the setpoints in the ArmSubsystem and ends when the 
     * @param x The distance from (0,0) horizontally. +x is the front side of the Robot.
     * @param y The distance from (0,0) vertically. +y is the upwards direction.
     */
    public GetArmToPositionCommand(double x, double y) {
        this.targetX = x;
        this.targetY = y;
    }


    /**
     * This should set the arm setpoint.
     */
    @Override
    public void initialize() {

    }

    /**
     * This should only end when the current position is within MARGIN_OF_ERROR of the target position.
     */
    @Override
    public void end(boolean isFinished) {

    }
}
