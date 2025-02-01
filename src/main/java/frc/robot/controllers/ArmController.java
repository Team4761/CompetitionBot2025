package frc.robot.controllers;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.event.EventLoop;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.subsystems.muncher.YeetCommand;
import frc.robot.Robot;

/**
 * This will control both the arm AND the muncher/outtake.
 */
public class ArmController extends XboxController {

    public ArmController(int port) {
        super(port);
        leftTrigger(0.25, bind(this::onLeftTrigger));
        rightTrigger(0.25, bind(this::onRightTrigger));
    }

    public void teleopPeriodic() {
        if (Robot.map.muncher != null) {
            if (getRightBumperButtonPressed() == true) {
                CommandScheduler.getInstance().schedule(YeetCommand.create());
            }
        }
    }

    public void onLeftTrigger() {
        Robot.map.muncher.intake(getLeftTriggerAxis());
    }

    public void onRightTrigger() {
        Robot.map.muncher.intake(-getRightTriggerAxis());
    }

    private EventLoop bind(Runnable runnable) {
        EventLoop trigger = new EventLoop();
        trigger.bind(runnable);
        return trigger;
    }
}
