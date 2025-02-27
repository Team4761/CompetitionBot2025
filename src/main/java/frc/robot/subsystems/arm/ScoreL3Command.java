// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.arm;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.muncher.YeetCommand;
import frc.robot.Constants;
import frc.robot.Robot;
import frc.robot.auto.AlignWithAprilTag;

/** 
 * Delete this if this wasn't supposed to be done, original command is commented out at the bottom
 * Delete the original command if this was supposed to happen
 * I have adjusted the code to be for L3 after copying from L2
 */
public class ScoreL3Command extends Command {


    /**
     * DO NOT USE THE CONSTRUCTOR! Use ScoreL3Command.create() instead!
     * HDHAS  -Julian
     */
    private ScoreL3Command() {}


    /**
     * This will rotate the arm while aligning our robot with the april tag (assuming we have vision working) and then score.
     * @param aprilTagID The ID of the april tag we should align ourselves with.
     * @param scoreStrategy 0 -> don't adjust aligment, 1 -> left rung adjustment for coral placement, 2 -> right rung adjustment for coral placement
     * @return 
     */
    public static Command create(int scoreStrategy, int aprilTagID) {
        return new ParallelCommandGroup(
            new SequentialCommandGroup(
                GetArmToPositionCommand.create(Constants.L3_X, Constants.L3_Y), 
                YeetCommand.create()
            ),
            AlignWithAprilTag.create(aprilTagID, scoreStrategy)
        );
    }


    /**
     * This should only be used if we don't have vision. Use create(aprilTagID, scoreRightOfAprilTag) instead if we have vision.
     * This will get the arm to the L3 rung and yeet our coral onto it.
     * @param scoreStrategy 0 -> don't adjust aligment, 1 -> left rung adjustment for coral placement, 2 -> right rung adjustment for coral placement
     * @return
     */
    // TODO: scoreStrategy currently doesn't do anything. Add functionality to it.
    public static Command create(int scoreStrategy) {
        return new SequentialCommandGroup(
            GetArmToPositionCommand.create(Constants.L3_X, Constants.L3_Y), 
            YeetCommand.create()
        );
    }
}   

/**
public class ScoreL3Command extends Command {
    public static Command create() {
        return new SequentialCommandGroup(GetArmToPositionCommand.create(Constants.L3_X, (Constants.L3_Y + 6.5)), 
        YeetCommand.create());
    }
}
*/
