package frc.robot.auto;

import com.pathplanner.lib.auto.NamedCommands;
import com.pathplanner.lib.commands.PathPlannerAuto;
import com.pathplanner.lib.events.EventTrigger;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.PrintCommand;
import frc.robot.Robot;
import frc.robot.subsystems.arm.ScoreL2Command;
import frc.robot.subsystems.swerve.MoveDistanceCommand;
import frc.robot.subsystems.swerve.MoveForTimeAtSpeedCommand;
import frc.robot.subsystems.swerve.SwerveSubsystem;
import edu.wpi.first.wpilibj2.command.sysid.SysIdRoutine;

public class AutoHandler {

    private static SendableChooser<Command> autoChooser;
    private static SendableChooser<StartingPosition> startingPositionChooser;
    private static SendableChooser<Command> testChooser;
    

    /**
     * <p> This makes a list of our autos and puts them on the Dashboard.
     */
    public static void setupAutoSelector() {
        autoChooser = new SendableChooser<Command>();
        startingPositionChooser = new SendableChooser<StartingPosition>();
        testChooser = new SendableChooser<Command>();

        // Add commands to the chooser by copying the line below and changing the name & command
        autoChooser.addOption("Say Hi (Do Nothing)", new SayHiCommand());
        autoChooser.addOption("Move 2 Meters Backward", MoveDistanceCommand.create(-2, 0, new Rotation2d(0)));
        autoChooser.addOption("Move 1m Back, 1m Left, and Rotate 180 degrees", MoveDistanceCommand.create(-1, 1, new Rotation2d(Units.degreesToRadians(180))));
        autoChooser.addOption("Move frowaards", MoveForTimeAtSpeedCommand.create(0.3, 0, 0, 2.5));
        autoChooser.addOption("Move Barckwaards", MoveForTimeAtSpeedCommand.create(-0.3, 0, 0, 2.5));
        autoChooser.addOption("Score L2", ScoreL2Command.create(2)); // command not finished
        // Only add the path planner stuff if swerve is initialized
        if (Robot.map.swerve != null) {
            autoChooser.addOption("PP: One Meter Forward", new PathPlannerAuto("One Meter Forward"));
        }

        // This is for choosing which spot we started in
        startingPositionChooser.addOption("Blue - LEFT", StartingPosition.BLUE_LEFT);
        startingPositionChooser.addOption("Blue - CENTER", StartingPosition.BLUE_CENTER);
        startingPositionChooser.addOption("Blue - RIGHT", StartingPosition.BLUE_RIGHT);
        startingPositionChooser.addOption("Red - LEFT", StartingPosition.RED_LEFT);
        startingPositionChooser.addOption("Red - CENTER", StartingPosition.RED_CENTER);
        startingPositionChooser.addOption("Red - RIGHT", StartingPosition.RED_RIGHT);

        // Put the choosers on the dashboard
        testChooser.addOption("Swerve: quasistatic - forward", Robot.map.swerve.sysIdRoutine.quasistatic(SysIdRoutine.Direction.kForward));
        testChooser.addOption("Swerve: quasistatic - reverse", Robot.map.swerve.sysIdRoutine.quasistatic(SysIdRoutine.Direction.kReverse));
        testChooser.addOption("Swerve: dynamic - forward", Robot.map.swerve.sysIdRoutine.dynamic(SysIdRoutine.Direction.kForward));
        testChooser.addOption("Swerve: dynamic - reverse", Robot.map.swerve.sysIdRoutine.dynamic(SysIdRoutine.Direction.kReverse));
 
        SmartDashboard.putData("Auto/Selected Auto", autoChooser);
        SmartDashboard.putData("Auto/Starting Position", startingPositionChooser);
        SmartDashboard.putData("Auto/Test Chooser", testChooser);
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
            return StartingPosition.BLUE_LEFT;
        }
        return startingPositionChooser.getSelected(); // Default auto will be `Commands.none()`
    }
}
