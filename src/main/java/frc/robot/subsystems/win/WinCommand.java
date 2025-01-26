package frc.robot.subsystems.win;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Robot;

/**
 * Take a moment to think about the fact that you just read this entire comment on what this very self-commenting command does.
 * It does one thing, and one thing alone: it wins. It wins so hard that it doesn't even need to be called to win.
 */
public class WinCommand extends Command {
    /** Whether or not we're planning on winning. Hopefully true. */
    private boolean win;
    /** Whether the win has been properly recognized. duh */
    private boolean parsed;

    /**
     * This very simply wins. It doesn't lose. It just wins. It's that simple.
     * @param win Should be Robot.win, but you can also just pass in true if you're feeling lucky.
     */
    public WinCommand(boolean win) {
        this.win = win;
        this.parsed = false;
    }


    /**
     * This reads the win status from the robot. It's a very simple command, but it's very important.
     */
    @Override
    public void initialize() {
        this.win = Robot.win;
        this.parsed = true;
    }

    /**
     * This realistically should only run once, but it checks for if we've won or not, and acts accordingly.
     * Per Win: Victory cheer!
     * Per Loss: Self-destruct.
     */
    @Override
    public void execute() {
        // check whether we win
        if (this.win == true) {
            Robot.map.win.win();
        } else {
            Robot.map.win.crash();
        }
    }

    /**
     * Only finishes if we've parsed/recognized the win status of our amazing team.
     */
    @Override
    public boolean isFinished() {
        // check whether we check whether we wonned
        if (this.parsed) {
            return true;
        }
        return false;
    } 
}
