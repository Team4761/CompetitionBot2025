package frc.robot.controllers;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.Robot;
import frc.robot.subsystems.muncher.YeetCommand;

/**
 * This will control both the arm AND the muncher/outtake.
 * TODO: consider using CommandXBoxController for built in eventHandler conveniences, e.g
 * rightBumper().onTrue(YeetCommand.create())
 */
public class ArmController extends XboxController {
    private boolean armManualControl = true;
    private boolean extendArmMotorEnabled = true;
    private boolean pivotArmMotorEnabled = true;

    private double intakeSpeed = 0.3;
    private double outtakeSpeed = 0.2;
    private double yeetSpeed = 0.2;

    private double extendSpeed = 0.1;
    private double pivotSpeed = 0.1;

    private boolean invertPivot = false;
    private boolean invertExtend = false;
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


    /**
     * This should be called in Robot.java during teleopPeriodic with armController.teleopPeriodic()
     */
    public void teleopPeriodic() {
        if (Robot.map.muncher != null) {
            if (getRightBumperButtonPressed() && !armManualControl) {
                CommandScheduler.getInstance().schedule(YeetCommand.create());
            }
            if (armManualControl && !getYButton()) {
                if (getBButton()) {
                    Robot.map.muncher.yeet(-0.1);
                }
                if (getXButton()) {
                    Robot.map.muncher.yeet(intakeSpeed);
                }
                if (!getXButton() && !getBButton()) {
                    Robot.map.muncher.yeet(0);
                }
            }
        }
        if(Robot.map.arm != null && armManualControl)
        {
            Robot.map.arm.rotate(pivotSpeed*getLeftY());
            Robot.map.arm.extend(extendSpeed*getRightY());
        }
        if (Robot.map.muncher != null) {
            Robot.map.muncher.intake(-outtakeSpeed*getLeftTriggerAxis() + intakeSpeed*getRightTriggerAxis());
            // Robot.map.muncher.yeet(0.1*getRightTriggerAxis());

            if (getYButtonPressed()) {
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

    // These functions are pretty much only used for the dashboard.
    public void setArmManualControl(boolean armManualControl)
    {
        this.armManualControl = armManualControl;
    }

    public void setExtendArmMotorEnabled(boolean extendArmMotorEnabled)
    {
        this.extendArmMotorEnabled = extendArmMotorEnabled;
    }

    public void setPivotArmMotorEnabled(boolean rotateArmMotorEnabled)
    {
        this.pivotArmMotorEnabled = rotateArmMotorEnabled;
    }

    public void setIntakeSpeed(double speed)
    {
        intakeSpeed = speed;
    }
    public void setYeetSpeed(double speed)
    {
        yeetSpeed = speed;
    }
    public void setExtendSpeed(double speed)
    {
        extendSpeed = speed;
    }
    public void setPivotSpeed(double speed)
    {
        pivotSpeed = speed;
    }
    public void setExtendInverted(boolean inverted)
    {
        invertExtend = inverted;
    }
    public void setPivotInverted(boolean inverted)
    {
        invertPivot = inverted;
    }
    public void setOuttakeSpeed(double speed)
    {
        outtakeSpeed = speed;
    }

    
    public double getIntakeSpeed() { return this.intakeSpeed; }
    public double getOuttakeSpeed() { return this.outtakeSpeed; }
    public double getYeetSpeed() { return this.yeetSpeed; }
    public double getExtendSpeed() { return this.extendSpeed; }
    public double getPivotSpeed() { return this.pivotSpeed; }
    public boolean isExtendInverted() { return this.invertExtend; }
    public boolean isPivotInverted() { return this.invertPivot; }
    public boolean isArmManualControl() { return this.armManualControl; }
    public boolean isPivotEnabled() { return this.pivotArmMotorEnabled; }
    public boolean isExtendEnabled() { return this.extendArmMotorEnabled; }
}
