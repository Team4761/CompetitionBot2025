package frc.robot.subsystems.arm;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Robot;


/**
 * <p> DOOMSDAY COMMAND! 
 * <p> Is this name verbose? Yes. Why? Simple: we shouldn't be using this command unless we have to, so I'm going to make it annoying to type.
 * <p> This ONLY extends the arm of the robot.
 * <p> It does NOT pivot the arm.
 * <p> This should not be used if we have accurate encoders on the arm, as it is unaccurate and could potentially damage the mechanism if improperly used.
 */
public class ExtendArmForTimeAtSpeedCommand extends Command
 {
    /** The speed as a value between -1 to 1 where +1 represents full speed outwards.*/
    private double extensionSpeed;


    /**
     * DON'T USE THE CONSTUCTOR please use ExtendArmForTimeAtSpeedCommand.create() instead
     */
    private ExtendArmForTimeAtSpeedCommand() {
        addRequirements(Robot.map.arm); // disarm the bomb if required
    }

    /**
     * DON'T USE THE CONSTUCTOR please use ExtendArmForTimeAtSpeedCommand.create() instead
     */
    private ExtendArmForTimeAtSpeedCommand(double extensionSpeed)
    {
        this();
        this.extensionSpeed = extensionSpeed;
    }

    /**
     * Extends the arm for a duration at a specific speed.
     * @param duration The duration to extend for in seconds.
     * @param extensionSpeed The speed to extend for. Between -1 and 1 where 1 represents full speed extension.
     */
    public static Command create(double duration, double extensionSpeed)
    {
        return new ExtendArmForTimeAtSpeedCommand(extensionSpeed).withTimeout(duration);
    }

    /**
     * Guess what this does? It... extends the arm at the speed specified!
     */
    @Override
    public void execute()
    {
        Robot.map.arm.extend(extensionSpeed);
    }

    /**
     * It then needs to make sure to set the speed to zero when it's done.
     */
    @Override
    public void end(boolean isInterrupted)
    {
        Robot.map.arm.extend(0);
    }
}
    