package frc.robot.subsystems.arm;

import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Robot;
import frc.robot.auto.CommandCenter;

/**
 * <p> This controls the arm to get to the setpoint and ends when it gets within (TBD) meters of the desired location.
 * <p> TODO: Determine what to do if we tell it to go to an impossible location
 * <p> TODO: Change (0,0) to a description of where the origin actually is.
 * <p> TODO: Actually implement this.
 */
public class GetArmToPositionCommand extends Command {

    private ArmState targetState;
    
    /**
     * DO NOT USE THE CONSTRUCTOR. Please use GetArmToPositionCommand.create() instead.
     */
    private GetArmToPositionCommand() {}

    /**
     * DO NOT USE THE CONSTRUCTOR. Please use GetArmToPositionCommand.create() instead.
     */
    private GetArmToPositionCommand(double x, double y) {
        this(ArmSubsystem.getRotationExtensionFromSetPoint(x, y));
    }

    private GetArmToPositionCommand(ArmState state) {
        targetState = state;
    }


    /**
     * This sets the setpoints in the ArmSubsystem and ends when the arm is within the margin of error of that position.
     * @param x The distance from (0,0) horizontally. +x is the front side of the Robot.
     * @param y The distance from (0,0) vertically. +y is the upwards direction.
     */
    public static Command create(double x, double y) {
        return new GetArmToPositionCommand(x, y);
    }
    

    public static Command create(ArmState desiredState) {
        return new GetArmToPositionCommand(desiredState);
    }


    /**
     * This should set the arm setpoint.
     */
    @Override
    public void initialize() {
        CommandCenter.addRequirements(this, Robot.map.arm);
        Robot.map.arm.setState(targetState);
    }

    /**
     * This should only end when the current position is within MARGIN_OF_ERROR of the target position.
     */
    @Override
    public boolean isFinished() {
        if (
            // Rotation is within 3 degrees
            Math.abs(targetState.getPivotRotation().getDegrees() - Robot.map.arm.getPivotRotation().getDegrees()) <= 3 &&
            // Extension is within 3cm
            Math.abs(targetState.getExtensionLength() - Robot.map.arm.getExtensionLength()) <= 0.03
        ) {
            return true;
        }
        return false;
    }
}
