package frc.robot.subsystems.muncher;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Robot;


/**
 * <p> DOOMSDAY COMMAND!
 * <p> Is this name verbose? Yes. Why? Simple: we shouldn't be using this command unless we have to, so I'm going to make it annoying to type.
 * <p> This should intake
 */
public class IntakeForTimeAtSpeedCommand extends Command 
{
     /** The speed as a value between -1 to 1 where +1 represents full speed rotate inwards (Clock Wise) 
      * (Please make this slow so you dont break the robot).*/
     private double intakeSpeed;

    /**
    * DONT USE THE CONSTUCTOR please use the 'create' method INSTEAD
    */
     private IntakeForTimeAtSpeedCommand(double intakeSpeed)
     {
         this.intakeSpeed = intakeSpeed;
     }

     /**
      * Intakes for a duration at a specific speed.
      * @param duration The duration to intake for in seconds.
      * @param intakeSpeed The speed to intake. Between -1 and 1 where 1 represents full speed intake
      */
     public static Command create(double duration, double intakeSpeed)
     {
        return new IntakeForTimeAtSpeedCommand(intakeSpeed).withTimeout(duration);
     }

    /**
     * Guess what this does? It... intakes/outtakes (if negative speed) at the speed specified!
     */
    @Override
    public void execute()
    {
        Robot.map.muncher.intake(intakeSpeed);
    }
}