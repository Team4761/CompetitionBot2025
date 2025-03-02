package frc.robot.auto;

import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.math.geometry.Translation3d;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Robot;

/**
 * When we're at the reef, this command works with both vision and swerve to align our bot with the April Tag.
 */
public class AlignWithAprilTag extends Command {

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


    /**
     * This will try to constantly get the robot to align with the april tag.
     */
    @Override
    public void execute() {
        Transform3d currentPosition = Robot.map.vision.getRobotRelativeToAprilTag(aprilTagID);

        // We should only do stuff IF we can see the april tag...
        if (currentPosition != null) {
            positionRelativeToAprilTag = currentPosition;
        }

        // Should I use PID for this? Probably. Will I? Haha nope!
        // All my homies HATE the z axis #XYSuperiority
        Robot.map.swerve.setDesiredSpeeds(
            Math.signum(positionRelativeToAprilTag.getTranslation().minus(desiredDistanceFromAprilTag).getX())*0.05, 
            Math.signum(positionRelativeToAprilTag.getTranslation().minus(desiredDistanceFromAprilTag).getY())*0.05, 
            0
        );
    }


    @Override
    public boolean isFinished() {
        // Why would we schedule this if vision is null?...
        if (Robot.map.vision == null || Robot.map.swerve == null) {
            return true;
        }
        // TODO: Implement this
        return false;
    }


    @Override
    public void end(boolean isInterrupted) {
        Robot.map.swerve.setDesiredSpeeds(0, 0, 0);
    }
}
