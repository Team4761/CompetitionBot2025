// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.muncher;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.RobotMap;

/** Add your docs here. */
public class IntakeForTimeAtSpeed extends Command{
    private double speed;
    private double seconds;
    /** 
     * do not use this this constructor, use create instead
    */
    private IntakeForTimeAtSpeed(double seconds) {
        this.speed = speed;
        this.seconds = seconds;
    }

    public static Command create(double speed, double seconds) {
       return new IntakeForTimeAtSpeed(speed).withTimeout(seconds);
    }
    
    @Override
    public void execute() {
        RobotMap.muncher.intakeForSpeed(speed);
    }
}
