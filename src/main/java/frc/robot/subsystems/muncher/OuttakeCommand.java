package frc.robot.subsystems.muncher;

import edu.wpi.first.wpilibj2.command.Command;

/**
 * Runs the outtake for 5 seconds at 10% speed.
 */
public class OuttakeCommand extends Command
{

   /**
    * DONT USE THIS CONSTRUCTER, use create instead, please it aint worth it
    */
   private OuttakeCommand(){}

   /**
    * Runs the outtake for 5 seconds at 10% speed.
    */
   public static Command create() 
   {
      return IntakeForTimeAtSpeedCommand.create(5,-0.1);
   }
}
