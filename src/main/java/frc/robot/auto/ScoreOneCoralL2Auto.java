package frc.robot.auto;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.PrintCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Constants;
import frc.robot.controllers.OrientControlsCommand;
import frc.robot.subsystems.arm.ScoreL2Command;
import frc.robot.subsystems.swerve.MoveDistanceCommand;
import frc.robot.subsystems.swerve.ZeroGyroCommand;

public class ScoreOneCoralL2Auto {
    
    private ScoreOneCoralL2Auto() {}

    public static Command create(StartingPosition startingPosition) {
        switch (startingPosition) {
            case BLUE_LEFT, RED_LEFT: {
                return new SequentialCommandGroup(
                    new PrintCommand("Starting One Coral Auto from " + startingPosition),
                    ZeroGyroCommand.create(),   // Mainly for testing. Whatever direction is forwards is the 0.0degrees direction.
                    OrientControlsCommand.create(new Rotation2d(Units.degreesToRadians(180))),  // Make the operator controls facing the opposite alliance wall.
                    MoveDistanceCommand.create((7.668-5.115), 0, new Rotation2d(30)).withTimeout(5.0),    // From the starting line to the reef
                    ScoreL2Command.create(Constants.AprilTagAlignment.CENTER, (startingPosition == StartingPosition.BLUE_LEFT) ? 20 : 11),    // Align with the proper april tag
                    new PrintCommand("Finished One Coral Auto")
                );
            }
            case BLUE_CENTER, RED_CENTER: {
                return new PrintCommand("You can't score L2 from the center! :p");
            }
            case BLUE_RIGHT, RED_RIGHT: {
                return new SequentialCommandGroup(
                    new PrintCommand("Starting One Coral Auto from " + startingPosition),
                    ZeroGyroCommand.create(),   // Mainly for testing. Whatever direction is forwards is the 0.0degrees direction.
                    OrientControlsCommand.create(new Rotation2d(Units.degreesToRadians(-180))),  // Make the operator controls facing the opposite alliance wall.
                    MoveDistanceCommand.create((7.668-5.115), 0, new Rotation2d(-30)).withTimeout(5.0),    // From the starting line to the reef
                    ScoreL2Command.create(Constants.AprilTagAlignment.CENTER, (startingPosition == StartingPosition.BLUE_LEFT) ? 20 : 11),    // Align with the proper april tag
                    new PrintCommand("Finished One Coral Auto")
                );
            }
        }
        return new PrintCommand("How did you get here? This can't be reached...");
    }

}
