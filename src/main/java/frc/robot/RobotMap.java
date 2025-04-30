package frc.robot;

import frc.robot.subsystems.arm.ArmSubsystem;
import frc.robot.subsystems.leds.LEDSubsystem;
import frc.robot.subsystems.muncher.MuncherSubsystem;
import frc.robot.subsystems.swerve.SwerveSubsystem;
import frc.robot.subsystems.vision.VisionSubsystem;
import frc.robot.subsystems.win.WinSubsystem;

/**
 * This is mainly for debugging purposes.
 * By commenting out the initialization lines for each subsystem, that will effectively disable the subsystem.
 * The rest of the code must account for any subsystem being null.
 * 
 * RobotMap will be statically initialized ONCE in Robot.java
 */
public class RobotMap {
    
    public ArmSubsystem arm = null;
    public LEDSubsystem leds = null;
    public MuncherSubsystem muncher = null;
    public SwerveSubsystem swerve = null;
    public VisionSubsystem vision = null;
    public WinSubsystem win = null;

    
    public RobotMap() {
        // COMMENT OUT SUBSYSTEMS BELOW TO DISABLE THEM
        arm = new ArmSubsystem();
        leds = new LEDSubsystem();
        muncher = new MuncherSubsystem();
        swerve = new SwerveSubsystem();
        vision = new VisionSubsystem();
        win = new WinSubsystem();
    }

}


/**
 * The Fitness Gram™ Pacer Test is a multi-stage aerobic capacity test that progressively gets more difficult as it continues.
 * The 20 meter pacer test will begin in 30 seconds.
 * Line up at the start.
 * The running speed starts slowly, but gets faster each minute after you hear this signal.
 * [beep]
 * A single lap should be completed each time you hear this sound.
 * [ding]
 * Remember to run in a straight line, and run for as long as possible.
 * The second time you fail to complete a test before the sound, your test is over.
 * The test will begin on the word start.
 * On your mark, get ready, start.
 */
