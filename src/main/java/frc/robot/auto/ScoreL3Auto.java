package frc.robot.auto;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.PrintCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Constants;
import frc.robot.controllers.OrientControlsCommand;
import frc.robot.subsystems.arm.ArmState;
import frc.robot.subsystems.arm.GetArmToPositionCommand;
import frc.robot.subsystems.arm.ScoreL1Command;
import frc.robot.subsystems.arm.ScoreL2Command;
import frc.robot.subsystems.muncher.OuttakeCommand;
import frc.robot.subsystems.muncher.YeetCommand;
import frc.robot.subsystems.swerve.MoveDistanceCommand;
import frc.robot.subsystems.swerve.ZeroGyroCommand;

/**
 * Scores ONE coral on L3 currently only from the center position.
 * This removes algae!
 */
public class ScoreL3Auto {
    
    private ScoreL3Auto() {}

    public static Command create(StartingPosition startingPosition) {
        switch (startingPosition) {
            case BLUE_LEFT, RED_LEFT: {
                return new PrintCommand("Currently not made for the left side!");
            }
            case BLUE_CENTER, RED_CENTER: {
                return new SequentialCommandGroup(
                    new PrintCommand("Starting One Coral L3 Auto from " + startingPosition),
                    ZeroGyroCommand.create(),   // Mainly for testing. Whatever direction is forwards is the 0.0degrees direction.
                    OrientControlsCommand.create(new Rotation2d(Units.degreesToRadians(180))),  // Make the operator controls facing the opposite alliance wall.
                    new ParallelCommandGroup(
                        MoveDistanceCommand.create(1.70, 0, new Rotation2d(0)).withTimeout(3.0),    // From the starting line to the reef
                        new SequentialCommandGroup(
                            new WaitCommand(0.3),
                            GetArmToPositionCommand.create(Constants.ALGAE_REMOVE_ARM_STATE).withTimeout(1.5)
                        )
                    ),
                    new ParallelCommandGroup(
                        GetArmToPositionCommand.create(new ArmState(new Rotation2d(Units.degreesToRadians(55)),0.35)).withTimeout(1.5),
                        OuttakeCommand.create().withTimeout(3.5),
                        new SequentialCommandGroup(
                            new WaitCommand(1.0),
                            MoveDistanceCommand.create(-0.6, 0, new Rotation2d(0))
                        )
                    ),
                    
                    // We've now reoved algae (hopefully, pls)
                    MoveDistanceCommand.create(0,0, new Rotation2d(Units.degreesToRadians(90))).withTimeout(1.5),
                    new ParallelCommandGroup(
                        GetArmToPositionCommand.create(Constants.L3_ARM_STATE).withTimeout(2.0),
                        AlignWithAprilTag.create(Constants.AprilTagAlignment.LEFT).withTimeout(2.5)
                    ),
                    YeetCommand.create(false).withTimeout(2),
                    new PrintCommand("Finished One Coral Auto")
                );
            }
            case BLUE_RIGHT, RED_RIGHT: {
                return new PrintCommand("Currently not made for the right side!");
            }
        }
        return new PrintCommand("How did you get here? This can't be reached...");
    }

}
