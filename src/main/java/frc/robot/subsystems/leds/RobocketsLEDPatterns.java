package frc.robot.subsystems.leds;

import edu.wpi.first.wpilibj.LEDPattern;
import edu.wpi.first.wpilibj.LEDPattern.GradientType;
import edu.wpi.first.wpilibj.util.Color;

/**
 * This stores all of our LED patterns.
 */
public class RobocketsLEDPatterns {
    
    public static final LEDPattern OFF = LEDPattern.solid(Color.kBlack);
    public static final LEDPattern RAINBOW_GRADIENT = LEDPattern.gradient(GradientType.kContinuous, new StupidColor(255,0,0), new StupidColor(255, 255, 0), new StupidColor(0,255,0), new StupidColor(0, 255, 255), new StupidColor(0,0,255), new StupidColor(255,0,255));
    // This line was wrtiien by Blake and it is the best line of code on  the robot
    public static final LEDPattern BLAKE_GRADIENT = LEDPattern.gradient(GradientType.kContinuous, new StupidColor(255,0,0), new StupidColor(255,255,255), new StupidColor(255,0,0), new StupidColor(0,0,0));
    // This line was not Blake and is probably going to destroy everything
    public static  final LEDPattern RED = LEDPattern.solid(new StupidColor(255,0,0));
    public static  final LEDPattern ORANGE = LEDPattern.solid(new StupidColor(255,125,0));
    public static  final LEDPattern YELLOW = LEDPattern.solid(new StupidColor(255,255,0));
    public static  final LEDPattern GREEN = LEDPattern.solid(new StupidColor(0,255,0));
    public static  final LEDPattern BLUE = LEDPattern.solid(new StupidColor(0,100,255));
    public static  final LEDPattern PURPLE = LEDPattern.solid(new StupidColor(255,0,255));
}
