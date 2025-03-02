package frc.robot.subsystems.arm;

import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.DutyCycleEncoder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Robot;

/**
 * <p> For the arm, +x represents forwards and +y represents up. Why not +z? Well, Pose2d uses x,y and I'm too lazy to make my own.
 * <p> This includes both the pivot motor AND the extension motor as well as 1 absolute encoder on each motor.
 */
public class ArmSubsystem extends SubsystemBase {


    /** This is the really funky gear ratio of the kraken motor we have hooked up to the pivot. 11 teeth turn 56 teeth, connected to 18 teeth turning 56 teeth... etc */
    private static final double ARM_PIVOT_KRAKEN_UNITS_TO_RADIANS = (2.0*Math.PI*(11.0/56.0)*(18.0/56.0)*(16.0/56.0));

    /**
     * Sadly, there is no easy way to zero the encoders. Therefore, the best we can do is have an offset.
     */
    /** To determine this, move the arm into the (0,0) state which is being in front of the robot, parallel to the ground and record the value of the absolute encoder read in the dashboard. */
    private static final Rotation2d PIVOT_ENCODER_OFFSET = new Rotation2d(Units.degreesToRadians(154.50));
    /** Meters. To determine this, move the arm into the (0,0) state which is unextended and record the value of the absolute encoder read in the dashboard. */
    private static final double EXTENSION_ENCODER_OFFSET = 0.0;

    // This is the conversion factor from encoder units to radians for the pivot absolute encoder.
    // I believe the encoders are 1 unit per rotation, but because of physics (curse you Newton), it's not exact.
    // Therefore, after measuring the actual rotation versus the measured rotation, I found the black magic number below (currently TBD)
    private static final double PIVOT_ENCODER_UNITS_TO_RADIANS = (Math.PI*2) * 1.000;

    // This is the conversion factor from encoder units to meters for the extend absolute encoder.
    // I believe the encoders are 1 unit per rotation, but because of physics (curse you Newton), it's not exact.
    // Therefore, after measuring the actual rotation versus the measured rotation, I found the black magic number below (currently TBD)
    // The math for this is [...]
    private static final double EXTENSION_ENCODER_UNITS_TO_METERS = 1.000;

    private boolean usingSetpointSystem = false;
    public Rotation2d lastRotation = new Rotation2d();

    public boolean isOperatorMode = true;
    // The forced stuff only matters if usingSetpointSystem is false.
    private Rotation2d forcedRotation = new Rotation2d(0);
    private double forcedExtension = 0; // Meters

    // Because of belt skipping, we need to give the operators the ability to tell the robot it's at an extension of 0.
    private double extensionOffset = 0.0;


    // +x represents forwards and +y represents up.
    // 0,0 is currently undecided (we need the design of the arm before we decide.)
    private ArmState targetState = new ArmState();
    
    // Both are Krakens
    private static TalonFX pivotMotor = new TalonFX(Constants.ARM_PIVOT_MOTOR_PORT);
    private static TalonFX extendMotor = new TalonFX(Constants.ARM_EXTEND_MOTOR_PORT);
        
    // Absolute Encoders (their readings persist even after turning the robot off)
    // Both encoders are REV Through Bore Encoders (https://www.revrobotics.com/rev-11-1271/)
    // WPILib counts these as DutyCycleEncoders (https://docs.wpilib.org/en/stable/docs/software/hardware-apis/sensors/encoders-software.html)
    private DutyCycleEncoder pivotEncoder = new DutyCycleEncoder(Constants.ARM_PIVOT_ENCODER_PORT);
    private DutyCycleEncoder extendEncoder = new DutyCycleEncoder(Constants.ARM_EXTEND_ENCODER_PORT);

    // PID controllers for the arm
    FancyArmFeedForward ff = new FancyArmFeedForward();
    // TODO: Tune these controllers!
    private ProfiledPIDController pivotPID = new ProfiledPIDController(
        0.13,
        0,
        0,
        new TrapezoidProfile.Constraints(Constants.ARM_MAX_ANGULAR_VELOCITY, Constants.ARM_MAX_ANGULAR_ACCELERATION)
    );

    private ProfiledPIDController extensionPID = new ProfiledPIDController(
        0.2,
        0,
        0,
        new TrapezoidProfile.Constraints(Constants.ARM_MAX_EXT_VELOCITY, Constants.ARM_MAX_EXT_ACCELERATION)
    );

    private double maxFeedForward = 0.31;


    /**
     * <p> This will constantly try to get the arm to the desired position.
     */
    @Override
    public void periodic() {
        if(!Robot.armController.isArmManualControl()){
            // [pivotDirection, extensionLength]
            // Should we have made our own data type called ArmConfiguration? Maybe. But we didn't, and it's fine... for now...
            double pivotSpeed = 0.0;
            double extensionSpeed = 0.0;
            double pivotFeedForward = 0.0;
            if (isOperatorMode) {
                pivotSpeed = forcedRotation.getRadians();
                extensionSpeed = forcedExtension;
                // This is the MINUMUM speed required to keep the arm upright to the current target position (counteracting gravity)
                // Currently, this does not take into account the arm going past 90 degrees (which is a no-no)
                // The cos(angle) is used to make it take less force as it's more upright (think about how it should kinda balance when it's fully upright, therefore requiring no motor force)
                // After "testing", I found that it should only take like 0.015 speed to keep the arm upright when it's not extended and 0.32 when extended.
                if (!Robot.armController.sendingRawInput && getPivotRotation().getDegrees() <= lastRotation.getDegrees()+2) {
                    pivotFeedForward =
                        Math.cos(lastRotation.getRadians()) * // This goes from 1.0 at 0 degrees to 0.0 at 90 degrees
                        (getExtensionLength()/ArmState.MAX_EXTENSION_LENGTH * maxFeedForward + 0.015);
                }
            }
            // If not in Operator Mode, we use PID control. 
            else {
                pivotSpeed = pivotPID.calculate(getPivotRotation().getRadians(), targetState.getPivotRotation().getRadians());
                extensionSpeed = extensionPID.calculate(getExtensionLength(), targetState.getExtensionLength());
                pivotFeedForward = 
                    Math.cos(targetState.getPivotRotation().getRadians()) * // This goes from 1.0 at 0 degrees to 0.0 at 90 degrees
                    (targetState.getExtensionLength()/ArmState.MAX_EXTENSION_LENGTH * maxFeedForward + 0.015);
            }

            SmartDashboard.putNumber("Arm PID Pivot Speed", pivotSpeed);
            SmartDashboard.putNumber("Arm PID Extension Speed", extensionSpeed);
            SmartDashboard.putNumber("Arm Target Angle", Units.degreesToRadians(targetState.getPivotRotation().getDegrees()));
            SmartDashboard.putNumber("Arm Target Extension", Units.degreesToRadians(targetState.getExtensionLength()));

            SmartDashboard.putNumber("Arm Extension Encoder Units", extendMotor.getPosition().getValueAsDouble());
            SmartDashboard.putNumber("Arm FF", pivotFeedForward);
            if (isPivotEncoderConnected()) {
                rotate(MathUtil.clamp(pivotSpeed+pivotFeedForward,-0.5,0.5));
            }
            extend(extensionSpeed);
        }
        // If we are in manual control, the armController in Robot.java will handle the motors.
    }


    /**
     * <p> Takes in a point in 2D space and returns the arm configuration to reach it. Outputs {-1, -1} if it is unreachable.
     * <p> (0,0) represents the base of the arm.
     * @param x The X value of the point to be reached where +x is the forwards direction (of the robot). Meters.
     * @param y The Y value of the point to be reached where +y is the up direction. Meters.
     * @return double[pivotDirection, extensionPercent] OR {-1,-1} if the point is unreachable:
     *          <p> - pivotDirection = The direction of the pivot in radians where 0 radians represents parallel to the ground (pointing forwards) and the positive direction is upwards
     *          <p> - extensionLength = The length to extend the arm to reach the point in meters.
     */
    public static ArmState getRotationExtensionFromSetPoint(double x, double y) {
        double directionTowardsPoint = Math.atan2(y,x);
        double distanceToPoint = Math.sqrt((x*x)+(y*y));
        double lengthOfExtension = distanceToPoint - Constants.ARM_PIVOT_LENGTH;
        double percentOfExtension = lengthOfExtension/Constants.ARM_EXTEND_LENGTH;
        // Angle Boundaries: 0 radians to PI radians (range of motion: 180 degrees) 
        // Extension Boundaries: 0% to 100%
        if ((0 < percentOfExtension && percentOfExtension < 1) && (0 < directionTowardsPoint && directionTowardsPoint < Math.PI)) {
            return new ArmState(new Rotation2d(directionTowardsPoint), lengthOfExtension);
        }
        return null;
    }


    /**
     * <p> This gets the setpoint (or where the arm currently is in x,y) from the current rotation and extension.
     * @param rotation The rotation of the arm where 0 radians represents parallel to the ground (pointing forwards) and the positive direction is upwards.
     * @param extension The length of the extension in meters. (where 0 represents the arm's base, unextended length)
     * @return (x,y) in meters where +x is forwards and +y is left.
     */
    public Translation2d getSetPointFromRotationAndExtension(Rotation2d rotation, double extension) {
        double x = Math.cos(rotation.getRadians()) * (Constants.ARM_PIVOT_LENGTH + extension);
        double y = Math.sin(rotation.getRadians()) * (Constants.ARM_PIVOT_LENGTH + extension);
        return new Translation2d(x, y);
    }


    /**
     * <p> This checks if the desired position is reachable and safe, and if it is, will set the desired position to the new position.
     * <p> (0,0) is the pivot point of the arm.
     * @param x How forwards the end of the arm should get to in meters. +x represents the forwards direction.
     * @param y How up the arm should get to in meters. +y represents the upwards direction.
     */
    public void setDesiredPosition(double x, double y) {
        // Check to see if the desired location is within the constraints
        double directionTowardsPoint = Math.atan2(y,x);
        double unsafeAngle = Math.atan2(-Constants.ARM_PIVOT_TO_BASE_DISTANCE, Constants.ROBOT_SIDE_LENGTH/2); //Angles past this point are deemed unsafe.
        ArmState parameters = getRotationExtensionFromSetPoint(x, y);

        if (parameters == null) {
            // System.out.println("POINT IS UNREACHABLE (MECHANICALLY IMPOSSIBLE)");
            return;
        }
        else if (directionTowardsPoint <= unsafeAngle) {
            // System.out.println("POINT CLIPS ROBOT");
            return;
        }
        else if (y <= -(Constants.ARM_PIVOT_TO_BASE_DISTANCE + Constants.ROBOT_HEIGHT)) {
            // System.out.println("POINT CLIPS GROUND");
            return;
        }
        else if (directionTowardsPoint >= Units.degreesToRadians(90)) {
            // System.out.println("POINT CAUSES SIGNFICANT STRAIN (GOES OVER THE ROBOT)");
            return;
        }

        targetState = parameters;
    }


    /**
     * <p> Rotates the pivot of the arm at the specified speed.
     * @param rotationalVelocity Power of the motor between -1 and 1 where +1 represents full rotation upwards.
     */
    public void rotate(double rotationalVelocity)
    {
        if(Robot.armController.isPivotEnabled() == true)
        {
            // The arm shouldn't be able to rotate down too far
            if (getPivotRotation().getDegrees() <= -10 && rotationalVelocity < 0) {
                pivotMotor.set(0);
                return;
            }
            // Shouldn't be able to go over the top
            if (getPivotRotation().getDegrees() >= 95 && rotationalVelocity > 0) {
                pivotMotor.set(0);
                return;
            }
            pivotMotor.set(-rotationalVelocity);
        }
    }   

    /**
     * <p> Runs the extension motor for the arm at the specified speed.
     * @param extensionVelocity Power of the motor between -1 and 1 where +1 represents full extension and -1 represents full retraction.
     */
    public void extend(double extensionVelocity)
    {
        if(Robot.armController.isExtendEnabled() == true)
        {
            extendMotor.set(extensionVelocity);
        }
    }


    /**
     * <p> This gets the rotation determined by the pivot absolute encoder after applying the conversion factor.
     * @return The rotation of the arm where 0 radians/degrees represents [...] <-- NEED TO DETERMINE THIS
     */
    public Rotation2d getPivotRotation() {
        if (pivotEncoder.isConnected())
            return new Rotation2d(pivotEncoder.get() * PIVOT_ENCODER_UNITS_TO_RADIANS).minus(PIVOT_ENCODER_OFFSET);
        else
            // This is the motor's native encoder units (which is rotations) times the gear ratio (ARM_PIVOT_KRAKEN_UNITS_TO_RADIANS) minus the offset (the arm at 0 degrees)
            return new Rotation2d(pivotMotor.getPosition().getValueAsDouble()*ARM_PIVOT_KRAKEN_UNITS_TO_RADIANS).minus(new Rotation2d(0.0));
    }


    /**
     * <p> This gets the length of the current extension based on the absolute encoder attached after applying the conversion factor.
     * @return The length of the arm's extension in meters where 0 represents no extension.
     */
    public double getExtensionLength() {
        if (isExtensionEncoderConnected())
            return extendEncoder.get() * EXTENSION_ENCODER_UNITS_TO_METERS - EXTENSION_ENCODER_OFFSET;
        else {
            // A full extension is 123.334 encoder units
            return (extendMotor.getPosition().getValueAsDouble() / 123.334) * ArmState.MAX_EXTENSION_LENGTH - extensionOffset;
        }
    }


    public boolean isPivotEncoderConnected() {
        return pivotEncoder.isConnected();
    }
    public boolean isExtensionEncoderConnected() {
        return extendEncoder.isConnected();
    }


    /**
     * Just some testing for the arm math.
     * Check out the desmos graph linked below for the math in more detail:
     * https://www.desmos.com/calculator/acillm6yyc
     */
    public static void main(String[] args) {
        /* 
        double[] output = getRotationExtensionFromSetPoint(20,20);
        System.out.println(output[0]);
        System.out.println(output[1]);
        */
    }


    public Translation2d getTargetPoint() {
        return getSetPointFromRotationAndExtension(targetState.getPivotRotation(), targetState.getExtensionLength());
    }


    public Translation2d getCurrentPoint() {
        return getSetPointFromRotationAndExtension(getPivotRotation(), getExtensionLength());
    }

    /**
     * Technically all in meters...
     * @param deltaX +x = forwards
     * @param deltaY +y = up
     */
    public void changeSetpoint(double deltaX, double deltaY) {
        Translation2d desiredPosition = getTargetPoint();
        setDesiredPosition(desiredPosition.getX() + deltaX, desiredPosition.getY() + deltaY);
    }

    public void setSetpoint(double x, double y) {
        setDesiredPosition(x, y);
    }

    public void setState(ArmState state) {
        this.targetState = state;
    }

    public void setState(Rotation2d targetPivotRotation, double targetExtensionLengthMeters) {
        this.targetState = new ArmState(targetPivotRotation, targetExtensionLengthMeters);
    }


    public boolean usingSetpointSystem() {
        return this.usingSetpointSystem;
    }

    public void setUsingSetpointSystem(boolean usingSetpointSystem) {
        this.usingSetpointSystem = usingSetpointSystem;
    }


    public void setExtendP(double p) { this.extensionPID.setP(p); }
    public void setExtendI(double i) { this.extensionPID.setI(i); }
    public void setExtendD(double d) { this.extensionPID.setD(d); }
    public void setRotateP(double p) { this.pivotPID.setP(p); }
    public void setRotateI(double i) { this.pivotPID.setI(i); }
    public void setRotateD(double d) { this.pivotPID.setD(d); }

    public ProfiledPIDController getExtensionPID() { return this.extensionPID; }
    public ProfiledPIDController getPivotPID() { return this.pivotPID; }

    public void setForcedRotation(Rotation2d rotation) { this.forcedRotation = rotation; }
    public Rotation2d getForcedRotation() { return this.forcedRotation; }
    public void setForcedExtension(double extensionMeters) { this.forcedExtension = extensionMeters; }
    public double getForcedExtension() { return this.forcedExtension; }

    public void setMaxFeedForward(double ff) { this.maxFeedForward = ff; }
    public double getMaxFeedForward() { return this.maxFeedForward; }

    public void setExtensionOffset(double extensionOffsetMeters) { this.extensionOffset = extensionOffsetMeters; }
    public double getExtensionOffset() { return this.extensionOffset; }
}