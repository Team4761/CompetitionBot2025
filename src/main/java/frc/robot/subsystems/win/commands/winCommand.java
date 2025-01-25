package frc.robot.subsystems.win.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Robot;

public class winCommand extends Command {
    boolean win;
    boolean parsed;

    public winCommand(boolean win) {
        this.win = false;
        this.parsed = false;
    }

    @Override
    public void initialize() {
        this.win = Robot.win;
        this.parsed = true;
    }  
    @Override
    public void execute() {
        // check whether we win
        if (this.win == true) {
            Robot.map.win.win();
        } else {
            Robot.map.win.crash();
        }
    }
    @Override
    public boolean isFinished() {
        // check whether we check whether we wonned
        if (this.parsed) {
            return true;
        }
        return false;
    } 
}
