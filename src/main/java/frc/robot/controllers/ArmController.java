package frc.robot.controllers;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.RobotMap;
import frc.robot.subsystems.muncher.IntakeCommand;
import frc.robot.subsystems.muncher.OuttakeCommand;
import frc.robot.subsystems.muncher.YeetCommand;
import frc.robot.Robot;

/**
 * This will control both the arm AND the muncher/outtake.
 */
public class ArmController extends XboxController {
    
    public ArmController (int port) {
        super(port);
        
    }
    public void teleopPeriodic()
        {
            if(Robot.map.muncher!=null)
            {
                if(getLeftTriggerAxis() >= 0.25)
                {
                    CommandScheduler.getInstance().schedule(IntakeCommand.create());
                }
                if(getRightTriggerAxis() >= 0.25)
                {
                    CommandScheduler.getInstance().schedule(OuttakeCommand.create());
                }
                if(getRightBumperButtonPressed() == true)
                {
                    CommandScheduler.getInstance().schedule(YeetCommand.create());
                }
            }
        }
}
