package frc.robot.subsystems.muncher;

import com.revrobotics.spark.SparkFlex;
import com.revrobotics.spark.SparkMax;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

/**
 * <p> This is in charge of both intaking/outtaking coral ("munching" through the front "mouth") and yeeting/scoring/ejecting coral out the side.
 * <p> The intake motor is a NEO connected to a SparkMAX and the yeet motor is a NEO Vortex connected to a SparkFLEX.
 */
public class MuncherSubsystem extends SubsystemBase {

    /** A NEO where positive speed represents (intake?) */
    private SparkMax intakeMotor = new SparkMax(Constants.MUNCHER_INTAKE_MOTOR_PORT, SparkMax.MotorType.kBrushless);
    /** A NEO Vortex where positive speed represents (yeeting/outtake?) */
    private SparkFlex yeetMotor = new SparkFlex(Constants.MUNCHER_YEET_MOTOR_PORT, SparkFlex.MotorType.kBrushless);


    /**
     * Runs the intake/outtake at the given speed.
     * @param intakeSpeed A percent between -1 to 1 where 1 is full speed intake and -1 is full speed outtake.
     */
    public void intake(double intakeSpeed)
    {
        intakeMotor.set(intakeSpeed);
    }

    /**
     * Runs the yeet motor at the given speed. This pushes the "finger" of the yeeter, and the finger pushes the coral out of the side of the robot.
     * @param yeetSpeed A percent between -1 to 1 where 1 is full speed intake and -1 is full speed outtake.
     */
    public void yeet(double yeetSpeed)
    {
        yeetMotor.set(-yeetSpeed);
    }
}