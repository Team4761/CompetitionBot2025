package frc.robot.subsystems.swerve;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Robot;

public class ZeroGyroCommand extends Command {

    private ZeroGyroCommand() {}

    public static Command create() {
        return new ZeroGyroCommand();
    }

    @Override
    public void initialize() {
        Robot.map.swerve.zeroGyro();
    }

    @Override
    public boolean isFinished() {
        return true;
    }
    
}
