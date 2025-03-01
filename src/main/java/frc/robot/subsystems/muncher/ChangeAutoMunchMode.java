package frc.robot.subsystems.muncher;

import edu.wpi.first.wpilibj2.command.Command;

public class ChangeAutoMunchMode extends Command {
    private boolean autoMunchMode;
    public ChangeAutoMunchMode(boolean autoMunchMode) { this.autoMunchMode = autoMunchMode; }
    public void initialize() { YeetCommand.autoMunchMode = this.autoMunchMode; }
    public boolean isFinished() { return true; }
 }
