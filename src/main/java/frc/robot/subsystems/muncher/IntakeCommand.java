package frc.robot.subsystems.muncher;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Robot;
import frc.robot.auto.CommandCenter;

/**
 * Just runs the intake for 5 seconds at 10% speed.
 */
public class IntakeCommand extends Command
{
   /**
    * DONT USE THIS CONSTRUCTER, use IntakeCommand.create() instead please it aint worth it
    */
   private IntakeCommand(){}

   @Override
   public void initialize() {
      CommandCenter.addRequirements(this, Robot.map.muncher);
   }

   /**
    * Just runs the intake for 5 seconds at 10% speed.
    */
   public static Command create() 
   {
      return IntakeForTimeAtSpeedCommand.create(5,0.1);
   }
}
