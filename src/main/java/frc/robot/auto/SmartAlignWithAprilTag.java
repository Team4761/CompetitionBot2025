package frc.robot.auto;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Transform2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.PrintCommand;
import frc.robot.Robot;
import frc.robot.subsystems.swerve.GetToFieldPositionCommand;

/**
 * This will attempt to align with the nearest reef april tag.
 */
public class SmartAlignWithAprilTag extends Command {

    // All of these poses assume that towards the red alliance is 0 degrees. CCW is positive. And all of these are made to align our robot FACING the reef.
    private final static Pose2d[] blueReefPositions = {
        // Starts at ID 17
        new Pose2d(3.801, 2.891, new Rotation2d(Units.degreesToRadians(60))),       // ID 17
        new Pose2d(3.140, 4.026, new Rotation2d(Units.degreesToRadians(0))),        // ID 18
        new Pose2d(3.817, 5.189, new Rotation2d(Units.degreesToRadians(-60))),      // ID 19
        new Pose2d(5.184, 5.220, new Rotation2d(Units.degreesToRadians(-120))),     // ID 20
        new Pose2d(5.830, 4.026, new Rotation2d(Units.degreesToRadians(180))),      // ID 21
        new Pose2d(5.214, 2.876, new Rotation2d(Units.degreesToRadians(120)))       // ID 22
        // Ends at ID 22
    };

    private final static Pose2d[] redReefPositions = {
        // Starts at ID 6
        new Pose2d(13.824, 2.861, new Rotation2d(Units.degreesToRadians(120))),     // ID 6
        new Pose2d(14.395, 4.026, new Rotation2d(Units.degreesToRadians(180))),     // ID 7
        new Pose2d(13.809, 5.220, new Rotation2d(Units.degreesToRadians(-120))),    // ID 8
        new Pose2d(12.426, 5.174, new Rotation2d(Units.degreesToRadians(-60))),     // ID 9
        new Pose2d(11.720, 4.026, new Rotation2d(Units.degreesToRadians(0))),       // ID 10
        new Pose2d(12.441, 2.861, new Rotation2d(Units.degreesToRadians(60)))       // ID 11
        // Ends at ID 11
    };
    
    /**
     * DO NOT USE THE CONSTRUCTOR! Use AlignWithAprilTag.create() instead!
     */
    private SmartAlignWithAprilTag() {}


    /**
     * This will align our robot with the april tag on the side of the reef we're trying to score at.
     * @param scoreStrategy 0 -> don't adjust aligment, 1 -> left rung adjustment for coral placement, 2 -> right rung adjustment for coral placement
     * @param facingTheReef If true, this will align the bot to be facing the reef. If false, it will align the bot to be able to yeet out coral (rather than outtake).
     * @param duration The MAXIMUM length this command can run for in seconds!
     * @return
     */
    public static Command create(int scoreStrategy, boolean facingTheReef, double duration) {
        if (Robot.map.vision == null) {
            return new PrintCommand("Vision is null! Cannot perform a SmartAlignWithAprilTag.");
        }
        if (Robot.map.swerve == null) {
            return new PrintCommand("Swerve is null! Cannot perform a SmartAlignWithAprilTag.");
        }
        Pose2d targetPosition = getNearestReefPosition();

        // The offset side-to-side of the reef (so we can do left rung, center algae, and right rung)
        double xOffset = 0.0;
        switch(scoreStrategy) {
            case 0:
                xOffset = 0.60;
                break;
            case 1:
                xOffset = 0.44;
                break;
            case 2:
                xOffset = 0.76;
                break;
        }

        // Apply the offset from the reef.
        Pose2d offset = new Pose2d(new Translation2d(-0.40, xOffset), new Rotation2d()).rotateBy(targetPosition.getRotation());
        targetPosition = targetPosition.plus(new Transform2d(offset.getX(), offset.getY(), new Rotation2d()));
        return GetToFieldPositionCommand.create(targetPosition).withTimeout(duration);
    }


    private static Pose2d getNearestReefPosition() {
        Pose2d currentPosition = Robot.map.vision.getFieldPose().toPose2d();
        Pose2d closestReefPosition = null;
        double closestDistance = Double.MAX_VALUE;

        if (DriverStation.getAlliance().get() == DriverStation.Alliance.Blue) {
            for (int i = 0; i < blueReefPositions.length; i++) {
                if (currentPosition.getTranslation().getDistance(blueReefPositions[i].getTranslation()) < closestDistance) {
                    closestDistance = currentPosition.getTranslation().getDistance(blueReefPositions[i].getTranslation());
                    closestReefPosition = blueReefPositions[i];
                }
            }
        }
        else {
            for (int i = 0; i < redReefPositions.length; i++) {
                if (currentPosition.getTranslation().getDistance(redReefPositions[i].getTranslation()) < closestDistance) {
                    closestDistance = currentPosition.getTranslation().getDistance(redReefPositions[i].getTranslation());
                    closestReefPosition = redReefPositions[i];
                }
            }
        }

        return closestReefPosition;
    }
}
