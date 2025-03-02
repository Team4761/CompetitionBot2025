package frc.robot.subsystems.leds;

import static edu.wpi.first.units.Units.Percent;
import static edu.wpi.first.units.Units.Second;
import static edu.wpi.first.units.Units.Seconds;

import java.util.Map;

import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.units.measure.Distance;
import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.LEDPattern;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Robot;


public class LEDSubsystem extends SubsystemBase {
    private AddressableLED leds;
    private AddressableLEDBuffer buffer;
    
    // LEDs will be a bit different (i'll send the announcement after robotics today (Feb 27)) -Alistair 
    // there was no announcement -Not Alistair
    /** what pattern should I use for LEDs?
     * <p> nominate your LED pattern below (keep in mind, this is a one-dimesional strip) Current nomintaions are:
     * <p> gradient
     * <p> 4761 in binary
     * <p> "Win" in morse code
     * <p> lights that cross over eachother in the middle, then the colors xor
     * <p> lights that blink blanched almond when the robot is perfectly aligned in teleop (the only one with a debugging function)
     */
    public LEDSubsystem() {
        // Comment out patterns that aren't being used

        

        
        leds = new AddressableLED(Constants.LEDS_PORT);
        buffer = new AddressableLEDBuffer(Constants.LEDS_NUMBER_OF_LEDS); // 32 LEDs in a straight line
        leds.setLength(Constants.LEDS_NUMBER_OF_LEDS);
        leds.start();
        setDefaultCommand(runPattern(LEDPattern.solid(Color.kBlack)).withName("Off"));
        }

        /**
        // progress bar fills proportional to how much the move joystick is pushed (kinda)
        public void periodic() {
            LEDPattern pattern = LEDPattern.progressMaskLayer(Robot.map.leds::getProgress);
            pattern.applyTo(buffer);
            leds.setData(buffer); 
        }

        public double progressOfLEDs = 0;
        // value of how many LEDs are on (0 = 0%, 1 = 100%)

        public double getProgress() {
            progressOfLEDs = Math.abs(Robot.driveController.getLeftX() + -Robot.driveController.getLeftY());
            return progressOfLEDs;
        }
        */



        // Green and black discontinuous gradient that scrolls at a quarter of its length per second
        /**
        public void periodic() {
            LEDPattern base = LEDPattern.gradient(LEDPattern.GradientType.kDiscontinuous, Color.kGreen, Color.kBlack);
            LEDPattern pattern = base.scrollAtRelativeSpeed(Percent.per(Second).of(25));
            pattern.applyTo(buffer);
            leds.setData(buffer); 
        }
        */


        // 4761 in binary
        // for a 13 bit integer, 4761 would be 1001010011001, and unused LEDs are red.
        // However, this is an odd number of digits, meaning that the amount of red on each side will be uneven.
        // So to prevent you from feeling the agony of choice, I have decided that the left side will have more red.
        /**
        public void periodic() {
            // this is the pattern, but it is offset to wrap the red around to the back, because BScode screams at me for repeating red. And ONLY red.
            LEDPattern base = LEDPattern.steps(Map.of(0, Color.kRed, 0.59375, Color.kWhite, 0.625, Color.kBlack, 0.6875, Color.kWhite, 0.71875, Color.kBlack, 0.75, Color.kWhite, 0.78125, Color.kBlack, 0.84375, Color.kWhite, 0.90625, Color.kBlack, 0.96875, Color.kWhite));
            LEDPattern pattern = base.offsetBy(-9);
            pattern.applyTo(buffer);
            leds.setData(buffer);
        }
        */


        // Win in morse code
        /**
        public void periodic() {
            // red is unused LEDs
            // 101101100101001101, 18 digits
            LEDPattern base = LEDPattern.steps(Map.ofEntries(
//              Map.entry(1, Color.kBlanchedAlmond); OMG IT'S BLANCHED ALMOND, THE BEST COLOR
                Map.entry(0, Color.kRed),
                Map.entry(0.4375, Color.kWhite),
                Map.entry(0.46875, Color.kBlack),
                Map.entry(0.5, Color.kWhite),
                Map.entry(0.5625, Color.kBlack),
                Map.entry(0.59375, Color.kWhite),
                Map.entry(0.65625, Color.kBlack),
                Map.entry(0.71875, Color.kWhite),
                Map.entry(0.75, Color.kBlack),
                Map.entry(0.78125, Color.kWhite),
                Map.entry(0.8125, Color.kBlack)));
            LEDPattern pattern = base.offsetBy(-7);
            pattern.applyTo(buffer);
            leds.setData(buffer);
        }
        */


        // 2 solid colors that move towards the side opposite from where they started. every time the colors bounce off a wall, the colors change to a new random one (super unfinished)
        /**
        public void periodic() {
            // colors always start as Red and White
            LEDPattern left = LEDPattern.steps(Map.of(0, Color.kRed, 0.125, Color.kBlack));
            LEDPattern rightBase = LEDPattern.steps(Map.of(0, Color.kWhite, 0.125, Color.kBlack));
            LEDPattern right = rightBase.reversed();
            LEDPattern pattern = null; //placeholder
            pattern.applyTo(buffer);
            leds.setData(buffer);
        }
        */


        // blinks Blanched Almond when the robot has successfully aligned with an april tag during teleop (unfinished)
        
        public void periodic() {
            LEDPattern base = LEDPattern.solid(Color.kBlanchedAlmond);
            LEDPattern pattern = base.blink(Seconds.of(0.5));
            pattern.applyTo(buffer);
            leds.setData(buffer);
        }
        


        // the command that aplies the pattern to the LEDs
        public Command runPattern(LEDPattern pattern) {
            return run(() -> pattern.applyTo(buffer));
        }
}