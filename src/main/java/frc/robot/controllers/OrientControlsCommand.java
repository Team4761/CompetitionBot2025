package frc.robot.controllers;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Robot;

public class OrientControlsCommand extends Command {

    private final Rotation2d forwardsDirection;
    
    /**
     * DO NOT USE THE CONSTRUCTOR! Use OrientControlsCommand.create(forwardsDirection) instead
     */
    private OrientControlsCommand(Rotation2d forwardsDirection) {
        this.forwardsDirection = forwardsDirection;
    }

    /**
     * This will set the forwards direction for controlling the robot to whatever direction is specified.
     * For example, if you set the forwards direction to PI/2 (90 degrees), then the forwards direction will become whatever the field oriented forwards direction is but rotated counterclockwise by 90 degrees.
     * @param forwardsDirection The offset from the forwards field-oriented direction. Counterclockwise = positive.
     * @return
     */
    public static Command create(Rotation2d forwardsDirection) {
        return new OrientControlsCommand(forwardsDirection);
    }


    @Override
    public void initialize() {
        Robot.driveController.orientForwardsControllingDirection(forwardsDirection);
    }


    @Override
    public boolean isFinished() {
        return true;
    }
}
