package frc.robot.subsystems.leds;

import edu.wpi.first.wpilibj.util.Color;

/**
 * Literally all this does is account for the fact that we're using GRB LEDs instead of RGB LEDs.
 */
public class StupidColor extends Color {

    private static double ledBrightness = 0.05;

    /**
     * Just use this like a normal Color, but when the code is sent off the to bot, the R & G channels will be switched.
     * RGB -> GRB
     * @param R 0-255
     * @param G 0-255
     * @param B 0-255
     */
    public StupidColor(int R, int G, int B) {
        this(R / 255.0, G / 255.0, B / 255.0);
    }


    /**
     * Just use this like a normal Color, but when the code is sent off the to bot, the R & G channels will be switched.
     * RGB -> GRB
     * @param R
     * @param G
     * @param B
     */
    private StupidColor(double R, double G, double B) {
        super(G*ledBrightness, R*ledBrightness, B*ledBrightness);
    }


    /**
     * Just use this like a normal Color, but when the code is sent off the to bot, the R & G channels will be switched.
     * RGB -> GRB
     * @param color
     */
    public StupidColor(Color color) {
        this(color.red, color.green, color.blue);
    }


    public static double getLEDBrightness() { return ledBrightness; }
    public static void setLEDBrightness(double ledBrightness) { StupidColor.ledBrightness = ledBrightness; }
}
