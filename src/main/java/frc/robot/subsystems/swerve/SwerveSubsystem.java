package frc.robot.subsystems.swerve;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

/**
 * For swerve, +x represents the forward direction and +y represents left.
 */
public class SwerveSubsystem extends SubsystemBase {
    
    // All as a number between -1 to 1 where 1 represents 100% speed forwards.
    private double desiredSpeedX = 0.0;
    private double desiredSpeedY = 0.0;
    private double desiredSpeedRotation = 0.0;


    /**
     * <p> This is going to make it constantly try to get to the desired set speeds.
     * <p> WIP
     */
    @Override
    public void periodic() {
        /* TODO: Make it get to the desired set speeds */
    }
}
