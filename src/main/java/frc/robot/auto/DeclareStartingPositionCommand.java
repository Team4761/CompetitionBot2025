package frc.robot.auto;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Robot;

/**
 * This will start our odometry off at the correct position.
 */
public class DeclareStartingPositionCommand extends Command {

    /**
     * DO NOT USE THE CONSTRUCTOR! Use DeclareStartingPositionCommand.create() instead.
     * This will start our odometry off at the correct position.
     */
    private DeclareStartingPositionCommand() {}

    /**
     * This will start our odometry off at the correct position.
     * @return
     */
    public static Command create() {
        return new DeclareStartingPositionCommand();
    }


    /**
     * Sets our current position to wherever our "Starting Position" is set to in the Dashboard.
     */
    @Override
    public void initialize() {
        switch (AutoHandler.getStartingPosition()) {
            case BLUE_LEFT: {
                Robot.map.swerve.resetPosition(new Pose2d(7.5565, 6.03885, new Rotation2d(Math.PI)));
                break;
            }
            case BLUE_CENTER: {
                Robot.map.swerve.resetPosition(new Pose2d(7.5565, 4.02590, new Rotation2d(Math.PI)));
                break;
            }
            case BLUE_RIGHT: {
                Robot.map.swerve.resetPosition(new Pose2d(7.5565, 2.01295, new Rotation2d(Math.PI)));
                break;
            }
            case RED_LEFT: {
                Robot.map.swerve.resetPosition(new Pose2d(10, 6.03885, new Rotation2d(0)));
                break;
            }
            case RED_CENTER: {
                Robot.map.swerve.resetPosition(new Pose2d(10, 4.02590, new Rotation2d(0)));
                break;
            }
            case RED_RIGHT: {
                Robot.map.swerve.resetPosition(new Pose2d(10, 2.01295, new Rotation2d(0)));
                break;
            }
        }
    }


    public boolean isFinished() {
        // Instantly finishes
        return true;
    }
    
}
