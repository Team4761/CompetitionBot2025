package frc.robot.subsystems.vision;

import java.util.List;
import java.util.Optional;

import org.photonvision.EstimatedRobotPose;
import org.photonvision.PhotonCamera;
import org.photonvision.PhotonPoseEstimator;
import org.photonvision.PhotonPoseEstimator.PoseStrategy;
import org.photonvision.PhotonUtils;
import org.photonvision.targeting.PhotonPipelineResult;
import org.photonvision.targeting.PhotonTrackedTarget;

import edu.wpi.first.apriltag.AprilTag;
import edu.wpi.first.apriltag.AprilTagFieldLayout;
import edu.wpi.first.apriltag.AprilTagFields;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Robot;

/**
 * This does NOT find April Tags, that is handled by the Orange Pi. Instead, this allows the robot to react to where the Orange Pi found april tags.
 * Vision works with the following pipeline: Camera (RAW_DATA) --> Orange Pi (APRIL_TAG_SPOTS) --> Radio (NETWORKING) --> RoboRIO (REACTING_TO_DATA) & Driverstation (DEBUGGING)
 * 
 * If using our Microsoft cams, we should have them set to 640x480 @ 30fps (can be set easily through shuffleboard)
 * 
 * Most of the stuff here was found using the PhotonVision dashboard. You can get there by plugging the Orange Pi into power and ethernet TO A RADIO (not a computer), 
 * and then going to psebastian.local:5800 on google on the driverstation.
 */
public class VisionSubsystem extends SubsystemBase {

    // +x = forwards. +y = left. +z = up. Rotation is AROUND those axises in a counterclockwise direction! So roll = rotation AROUND +x.
    public static final Transform3d FRONT_CAMERA_ON_ROBOT_POSE = new Transform3d(Units.inchesToMeters(-8.5), Units.inchesToMeters(-13.5), Units.inchesToMeters(8), new Rotation3d(0, 0, 0));
    public static final Transform3d SIDE_CAMERA_ON_ROBOT_POSE = new Transform3d(Units.inchesToMeters(-11), Units.inchesToMeters(-14), Units.inchesToMeters(8), new Rotation3d(0, 0, Units.degreesToRadians(-90)));

    // Actual field layout for when testing is done.
    // If the following line is throwing an error message, you don't have the most up-to-date WPILib version.
    // This requires version 2025.2.1+ to work.
    private static final AprilTagFieldLayout APRIL_TAG_FIELD_LAYOUT = AprilTagFieldLayout.loadField(AprilTagFields.k2025Reefscape);

    /** This stores the time that when each april tag was seen (in ms from System.currentTimeMillis()). For example, to get the time that April Tag 12 was seen, you can do timesSinceSeenTags[12]. -1 If we haven't seen the tag.*/
    private long[] timesSinceSeenTags = new long[25];

    //  This is the last recorded pose by vision (which tries to update its pose in the periodic method)
    // (0,0) represents the left corner of the blue alliance-wall, looking towards the red alliance. Towards the red alliance is +x, towards the other side of the alliance wall is +y.
    private Pose3d fieldPosition;
    private int aprilTagID = -1;
    private double latency = 0;
    private double lastTimestamp = 0;
    private boolean foundAprilTag = false;

    private Pose3d sideFieldPosition;
    private int sideAprilTagID = -1;
    private double sideLatency = 0;
    private double sideLastTimestamp = 0;
    private boolean sideFoundAprilTag = false;

    private List<PhotonPipelineResult> results;

    // Default hostname is "photonvision", but we changed that to "CAMERA_NAME"
    private PhotonCamera frontCamera;
    private PhotonCamera sideCamera;

    // Used to calculate a pose over time
    PhotonPoseEstimator photonPoseEstimator;

    // A tag Pose is the CENTER of the tag.
    // The rotation assumes that facing AWAY from the blue alliance wall is 0 degrees, rotating counter clockwise.
    // PLACEHOLDER FOR TESTING PURPOSES
    // private final AprilTagFieldLayout APRIL_TAG_FIELD_LAYOUT = new AprilTagFieldLayout(
    //     List.of(
    //         new AprilTag(2, new Pose3d(10, 10, 0, new Rotation3d(0, 0, Math.PI))),   // Should be facing towards the blue alliance... I think...
    //         new AprilTag(4, new Pose3d(3, 3, 0, new Rotation3d(0, 0, Math.PI)))
    //     ),
    //     100,
    //     100
    // );


    /**
     * This will be in charge of setting up the cameras and pose estimator.
     */
    public VisionSubsystem() {
        frontCamera = new PhotonCamera("Front Camera");
        sideCamera = new PhotonCamera("Right Camera");
        // Other camera is called "Right Camera"

        // MULTI_TAG_PNP_ON_COPROCESSOR is best, but we're using CLOSEST_TO_LAST_POSE for now.
        photonPoseEstimator = new PhotonPoseEstimator(APRIL_TAG_FIELD_LAYOUT, PoseStrategy.MULTI_TAG_PNP_ON_COPROCESSOR, FRONT_CAMERA_ON_ROBOT_POSE);

        // Maybe make this actually check for the robot's position once on startup?
        fieldPosition = new Pose3d();

        for (int i = 0; i < timesSinceSeenTags.length; i++) {
            timesSinceSeenTags[i] = -1;
        }
    }


    /**
     * During the periodic method, I am going to log all of the data and calculate the robot pose relative to the entire field.
     */
    @Override
    public void periodic() {
        updateFrontCamera();
        updateRightCamera();
    }


    /**
     * This will get the most up-to-date info from the camera on the front of the robot.
     * This is called in the periodic method.
     */
    public void updateFrontCamera() {
        // Get the last processed frame (technically just results) from the camera.
        results = frontCamera.getAllUnreadResults();
        
        // Check for if there are any April Tags in the result
        for (PhotonPipelineResult result : results) {
            if (result.hasTargets()) {
                // List<PhotonTrackedTarget> targets = result.getTargets();
                PhotonTrackedTarget target = result.getBestTarget();
                
                // Calculate robot's field relative pose
                if (APRIL_TAG_FIELD_LAYOUT.getTagPose(target.getFiducialId()).isPresent()) {
                    fieldPosition = PhotonUtils.estimateFieldToRobotAprilTag(
                        target.getBestCameraToTarget(), // The position of the April Tag relative to the camera
                        APRIL_TAG_FIELD_LAYOUT.getTagPose(target.getFiducialId()).get(),   // The position of the April Tag in the field
                        FRONT_CAMERA_ON_ROBOT_POSE    // Transform of the robot relative to the camera. (center of the robot is 0,0)
                    );

                    // This "should" do the same thing as above, but I want to compare the two for differences.
                    Optional<EstimatedRobotPose> estimatedPoseMaybeNull = photonPoseEstimator.update(result);

                    if (estimatedPoseMaybeNull.isPresent()) {
                        EstimatedRobotPose estimatedPose = estimatedPoseMaybeNull.get();
                        SmartDashboard.putNumber("X - Estimated Vision Pose", estimatedPose.estimatedPose.getX());
                        SmartDashboard.putNumber("Y - Estimated Vision Pose", estimatedPose.estimatedPose.getY());
                        SmartDashboard.putNumber("Z - Estimated Vision Pose", estimatedPose.estimatedPose.getZ());
                    }

                    aprilTagID = target.getFiducialId();
                    latency = result.getTimestampSeconds() - lastTimestamp;
                    lastTimestamp = result.getTimestampSeconds();
                    foundAprilTag = true;

                    if (aprilTagID > 0 && aprilTagID < timesSinceSeenTags.length) {
                        timesSinceSeenTags[aprilTagID] = System.currentTimeMillis();
                    }
                }
            }
            else {
                foundAprilTag = false;
            }
        }
    }


    public void updateRightCamera() {
        // Get the last processed frame (technically just results) from the camera.
        results = sideCamera.getAllUnreadResults();
        
        // Check for if there are any April Tags in the result
        for (PhotonPipelineResult result : results) {
            if (result.hasTargets()) {
                // List<PhotonTrackedTarget> targets = result.getTargets();
                PhotonTrackedTarget target = result.getBestTarget();
                
                // Calculate robot's field relative pose
                if (APRIL_TAG_FIELD_LAYOUT.getTagPose(target.getFiducialId()).isPresent()) {
                    fieldPosition = PhotonUtils.estimateFieldToRobotAprilTag(
                        target.getBestCameraToTarget(), // The position of the April Tag relative to the camera
                        APRIL_TAG_FIELD_LAYOUT.getTagPose(target.getFiducialId()).get(),   // The position of the April Tag in the field
                        SIDE_CAMERA_ON_ROBOT_POSE    // Transform of the robot relative to the camera. (center of the robot is 0,0)
                    );

                    sideAprilTagID = target.getFiducialId();
                    sideLatency = result.getTimestampSeconds() - sideLastTimestamp;
                    sideLastTimestamp = result.getTimestampSeconds();
                    sideFoundAprilTag = true;

                    if (aprilTagID > 0 && aprilTagID < timesSinceSeenTags.length) {
                        timesSinceSeenTags[aprilTagID] = System.currentTimeMillis();
                    }
                }
            }
            else {
                sideFoundAprilTag = false;
            }
        }
    }


    /**
     * This uses the most up to date vision information to overwrite the swerve drive position.
     */
    public void updateRobotPositionFromAprilTags() {
        if (Robot.map.swerve == null) {
            return;
        }

        Robot.map.swerve.resetPosition(new Pose2d(
            fieldPosition.getX(),
            fieldPosition.getY(),
            fieldPosition.getRotation().toRotation2d()
        ));
    }


    /**
     * Checks the cameras and the time since it last saw an april tag.
     * @return The current CameraState.
     */
    public int getCameraState() {
        // The following are the reef april tags:
        // RED: 20, 21, 22
        // BLUE: 9, 10, 11
        // Only uses the data if the camera has seen a reef april tag in the last 1 second.

        // Side Camera
        if (((sideAprilTagID >= 6 && sideAprilTagID <= 11) || (sideAprilTagID >= 17 && sideAprilTagID <= 22)) && timesSinceSeenTags[sideAprilTagID] >= System.currentTimeMillis()-1000) {
            return CameraState.SIDE_REEF_TAG;
        }
        // Front Camera
        if (((aprilTagID >= 6 && aprilTagID <= 11) || (aprilTagID >= 17 && aprilTagID <= 22)) && timesSinceSeenTags[aprilTagID] >= System.currentTimeMillis()-1000) {
            return CameraState.FRONT_REEF_TAG;
        }
        return CameraState.NO_APRIL_TAG;
    }


    /**
     * This will iterate through the last list of april tags that were found and give the relative position of the robot in relation to the april tag.
     * @param aprilTagID The ID of the april tag to look for.
     * @return The position of the robot's center RELATIVE to the april tag in meters! null if the april tag was not found.
     */
    public Transform3d getRobotRelativeToAprilTag(int aprilTagID) {
        // TODO: Make this work with Pose3d (as in get rotational information of the robot as well)
        for (PhotonPipelineResult result : results) {
            if (result.hasTargets()) {
                PhotonTrackedTarget target = result.getBestTarget();
                if (target.getFiducialId() == aprilTagID) {
                    return target.getBestCameraToTarget().inverse().plus(FRONT_CAMERA_ON_ROBOT_POSE);
                }
            }
        }
        return null;
    }


    public Pose3d getFieldPose() {
        return fieldPosition;
    }


    public double getLastAprilTagID() {
        return aprilTagID;
    }


    public boolean isSeeingAprilTag() {
        return foundAprilTag;
    }


    public double getLatency() {
        return latency;
    }

    public double getLastSideAprilTagID() {
        return sideAprilTagID;
    }
}
