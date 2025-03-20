package frc.robot.subsystems.leds;

import static edu.wpi.first.units.Units.Percent;
import static edu.wpi.first.units.Units.Second;
import static edu.wpi.first.units.Units.Seconds;

import java.util.Map;
import frc.robot.subsystems.leds.StupidColor;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.units.measure.Distance;
import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.LEDPattern;
import edu.wpi.first.wpilibj.LEDPattern.GradientType;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Robot;


public class LEDSubsystem extends SubsystemBase {
    private AddressableLED leds;
    private AddressableLEDBuffer buffer;

    private LEDPattern previousPattern;
    private LEDPattern currentPattern;
        // Here's a bullshit list of colors that we can use for the LEDs
        static StupidColor[] LEDColorList = {
            new StupidColor(Color.kDenim),
            new StupidColor(Color.kFirstBlue),
            new StupidColor(Color.kFirstRed),
            new StupidColor(Color.kAliceBlue),
            new StupidColor(Color.kAqua),
            new StupidColor(Color.kAquamarine),
            new StupidColor(Color.kBeige),
            new StupidColor(Color.kBisque),
            new StupidColor(Color.kBlack),
            new StupidColor(Color.kBlanchedAlmond),
            new StupidColor(Color.kBlue),
            new StupidColor(Color.kBlueViolet),
            new StupidColor(Color.kBrown),
            new StupidColor(Color.kBurlywood),
            new StupidColor(Color.kCadetBlue),
            new StupidColor(Color.kChartreuse),
            new StupidColor(Color.kChocolate),
            new StupidColor(Color.kCoral),
            new StupidColor(Color.kCornflowerBlue),
            new StupidColor(Color.kCornsilk),
            new StupidColor(Color.kCrimson),
            new StupidColor(Color.kCyan),
            new StupidColor(Color.kDarkBlue),
            new StupidColor(Color.kDarkCyan),
            new StupidColor(Color.kDarkGoldenrod),
            new StupidColor(Color.kDarkGreen),
            new StupidColor(Color.kDarkKhaki),
            new StupidColor(Color.kDarkMagenta),
            new StupidColor(Color.kDarkOliveGreen),
            new StupidColor(Color.kDarkOrange),
            new StupidColor(Color.kDarkOrchid),
            new StupidColor(Color.kDarkRed),
            new StupidColor(Color.kDarkSalmon),
            new StupidColor(Color.kDarkSeaGreen),
            new StupidColor(Color.kDarkSlateBlue),
            new StupidColor(Color.kDarkTurquoise),
            new StupidColor(Color.kDarkViolet),
            new StupidColor(Color.kDeepPink),
            new StupidColor(Color.kDeepSkyBlue),

            //0x696969
            new StupidColor(Color.kDimGray),

            new StupidColor(Color.kDodgerBlue),
            new StupidColor(Color.kFirebrick),
            new StupidColor(Color.kForestGreen),
            new StupidColor(Color.kFuchsia),
            new StupidColor(Color.kGold),
            new StupidColor(Color.kGoldenrod),
            new StupidColor(Color.kGray),
            new StupidColor(Color.kGreen),
            new StupidColor(Color.kGreenYellow),
            new StupidColor(Color.kHotPink),
            new StupidColor(Color.kIndianRed),
            new StupidColor(Color.kIndigo),
            new StupidColor(Color.kKhaki),
            new StupidColor(Color.kLavender),
            new StupidColor(Color.kLavenderBlush),
            new StupidColor(Color.kLawnGreen),
            new StupidColor(Color.kLightBlue),
            new StupidColor(Color.kLightCoral),
            new StupidColor(Color.kLightGreen),
            new StupidColor(Color.kLightPink),
            new StupidColor(Color.kLightSalmon),
            new StupidColor(Color.kLightSeaGreen),
            new StupidColor(Color.kLightSkyBlue),
            new StupidColor(Color.kLightSteelBlue),
            new StupidColor(Color.kLime),
            new StupidColor(Color.kLimeGreen),
            new StupidColor(Color.kMagenta),
            new StupidColor(Color.kMaroon),
            new StupidColor(Color.kMediumAquamarine),
            new StupidColor(Color.kMediumBlue),
            new StupidColor(Color.kMediumOrchid),
            new StupidColor(Color.kMediumPurple),
            new StupidColor(Color.kMediumSeaGreen),
            new StupidColor(Color.kMediumSlateBlue),
            new StupidColor(Color.kMediumSpringGreen),
            new StupidColor(Color.kMediumTurquoise),
            new StupidColor(Color.kMediumVioletRed),
            new StupidColor(Color.kMidnightBlue),
            new StupidColor(Color.kMistyRose),
            new StupidColor(Color.kMoccasin),
            new StupidColor(Color.kNavajoWhite),
            new StupidColor(Color.kNavy),
            new StupidColor(Color.kOliveDrab),
            new StupidColor(Color.kOrange),
            new StupidColor(Color.kOrangeRed),
            new StupidColor(Color.kOrchid),
            new StupidColor(Color.kPaleGoldenrod),
            new StupidColor(Color.kPaleGreen),
            new StupidColor(Color.kPaleTurquoise),
            new StupidColor(Color.kPaleVioletRed),
            new StupidColor(Color.kPapayaWhip),
            new StupidColor(Color.kPeachPuff),
            new StupidColor(Color.kPeru),
            new StupidColor(Color.kPink),
            new StupidColor(Color.kPlum),
            new StupidColor(Color.kPowderBlue),
            new StupidColor(Color.kPurple),
            new StupidColor(Color.kRed),
            new StupidColor(Color.kRosyBrown),
            new StupidColor(Color.kRoyalBlue),
            new StupidColor(Color.kSaddleBrown),
            new StupidColor(Color.kSalmon),
            new StupidColor(Color.kSandyBrown),
            new StupidColor(Color.kSeaGreen),
            new StupidColor(Color.kSienna),
            new StupidColor(Color.kSkyBlue),
            new StupidColor(Color.kSlateBlue),
            new StupidColor(Color.kSpringGreen),
            new StupidColor(Color.kSteelBlue),
            new StupidColor(Color.kTan),
            new StupidColor(Color.kTeal),
            new StupidColor(Color.kTomato),
            new StupidColor(Color.kTurquoise),
            new StupidColor(Color.kViolet),
            new StupidColor(Color.kWheat),
            new StupidColor(Color.kWhite),
            new StupidColor(Color.kYellow),
            new StupidColor(Color.kYellowGreen)
            };
            
            int LEDColorIndex1 = (int)(Math.random() * (117 - 0 + 1) + 0);
            int LEDColorIndex2 = (int)(Math.random() * (117 - 0 + 1) + 0);
            StupidColor LEDColor1 = LEDColorList[LEDColorIndex1];
            StupidColor LEDColor2 = LEDColorList[LEDColorIndex2];

    // Also, per LED strip, there are 150 LEDs.
    // Supposedly the LEDs function in GRB not RGB... We'll need to test this though.
    /** Available LED patterns:
     * <p> green-black discontinuous gradient
     * <p> lights that move across the strip, and change to a random color when bounce of the edge
     * <p> LED patterns that aren't finished:
     * <p> lights that blink blanched almond when the robot is perfectly aligned in teleop (the only one with a debugging function)
     */
    public LEDSubsystem() {
        // Comment out patterns that aren't being used
        

        leds = new AddressableLED(Constants.LEDS_PORT);
        buffer = new AddressableLEDBuffer(Constants.LEDS_NUMBER_OF_LEDS); // 32 LEDs in a straight line
        leds.setLength(Constants.LEDS_NUMBER_OF_LEDS);
        leds.start();
        
        currentPattern = RobocketsLEDPatterns.OFF;
        previousPattern = RobocketsLEDPatterns.OFF;
    }


        /**
        public void periodic() {
            LEDPattern pattern = LEDPattern.solid(Color.kBlanchedAlmond);
            pattern.applyTo(buffer);
            leds.setData(buffer); 
        }


        
        // progress bar fills proportional to how much the move joystick is pushed (kinda)
        /**
        public void periodic() {
            LEDPattern pattern = LEDPattern.progressMaskLayer(Robot.map.leds::getProgress);
            pattern.applyTo(buffer);
            leds.setData(buffer); 
        }

    

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


        
        // 2 solid colors that move towards the side opposite from where they started. every time the colors bounce off a wall, the colors change to a new random one.
        // Filler
        // Currently it should create 2 groups of differnt colors, and have them move accross the strip and bounce off walls. (it does)
        // I hope you aren't afraid of unnecessary comments
        // Oh also I don't know how to blend the patterns, because the WPILib document contains jack-shit about it. So it doesnt actually make a pattern...
        // Update: I figured out how to blend them, but we actually need to overlay them instead (which I also figured out)
        // Here's another line just to make sure
        
        public static int LEDOffset = 0;
        public void periodic() {
            // colors always start as random
            LEDPattern rightBaseBase = LEDPattern.steps(Map.of(0, LEDColor1, 0.125, Color.kBlack));
            LEDPattern leftBase = LEDPattern.steps(Map.of( 0, LEDColor2, 0.125, Color.kBlack));
            LEDPattern rightBase = rightBaseBase.reversed();
            LEDPattern left = leftBase.offsetBy(LEDOffset);
            LEDPattern right = rightBase.offsetBy(-LEDOffset);
            LEDPattern pattern = left.overlayOn(right);
            pattern.applyTo(buffer);
            leds.setData(buffer);

            // moves the LEDs at a speed of 1 LEDs/tick
            // the strips doesn't actually "bounce" off the walls, they just return to their start positions. However, it looks like they are bouncing because of the color change.
            if (LEDOffset < 132) {
                LEDOffset++;                
            }
            else {
                LEDOffset = 0;
                LEDColorIndex1 = (int)(Math.random() * (117 - 0 + 1) + 0);
                LEDColorIndex2 = (int)(Math.random() * (117 - 0 + 1) + 0);
                LEDColor1 = LEDColorList[LEDColorIndex1];
                LEDColor2 = LEDColorList[LEDColorIndex2];
            }
        }


    public void setPattern(LEDPattern pattern) {
        previousPattern = currentPattern;
        currentPattern = pattern;
        pattern.applyTo(buffer);
        leds.setData(buffer);
    }


    /**
     * This gets the last pattern that was used. This starts out as RobocketsLEDPatterns.OFF
     * @return The previously used LED Pattern
     */
    public LEDPattern getPreviousPattern() {
        return previousPattern;
    }


    /**
     * This sets the LEDs to the "black" color (which turns them off)
     */
    public void stopLEDs() {
        LEDPattern off = LEDPattern.solid(Color.kBlack);
        off.applyTo(buffer);
        leds.setData(buffer);
    }
        


    // the command that aplies the pattern to the LEDs
    public Command runPattern(LEDPattern pattern) {
        return run(() -> setPattern(pattern));
    }
}