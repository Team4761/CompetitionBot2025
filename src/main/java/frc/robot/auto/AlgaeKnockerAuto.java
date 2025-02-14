package frc.robot.auto;

import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.field.Field;
import frc.robot.subsystems.arm.GetArmToPositionCommand;
import frc.robot.subsystems.muncher.YeetForTimeAtSpeedCommand;
import frc.robot.subsystems.swerve.GetToFieldPositionCommand;

public class AlgaeKnockerAuto extends Command {

    public static Command create() {
        return new SequentialCommandGroup(
            DeclareStartingPositionCommand.create(),
            GetToFieldPositionCommand.create(Field.DISTANCE_OF_REEF_TO_STARTING_LINE, 0.0, Units.degreesToRadians(180)), // ideally should be facing the reef and at the edge of the reef
            GetArmToPositionCommand.create(Field.SAME_SIDE_OF_REEF_SEPRATION_DISTANCE, (Field.L2_HEIGHT+Field.L3_HEIGHT)/2), // Should get to the algae
            YeetForTimeAtSpeedCommand.create(-0.7, 1), // grab algae
            GetArmToPositionCommand.create(-Field.SAME_SIDE_OF_REEF_SEPRATION_DISTANCE, (Field.L2_HEIGHT+Field.L3_HEIGHT)/2), // turn away from reef
            YeetForTimeAtSpeedCommand.create(1, 0.5) // yeet algae
        );
    } 
    
}

