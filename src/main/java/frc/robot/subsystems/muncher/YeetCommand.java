package frc.robot.subsystems.muncher;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
/**
 * Runs the yeet device (the coral pusher) until it has pushed the coral out 85% of the way (which was a value found via testing), and then retracts the finger until 5%.
 */
public class YeetCommand extends Command
{

   public static boolean autoMunchMode = false;
   /**
    * DONT USE THIS CONSTRUCTER, use YeetCommand.create() instead, please it aint worth it
    */
   private YeetCommand(){}

   /**
    * Runs the yeet device (the coral pusher) until it has pushed the coral out 85% of the way (which was a value found via testing), and then retracts the finger until 5%.
    */
   public static Command create() 
   {
      // return new SequentialCommandGroup
      // (
      //    YeetForTimeAtSpeedCommand.create(0.3,0.4), 
      //    YeetForTimeAtSpeedCommand.create(-0.2,0.6)
      // );

      return new SequentialCommandGroup(
         new ChangeAutoMunchMode(true),
         GetYeetToPercentCommand.create(0.85, 0.3),
         GetYeetToPercentCommand.create(0.10, 0.2),
         GetYeetToPercentCommand.create(0.03, 0.05),
         new ChangeAutoMunchMode(false)
      );
   }

   
}