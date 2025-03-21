// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.arm;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.muncher.YeetCommand;
import frc.robot.Constants;
import frc.robot.auto.AlignWithAprilTag;

/** 
 * End goal is to have this make the arm get to the position (16.625, 11.875), rotate arm to correct position, yeet, then retract finger
 * <p> however, I am incompetent and can't figure this out (you are not, this is exactly what we wanted) [I may have figured this out, but I am still incompetent]
 */
public class ScoreL2Command extends Command {


    /**
     * DO NOT USE THE CONSTRUCTOR! Use ScoreL2Command.create() instead!
     * HDHAS  -Julian
     */
    private ScoreL2Command() {}


    /**
     * This will rotate the arm while aligning our robot with the april tag (assuming we have vision working) and then score.
     * @param aprilTagID The ID of the april tag we should align ourselves with.
     * @param scoreStrategy 0 -> don't adjust aligment, 1 -> left rung adjustment for coral placement, 2 -> right rung adjustment for coral placement
     * @return 
     */
    public static Command create(int scoreStrategy, int aprilTagID) {
        return new ParallelCommandGroup(
            new SequentialCommandGroup(
                GetArmToPositionCommand.create(Constants.L2_X, Constants.L2_Y), 
                YeetCommand.create(false)
            ),
            AlignWithAprilTag.create(aprilTagID, scoreStrategy)
        );
    }


    /**
     * This should only be used if we don't have vision. Use create(aprilTagID, scoreRightOfAprilTag) instead if we have vision.
     * This will get the arm to the L2 rung and yeet our coral onto it.
     * @param scoreStrategy 0 -> don't adjust aligment, 1 -> left rung adjustment for coral placement, 2 -> right rung adjustment for coral placement
     * @return
     */
    // TODO: scoreStrategy currently doesn't do anything. Add functionality to it.
    public static Command create(int scoreStrategy) {
        return new SequentialCommandGroup(
            GetArmToPositionCommand.create(Constants.L2_ARM_STATE).withTimeout(3.5), 
            YeetCommand.create(false)
        );
    }
}
