package main.java.frc.robot.field;

public class Field {
    public static final double BARGE_WIDTH = InchesToMeters(146.5);
    public static final double BARGE_LENGTH = InchesToMeters(46.0);
    public static final double BARGE_HEIGHT = InchesToMeters(101.0);

    public static final double REEF_WIDTH = InchesToMeters(65.5);
    public static final double DISTANCE_OF_REEF_TO_STARTING_LINE = InchesToMeters(88.0);
    public static final double CORAL_STATION_TO_REEF_X_COMPONET = InchesToMeters(144.0); 
    public static final double CORAL_STATION_TO_REEF_Y_COMPONET = InchesToMeters(109.13);   //this is 1/2 of DRIVE_WALL_Y_LENGTH
    public static final double CORAL_STATION_TO_REEF_DISTANCE = InchesToMeters(142.24);
    public static final double CORAL_STATION_TO_STARTING_LINE_X = InchesToMeters(231.66);
 
    public static final double DRIVE_WALL_Y_LENGTH = InchesToMeters(217.25);

    public static final double DISTANCE_OF_DRIVE_WALL_TO_CORALS_X = InchesToMeters(48.0);
    public static final double DISTANCE_Y_OF_CORAL_2 = CORAL_STATION_TO_REEF_Y_COMPONET + InchesToMeters(72.0);
    public static final double DISTANCE_Y_OF_CORAL_1 = CORAL_STATION_TO_REEF_Y_COMPONET;
    public static final double DISTANCE_Y_OF_CORAL_0 = CORAL_STATION_TO_REEF_Y_COMPONET - InchesToMeters(72.0);

    
    public static final double DISTANCE_OF_CENTER_OF_PROCCESOR_OPENING_TO_STARTING_LINE = InchesToMeters(61.76);
}
