package frc.robot.subsystems.leds;

import edu.wpi.first.wpilibj.util.Color;

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
    
       
    public LEDSubsystem() {
        leds = new AddressableLED(Constants.LEDS_PORT); // the port is 0
        buffer = new AddressableLEDBuffer(Constants.LEDS_NUMBER_OF_LEDS); // 32 LEDs in a straight line
        leds.setLength(Constants.LEDS_NUMBER_OF_LEDS);
        leds.start();
        setDefaultCommand(runPattern(LEDPattern.solid(Color.kBlack)).withName("Off"));
        }

        public void periodic() {
            LEDPattern pattern = LEDPattern.progressMaskLayer(Robot.map.leds::getProgress);
            pattern.applyTo(buffer);
            leds.setData(buffer); 
        }

    public Command runPattern(LEDPattern pattern) {
        return run(() -> pattern.applyTo(buffer));
    }

    public double progressOfLEDs = 0;
    // value of how many LEDs are on (0 = 0%, 1 = 100%)

    public double getProgress() {
        progressOfLEDs = Math.abs(Robot.driveController.getLeftX() + -Robot.driveController.getLeftY());
        return progressOfLEDs;
    }
}