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
import frc.robot.subsystems.muncher.IntakeCommand;
import frc.robot.subsystems.swerve.MoveDistanceCommand;
import frc.robot.subsystems.swerve.ZeroGyroCommand;

/**
 * This is supposed to get to the reef, rotate the arm, and score the coral.
 */
public class TwoCoralL1Auto {

    /**
     * Here's a list of every april tag to line up for when scoring (the parenthesis show where our robot wants to be in field coordinates):
     * 
     * BLUE ALLIANCE:
     *  LEFT - 20   (5.26097, )
     *  CENTER - 21 (5.67690, 4.02590)
     *  RIGHT - 22  (5.26097, )
     * 
     * RED ALLIANCE:
     *  LEFT - 11
     *  CENTER - 10
     *  RIGHT - 9
     * 
     * CORAL STATIONS:
     *  BLUE:
     *   LEFT - 13
     *   RIGHT - 12
     * 
     *  RED:
     *   LEFT - 1
     *   RIGHT - 2
     */

    /**
     * Because of Algae, here is a graphic of available coral ([] = accessible coral, # = algae-blocked coral, A = algae):
     * 
     * L4: ## ##   [] []   ## ##
     *       A               A
     * L3: ## ##   ## ##   ## ##
     *               A
     * L2: [] []   ## ##   [] []
     * 
     * Based on this graphic, if we're in the center, we can ONLY score on either L4 or L1 (unless our teammates let us take one of their coral instead)
     */

    /**
     * DO NOT USE THE CONSTRUCTOR! Use ScoreOneCoralAuto.create() instead.
     */
    private TwoCoralL1Auto() {}
    
    /**
     * This is a bit complicated because it takes into account multiple starting positions and is dependent on side.
     * @param startingPosition The starting position of our bot on the field facing the opposite alliance wall.
     * @param onRedAlliance If we on red alliance, we be true. If no, false. Blam bada bop boom, pow.
     * @return
     */
    public static Command create(StartingPosition startingPosition) {
        switch (startingPosition) {
            case BLUE_LEFT, RED_LEFT:
            return new SequentialCommandGroup(
                new PrintCommand("Starting One Coral Auto from " + startingPosition),
                ZeroGyroCommand.create(),   // Mainly for testing. Whatever direction is forwards is the 0.0degrees direction.
                OrientControlsCommand.create(new Rotation2d(Units.degreesToRadians(180))),  // Make the operator controls facing the opposite alliance wall.
                MoveDistanceCommand.create(0,0, new Rotation2d(Units.degreesToRadians(60))).withTimeout(2),
                new ParallelCommandGroup(
                    MoveDistanceCommand.create(1.88, 0, new Rotation2d(0)).withTimeout(3.0),    // From the starting line to the reef
                    new SequentialCommandGroup(
                        new WaitCommand(0.5),
                        ScoreL1Command.create(Constants.AprilTagAlignment.CENTER, (startingPosition == StartingPosition.BLUE_CENTER) ? 21 : 10)    // Align with the proper april tag
                    )
                ),
                new ParallelCommandGroup(
                    GetArmToPositionCommand.create(Constants.L1_ARM_STATE),
                    MoveDistanceCommand.create(3.8, 2.4, new Rotation2d(Units.degreesToRadians(90))).withTimeout(3)
                ),
                IntakeCommand.create().withTimeout(null),
                new PrintCommand("Finished One Coral Auto")
            );
            case BLUE_CENTER, RED_CENTER:
                return new PrintCommand("Can't do center two thingies. How about one instead?");
            case BLUE_RIGHT, RED_RIGHT:
                return new PrintCommand("Not made for right side yet");
            default:
                return new PrintCommand("The starting position of " + startingPosition + " is not valid");
        }
    }
}
