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
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

/**
 * This does NOT find April Tags, that is handled by the Orange Pi. Instead, this allows the robot to react to where the Orange Pi found april tags.
 * Vision works with the following pipeline: Camera (RAW_DATA) --> Orange Pi (APRIL_TAG_SPOTS) --> Radio (NETWORKING) --> RoboRIO (REACTING_TO_DATA) & Driverstation (DEBUGGING)
 * 
 * If using our Microsoft cams, we should have them set to 640x480 @ 30fps (can be set easily through shuffleboard)
 * 
 * Most of the stuff here was found using the PhotonVision dashboard. You can get there by plugging the Orange Pi into power and ethernet TO A RADIO (not a computer), 
 * and then going to photonvision.local:5800 on google on the driverstation.
 */
public class VisionSubsystem extends SubsystemBase {

    // +x = forwards. +y = left. +z = up. Rotation is AROUND those axises in a counterclockwise direction! So roll = rotation AROUND +x.
    public static final Transform3d CAMERA_ON_ROBOT_POSE = new Transform3d(0.3, 0, 0, new Rotation3d(0, 0, 0));

    // Default hostname is "photonvision", but we changed that to "CAMERA_NAME"
    PhotonCamera camera;
    double lastTimestamp = 0;

    // Used to calculate a pose over time
    PhotonPoseEstimator photonPoseEstimator;

    // A tag Pose is the CENTER of the tag.
    // The rotation assumes that facing AWAY from the blue alliance wall is 0 degrees, rotating counter clockwise.
    // PLACEHOLDER FOR TESTING PURPOSES
    AprilTagFieldLayout aprilTagFieldLayout = new AprilTagFieldLayout(
        List.of(
            new AprilTag(1, new Pose3d(10, 10, 0, new Rotation3d(0, 0, Math.PI)))   // Should be facing towards the blue alliance... I think...
        ),
        100,
        100
    );

    // Actual field layout for when testing is done.
    AprilTagFieldLayout APRIL_TAG_FIELD_LAYOUT = AprilTagFieldLayout.loadField(AprilTagFields.k2025Reefscape);


    /**
     * This will be in charge of setting up the cameras and pose estimator.
     */
    public VisionSubsystem() {
        camera = new PhotonCamera("CAMERA_NAME");

        // MULTI_TAG_PNP_ON_COPROCESSOR is best, but we're using CLOSEST_TO_LAST_POSE for now.
        photonPoseEstimator = new PhotonPoseEstimator(aprilTagFieldLayout, PoseStrategy.CLOSEST_TO_LAST_POSE, CAMERA_ON_ROBOT_POSE);
    }


    /**
     * During the periodic method, I am going to log all of the data and calculate the robot pose relative to the entire field.
     */
    @Override
    public void periodic() {
        // Get the last processed frame (technically just results) from the camera.
        PhotonPipelineResult result = camera.getLatestResult();
        
        // Check for if there are any April Tags in the result
        if (result.hasTargets()) {
            // Normally, we wouldn't get both the best AND all targets, but I want to for testing purposes.
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
                Pose3d robotPose = PhotonUtils.estimateFieldToRobotAprilTag(
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

                SmartDashboard.putNumber("April Tag ID", target.getFiducialId());
                SmartDashboard.putNumber("X - Vision Pose", robotPose.getX());
                SmartDashboard.putNumber("Y - Vision Pose", robotPose.getY());
                SmartDashboard.putNumber("Z - Vision Pose", robotPose.getZ());
                SmartDashboard.putNumber("Roll - Vision Pose", Units.radiansToDegrees(robotPose.getRotation().getX()));
                SmartDashboard.putNumber("Pitch - Vision Pose", Units.radiansToDegrees(robotPose.getRotation().getY()));
                SmartDashboard.putNumber("Yaw - Vision Pose", Units.radiansToDegrees(robotPose.getRotation().getZ()));

                SmartDashboard.putNumber("Camera Latency", result.getTimestampSeconds() - lastTimestamp);
                lastTimestamp = result.getTimestampSeconds();
            }
        }
    }
}
