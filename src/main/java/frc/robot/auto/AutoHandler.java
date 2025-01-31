package frc.robot.auto;

import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.auto.NamedCommands;
import com.pathplanner.lib.commands.PathPlannerAuto;
import com.pathplanner.lib.events.EventTrigger;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import frc.robot.Robot;
import frc.robot.subsystems.swerve.MoveForTimeAtSpeedCommand;

public class AutoHandler {

    private static SendableChooser<Command> autoChooser;
    

    /**
     * <p> This makes a list of our autos and puts them on the Dashboard.
     */
    public static void setupAutoSelector() {
        autoChooser = new SendableChooser<Command>();
        // autoChooser = AutoBuilder.buildAutoChooser(); 

        // Add commands to the chooser by copying the line below and changing the name & command
        autoChooser.addOption("Say Hi", new SayHiCommand());
        autoChooser.addOption("Move frowaards", MoveForTimeAtSpeedCommand.create(1, 0, 0, 1));
        autoChooser.addOption("Move Barckwaards", MoveForTimeAtSpeedCommand.create(-1, 0, 0, 1));
        // autoChooser.addOption("PP: One Meter Forward", new PathPlannerAuto("One Meter Forward"));


        SmartDashboard.putData("Selected Auto", autoChooser);
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
     * @return The currently selected auto or `Commands.none()` if no auto is selected.
     */
    public static Command getSelectedAuto() {
        return autoChooser.getSelected(); // Default auto will be `Commands.none()`
    }
}
