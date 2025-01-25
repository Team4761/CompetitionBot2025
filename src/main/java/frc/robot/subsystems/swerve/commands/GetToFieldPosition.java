package frc.robot.subsystems.swerve.commands;

import edu.wpi.first.wpilibj2.command.Command;

public class GetToFieldPosition extends Command{
    public static void GetToFieldPostion(float x, float y, float desiredRotation){
        System.out.println(x);
        System.out.println(y);
        System.out.println(desiredRotation);

    }
}
