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
    // Also, per LED strip, there are 150 LEDs.
    // Supposedly the LEDs function in GRB not RGB... We'll need to test this though.
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


        int LEDColorIndex1 = (int)Math.random() * LEDColorList.length;
        int LEDColorIndex2 = (int)Math.random() * LEDColorList.length;
        StupidColor LEDColor1 = LEDColorList[LEDColorIndex1];
        StupidColor LEDColor2 = LEDColorList[LEDColorIndex2];
        

        leds = new AddressableLED(Constants.LEDS_PORT);
        buffer = new AddressableLEDBuffer(Constants.LEDS_NUMBER_OF_LEDS); // 32 LEDs in a straight line
        leds.setLength(Constants.LEDS_NUMBER_OF_LEDS);
        leds.start();
        
        currentPattern = RobocketsLEDPatterns.OFF;
        previousPattern = RobocketsLEDPatterns.OFF;
    }
    public static void main(String[] args) {
        System.out.println(LEDColorList[0]);
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

    /**
    // progress bar fills proportional to how much the move joystick is pushed (kinda)

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
            Map.entry(1, Color.kBlanchedAlmond); OMG IT'S BLANCHED ALMOND, THE BEST COLOR
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

        
        // 2 solid colors that move towards the side opposite from where they started. every time the colors bounce off a wall, the colors change to a new random one. (super unfinished)
        // WARNING: I DO NOT KNOW HOW TO CODE WELL, AND THIS MAY BE COMPLETELY WRONG IN MULTIPLE WAYS. If this is wrong in a massive way, please yell at me for it next time you see me.
        // Filler
        // Currently it should create 2 groups of differnt colors, and have them move accross the strip and bounce off walls.
        // I hope you aren't afraid of unnecessary comments
        // Oh also I don't know how to blend the patterns, because the WPILib document contains jack-shit about it. So it doesnt actually make a pattern...
        // Here's another line just to make sure
        
        public int LEDOffset = 0;
        public void periodic() {
            // colors always start as random
            LEDPattern rightBaseBaseBase = LEDPattern.steps(Map.of(0, Color.kWhite, 0.125, Color.kBlack));
            LEDPattern leftBaseBase = LEDPattern.steps(Map.of(0, Color.kRed, 0.125, Color.kBlack));
            LEDPattern rightBaseBase = rightBaseBaseBase.reversed();
            LEDPattern leftBase = leftBaseBase.offsetBy(LEDOffset);
            LEDPattern rightBase = rightBaseBase.offsetBy(LEDOffset);
            LEDPattern left = leftBase;
            LEDPattern Right = rightBase;
            LEDPattern pattern = null; //placeholder
            pattern.applyTo(buffer);
            leds.setData(buffer);
        }
            
         

        // this should constantly move the offset. I can't test this at the moment, so it will probably be wrong.
        /**
        public int LEDOffset1() {
            int direction = 0;
            if (LEDOffset < 28) {
                if(direction == 0) {
                    LEDOffset = LEDOffset++;
                }
            }
            else {
                direction = 1;
            }
            if (LEDOffset > 0) {
                if (direction == 1) {
                    LEDOffset = LEDOffset--;
                }
            }
            else {
                direction = 0;
            }
            return LEDOffset;
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

    // // 2 solid colors that move towards the side opposite from where they started. every time the colors bounce off a wall, the colors change to a new random one. (super unfinished)
    // // WARNING: I DO NOT KNOW HOW TO CODE WELL, AND THIS MAY BE COMPLETELY WRONG IN MULTIPLE WAYS. If this is wrong in a massive way, please yell at me for it next time you see me.
    // // Filler
    // // Currently it should create 2 groups of differnt colors, and have them move accross the strip and bounce off walls.
    // // I hope you aren't afraid of unnecessary comments
    // // Oh also I don't know how to blend the patterns, because the WPILib document contains jack-shit about it. So it doesnt actually make a pattern...
    // // Here's another line just to make sure
    // public int LEDOffset = 0;
    // public void periodic() {
    //     // colors always start as Red and White
    //     LEDPattern leftBase = LEDPattern.steps(Map.of(0, Color.kRed, 0.125, Color.kBlack));
    //     LEDPattern rightBaseBase = LEDPattern.steps(Map.of(0, Color.kWhite, 0.125, Color.kBlack));
    //     LEDPattern rightBase = rightBaseBase.reversed();
    //     LEDPattern left = leftBase.offsetBy(LEDOffset);
    //     LEDPattern right = rightBase.offsetBy(LEDOffset);
    //     LEDPattern pattern = null; //placeholder
    //     pattern.applyTo(buffer);
    //     leds.setData(buffer);
    // }

    // // this should constantly move the offset. I can't test this at the moment, so it will probably be wrong.
    // public int LEDOffset1() {
    //     int direction = 0;
    //     if (LEDOffset < 28) {
    //         if(direction == 0) {
    //             LEDOffset = LEDOffset + 1;
    //         }
    //     }
    //     else {
    //         direction = 1;
    //     }
    //     if (LEDOffset > 0) {
    //         if (direction == 1) {
    //             LEDOffset = LEDOffset - 1;
    //         }
    //     }
    //     else {
    //         direction = 0;
    //     }
    //     return LEDOffset;
    // }
}