package frc.robot.subsystems.leds;

import edu.wpi.first.wpilibj.LEDPattern;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Robot;

public class DisplayLEDPatternCommand extends Command {

    private LEDPattern pattern;
    private LEDPattern previousPattern = RobocketsLEDPatterns.OFF;
    
    private DisplayLEDPatternCommand(LEDPattern pattern, LEDPattern endingPattern) {
        this.pattern = pattern;
        this.previousPattern = endingPattern;
    }

    
    public static Command create(LEDPattern pattern, double durationSeconds) {
        return new DisplayLEDPatternCommand(pattern, null).withTimeout(3);
    }


    public static Command create(LEDPattern pattern, LEDPattern endingPattern, double durationSeconds) {
        return new DisplayLEDPatternCommand(pattern, endingPattern).withTimeout(3);
    }


    @Override
    public void initialize() {
        addRequirements(Robot.map.leds);

        Robot.map.leds.setPattern(pattern);

        if (previousPattern == null)
            this.previousPattern = Robot.map.leds.getPreviousPattern();
    }


    @Override
    public void end(boolean isInterrupted) {
        Robot.map.leds.setPattern(previousPattern);
    }
}
