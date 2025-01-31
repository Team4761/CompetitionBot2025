package frc.robot.subsystems.muncher;

import edu.wpi.first.wpilibj2.command.Command;
public class OuttakeCommand extends Command
{
/**
 * DONT USE THIS CONSTRUCTER, use create instead, please it aint worth it
 */
private OuttakeCommand(){}
 public Command create() 
 {
    return IntakeForTimeAtSpeedCommand.create(5,-0.1);
 }
}
