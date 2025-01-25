package frc.robot;

import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.auto.NamedCommands;
import com.pathplanner.lib.commands.PathPlannerAuto;
import com.pathplanner.lib.events.EventTrigger;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.subsystems.arm.ArmSubsystem;
import frc.robot.subsystems.leds.LEDSubsystem;
import frc.robot.subsystems.muncher.MuncherSubsystem;
import frc.robot.subsystems.swerve.SwerveSubsystem;
import frc.robot.subsystems.vision.VisionSubsystem;

/**
 * This is mainly for debugging purposes.
 * By commenting out the initialization lines for each subsystem, that will effectively disable the subsystem.
 * The rest of the code must account for any subsystem being null.
 * 
 * RobotMap will be statically initialized ONCE in Robot.java
 */
public class RobotMap {
    
    public ArmSubsystem arm = null;
    public LEDSubsystem leds = null;
    public MuncherSubsystem muncher = null;
    public SwerveSubsystem swerve = null;
    public VisionSubsystem vision = null;

    // For Path Planner testing
    private SendableChooser<Command> autoChooser;
    

    
    public RobotMap() {
        // COMMENT OUT SUBSYSTEMS BELOW TO DISABLE THEM
        // arm = new ArmSubsystem();
        // leds = new LEDSubsystem();
        // muncher = new MuncherSubsystem();
        // swerve = new SwerveSubsystem();
        vision = new VisionSubsystem();
    }


    public void setupPathPlanner() {
        if (swerve == null) {
            return;
        }
        NamedCommands.registerCommand("marker1", Commands.print("Passed marker 1"));
        NamedCommands.registerCommand("marker2", Commands.print("Passed marker 2"));
        NamedCommands.registerCommand("print hello", Commands.print("hello"));

        // Use event markers as triggers
        new EventTrigger("Example Marker").onTrue(Commands.print("Passed an event marker"));

        // Configure the trigger bindings
        configureBindings();

        autoChooser = AutoBuilder.buildAutoChooser(); // Default auto will be `Commands.none()`
        SmartDashboard.putData("Auto Mode", autoChooser);
    }


    /**
   * Use this method to define your trigger->command mappings. Triggers can be created via the
   * {@link Trigger#Trigger(java.util.function.BooleanSupplier)} constructor with an arbitrary
   * predicate, or via the named factories in {@link
   * edu.wpi.first.wpilibj2.command.button.CommandGenericHID}'s subclasses for {@link
   * CommandXboxController Xbox}/{@link edu.wpi.first.wpilibj2.command.button.CommandPS4Controller
   * PS4} controllers or {@link edu.wpi.first.wpilibj2.command.button.CommandJoystick Flight
   * joysticks}.
   * 
   * This is scary; send help
   */
  private void configureBindings() {
    // Add a button to run the example auto to SmartDashboard, this will also be in the auto chooser built above
    SmartDashboard.putData("1 Meter Forward", new PathPlannerAuto("1 Meter Forward Auto"));
  }

}
