package frc.robot.subsystems.win;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Robot;


// This subsystem is in charge of winning.
public class WinSubsystem extends SubsystemBase {
    public void win() {
        System.out.println("I CAN'T STOP WINNING!");
    }
    public void crash() {
        System.exit(0);
    }
}
