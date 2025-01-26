package frc.robot.subsystems.arm;

import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

/**
 * <p> For the arm, +x represents forwards and +y represents up. Why not +z? Well, Translation2d uses x,y and I'm too lazy to make my own.
 * <p> This includes both the pivot motor AND the extension motor as well as 1 absolute encoder on each motor.
 */
public class ArmSubsystem extends SubsystemBase {
    // +x represents forwards and +y represents up.
    // 0,0 is currently undecided (we need the design of the arm before we decide.)
    private Translation2d desiredPosition;
    
    // Both are Krakens
    private TalonFX pivotMotor = new TalonFX(Constants.ARM_PIVOT_ENCODER_PORT);
    private TalonFX extendMotor = new TalonFX(Constants.ARM_EXTEND_MOTOR_PORT);
    // TODO: Add encoders

    
    /**
     * <p> Takes in a point in 2D space and returns the arm configuration to reach it. Outputs {-1, -1} if it is unreachable.
     * <p> (0,0) represents the base of the arm.
     * @param x The X value of the point to be reached where +x is the forwards direction (of the robot). Meters.
     * @param y The Y value of the point to be reached where +y is the up direction. Meters.
     * @return double[pivotDirection, extensionPercent] OR {-1,-1} if the point is unreachable:
     *          <p> pivotDirection: The direction of the pivot in radians where 0 radians represents parallel to the ground (pointing forwards) and the positive direction is upwards
     *          <p> extensionPercent: The percent to extend the arm to reach the point (0 = no extension, 1 = full extension).
     */
    public static double[] getRotationExtensionFromSetPoint(double x, double y) {
        double directionTowardsPoint = Math.atan2(y,x);
        double distanceToPoint = Math.sqrt(x*x+y*y);
        double lengthOfExtension = distanceToPoint - Constants.ARM_PIVOT_LENGTH;
        double percentOfExtension = lengthOfExtension/Constants.ARM_EXTEND_LENGTH;
        // Angle Boundaries: 0 radians to PI radians (range of motion: 180 degrees)
        // Extension Boundaries: 0% to 100%
        if ((0 < percentOfExtension && percentOfExtension < 1) && (0 < directionTowardsPoint && directionTowardsPoint < Math.PI)) {
            return new double[]{directionTowardsPoint, percentOfExtension};
        }
        return new double[]{-1,-1};
    }



    /**
     * Rotates the pivot of the arm at the specified speed.
     * @param rotationalVelocity Power of the motor between -1 and 1 where +1 represents full rotation upwards.
     */
    public void rotate(double rotationalVelocity)
    {
        pivotMotor.set(rotationalVelocity);
    }

    /**
     * Runs the extension motor for the arm at the specified speed.
     * @param extensionVelocity Power of the motor between -1 and 1 where +1 represents full extension and -1 represents full retraction.
     */
    public void extend(double extensionVelocity)
    {
        extendMotor.set(extensionVelocity);
    }


    /**
     * Just some testing for the arm math.
     * Check out the desmos graph linked below for the math in more detail:
     * https://www.desmos.com/calculator/acillm6yyc
     */
    public static void main(String[] args) {
        double[] output = getRotationExtensionFromSetPoint(20,20);
        System.out.println(output[0]);
        System.out.println(output[1]);
    }
}