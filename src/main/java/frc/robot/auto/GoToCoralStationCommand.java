package frc.robot.auto;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.PrintCommand;
import frc.robot.field.Field;
import frc.robot.subsystems.swerve.GetToFieldPositionCommand;


public class GoToCoralStationCommand {
    public Command goToCoralStation(String CoralStation)
    {
        if(CoralStation.equals("Blue Left"))
           return GetToFieldPositionCommand.create(1.138,1.138,Units.degreesToRadians(-130));
        else if(CoralStation.equals("Blue Right"))
        {
            return GetToFieldPositionCommand.create(1.138, 6.882,Units.degreesToRadians(130));
        }
        else if(CoralStation.equals("Red Left"))
        {
            return GetToFieldPositionCommand.create((Field.FIELD_WIDTH - 1.138), 6.882,Units.degreesToRadians(130));
        }
        else if(CoralStation.equals("Red Right") )
        {
            return GetToFieldPositionCommand.create((Field.FIELD_WIDTH - 1.138), 1.138, Units.degreesToRadians(-130));
        }
        else
        {
            return new PrintCommand("String "+ CoralStation + " was not valid. The valid cases are 'Blue Left','Blue Right','Red Left', and 'Red Right'.");
        }
    }
}
