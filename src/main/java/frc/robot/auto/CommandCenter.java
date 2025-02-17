package frc.robot.auto;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.arm.*;
import frc.robot.subsystems.muncher.*;
import frc.robot.subsystems.swerve.*;

public class CommandCenter {
    // Muncher commands
    public static Command yeet() { return YeetCommand.create(); } // Ejects coral at internal speed and time, then resets 
    public static Command yeetRaw(double speed, double seconds) { return YeetForTimeAtSpeedCommand.create(speed, seconds); } // Applies a raw speed to the yeet motor for a set time
    
    public static Command eat() { return IntakeCommand.create(); } // Intakes coral at internal speed and time
    public static Command eatRaw(double speed, double seconds) { return IntakeForTimeAtSpeedCommand.create(speed, seconds); } // Applies a raw speed to the intake motor for a set time
    public static Command hurl() { return OuttakeCommand.create(); } // Ejects coral at internal speed and time

    // Arm Commands
    public static Command armGo(double x, double y) { return GetArmToPositionCommand.create(x, y); } // Moves head of the arm to specified x and y coordinates relative to robot
    public static Command armTurnRaw(double dureation, double speed) { return RotateArmForTimeAtSpeedCommand.create(dureation, speed); } // Rotates the arm for a duration at a specific speed
    public static Command armExtendRaw(double duration, double extensionSpeed) { return ExtendArmForTimeAtSpeedCommand.create(duration, extensionSpeed); } // Extends the arm for a duration at a specific speed
    
    public static Command coralPrep() { return CoralStationIntakePrepCommand.create(); } // Moves the arm to the coral station and prepares to intake coral
    public static Command scoreL1() { return ScoreL1Command.create(); } // Scores coral at level 1
    public static Command scoreL2(boolean orientation, int aprilTagID) { return ScoreL2Command.create(orientation, aprilTagID); } // Scores coral at level 2
    public static Command scoreL3() { return ScoreL3Command.create(); } // Scores coral at level 3
    public static Command scoreL4() { return ScoreL4Command.create(); } // Scores coral at level 4

    // Swerve Commands
    public static Command fieldGo(double x, double y, double angle) { return GetToFieldPositionCommand.create(x, y, angle); }
    public static Command fieldGoRaw(double speedx, double speedy, double turnSpeed, double duration) { return MoveForTimeAtSpeedCommand.create(speedx, speedy, turnSpeed, duration); }
    public static Command fieldMove(double deltax, double deltay, Rotation2d deltaRot) { return MoveDistanceCommand.create(deltax, deltay, deltaRot); }
}
