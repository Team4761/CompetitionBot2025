package frc.robot.subsystems.muncher;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
import edu.wpi.first.wpilibj2.command.PrintCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
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
   public static Command create(boolean alsoSpinOuttake) 
   {
      // return new SequentialCommandGroup
      // (
      //    YeetForTimeAtSpeedCommand.create(0.3,0.4), 
      //    YeetForTimeAtSpeedCommand.create(-0.2,0.6)
      // );

      return new SequentialCommandGroup(
         new ChangeAutoMunchMode(true),
         new ParallelRaceGroup(
            new SequentialCommandGroup(
               GetYeetToPercentCommand.create(0.75, 0.3),
               GetYeetToPercentCommand.create(0.95,0.15),
               GetYeetToPercentCommand.create(0.10, 0.2),
               GetYeetToPercentCommand.create(0.03, 0.05)
            ),
            new SequentialCommandGroup(
               WaitUntilYeetAtPercentCommand.create(0.5),
               (alsoSpinOuttake ? IntakeForTimeAtSpeedCommand.create(10, -0.1) : new WaitCommand(10))
            )
         ),
         new ChangeAutoMunchMode(false)
      );
   }

   
}