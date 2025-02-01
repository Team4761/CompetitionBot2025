package frc.robot.subsystems.swerve;

import edu.wpi.first.wpilibj2.command.Command;

/**
 * TODO: Implement this.
 * <p> This uses vision to get to a specific x,y position on the field.
 * <p> +x is the meters across in the direction from the blue alliance wall to the red alliance wall.
 * <p> +y is the meters across from the right side of the blue alliance wall to the left side of the blue alliance wall.
 */
public class GetToFieldPositionCommand extends Command{
    public static void GetToFieldPostion(float x, float y, float desiredRotation){
        System.out.println(x);
        System.out.println(y);
        System.out.println(desiredRotation);

    }
}
