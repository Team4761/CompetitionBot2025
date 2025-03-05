package frc.robot.subsystems.muncher;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Robot;

public class WaitUntilYeetAtPercentCommand extends Command {

    private double percent;
    private boolean yeetingOutwards;

    private WaitUntilYeetAtPercentCommand(double percent) {
        this.percent = percent;
    }

    public static Command create(double percent) {
        return new WaitUntilYeetAtPercentCommand(percent);
    }


    @Override
    public void initialize() {
        if (Robot.map.muncher.getPercentOfYeet() < percent) {
            yeetingOutwards = true;
        }
        else {
            yeetingOutwards = false;
        }
    }


    @Override
    public boolean isFinished() {
        if (yeetingOutwards) {
            return (percent <= Robot.map.muncher.getPercentOfYeet());
        }
        else {
            return (percent >= Robot.map.muncher.getPercentOfYeet());
        }
    }
    
}
