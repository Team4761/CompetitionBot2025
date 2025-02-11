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
import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

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
    public static final Transform3d CAMERA_ON_ROBOT_POSE = new Transform3d(0.3, 0, 0, new Rotation3d(0, 0, 0));

    //  This is the last recorded pose by vision (which tries to update its pose in the periodic method)
    // (0,0) represents the left corner of the blue alliance-wall, looking towards the red alliance. Towards the red alliance is +x, towards the other side of the alliance wall is +y.
    private Pose3d fieldPosition;
    private int aprilTagID = -1;
    private double latency = 0;
    private boolean foundAprilTag = false;
    private List<PhotonPipelineResult> results;

    // Default hostname is "photonvision", but we changed that to "CAMERA_NAME"
    PhotonCamera camera;
    double lastTimestamp = 0;

    // Used to calculate a pose over time
    PhotonPoseEstimator photonPoseEstimator;

    // A tag Pose is the CENTER of the tag.
    // The rotation assumes that facing AWAY from the blue alliance wall is 0 degrees, rotating counter clockwise.
    // PLACEHOLDER FOR TESTING PURPOSES
    private final AprilTagFieldLayout aprilTagFieldLayout = new AprilTagFieldLayout(
        List.of(
            new AprilTag(2, new Pose3d(10, 10, 0, new Rotation3d(0, 0, Math.PI))),   // Should be facing towards the blue alliance... I think...
            new AprilTag(4, new Pose3d(3, 3, 0, new Rotation3d(0, 0, Math.PI)))
        ),
        100,
        100
    );

    // Actual field layout for when testing is done.
    // If the following line is throwing an error message, you don't have the most up-to-date WPILib version.
    // This requires version 2025.2.1+ to work.
    // AprilTagFieldLayout APRIL_TAG_FIELD_LAYOUT = AprilTagFieldLayout.loadField(AprilTagFields.k2025Reefscape);


    /**
     * This will be in charge of setting up the cameras and pose estimator.
     */
    public VisionSubsystem() {
        camera = new PhotonCamera("Psebastian");

        // MULTI_TAG_PNP_ON_COPROCESSOR is best, but we're using CLOSEST_TO_LAST_POSE for now.
        photonPoseEstimator = new PhotonPoseEstimator(aprilTagFieldLayout, PoseStrategy.MULTI_TAG_PNP_ON_COPROCESSOR, CAMERA_ON_ROBOT_POSE);

        // Maybe make this actually check for the robot's position once on startup?
        fieldPosition = new Pose3d();
    }


    /**
     * During the periodic method, I am going to log all of the data and calculate the robot pose relative to the entire field.
     */
    @Override
    public void periodic() {
        // Get the last processed frame (technically just results) from the camera.
        results = camera.getAllUnreadResults();
        
        // Check for if there are any April Tags in the result
        for (PhotonPipelineResult result : results) {
            if (result.hasTargets()) {
                // List<PhotonTrackedTarget> targets = result.getTargets();
                PhotonTrackedTarget target = result.getBestTarget();

                // Get information from a generic target (not necessarily april tags)
                // List<TargetCorner> corners = target.getDetectedCorners();
                // double yaw = target.getYaw();
                // double pitch = target.getPitch();
                // double area = target.getArea();

                // Skew is not available with April Tags sadly :(
                // double skew = target.getSkew();

                // April tag specific information
                // Transform3d pose = target.getBestCameraToTarget();
                // int targetID = target.getFiducialId();
                // double poseAmbiguity = target.getPoseAmbiguity();

                // Calculate robot's field relative pose
                if (aprilTagFieldLayout.getTagPose(target.getFiducialId()).isPresent()) {
                    fieldPosition = PhotonUtils.estimateFieldToRobotAprilTag(
                        target.getBestCameraToTarget(), // The position of the April Tag relative to the camera
                        aprilTagFieldLayout.getTagPose(target.getFiducialId()).get(),   // The position of the April Tag in the field
                        CAMERA_ON_ROBOT_POSE    // Transform of the robot relative to the camera. (center of the robot is 0,0)
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
                }
            }
            else {
                SmartDashboard.putBoolean("Found April Tag", false);
            }
        }
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
                    return target.getBestCameraToTarget().inverse().plus(CAMERA_ON_ROBOT_POSE);
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
}
