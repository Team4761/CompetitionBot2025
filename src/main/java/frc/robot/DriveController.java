package frc.robot;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.filter.SlewRateLimiter;
import edu.wpi.first.wpilibj.XboxController;

/**
 * This just controls swerve.
 */
public class DriveController extends XboxController {

    // Slew rate limiters caps the transition between different speeds. (ex, a limiter of 3 means that the max change in speed is 3 per second.)
    // A rateLimit of 3 means that it will take ~1/3 to go from 0 -> 1.
    private final SlewRateLimiter leftXLimiter = new SlewRateLimiter(3);
    private final SlewRateLimiter leftYLimiter = new SlewRateLimiter(3);
    private final SlewRateLimiter rightXLimiter = new SlewRateLimiter(3);
    private final SlewRateLimiter rightYLimiter = new SlewRateLimiter(3);


    public DriveController (int port) {
        super(port);
    }
    
    // Applying slew limiters and deadzone (deadband) to every single axis.
    @Override
    public double getLeftX() {
        return leftXLimiter.calculate(MathUtil.applyDeadband(getLeftX(), 0.02));
    }
    @Override
    public double getLeftY() {
        return leftYLimiter.calculate(MathUtil.applyDeadband(getLeftY(), 0.02));
    }
    @Override
    public double getRightX() {
        return rightXLimiter.calculate(MathUtil.applyDeadband(getRightX(), 0.02));
    }
    @Override
    public double getRightY() {
        return rightYLimiter.calculate(MathUtil.applyDeadband(getRightY(), 0.02));
    }
}
