// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.arm;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.arm.GetArmToPositionCommand;
import frc.robot.subsystems.muncher.YeetCommand;
import frc.robot.Constants;

/** Add your docs here. */
public class ScoreL4Command extends Command {
    public static Command create() {
        return new SequentialCommandGroup(GetArmToPositionCommand.create(Constants.L4_X, Constants.L4_Y), 
        YeetCommand.create());
    }
}
