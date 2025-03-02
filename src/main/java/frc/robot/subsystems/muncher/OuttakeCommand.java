package frc.robot.subsystems.muncher;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Robot;
import frc.robot.auto.CommandCenter;

/**
 * Runs the outtake for 5 seconds at 10% speed.
 */
public class OuttakeCommand extends Command
{

   /**
    * DONT USE THIS CONSTRUCTER, use create instead, please it aint worth it
    */
   private OuttakeCommand(){}

   @Override
   public void initialize() {
      CommandCenter.addRequirements(this, Robot.map.muncher);
   }

   /**
    * Runs the outtake for 1 seconds at the speed on the dashboard speed.
    */
   public static Command create() 
   {
      return IntakeForTimeAtSpeedCommand.create(1.0,Robot.armController.getOuttakeSpeed());
   }
}
