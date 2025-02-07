package frc.robot.dashboard;

import java.util.ArrayList;

import frc.robot.Robot;
import frc.robot.dashboard.reactive.ReactiveEntry;
import frc.robot.dashboard.telemetry.TelemetryEntry;

/**
 * This is to be used for auto updating all the telemetry and reactive data on the dashboard.
 */
public class DashboardHandler {
    
    private static ArrayList<TelemetryEntry> telemetryEntries = new ArrayList<TelemetryEntry>();
    private static ArrayList<ReactiveEntry> reactiveEntries = new ArrayList<ReactiveEntry>();


    /**
     * This will call the update method for all the telemetry and reactive entries on the dashboard.
     */
    public static void updateDashboard() {
        for (TelemetryEntry entry : telemetryEntries) {
            entry.update();
        }
        for (ReactiveEntry entry : reactiveEntries) {
            entry.update();
        }
        Robot.dashboard.update();
    }


    public static void addTelemetryEntry(TelemetryEntry entry) {
        telemetryEntries.add(entry);
    }


    public static void addReactiveEntry(ReactiveEntry entry) {
        reactiveEntries.add(entry);
    }
}
