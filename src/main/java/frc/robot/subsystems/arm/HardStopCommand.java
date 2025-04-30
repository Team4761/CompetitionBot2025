package frc.robot.subsystems.arm;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.PrintCommand;
import frc.robot.Robot;

public class HardStopCommand extends Command {
    
    private HardStopCommand() {}

    public static Command create() {
        if (Robot.map.arm == null) {
            return new PrintCommand("Arm is null! Can't run HardStopCommand!");
        }
        else {
            return new HardStopCommand().withTimeout(2);
        }
    }

    @Override
    public void initialize() {
        Robot.map.arm.runHardStopMotor(0.2);
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean isInterrupted) {
        Robot.map.arm.runHardStopMotor(0.0);
    }
}
