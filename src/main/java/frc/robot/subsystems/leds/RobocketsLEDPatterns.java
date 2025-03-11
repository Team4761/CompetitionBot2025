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
}
