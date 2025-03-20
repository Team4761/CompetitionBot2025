// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.arm;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.muncher.OuttakeCommand;
import frc.robot.auto.AlignWithAprilTag;

/** 
 * Delete this if this wasn't supposed to be done, original command is commented out at the bottom
 * Delete the original command if this was supposed to happen
 * I have adjusted the code to be for L1 after copying from L2
 */
public class ScoreL1Command extends Command {


    /**
     * DO NOT USE THE CONSTRUCTOR! Use ScoreL1Command.create() instead!
     * HDHAS  -Julian
     */
    private ScoreL1Command() {}


    /**
     * This will rotate the arm while aligning our robot with the april tag (assuming we have vision working) and then score.
     * @param aprilTagID The ID of the april tag we should align ourselves with.
     * @param scoreStrategy 0 -> don't adjust aligment, 1 -> left rung adjustment for coral placement, 2 -> right rung adjustment for coral placement
     * @return 
     */
    public static Command create(int scoreStrategy, int aprilTagID) {
        return new ParallelCommandGroup(
            new SequentialCommandGroup(
                // GetArmToPositionCommand.create(Constants.L1_X, Constants.L1_Y), 
                GetArmToPositionCommand.create(new ArmState(new Rotation2d(Units.degreesToRadians(30)), 0)).withTimeout(5.0),
                OuttakeCommand.create()
            ),
            AlignWithAprilTag.create(aprilTagID, scoreStrategy)
        );
    }


    /**
     * This should only be used if we don't have vision. Use create(aprilTagID, scoreRightOfAprilTag) instead if we have vision.
     * This will get the arm to the L1 rung and yeet our coral onto it.
     * @param scoreStrategy 0 -> don't adjust aligment, 1 -> left rung adjustment for coral placement, 2 -> right rung adjustment for coral placement
     * @return
     */
    // TODO: scoreStrategy currently doesn't do anything. Add functionality to it.
    public static Command create(int scoreStrategy) {
        return new SequentialCommandGroup(
            // GetArmToPositionCommand.create(Constants.L1_X, Constants.L1_Y), 
            GetArmToPositionCommand.create(new ArmState(new Rotation2d(Units.degreesToRadians(30)), 0)).withTimeout(5),
            OuttakeCommand.create()
        );
    }
}   

/**
public class ScoreL1Command extends Command {
        public static Command create() {
            return new SequentialCommandGroup(GetArmToPositionCommand.create(Constants.L1_X, (Constants.L1_Y + 6.5)), // 6.5 extra inches to account for robot arm height
        YeetCommand.create());
    }
}
*/