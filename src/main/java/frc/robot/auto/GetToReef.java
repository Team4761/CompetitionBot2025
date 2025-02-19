package frc.robot.auto;

import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.field.Field;
import static frc.robot.auto.CommandCenter.*;

import com.fasterxml.jackson.databind.ser.std.StdKeySerializers.Default;

public class GetToReef extends Command {
    public static Command create() {
        int boardStartingPosition = 2; // 1 -> left(ish), 2 -> center(ish), or 3 -> right(ish), relative to the driver

        switch(boardStartingPosition) {
            case 1: // Left(ish)
                return new SequentialCommandGroup(
                    fieldGo(Units.inchesToMeters(30), Units.inchesToMeters(30), 0.0),
                    fieldGo(Units.inchesToMeters(30), Units.inchesToMeters(30), 0.0)
                );
            case 2: // Center(ish)
                return new SequentialCommandGroup(
                    fieldGo(Units.inchesToMeters(30), Units.inchesToMeters(30), 0.0)
                );
            case 3: // Right(ish)
                return new SequentialCommandGroup(
                    fieldGo(Units.inchesToMeters(30), Units.inchesToMeters(30), 0.0),
                    fieldGo(Units.inchesToMeters(30), Units.inchesToMeters(30), 0.0)
                );
            default: // idgaf u messed up
                return new SequentialCommandGroup(
                    DeclareStartingPositionCommand.create()
                );
        }
    }
    
}
