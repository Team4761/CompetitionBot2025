package frc.robot.auto;

import com.pathplanner.lib.auto.NamedCommands;
import com.pathplanner.lib.commands.PathPlannerAuto;
import com.pathplanner.lib.events.EventTrigger;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.PrintCommand;
import frc.robot.Constants;
import frc.robot.Robot;
import frc.robot.subsystems.arm.GetArmToPositionCommand;
import frc.robot.subsystems.arm.ScoreL2Command;
import frc.robot.subsystems.swerve.MoveForTimeAtSpeedCommand;

public class AutoHandler {

    private static SendableChooser<Command> autoChooser;
    private static SendableChooser<StartingPosition> startingPositionChooser;
    

    /**
     * <p> This makes a list of our autos and puts them on the Dashboard.
     */
    public static void setupAutoSelector() {
        autoChooser = new SendableChooser<Command>();
        startingPositionChooser = new SendableChooser<StartingPosition>();

        // Add commands to the chooser by copying the line below and changing the name & command
        autoChooser.addOption("Say Hi", new SayHiCommand());
        autoChooser.addOption("Move frowaards", MoveForTimeAtSpeedCommand.create(1, 0, 0, 1));
        autoChooser.addOption("Move Barckwaards", MoveForTimeAtSpeedCommand.create(-1, 0, 0, 1));
        autoChooser.addOption("Score L2", ScoreL2Command.create(true)); // command not finished
        autoChooser.addOption("PP: One Meter Forward", new PathPlannerAuto("One Meter Forward"));

        // This is for choosing which spot we started in
        startingPositionChooser.addOption("LEFT", StartingPosition.LEFT);
        startingPositionChooser.addOption("CENTER", StartingPosition.CENTER);
        startingPositionChooser.addOption("RIGHT", StartingPosition.RIGHT);
 
        SmartDashboard.putData("Selected Auto", autoChooser);
        SmartDashboard.putData("Starting Position", startingPositionChooser);

        // Check the alliance and put it on the dashboard as well (as a boolean. True = red alliance)
        boolean onRedAlliance = false;
        var alliance = DriverStation.getAlliance();
        if (alliance.isPresent()) {
            onRedAlliance = alliance.get() == DriverStation.Alliance.Red;
        }
        else {
            onRedAlliance = false;
        }
        SmartDashboard.putBoolean("Alliance", onRedAlliance);
    }   


    /**
     * <p> This sets up Path Planner assuming that Swerve has already initialized it's AutoBuilder.
     * <p> In the event that swerve has not been properly initialized, this will crash.
     */
    public static void setupPathPlanner() {
        // If swerve is null, we can't do Path Planner at all.
        if (Robot.map.swerve == null) {
            return;
        }
        // Instead of events (I think), we can use named commands which function in a very similar way... I think...
        NamedCommands.registerCommand("marker1", Commands.print("Passed marker 1"));
        NamedCommands.registerCommand("marker2", Commands.print("Passed marker 2"));
        NamedCommands.registerCommand("print hello", Commands.print("hello"));

        // In PathPlanner, you can name event markers. When the path gets to that marker, if will trigger the command you specify.
        new EventTrigger("Example Marker").onTrue(Commands.print("Passed an event marker"));
    }


    /**
     * <p> Gets the currently selected auto in the auto chooser.
     * @return The currently selected auto or `PrintCommand("No Auto Selected")` if no auto is selected.
     */
    public static Command getSelectedAuto() {
        if (autoChooser.getSelected() == null)
            return new PrintCommand("No Auto Selected");
        return autoChooser.getSelected(); // Default auto will be `Commands.none()`
    }


    public static StartingPosition getStartingPosition() {
        if (autoChooser.getSelected() == null) {
            System.out.println("Errrm there is no selected starting position, so I'm going to assume left.");
            return StartingPosition.LEFT;
        }
        return startingPositionChooser.getSelected(); // Default auto will be `Commands.none()`
    }
}
