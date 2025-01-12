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
    /**
     * 
     * @param x The X value of the point to be reached.
     * @param y The Y value of the point to be reached.
     * @return Returns the direction of the pivot (in radians) and the percent to extend the arm to reach the point.
     * 
     * <p> Takes in a point in 2D space and returns the arm configuration to reach it. Outputs {-1, -1} if it is unreachable.
     */
    public static double[] gotoPoint(double x, double y) {
        double directionTowardsPoint = Math.atan2(y,x);
        double distanceToPoint = Math.sqrt(x*x+y*y);
        double lengthOfExtension = distanceToPoint - Constants.ARM_PIVOT_LENGTH;
        double percentOfExtension = lengthOfExtension/Constants.ARM_EXTEND_LENGTH;
        // Angle Boundaries: 0 radians to PI radians
        // Extension Boundaries: 0% to 100%
        if ((0 < percentOfExtension && percentOfExtension < 1) && (0 < directionTowardsPoint && directionTowardsPoint < Math.PI)) {
            return new double[]{directionTowardsPoint, percentOfExtension};
        }
        return new double[]{-1,-1};
    } 
    public static void main(String[] args) {
        double[] output = gotoPoint(20,20);
        System.out.println(output[0]);
        System.out.println(output[1]);
    }
}
