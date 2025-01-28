package frc.robot.subsystems.swerve.commands;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.units.measure.Distance;
import edu.wpi.first.wpilibj.DutyCycleEncoder;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.Robot;


//Command to move a set distance.
public class MoveDistance extends Command{


    double deltaX;
    double deltaY;
    Rotation2d deltaRot;

    /**
     * 
     * @param deltaX How far the x value is from where the robot is now in meters. Positive x is forward and negative is backward.
     * @param deltaY How far the y value is from where the robot is now in meters. Positive y is left and negative is right.
     * @param deltaRot How much the robot should rotate
     */
    private MoveDistance(Double deltaX, Double deltaY, Rotation2d deltaRot){
        this.deltaX = deltaX;
        this.deltaY = deltaY;
        this.deltaRot = deltaRot;
    }


    /**
     * <p> All that needs to be initialized.
     */
    public static Command create(Double deltaX, Double deltaY, Rotation2d deltaRot)
    {
        return new MoveDistance(deltaX, deltaY, deltaRot);
    }

    final ProfiledPIDController DistanceXPID = new ProfiledPIDController(
        1,
        0,
        0,
        new TrapezoidProfile.Constraints(Constants.SWERVE_MAX_DRIVE_SPEED, Constants.SWERVE_MAX_ANGULAR_ACCELERATION)
        );

        final ProfiledPIDController DistanceYPID = new ProfiledPIDController(
        1,
        0,
        0,
        new TrapezoidProfile.Constraints(Constants.SWERVE_MAX_DRIVE_SPEED, Constants.SWERVE_MAX_ANGULAR_ACCELERATION)
        );


        final ProfiledPIDController RotationPID = new ProfiledPIDController(
        3,
        0,
        0,
        new TrapezoidProfile.Constraints(Constants.SWERVE_MAX_DRIVE_SPEED, Constants.SWERVE_MAX_ANGULAR_ACCELERATION)
        );
        private DutyCycleEncoder flDriveEncoder = new DutyCycleEncoder(Constants.SWERVE_FL_ENCODER_PORT);
        private DutyCycleEncoder frDriveEncoder = new DutyCycleEncoder(Constants.SWERVE_FR_ENCODER_PORT);
        private DutyCycleEncoder blDriveEncoder = new DutyCycleEncoder(Constants.SWERVE_BL_ENCODER_PORT);
        private DutyCycleEncoder brDriveEncoder = new DutyCycleEncoder(Constants.SWERVE_BR_ENCODER_PORT);


    @Override
    public void initialize() {
        addRequirements(Robot.map.swerve); //first come, first swerve (-Slim Jim from 2024 competition bot)
        
        
        
    }
    
    public void execute() {
        
        final double flDriveSpeed = DistanceXPID.calculate(flDriveEncoder.get());
        final double frDriveSpeed = DistanceXPID.calculate(frDriveEncoder.get());
        final double blDriveSpeed = DistanceXPID.calculate(blDriveEncoder.get());
        final double brDriveSpeed = DistanceXPID.calculate(brDriveEncoder.get());

        
        
    }
}
