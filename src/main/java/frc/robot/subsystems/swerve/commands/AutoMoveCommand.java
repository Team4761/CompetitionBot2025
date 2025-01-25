package frc.robot.subsystems.swerve.commands;


import edu.wpi.first.wpilibj2.command.Command;

import frc.robot.Robot;

public class AutoMoveCommand extends Command {
    double speedForwards;
    double speedHorizontal; // unknown velocity speed, positive is left
    double speedTurn;
    double moveTime; // in seconds

    double endTime;
    public AutoMoveCommand(double speedForwards, double speedHorizontal, double speedTurn, double moveTime) {
        this.speedForwards = speedForwards;
        this.speedHorizontal = speedHorizontal;
        this.speedTurn = speedTurn;
        this.moveTime = moveTime;
    }
    @Override
    public void initialize() {
        // Run for moveTime seconds
        endTime = System.currentTimeMillis() + (moveTime * 1000);
    }

    @Override
    public void execute() {
        Robot.map.swerve.setDesiredSpeeds(speedForwards, speedHorizontal, speedTurn);
    }
    @Override
    public boolean isFinished() {
        if (System.currentTimeMillis() >= endTime) {
            return true;
        }
        return false;
    }

    public void end() {
        Robot.map.swerve.setDesiredSpeeds(0, 0, 0);
    }
}
