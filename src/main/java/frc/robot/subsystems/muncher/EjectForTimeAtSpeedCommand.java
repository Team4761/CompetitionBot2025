package frc.robot.subsystems.muncher;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Robot;


/**
 * <p> DOOMSDAY COMMAND!
 * <p> Is this name verbose? Yes. Why? Simple: we shouldn't be using this command unless we have to, so I'm going to make it annoying to type.
 * <p> This should intake
 */
public class EjectForTimeAtSpeedCommand extends Command 
{
     /** The speed as a value between -1 to 1 where +1 represents full speed outwards (Clock Wise) 
      * (Please make this slow so you dont break the robot).*/
     private double ejectSpeed;

    /**
    * DONT USE THE CONSTUCTOR please use the 'create' method INSTEAD
    */
     private EjectForTimeAtSpeedCommand(double ejectSpeed)
     {
         this.ejectSpeed = ejectSpeed;
     }

     /**
      * Runs the Eject motor for a duration at a specific speed.
      * @param duration The duration to run the eject motor for in seconds.
      * @param intakeSpeed The speed to run the eject motor at. Between -1 and 1 where 1 represents full speed outwards
      */
     public static Command create(double duration, double ejectSpeed)
     {
        return new EjectForTimeAtSpeedCommand(ejectSpeed).withTimeout(duration);
     }

    /**
     * Guess what this does? It... Ejects at the speed specified!
     */
    @Override
    public void execute()
    {
        Robot.map.muncher.yeet(ejectSpeed);
    }
}