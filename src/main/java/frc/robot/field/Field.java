package main.java.frc.robot.field;

public class Field {
    //BARGE
    public static final double BARGE_WIDTH = InchesToMeters(146.5);
    public static final double BARGE_LENGTH = InchesToMeters(46.0);
    public static final double BARGE_HEIGHT = InchesToMeters(101.0);
    //REEF
    public static final double REEF_WIDTH = InchesToMeters(65.5);
    public static final double DISTANCE_OF_REEF_TO_STARTING_LINE = InchesToMeters(88.0);
    public static final double SAME_SIDE_OF_REEF_SEPRATION_DISTANCE = InchesToMeters(13.0);
    public static final double REEF_PIPE_DAIMETER = InchesToMeters(1.25); // not sure if radius or diameter but 90% sure its diameter (pg 24)
    public static final double L1_FRONT_EDGE_HEIGHT = InchesToMeters(18);
    public static final double L2_HEIGHT = InchesToMeters(31.875);
    public static final double L2_ANGLE = 35.0; //In degress
    public static final double L2_INSET = InchesToMeters(1.625);
    public static final double L3_HEIGHT = InchesToMeters(47.625);
    public static final double L3_ANGLE = 35.0; //In degress
    public static final double L3_INSET = InchesToMeters(1.625);
    public static final double L4_HEIGHT = InchesToMeters();
    public static final double L4_ANGLE = 90; //In degress Dont know if 90 is vertical but I think so
    public static final double L4_INSET = InchesToMeters(1.125);

    public static final double CORAL_STATION_TO_REEF_X_COMPONET = InchesToMeters(144.0); 
    public static final double CORAL_STATION_TO_REEF_Y_COMPONET = InchesToMeters(109.13);   //this is 1/2 of DRIVE_WALL_Y_LENGTH
    public static final double CORAL_STATION_TO_REEF_DISTANCE = InchesToMeters(142.24);
    public static final double CORAL_STATION_TO_STARTING_LINE_X = InchesToMeters(231.66);
 
    public static final double DRIVE_WALL_Y_LENGTH = InchesToMeters(217.25);
    //CORAL POSTIONS 
    public static final double DISTANCE_OF_DRIVE_WALL_TO_CORALS_X = InchesToMeters(48.0);
    public static final double DISTANCE_OF_CORAL_TO_STARTING_LINE = InchesToMeters(249.49);
    public static final double DISTANCE_Y_OF_CORAL_2 = CORAL_STATION_TO_REEF_Y_COMPONET + InchesToMeters(72.0);
    public static final double DISTANCE_Y_OF_CORAL_1 = CORAL_STATION_TO_REEF_Y_COMPONET;
    public static final double DISTANCE_Y_OF_CORAL_0 = CORAL_STATION_TO_REEF_Y_COMPONET - InchesToMeters(72.0);
    //CORAL PEICES
    public static final double CORAL_DIAMETER =InchesToMeters(4.0);
    public static final double CORAL_LENGTH =InchesToMeters(11.875);
    //ALGEE PEICES
    public static final double ALGEE_RADIUS = InchesToMeters(16.0);
    //PROCESSOR
    public static final double DISTANCE_OF_CENTER_OF_PROCCESOR_OPENING_TO_STARTING_LINE = InchesToMeters(61.76);
    public static final double PROCESSOR_OPENING_WIDTH = InchesToMeters(28.0);
    public static final double PROCESSOR_OPENING_HEIGHT = InchesToMeters(20.0);
    public static final double PROCESSOR_OPENING_HIEHGT_FROM_GROUND = InchesToMeters(7.0);
    //APRIL TAGS
    public static final double CORAL_STATION_APRIL_TAG_HEIGHT =InchesToMeters(53.25); //To the bottom of the tag
    public static final double PROCESSOR_APRIL_TAG_HEIGHT =InchesToMeters(45.875); //To the bottom of the tag
    public static final double REEF_APRIL_TAG_HEIGHT =InchesToMeters(6.875); //To the bottom of the tag
    public static final double BARGE_APRIL_TAG_HEIGHT =InchesToMeters(69.0); //To the bottom of the tag
}
