package frc.robot.subsystems.vision;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

/**
 * This does NOT find April Tags, that is handled by the Orange Pi. Instead, this allows the robot to react to where the Orange Pi found april tags.
 * Vision works with the following pipeline: Camera (RAW_DATA) --> Orange Pi (APRIL_TAG_SPOTS) --> Radio (NETWORKING) --> RoboRIO (REACTING_TO_DATA) & Driverstation (DEBUGGING)
 * 
 * If using our Microsoft cams, we should have them set to 640x480 @ 30fps (can be set easily through shuffleboard)
 */
public class VisionSubsystem extends SubsystemBase {
    
}
