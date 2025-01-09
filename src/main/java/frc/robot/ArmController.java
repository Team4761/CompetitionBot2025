package frc.robot;

import edu.wpi.first.wpilibj.XboxController;

/**
 * This will control both the arm AND the muncher/outtake.
 */
public class ArmController extends XboxController {
    
    public ArmController (int port) {
        super(port);
    }
}
