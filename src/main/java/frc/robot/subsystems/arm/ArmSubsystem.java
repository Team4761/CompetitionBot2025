package frc.robot.subsystems.arm;

import com.ctre.phoenix6.StatusSignal;
import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.units.AngleUnit;
import edu.wpi.first.units.measure.Angle;
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

    
    /**
     * <p> Takes in a point in 2D space and returns the arm configuration to reach it. Outputs {-1, -1} if it is unreachable.
     * <p> (0,0) represents the base of the arm.
     * @param x The X value of the point to be reached where +x is the forwards direction (of the robot). Meters.
     * @param y The Y value of the point to be reached where +y is the up direction. Meters.
     * @return double[pivotDirection, extensionPercent] OR {-1,-1} if the point is unreachable:
     *          <p> pivotDirection: The direction of the pivot in radians where 0 radians represents parallel to the ground (pointing forwards) and the positive direction is upwards
     *          <p> extensionPercent: The percent to extend the arm to reach the point (0 = no extension, 1 = full extension).
     */
    public static double[] calculateConfigForPoint(double x, double y) {
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
     * Just some testing for the arm math.
     * Check out the desmos graph linked below for the math in more detail:
     * https://www.desmos.com/calculator/acillm6yyc
     */

    public double[] getCurrentParam() {
        // I have no clue what I'm doing. Point of this function is to get the rotation of the pivotMotor and the extension percent of the extendMotor.
        StatusSignal<Angle> currentAngle = pivotMotor.getPosition();
        Angle currentAngleValue = currentAngle.getValue();
        double doubledCurrentAngleValue = currentAngleValue.magnitude();
        StatusSignal<Angle> currentExtensionPercent = extendMotor.getPosition();
        return new double[]{doubledCurrentAngleValue};
    }



    boolean reached = false;
    public static void main(String[] args) {
        double[] output = calculateConfigForPoint(20,20);
        System.out.println(output[0]);
        System.out.println(output[1]);       
    }
}
