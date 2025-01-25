package frc.robot.subsystems.arm;

import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.Robot;


public class ExtendArm extends Command
 {
    private double time;
    private double extensionSpeed;

    public ExtendArm(double time, double extensionSpeed)
    {
        this.time = time;
        this.extensionSpeed = extensionSpeed;
    }
    @Override
    public void initialize()
    {
        withTimeout(time);
    }
    @Override
    public void execute()
    {
        Robot.map.arm.extend(extensionSpeed);
    }
}
    