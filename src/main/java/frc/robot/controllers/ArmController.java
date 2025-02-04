package frc.robot.controllers;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.Robot;
import frc.robot.subsystems.arm.ArmSubsystem;
import frc.robot.subsystems.muncher.YeetCommand;

/**
 * This will control both the arm AND the muncher/outtake.
 * TODO: consider using CommandXBoxController for built in eventHandler conveniences, e.g
 * rightBumper().onTrue(YeetCommand.create())
 */
public class ArmController extends XboxController {
    public Boolean armManualControl = false;
    public Boolean extendArmMotorEnabled = true;
    public Boolean rotateArmMotorEnabled = true;
    public ArmController(int port) {
        super(port);
        leftTrigger(.25, CommandScheduler.getInstance().getDefaultButtonLoop()).ifHigh(this::onLeftTrigger);
        rightTrigger(.25, CommandScheduler.getInstance().getDefaultButtonLoop()).ifHigh(this::onRightTrigger);
    }

    public void teleopPeriodic() {
        if (Robot.map.muncher != null) {
            if (getRightBumperButtonPressed()) {
                CommandScheduler.getInstance().schedule(YeetCommand.create());
            }
        }
        if(Robot.map.arm != null && armManualControl)
        {
            ArmSubsystem.rotate(getLeftY());
            ArmSubsystem.extend(getRightY());
        }
    }

    public void onLeftTrigger() {
        Robot.map.muncher.intake(getLeftTriggerAxis());
    }

    public void onRightTrigger() {
        Robot.map.muncher.intake(-getRightTriggerAxis());
    }

    public void armManualControl(boolean armManualControl)
    {
        this.armManualControl = armManualControl;
    }
}
