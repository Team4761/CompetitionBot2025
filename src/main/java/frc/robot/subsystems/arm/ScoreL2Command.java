// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.arm;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.arm.GetArmToPositionCommand;
import frc.robot.subsystems.muncher.YeetCommand;
import frc.robot.Constants;

/** 
 * End goal is to have this make the arm get to the position (16.625, 11.875), rotate arm to correct position, yeet, then retract finger
 * <p> however, I am incompetent and can't figure this out
 */
public class ScoreL2Command extends Command {
    public static Command create() {
        return new SequentialCommandGroup(GetArmToPositionCommand.create(Constants.L2_X, Constants.L2_Y), 
        YeetCommand.create());
    }
}   
