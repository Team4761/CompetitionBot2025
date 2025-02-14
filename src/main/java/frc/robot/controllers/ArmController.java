package frc.robot.controllers;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.Robot;
import frc.robot.subsystems.muncher.IntakeCommand;
import frc.robot.subsystems.muncher.YeetCommand;

/**
 * This will control both the arm AND the muncher/outtake.
 * TODO: consider using CommandXBoxController for built in eventHandler conveniences, e.g
 * rightBumper().onTrue(YeetCommand.create())
 */
public class ArmController extends XboxController {
    public boolean armManualControl = true;
    public boolean extendArmMotorEnabled = true;
    public boolean rotateArmMotorEnabled = true;
    public double intakeSpeed = 0.3;
    public double yeetSpeed = 0.2;
    /**
     * @param port The port that Driverstation has the controller set to. (you can change this in Driverstation)
     */
    public ArmController(int port) {
        super(port);
        // These run the onLeftTrigger and onRightTrigger methods when the triggers are pressed more than 25% down.
        // if (Robot.map.muncher != null) {
        //     leftTrigger(.25, CommandScheduler.getInstance().getDefaultButtonLoop()).ifHigh(this::onLeftTrigger);
        //     rightTrigger(.25, CommandScheduler.getInstance().getDefaultButtonLoop()).ifHigh(this::onRightTrigger);
    
        // }
    }

    public void setIntakeSpeed(double speed)
    {
        intakeSpeed = speed;
    }
    public void setYeetSpeed(double speed)
    {
        yeetSpeed = speed;
    }


    /**
     * This should be called in Robot.java during teleopPeriodic with armController.teleopPeriodic()
     */
    public void teleopPeriodic() {
        if (Robot.map.muncher != null) {
            if (getRightBumperButtonPressed() && !armManualControl) {
                CommandScheduler.getInstance().schedule(YeetCommand.create());
            }
            if (armManualControl) {
                if (getAButton()) {
                    // This was in, positive yeet is IN
                    Robot.map.muncher.yeet(intakeSpeed);
                }
                if (getBButton()) {
                    Robot.map.muncher.yeet(-intakeSpeed);
                }
                if (!getAButton() && !getBButton()) {
                    Robot.map.muncher.yeet(0);
                }
            }
        }
        if(Robot.map.arm != null && armManualControl)
        {
            Robot.map.arm.rotate(0.1*getLeftY());
            Robot.map.arm.extend(0.1*getRightY());
        }
        if (Robot.map.muncher != null) {
            Robot.map.muncher.intake(-intakeSpeed*getLeftTriggerAxis() + intakeSpeed*getRightTriggerAxis());
            // Robot.map.muncher.yeet(0.1*getRightTriggerAxis());
        }
    }


    public void onLeftTrigger() {
        Robot.map.muncher.intake(getLeftTriggerAxis());
    }

    public void onRightTrigger() {
        Robot.map.muncher.intake(-getRightTriggerAxis());
    }

    // These functions are pretty much only used for the dashboard.
    public void setArmManualControl(boolean armManualControl)
    {
        this.armManualControl = armManualControl;
    }

    public void setExtendArmMotorEnabled(boolean extendArmMotorEnabled)
    {
        this.extendArmMotorEnabled = extendArmMotorEnabled;
    }

    public void setRotateArmMotorEnabled(boolean rotateArmMotorEnabled)
    {
        this.rotateArmMotorEnabled = rotateArmMotorEnabled;
    }
}
