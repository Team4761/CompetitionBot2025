package frc.robot.subsystems.muncher;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.muncher.YeetForTimeAtSpeedCommand;
public class YeetCommand extends Command
{
/**
 * DONT USE THIS CONSTRUCTER, use create instead, please it aint worth it
 */
   private YeetCommand(){}
   public static Command create() 
   {
      return new SequentialCommandGroup
      (
         YeetForTimeAtSpeedCommand.create(5,0.1), 
         YeetForTimeAtSpeedCommand.create(5,-0.1)
      );
   }
}
 