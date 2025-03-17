package frc.robot.controllers;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.Constants;
import frc.robot.Robot;
import frc.robot.subsystems.arm.ArmState;
import frc.robot.subsystems.muncher.YeetCommand;

/**
 * This will control both the arm AND the muncher/outtake.
 * TODO: consider using CommandXBoxController for built in eventHandler conveniences, e.g
 * rightBumper().onTrue(YeetCommand.create())
 */
public class ArmController extends XboxController {
    private Command currentCommand = null;

    private boolean armManualControl = false;
    private boolean extendArmMotorEnabled = true;
    private boolean pivotArmMotorEnabled = true;

    private double intakeSpeed = 0.40;
    private double outtakeSpeed = 0.2;
    private double yeetSpeed = 0.2;

    private double extendSpeed = 0.;
    private double pivotSpeed = 0.25;

    private boolean invertPivot = true;
    private boolean invertExtend = true;

    public boolean sendingRawInput = true;

    public boolean runningCommand = false;
    private long cooldown = 0;
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
        if (currentCommand != null) {
            runningCommand = true;
        }

        // Muncher
        if (Robot.map.muncher != null) {
            // Yeet
            if (!YeetCommand.autoMunchMode) {
                // Manual yeet (to be removed)
                if (getBButton()) {
                    Robot.map.muncher.yeet(intakeSpeed);
                }
                if (getXButton()) {
                    Robot.map.muncher.yeet(-0.1);
                }
                if (!getXButton() && !getBButton()) {
                    Robot.map.muncher.yeet(0);
                }
            }
            // Schedule an auto yeet
            if (getYButtonPressed() && Robot.map.arm != null && Robot.map.arm.getExtensionLength() >= 0.10) {
                CommandScheduler.getInstance().schedule(YeetCommand.create(true));
            }
            if (getAButtonPressed() && Robot.map.arm != null && Robot.map.arm.getExtensionLength() >= 0.10) {
                CommandScheduler.getInstance().schedule(YeetCommand.create(false));
            }
            // Intake/outake
            if (!YeetCommand.autoMunchMode)
                Robot.map.muncher.intake(-outtakeSpeed*getLeftTriggerAxis() + intakeSpeed*getRightTriggerAxis());
        }
        // Arm
        // Operator control (maintain pivot rotation)
        if (Robot.map.arm != null) {
            if (getLeftY() != 0.0 || getRightY() != 0.0) {
                Robot.map.arm.isOperatorMode = true;
                cancelCurrentCommand();
            }
            if (getLeftY() != 0.0) {
                Robot.map.arm.lastRotation = Robot.map.arm.getPivotRotation();
                sendingRawInput = true;
            }
            else {
                sendingRawInput = false;
            }

            // Manual control
            if(armManualControl)
            {
                Robot.map.arm.rotate((invertPivot ? -1 : 1) * pivotSpeed*getLeftY());
                Robot.map.arm.extend((invertExtend ? -1 : 1) * extendSpeed*getRightY());
            }
            // Feedforward and manual control essentially
            else if (!armManualControl)
            {
                Robot.map.arm.setForcedRotation(new Rotation2d((invertPivot ? -1 : 1) * pivotSpeed*getLeftY()));
                Robot.map.arm.setForcedExtension((invertPivot ? -1 : 1) * pivotSpeed*getRightY());
            }

            if (getStartButtonPressed()) {
                Robot.map.arm.setExtensionOffset(Robot.map.arm.getExtensionLength());
            }

            if (getLeftBumperButtonPressed()) {
                // scheduleCommand(GetArmToPositionCommand.create(Constants.CORAL_STATION_ARM_STATE));
                setArmState(Constants.CORAL_STATION_ARM_STATE);
            }

            if (getRightBumperPressed()) {
                // scheduleCommand(GetArmToPositionCommand.create(Constants.GROUND_INTAKE_ARM_STATE));
                setArmState(Constants.GROUND_INTAKE_ARM_STATE);
            }
            if (getPOV() == 0 && cooldown <= System.currentTimeMillis()) {
                setArmState(Constants.L3_ARM_STATE);
                // scheduleCommand(GetArmToPositionCommand.create(Constants.L3_ARM_STATE));
            }
            else if (getPOV() == 90 && cooldown <= System.currentTimeMillis()) {
                setArmState(Constants.L1_ARM_STATE);
                // scheduleCommand(GetArmToPositionCommand.create(Constants.L1_ARM_STATE));
            }
            // else if (getPOV() == 270 && currentCommand == null) {
            //     scheduleCommand(GetArmToPositionCommand.create(Constants.L4_ARM_STATE));
            // }
            else if (getPOV() == 180 && cooldown <= System.currentTimeMillis()) {
                // scheduleCommand(GetArmToPositionCommand.create(Constants.L2_ARM_STATE));
                setArmState(Constants.L2_ARM_STATE);
            }
        }
    }


    public void cancelCurrentCommand() {
        if (currentCommand != null) {
            CommandScheduler.getInstance().cancel(currentCommand);
            currentCommand = null;
        }
    }


    public void scheduleCommand(Command command) {
        cooldown = System.currentTimeMillis() + 1000;
        runningCommand = false;
        cancelCurrentCommand();
        currentCommand = command;
        CommandScheduler.getInstance().schedule(currentCommand);
    }

    public void setArmState(ArmState armState) {
        Robot.map.arm.setState(armState);
        Robot.map.arm.isOperatorMode = false;
    }


    // Overrides to apply a deadband to axis
    @Override
    public double getLeftY() {
        if (super.getLeftY() < 0) 
            return MathUtil.applyDeadband(super.getLeftY(), 0.08);
        else
            return MathUtil.applyDeadband(super.getLeftY(), 0.08);
    }
    @Override
    public double getRightY() {
        return MathUtil.applyDeadband(super.getRightY(), 0.08);
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
