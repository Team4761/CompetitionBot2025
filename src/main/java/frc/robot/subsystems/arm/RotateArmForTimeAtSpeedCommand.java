package frc.robot.subsystems.arm;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Robot;


/**
 * <p> DOOMSDAY COMMAND!
 * <p> Is this name verbose? Yes. Why? Simple: we shouldn't be using this command unless we have to, so I'm going to make it annoying to type.
 * <p> This should rotate/pivot the arm at a set speed for the specified duration.
 * <p> It does NOT extend the arm.
 * <p> This should not be used if we have accurate encoders on the arm, as it is unaccurate and could potentially damage the mechanism if improperly used.
 */
public class RotateArmForTimeAtSpeedCommand extends Command 
{
     /** The speed as a value between -1 to 1 where +1 represents full speed rotate upwards (Clock Wise) 
      * (Please make this slow so you dont break the robot).*/
    private double rotateSpeed;

    
    /**
    * DONT USE THE CONSTUCTOR! Please use RotateArmForTimeAtSpeedCommand.create() instead.
    */
    private RotateArmForTimeAtSpeedCommand() {
        addRequirements(Robot.map.arm); // disarm the bomb if required
    }

    /**
    * DONT USE THE CONSTUCTOR! Please use RotateArmForTimeAtSpeedCommand.create() instead.
    */
     private RotateArmForTimeAtSpeedCommand(double rotateSpeed)
     {
        this();
        this.rotateSpeed = rotateSpeed;
     }

     /**
      * Extends the arm for a duration at a specific speed.
      * @param duration The duration to extend for in seconds.
      * @param extensionSpeed The speed to extend for. Between -1 and 1 where 1 represents full speed extension.
      */
     public static Command create(double duration, double rotateSpeed)
     {
        return new RotateArmForTimeAtSpeedCommand(rotateSpeed).withTimeout(duration);
     }

    /**
     * Guess what this does? It... rotates the arm at the speed specified!
     */
    @Override
    public void execute()
    {
        Robot.map.arm.rotate(rotateSpeed);
    }


    /**
     * Need to set the speed to 0 just in case it keeps running infinitely...
     */
    public void end(boolean isFinished)
    {
        Robot.map.arm.rotate(0);
    }
}