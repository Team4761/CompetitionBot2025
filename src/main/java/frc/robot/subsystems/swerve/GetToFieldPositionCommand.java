package frc.robot.subsystems.swerve;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Robot;
import frc.robot.auto.CommandCenter;

/**
 * TODO: Implement this.
 * <p> This uses vision to get to a specific x,y position on the field.
 * <p> +x is the meters across in the direction from the blue alliance wall to the red alliance wall.
 * <p> +y is the meters across from the right side of the blue alliance wall to the left side of the blue alliance wall.
 */
public class GetToFieldPositionCommand extends Command {

    private double x;
    private double y;
    private double desiredRotation;

    /**
     * DO NOT USE THE CONSTRUCTOR!!! USE GetToFieldPositionCommand.create()!!
     */
    private GetToFieldPositionCommand(double x, double y, double desiredRotation){
        this.x = x;
        this.y = y;
        this.desiredRotation = desiredRotation;
    }

    @Override
    public void initialize() {
        CommandCenter.addRequirements(this, Robot.map.swerve);
    }

    public static Command create(double x, double y, double desiredRotation)
    {
        return new GetToFieldPositionCommand(x, y, desiredRotation);
    }
}
