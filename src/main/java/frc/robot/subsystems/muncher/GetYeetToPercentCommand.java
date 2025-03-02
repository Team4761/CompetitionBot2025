package frc.robot.subsystems.muncher;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Robot;

public class GetYeetToPercentCommand extends Command {

    private double targetPercent;   // 0 to 1
    private double speed;   // -1 to 1
    private boolean pushingOutwards = false; // This is determined in the initialize function
    
    private GetYeetToPercentCommand(double targetPercent, double speed) {
        this.targetPercent = targetPercent;
        this.speed = speed;
    }


    public static Command create(double targetPercent, double speed) {
        return new GetYeetToPercentCommand(targetPercent, speed);
    }


    @Override
    public void initialize() {
        if (Robot.map.muncher.getPercentOfYeet() < targetPercent) {
            Robot.map.muncher.yeet(speed);
            pushingOutwards = true;
        }
        else {
            Robot.map.muncher.yeet(-speed);
            pushingOutwards = false;
        }
    }


    @Override
    public boolean isFinished() {
        if (pushingOutwards) {
            return (Robot.map.muncher.getPercentOfYeet() >= targetPercent);
        }
        else {
            return (Robot.map.muncher.getPercentOfYeet() <= targetPercent);
        }
    }


    @Override
    public void end(boolean isInterrupted) {
        Robot.map.muncher.yeet(0);
    }
}
