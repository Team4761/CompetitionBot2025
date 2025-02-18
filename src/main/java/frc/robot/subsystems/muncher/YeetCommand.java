package frc.robot.subsystems.muncher;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.PrintCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
/**
 * Runs the yeet device (the coral pusher) for 5 seconds at 10% speed, and then runs it in reverse to reset it for 5 seconds at -10% speed.
 */
public class YeetCommand extends Command
{
   /**
    * DONT USE THIS CONSTRUCTER, use YeetCommand.create() instead, please it aint worth it
    */
   private YeetCommand(){}

   /**
    * Runs the yeet device (the coral pusher) for 5 seconds at 10% speed, and then runs it in reverse to reset it for 5 seconds at -10% speed.
    */
   public static Command create() 
   {
      return new SequentialCommandGroup
      (
         new PrintCommand("Yeet"),
         YeetForTimeAtSpeedCommand.create(0.3,0.4), 
         YeetForTimeAtSpeedCommand.create(-0.2,1.0)
      );
   }
}