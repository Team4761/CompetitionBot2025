package frc.robot.subsystems.arm;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import frc.robot.Robot;

/**
 * Commands on the Arm Subsystem
 */
public class ArmCommands {
  /**
   * <p> DOOMSDAY COMMAND! 
   * <p> Is this name verbose? Yes. Why? Simple: we shouldn't be using this command unless we have to, so I'm going to make it annoying to type.
   * <p> This ONLY extends the arm of the robot.
   * <p> It does NOT pivot the arm.
   * <p> This should not be used if we have accurate encoders on the arm, as it is unaccurate and could potentially damage the mechanism if improperly used.
   */
  public static Command extendArmForTimeAtSpeed(double seconds, final double speed) {
    return Commands.run(() -> arm().extend(speed), arm()).withTimeout(seconds);
  }

  /**
   * <p> This controls the arm to get to the setpoint and ends when it gets within (TBD) meters of the desired location.
   * <p> TODO: Determine what to do if we tell it to go to an impossible location
   * <p> TODO: Change (0,0) to a description of where the origin actually is.
   * <p> TODO: Actually implement this.
   */
  public static Command getArmToPosition(final double x, final double y) {
    // TODO: implementation
    // return Commands.run(() -> arm().setDesiredPosition(x, y), arm());
    return Commands.run(() -> {}, arm());
  }

  /**
   * <p> DOOMSDAY COMMAND!
   * <p> Is this name verbose? Yes. Why? Simple: we shouldn't be using this command unless we have to, so I'm going to make it annoying to type.
   * <p> This should rotate/pivot the arm at a set speed for the specified duration.
   * <p> It does NOT extend the arm.
   * <p> This should not be used if we have accurate encoders on the arm, as it is unaccurate and could potentially damage the mechanism if improperly used.
   */
  public static Command rotateArmForTimeAtSpeed(double seconds, final double speed) {
    return Commands.run(() -> arm().rotate(speed), arm()).withTimeout(seconds);
  } 

  /** 
   * Shortcut to the arm
   */
  private static ArmSubsystem arm() { 
    return Robot.map.arm;
  }
}
