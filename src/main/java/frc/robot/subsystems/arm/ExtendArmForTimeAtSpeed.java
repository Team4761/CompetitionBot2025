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
public class ExtendArmForTimeAtSpeed extends Command
 {
    /** The duration to extend the arm for in seconds. */
    private double duration;
    /** The speed as a value between -1 to 1 where +1 represents full speed outwards.*/
    private double extensionSpeed;


    /**
     * Extends the arm for a duration at a specific speed.
     * @param duration The duration to extend for in seconds.
     * @param extensionSpeed The speed to extend for. Between -1 and 1 where 1 represents full speed extension.
     */
    public ExtendArmForTimeAtSpeed(double duration, double extensionSpeed)
    {
        this.duration = duration;
        this.extensionSpeed = extensionSpeed;
    }


    /**
     * All this will do is make the command timeout after the duration has passed.
     */
    @Override
    public void initialize()
    {
        // Will end the command after the duration is over.
        this.withTimeout(duration);
    }


    /**
     * Guess what this does? It... extends the arm at the speed specified!
     */
    @Override
    public void execute()
    {
        Robot.map.arm.extend(extensionSpeed);
    }
}
    