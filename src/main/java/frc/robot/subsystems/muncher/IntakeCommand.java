package frc.robot.subsystems.muncher;

import edu.wpi.first.wpilibj2.command.Command;

/**
 * Just runs the intake for 5 seconds at 10% speed.
 */
public class IntakeCommand extends Command
{
   /**
    * DONT USE THIS CONSTRUCTER, use IntakeCommand.create() instead please it aint worth it
    */
   private IntakeCommand(){}

   /**
    * Just runs the intake for 5 seconds at 10% speed.
    */
   public static Command create() 
   {
      return IntakeForTimeAtSpeedCommand.create(5,0.1);
   }
}
