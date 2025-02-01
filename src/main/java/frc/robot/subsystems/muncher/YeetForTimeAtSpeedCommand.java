// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.muncher;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Robot;

/** Add your docs here. */
public class YeetForTimeAtSpeedCommand extends Command {
    private double speed;

    /**
     * DO NOT USE THE CONSTRUCTOR. Use YeetForTimeAtSpeedCommand.create() instead.
     */
    private YeetForTimeAtSpeedCommand() {}


    /**
     * do not use this this constructor, use create instead
     */
    private YeetForTimeAtSpeedCommand(double speed) {
        this.speed = speed;
    }

    
    /**
     * This runs the yeet device of the muncher at a set speed for a set duration.
     * @param speed The speed as a percent between -1 and 1 where 1 represents full speed pushing outwards and -1 represents full speed pulling inwards.
     * @param seconds The duration in seconds to run the yeet device.
     * @return The YeetForTimeCommand that can be scheduled.
     */
    public static Command create(double speed, double seconds) {
        return new YeetForTimeAtSpeedCommand(speed).withTimeout(seconds);
    }

    @Override
    public void execute() {
        Robot.map.muncher.yeet(speed);
    }

    @Override
    public void end(boolean interrupted) {
        Robot.map.muncher.yeet(0);
    }
}
