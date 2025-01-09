package frc.robot.subsystems.leds;

import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class LEDSubsystem extends SubsystemBase {
    
    private AddressableLED leds;
    private AddressableLEDBuffer buffer;


    public LEDSubsystem() {
        leds = new AddressableLED(Constants.LEDS_PORT);
        buffer = new AddressableLEDBuffer(Constants.LEDS_NUMBER_OF_LEDS);

        leds.setLength(buffer.getLength());
        leds.start();
    }
}
