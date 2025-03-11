package frc.robot.auto;

import java.util.Map;

import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.math.geometry.Translation3d;
import edu.wpi.first.wpilibj.LEDPattern;
import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.Robot;
import frc.robot.subsystems.leds.DisplayLEDPatternCommand;
import frc.robot.subsystems.leds.StupidColor;
import frc.robot.subsystems.vision.CameraState;

/**
 * When we're at the reef, this command works with both vision and swerve to align our bot with the April Tag.
 */
public class AlignWithAprilTag extends Command {

    private LEDPattern initialPattern;

    private int aprilTagID;

    /** This stores the current robot position in relation to the april tag by the reef. (for example, if the robot is 1m away, this would be (1m, 0m, 0rads)) */
    private Transform3d positionRelativeToAprilTag;
    private Translation3d desiredDistanceFromAprilTag;
    
    /**
     * DO NOT USE THE CONSTRUCTOR! Use AlignWithAprilTag.create() instead!
     */
    private AlignWithAprilTag() {}


    /**
     * DO NOT USE THE CONSTRUCTOR! Use AlignWithAprilTag.create() instead!
     * This will align our robot with the april tag on the side of the reef we're trying to score at.
     * @param aprilTagID The ID of the april tag on the side of the reef we want to score on.
     * @param scoreStrategy 0 -> don't adjust aligment, 1 -> left rung adjustment for coral placement, 2 -> right rung adjustment for coral placement
     * @return
     */
    private AlignWithAprilTag(int aprilTagID, int scoreStrategy) {
        this.aprilTagID = aprilTagID;
        // TODO: Make the scoreRightOfAprilTag do something. It should affect the x:0.0 to be something like x:0.14, x:-0.14
        double xOffset = 0.0;
        switch(scoreStrategy) {
            case 0:
                xOffset = 0.0;
                break;
            case 1:
                xOffset = -0.16;
                break;
            case 2:
                xOffset = 0.16;
                break;
        }

        this.desiredDistanceFromAprilTag = new Translation3d(xOffset, 0.46, 0.0);
    }


    /**
     * This will align our robot with the april tag on the side of the reef we're trying to score at.
     * @param aprilTagID The ID of the april tag on the side of the reef we want to score on.
     * @param scoreStrategy 0 -> don't adjust aligment, 1 -> left rung adjustment for coral placement, 2 -> right rung adjustment for coral placement
     * @return
     */
    public static Command create(int aprilTagID, int scoreStrategy) {
        return new AlignWithAprilTag(aprilTagID, scoreStrategy).withTimeout(3.0);
    }


    public static Command create(int scoreStrategy) {
        return AlignWithAprilTag.create((int)Robot.map.vision.getLastSideAprilTagID(), scoreStrategy);
        // if (Robot.map.vision.getCameraState() == CameraState.FRONT_REEF_TAG) {
        //     return AlignWithAprilTag.create((int)Robot.map.vision.getLastAprilTagID(), scoreStrategy);
        // }
        // else {
        //     return AlignWithAprilTag.create((int)Robot.map.vision.getLastSideAprilTagID(), scoreStrategy);
        // }
    }


    /**
     * This will align our robot with the april tag on the side of the reef we're trying to score at.
     * @param aprilTagID The ID of the april tag on the side of the reef we want to score on.
     * @param scoreStrategy 0 -> don't adjust aligment, 1 -> left rung adjustment for coral placement, 2 -> right rung adjustment for coral placement
     * @param duration The MAXIMUM length this command can run for in seconds!
     * @return
     */
    public static Command create(int aprilTagID, int scoreStrategy, double duration) {
        return new AlignWithAprilTag(aprilTagID, scoreStrategy).withTimeout(duration);
    }


    @Override
    public void initialize() {
        System.out.println(aprilTagID);

        // Robot.driveController.setRumble(RumbleType.kLeftRumble, 0.3);
        if (Robot.map.leds != null) {
            initialPattern = Robot.map.leds.getPreviousPattern();
        }
    }


    /**
     * This will try to constantly get the robot to align with the april tag.
     */
    @Override
    public void execute() {
        if (aprilTagID == -1) {
            aprilTagID = (int)Robot.map.vision.getLastSideAprilTagID();
        }
        Transform3d currentPosition = Robot.map.vision.getRobotRelativeToAprilTag(aprilTagID);

        // We should only do stuff IF we can see the april tag...
        if (currentPosition != null) {
            positionRelativeToAprilTag = currentPosition;
        }

        // Should I use PID for this? Probably. Will I? Haha nope!
        if (positionRelativeToAprilTag != null) {
            // All my homies HATE the z axis #XYSuperiority
            Robot.map.swerve.setFieldOriented(false);
            Robot.map.swerve.setDesiredSpeeds( 
                Math.signum(positionRelativeToAprilTag.getTranslation().minus(desiredDistanceFromAprilTag).getY())*0.1, 
                -Math.signum(positionRelativeToAprilTag.getTranslation().minus(desiredDistanceFromAprilTag).getX())*0.1,
                0
            );
            SmartDashboard.putNumber("Testing/April Tag", aprilTagID);
            SmartDashboard.putNumber("Testing/Pos X to April Tag", positionRelativeToAprilTag.getX());
            SmartDashboard.putNumber("Testing/Pos Y to April Tag", positionRelativeToAprilTag.getY());

            positionRelativeToAprilTag = positionRelativeToAprilTag.plus(new Transform3d(Robot.map.swerve.getLastPositionChange()));
        }

        if (Robot.map.leds != null) {
            Robot.map.leds.setPattern(LEDPattern.steps(Map.of(0.0, new StupidColor(Color.kRed), Math.max(desiredDistanceFromAprilTag.getDistance(positionRelativeToAprilTag.getTranslation())*70.0, 0), new StupidColor(Color.kYellow))));
        }
    }


    @Override
    public boolean isFinished() {
        // Why would we schedule this if vision is null?...
        if (Robot.map.vision == null || Robot.map.swerve == null) {
            return true;
        }
        if (positionRelativeToAprilTag != null && desiredDistanceFromAprilTag.getDistance(positionRelativeToAprilTag.getTranslation()) < 0.01) {
            return true;
        }
        return false;
    }


    @Override
    public void end(boolean isInterrupted) {
        System.out.println("Alignment Complete!");
        // Robot.driveController.setRumble(RumbleType.kLeftRumble, 0.3);
        if (Robot.map.leds != null) {
            CommandScheduler.getInstance().schedule(DisplayLEDPatternCommand.create(
                LEDPattern.solid(new StupidColor(Color.kLightGreen)),
                initialPattern,
                3
            ));
        }
        Robot.map.swerve.setDesiredSpeeds(0, 0, 0);
    }
}
