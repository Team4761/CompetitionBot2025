package frc.robot.subsystems.muncher;

import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

/**
 * This is in charge of both "munching" coral (intaking through the front "mouth") and outtaking/scoring coral.
 */
public class MuncherSubsystem extends SubsystemBase {
    private TalonFX intakeMotor = new TalonFX(Constants.MUNCHER_INTAKE_MOTOR_PORT);
    private TalonFX yeetMotor = new TalonFX(Constants.MUNCHER_EJECT_MOTOR_PORT);
    public void intake(double intakeSpeed)
    {
        intakeMotor.set(intakeSpeed);
    }
    public void yeet(double yeetSpeed)
    {
        yeetMotor.set(yeetSpeed);
    }
}