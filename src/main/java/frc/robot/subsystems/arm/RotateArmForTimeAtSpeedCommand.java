package frc.robot.subsystems.arm;

import javax.xml.datatype.Duration;

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
     /** The duration to rotate the arm for in seconds. */
     private double duration;
     /** The speed as a value between -1 to 1 where +1 represents full speed rotate upwards (Clock Wise) 
      * (Please make this slow so you dont break the robot).*/
     private double rotateSpeed;

    /**
    * DONT USE THE CONSTUCTOR please use the 'create' method INSTEAD
    */
     private RotateArmForTimeAtSpeedCommand(double duration, double rotateSpeed)
     {
         this.duration = duration;
         this.rotateSpeed = rotateSpeed;
     }

     /**
      * Extends the arm for a duration at a specific speed.
      * @param duration The duration to extend for in seconds.
      * @param extensionSpeed The speed to extend for. Between -1 and 1 where 1 represents full speed extension.
      */
     public static Command create(double duration, double rotateSpeed)
     {
        return new RotateArmForTimeAtSpeedCommand(duration,rotateSpeed);
     }

    /**
     * Guess what this does? It... rotates the arm at the speed specified!
     */
    @Override
    public void execute()
    {
        Robot.map.arm.rotate(rotateSpeed);
    }
}