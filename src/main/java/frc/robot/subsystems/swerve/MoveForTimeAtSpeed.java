package frc.robot.subsystems.swerve;


import edu.wpi.first.wpilibj2.command.Command;

import frc.robot.Robot;

/**
 * <p> DOOMSDAY COMMAND!
 * <p> Is this name verbose? Yes. Why? Simple: we shouldn't be using this command unless we have to, so I'm going to make it annoying to type.
 * <p> This will move the robot at the specified speed for the specified time and then stop the bot once done.
 * <p> This should not be used if we have odometry or vision because this makes it so that movement is not very accurate.
 */
public class MoveForTimeAtSpeed extends Command {
    /** Meters per second. This is the same as +x for swerve.*/
    double speedForwards;
    /** Meters per second. This is the same as +y for swerve. Positive direction is left. */
    double speedHorizontal;
    /** Radians per second. The positive direction is counterclockwise (the front of the robot turning left).*/
    double speedTurn;
    /** Duration to move for in seconds. */
    double moveTime;

    // Changed it from storing a variable for endTime to using the withTimeout function to keep the code more consistent.

    /**
     * <p> This will move the robot at the specified speed for the specified time and then stop the bot once done.
     * @param speedForwards Meters per second. This is the same as +x for swerve.
     * @param speedHorizontal Meters per second. This is the same as +y for swerve. Positive direction is left.
     * @param speedTurn Radians per second. The positive direction is counterclockwise (the front of the robot turning left).
     * @param moveTime
     */
    private MoveForTimeAtSpeed(double speedForwards, double speedHorizontal, double speedTurn, double moveTime) {
        this.speedForwards = speedForwards;
        this.speedHorizontal = speedHorizontal;
        this.speedTurn = speedTurn;
        this.moveTime = moveTime;
    }
    public static Command create(double speedForwards, double speedHorizontal, double speedTurn, double moveTim)
    {
        return new MoveForTimeAtSpeed(speedForwards,speedHorizontal,speedTurn,moveTim);
    }

    /**
     * <p> All that needs to be initialized is the timer for ending the command.
     */
    @Override
    public void initialize() {
        // This command requires swerve (no other code can use the subsystem during this time)
        addRequirements(Robot.map.swerve);

        // Run for moveTime seconds and then stop
        this.withTimeout(moveTime);
    }

    /**
     * <p> Constantly updates the speed setpoints of swerve to the desired speeds.
     */
    @Override
    public void execute() {
        Robot.map.swerve.setDesiredSpeeds(speedForwards, speedHorizontal, speedTurn);
    }

    /**
     * <p> When the command ends, stop the bot.
     */
    public void end() {
        Robot.map.swerve.setDesiredSpeeds(0, 0, 0);
    }
}
