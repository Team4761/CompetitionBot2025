package frc.robot.field;

import edu.wpi.first.math.util.Units;
import edu.wpi.first.math.geometry.Rotation2d;

public class Field {
    //BARGE
    public static final double BARGE_WIDTH = Units.inchesToMeters(146.5);
    public static final double BARGE_LENGTH = Units.inchesToMeters(46.0);
    public static final double BARGE_HEIGHT = Units.inchesToMeters(101.0);
    //REEF
    public static final double REEF_WIDTH = Units.inchesToMeters(65.5);
    public static final double DISTANCE_OF_REEF_TO_STARTING_LINE = Units.inchesToMeters(88.0);
    public static final double SAME_SIDE_OF_REEF_SEPRATION_DISTANCE = Units.inchesToMeters(13.0);
    public static final double REEF_PIPE_DAIMETER = Units.inchesToMeters(1.25); // not sure if radius or diameter but 90% sure its diameter (pg 24)
    public static final double L1_FRONT_EDGE_HEIGHT = Units.inchesToMeters(18);
    public static final double L2_HEIGHT = Units.inchesToMeters(31.875);
    public static final Rotation2d L2_ANGLE = new Rotation2d(Units.degreesToRadians(35.0)); //In degress
    public static final double L2_INSET = Units.inchesToMeters(1.625);
    public static final double L3_HEIGHT = Units.inchesToMeters(47.625);
    public static final Rotation2d L3_ANGLE = new Rotation2d(Units.degreesToRadians(35.0)); //In degress
    public static final double L3_INSET = Units.inchesToMeters(1.625);
    public static final double L4_HEIGHT = Units.inchesToMeters(72.0);
    public static final Rotation2d L4_ANGLE = new Rotation2d(Units.degreesToRadians(90.0)); //In degress Dont know if 90 is vertical but I think so
    public static final double L4_INSET = Units.inchesToMeters(1.125);

    public static final double CORAL_STATION_TO_REEF_X_COMPONET = Units.inchesToMeters(144.0); 
    public static final double CORAL_STATION_TO_REEF_Y_COMPONET = Units.inchesToMeters(109.13); //this is 1/2 of DRIVE_WALL_Y_LENGTH
    public static final double CORAL_STATION_TO_REEF_DISTANCE = Units.inchesToMeters(142.24);
    public static final double CORAL_STATION_TO_STARTING_LINE_X = Units.inchesToMeters(231.66);
    public static final double CORAL_STATION_HEIGHT_FROM_GROUND = Units.inchesToMeters(41.5);   // distance from carpet to to bottom of opening and 4 in to account for the diamater of the tube
    public static final double CORAL_STATION_OPENING = Units.inchesToMeters(7);    // height of opening
    public static final double WIDTH_OF_CORAL_OPENING = Units.inchesToMeters(66);
 
    public static final double DRIVE_WALL_Y_LENGTH = Units.inchesToMeters(217.25);
    //CORAL POSTIONS 
    public static final double DISTANCE_OF_DRIVE_WALL_TO_CORALS_X = Units.inchesToMeters(48.0);
    public static final double DISTANCE_OF_CORAL_TO_STARTING_LINE = Units.inchesToMeters(249.49);
    public static final double DISTANCE_Y_OF_CORAL_2 = CORAL_STATION_TO_REEF_Y_COMPONET + Units.inchesToMeters(72.0);
    public static final double DISTANCE_Y_OF_CORAL_1 = CORAL_STATION_TO_REEF_Y_COMPONET;
    public static final double DISTANCE_Y_OF_CORAL_0 = CORAL_STATION_TO_REEF_Y_COMPONET - Units.inchesToMeters(72.0);
    //CORAL PEICES
    public static final double CORAL_DIAMETER = Units.inchesToMeters(4.0);
    public static final double CORAL_LENGTH = Units.inchesToMeters(11.875);
    //ALGEE PEICES
    public static final double ALGEE_RADIUS = Units.inchesToMeters(16.0);
    //PROCESSOR
    public static final double DISTANCE_OF_CENTER_OF_PROCCESOR_OPENING_TO_STARTING_LINE = Units.inchesToMeters(61.76);
    public static final double PROCESSOR_OPENING_WIDTH = Units.inchesToMeters(28.0);
    public static final double PROCESSOR_OPENING_HEIGHT = Units.inchesToMeters(20.0);
    public static final double PROCESSOR_OPENING_HIEHGT_FROM_GROUND = Units.inchesToMeters(7.0);
    //APRIL TAGS
    public static final double CORAL_STATION_APRIL_TAG_HEIGHT = Units.inchesToMeters(53.25); //To the bottom of the paper tag
    public static final double PROCESSOR_APRIL_TAG_HEIGHT = Units.inchesToMeters(45.875); //To the bottom of the paper tag
    public static final double REEF_APRIL_TAG_HEIGHT = Units.inchesToMeters(6.875); //To the bottom of the paper tag
    public static final double BARGE_APRIL_TAG_HEIGHT = Units.inchesToMeters(69.0); //To the bottom of the paper tag
    
    //CAGES
    public static final double CAGE_HEIGHT = Units.inchesToMeters(24.0);
    public static final double CAGE_WIDTH = Units.inchesToMeters(7.375);

    public static final double SHALLOW_HANG_HEIGHT= Units.inchesToMeters(29.375); //To the very bottom of the cage
    public static final double DEEP_HANG_HEIGHT= Units.inchesToMeters(3.5); //To the very bottom of the cage
    public static final double FIELD_WIDTH = 7.668;
}
