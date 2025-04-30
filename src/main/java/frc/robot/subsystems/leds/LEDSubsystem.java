package frc.robot.subsystems.leds;

import java.util.Map;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.AddressableLEDBufferView;
import edu.wpi.first.wpilibj.LEDPattern;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Robot;


public class LEDSubsystem extends SubsystemBase {
    private boolean enabled = true;

    private AddressableLED leds;
    private AddressableLEDBuffer buffer;

    private AddressableLEDBufferView frontRight;
    private AddressableLEDBufferView backRight;
    private AddressableLEDBufferView innerRight;
    private AddressableLEDBufferView outerRight;
    private AddressableLEDBufferView frontLeft;
    private AddressableLEDBufferView backLeft;
    private AddressableLEDBufferView innerLeft;
    private AddressableLEDBufferView outerLeft;

    private AddressableLEDBufferView rightTriangle;
    private AddressableLEDBufferView leftTriangle;

    // private AddressableLED rightSide;
    // private AddressableLEDBuffer rightBuffer;

    private LEDPattern previousPattern;
    private LEDPattern currentPattern;
    
    StupidColor idleColor = RobocketsColorPalette.getRandomColor();
    StupidColor nextIdleColor = RobocketsColorPalette.getRandomColor();

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
        buffer = new AddressableLEDBuffer(Constants.LEDS_NUMBER_OF_LEDS); // 200 LEDs in a not straight line
        backRight = new AddressableLEDBufferView(buffer, 0, 23);
        frontRight = new AddressableLEDBufferView(buffer, 24, 48).reversed();
        innerRight = new AddressableLEDBufferView(buffer, 49, 82).reversed();
        outerRight = new AddressableLEDBufferView(buffer, 83, 95);
        backLeft = new AddressableLEDBufferView(buffer, 96, 117);
        frontLeft = new AddressableLEDBufferView(buffer, 118, 142).reversed();
        innerLeft = new AddressableLEDBufferView(buffer, 143, 177);
        outerLeft = new AddressableLEDBufferView(buffer, 178, 198).reversed();

        rightTriangle = new AddressableLEDBufferView(buffer, 0,48);
        leftTriangle = new AddressableLEDBufferView(buffer, 96,142);

        leds.setLength(Constants.LEDS_NUMBER_OF_LEDS);
        leds.start();
        
        currentPattern = RobocketsLEDPatterns.OFF;
        previousPattern = RobocketsLEDPatterns.OFF;
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

    
    // Use scrollAtRelativeSpeed(Percent.per(Second).of(25)) to scroll smoothly at a length of 25% per second
    public static int LEDOffset = 0;

    @Override
    public void periodic() {
        updateLEDs();
        // should constantly update the offset of the LEDs
        if (LEDOffset < Constants.LEDS_NUMBER_OF_LEDS) {
            LEDOffset++;                
        }
        else {
            LEDOffset = 0;
            idleColor = nextIdleColor;
            nextIdleColor = RobocketsColorPalette.getRandomColor();
        }
    }


    /**
     * This handles which LED pattern should be displayed at what time.
     */
    public void updateLEDs() {
        switch (Robot.ledState) {
            case IDLE: {
                runSnakes();
            }
            case AUTO_ARM: {
                if (Robot.map.arm != null) {
                    runArmAlign(Robot.map.arm.getTargetState().getPivotRotation().minus(Robot.map.arm.getPivotRotation()));
                }
            }
            case AUTO_ALIGN: {

            }
        }
    }


    /**
     * Sets the current pattern of the LEDs and saves the last used pattern.
     * @param pattern The pattern to apply to ALL of the LEDs
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

    /**
     * This is mainly used by the dashboard to enable or disable all the LEDs
     * @return
     */
    public boolean isEnabled() {
        return enabled;
    }

    /**
     * Changes whether the LEDs should be turned on (used by the dashboard)
     */
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    /**
     * PATTERNS
     */

    /**
     * Sets it to a rainbow gradient, but only from side to side of the robot.
     */
    public void runRainbow() {
        RobocketsLEDPatterns.RED.applyTo(outerRight);
        RobocketsLEDPatterns.ORANGE.applyTo(innerRight);
        RobocketsLEDPatterns.YELLOW.applyTo(backRight);
        RobocketsLEDPatterns.YELLOW.applyTo(frontRight);
        RobocketsLEDPatterns.GREEN.applyTo(backLeft);
        RobocketsLEDPatterns.GREEN.applyTo(frontLeft);
        RobocketsLEDPatterns.BLUE.applyTo(innerLeft);
        RobocketsLEDPatterns.PURPLE.applyTo(outerLeft);
        leds.setData(buffer);
    }


    /**
     * This is our snake animation
     */
    public void runSnakes() {
        Color color = idleColor;
        if (LEDOffset >= Constants.LEDS_NUMBER_OF_LEDS-20) {
            color = new Color(
                MathUtil.clamp(idleColor.red + (nextIdleColor.red - idleColor.red)/20.0 * (Constants.LEDS_NUMBER_OF_LEDS-LEDOffset),0,255),
                MathUtil.clamp(idleColor.green + (nextIdleColor.green - idleColor.green)/20.0 * (Constants.LEDS_NUMBER_OF_LEDS-LEDOffset),0,255),
                MathUtil.clamp(idleColor.blue + (nextIdleColor.blue - idleColor.blue)/20.0 * (Constants.LEDS_NUMBER_OF_LEDS-LEDOffset),0,255)
            );
        }
        LEDPattern snakeOne = LEDPattern.steps(Map.of( 0, color, 0.125, Color.kBlack)).offsetBy(LEDOffset);
        LEDPattern snakeTwo = LEDPattern.steps(Map.of( 0, color, 0.125, Color.kBlack)).offsetBy((LEDOffset+50)%Constants.LEDS_NUMBER_OF_LEDS);
        LEDPattern snakeThree = LEDPattern.steps(Map.of( 0, color, 0.125, Color.kBlack)).offsetBy((LEDOffset+100)%Constants.LEDS_NUMBER_OF_LEDS);
        LEDPattern snakeFour = LEDPattern.steps(Map.of( 0, color, 0.125, Color.kBlack)).offsetBy((LEDOffset+150)%Constants.LEDS_NUMBER_OF_LEDS);
        LEDPattern snake = snakeOne.overlayOn(snakeTwo).overlayOn(snakeThree).overlayOn(snakeFour);
        snake.applyTo(buffer);
        leds.setData(buffer);
    }


    /**
     * This will fill in based on how far away we are from the set point.
     * @param distanceFromTarget should be equal to target-current (+ or - doesn't matter)
     */
    public void runArmAlign(Rotation2d distanceFromTarget) {
        // Red -> Orange -> Green
        // Red when > 10 degrees away
        // Orange until at set point
        double distance = Math.abs(distanceFromTarget.getDegrees());
        if (distance >= 10) {
            LEDPattern slowFillUpSides = LEDPattern.steps(Map.of(0, new StupidColor(255,0,0), MathUtil.clamp(distance/10.0,0,1), new StupidColor(0,0,0)));
            slowFillUpSides.applyTo(frontLeft);
            slowFillUpSides.applyTo(frontRight);
            slowFillUpSides.applyTo(backLeft);
            slowFillUpSides.applyTo(backRight);
        }
        else if (distance >= 1.5) {
            LEDPattern orangish = LEDPattern.solid(new StupidColor(255-(int)MathUtil.clamp((10-distance)*10,0,100), (int)MathUtil.clamp((10-distance)*10,0,100), 0));
            orangish.applyTo(frontLeft);
            orangish.applyTo(frontRight);
            orangish.applyTo(backLeft);
            orangish.applyTo(backRight);
        }
        else {
            LEDPattern green = LEDPattern.solid(new StupidColor(60, 255, 30));
            green.applyTo(frontLeft);
            green.applyTo(frontRight);
            green.applyTo(backLeft);
            green.applyTo(backRight);
        }
    }
}