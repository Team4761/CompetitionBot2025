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
 * <p> however, I am incompetent and can't figure this out (you are not, this is exactly what we wanted)
 */
public class ScoreL2Command extends Command {


    /**
     * DO NOT USE THE CONSTRUCTOR! Use ScoreL2Command.create() instead!
     */
    private ScoreL2Command() {}


    /**
     * This will rotate the arm while aligning our robot with the april tag (assuming we have vision working) and then score.
     * @param aprilTagID The ID of the april tag we should align ourselves with.
     * @param scoreRightOfAprilTag Since each april tag has two coral scoring locations above them, this determines if you're scoring on the RIGHT side of the coral area. (true if right, false if left)
     * @return
     */
    public static Command create(boolean scoreRightOfAprilTag, int aprilTagID) {
        return new ParallelCommandGroup(
            new SequentialCommandGroup(
                GetArmToPositionCommand.create(Constants.L2_X, Constants.L2_Y), 
                YeetCommand.create()
            ),
            AlignWithAprilTag.create(aprilTagID, scoreRightOfAprilTag)
        );
    }


    /**
     * This should only be used if we don't have vision. Use create(aprilTagID, scoreRightOfAprilTag) instead if we have vision.
     * This will get the arm to the L2 rung and yeet our coral onto it.
     * @param scoreRightOfAprilTag This determines if we're going for the rung to the right of the april tag, or to the left (per side). No idea if we'll make this do something yet.
     * @return
     */
    public static Command create(boolean scoreRightOfAprilTag) {
        return new SequentialCommandGroup(
            GetArmToPositionCommand.create(Constants.L2_X, Constants.L2_Y), 
            YeetCommand.create()
        );
    }
}   
