package frc.robot.auto;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.PrintCommand;
import frc.robot.field.Field;
import frc.robot.subsystems.swerve.GetToFieldPositionCommand;
import frc.robot.subsystems.swerve.MoveDistanceCommand;


public class GoToCoralStationCommand {
    /**
     * 
     * @param CoralStation is an int which codes for which coral station to go to; 
     * 0 codes for Blue Left, 1 codes for Blue Right, 2 for Red Left, and 3 for Red Right
     * @return
     */
    public Command goToCoralStationFieldOriented(int CoralStation)
    {
        if(CoralStation == 0)
            return GetToFieldPositionCommand.create(1.138,1.138, new Rotation2d(Units.degreesToRadians(-130)));
        else if(CoralStation == 1)
        {
            return GetToFieldPositionCommand.create(1.138, 6.882, new Rotation2d(Units.degreesToRadians(130)));
        }
        else if(CoralStation == 2)
        {
            return GetToFieldPositionCommand.create((Field.FIELD_WIDTH - 1.138), 6.882, new Rotation2d(Units.degreesToRadians(-50)));
        }
        else if(CoralStation ==3 )
        {
            return GetToFieldPositionCommand.create((Field.FIELD_WIDTH - 1.138), 1.138, new Rotation2d(Units.degreesToRadians(50)));
        }
        else
        {
            return new PrintCommand("Int " + CoralStation + " was not valid. The valid cases are 0 (Blue Left),1 (Blue Right), 2(Red Left), and 3(Red Right).");
        }
    }
    public Command goToCoralStationRobotRelative(int CoralStation, double CurrentX, double CurrentY, double CurrentAngle)
    {
        if(CoralStation == 0)
            return MoveDistanceCommand.create((1.138-CurrentX),(1.138-CurrentY),new Rotation2d(Units.degreesToRadians(-130-CurrentAngle)));
        else if(CoralStation == 1)
        {
            return MoveDistanceCommand.create((1.138-CurrentX),(6.882-CurrentY),new Rotation2d(Units.degreesToRadians(130-CurrentAngle)));
        }
        else if(CoralStation == 2)
        {
            return MoveDistanceCommand.create(((Field.FIELD_WIDTH -1.138)-CurrentX),(6.882-CurrentY),new Rotation2d(Units.degreesToRadians(-50-CurrentAngle)));
        }
        else if(CoralStation ==3 )
        {
            return MoveDistanceCommand.create(((Field.FIELD_WIDTH -1.138)-CurrentX),(1.138-CurrentY),new Rotation2d(Units.degreesToRadians(50-CurrentAngle)));
        }
        else
        {
            return new PrintCommand("Int " + CoralStation + " was not valid. The valid cases are: 0 (Blue Left), 1 (Blue Right), 2(Red Left), and 3(Red Right).");
        }
    }
    
}
