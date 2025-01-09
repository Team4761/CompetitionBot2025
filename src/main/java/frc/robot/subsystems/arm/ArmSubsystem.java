package frc.robot.subsystems.arm;

import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.math.geometry.Translation2d;
import frc.robot.Constants;

/**
 * For the arm, +x represents forwards and +y represents up. Why not +z? Well, Translation2d uses x,y and I'm too lazy to make my own.
 * This includes both the pivot motor AND the extension motor as well as 1 absolute encoder on each motor.
 */
public class ArmSubsystem {
    // +x represents forwards and +y represents up.
    // 0,0 is currently undecided (we need the design of the arm before we decide.)
    private Translation2d desiredPosition;

    // Both are Krakens
    private TalonFX pivotMotor = new TalonFX(Constants.ARM_PIVOT_ENCODER_PORT);
    private TalonFX extendMotor = new TalonFX(Constants.ARM_EXTEND_MOTOR_PORT);
    // TODO: Add encoders
}
