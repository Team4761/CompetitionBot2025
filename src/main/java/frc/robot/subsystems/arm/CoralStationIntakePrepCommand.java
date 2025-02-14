// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.arm;

import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.subsystems.arm.GetArmToPositionCommand;
import frc.robot.subsystems.muncher.IntakeCommand;
import frc.robot.field.Field;

/**IMPORTANT, MAKE THE ARM ROTATE TO CORRECT POSITION*/
public class CoralStationIntakePrepCommand extends Command {
    public static Command create() {
        return new SequentialCommandGroup(GetArmToPositionCommand.create(Units.inchesToMeters(21), Field.CORAL_STATION_HEIGHT_FROM_GROUND),
        new WaitCommand(1),
        IntakeCommand.create());
    }
}