package frc.robot.subsystems.arm;

import com.ctre.phoenix6.StatusSignal;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.Robot;

public class moveArmCommand extends Command {
    public void moveArm(double angle, double extensionPercent) {
        // moves Arm until reaches proper position
    }
    @Override
    public void initialize() {
       
    }    
    @Override
    public boolean isFinished() {
        return false;
    }

}
