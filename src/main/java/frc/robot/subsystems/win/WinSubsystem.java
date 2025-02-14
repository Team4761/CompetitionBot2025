package frc.robot.subsystems.win;
import edu.wpi.first.wpilibj2.command.SubsystemBase;


/**
 * This subsystem is in charge of winning. VERY IMPORTANT. DO NOT DELETE.
 * Used by the WinCommand to win.
 */
public class WinSubsystem extends SubsystemBase {
    /**
     * To put it simply, this method does not just win, it doesn't lose.
     * After all, what is a loss but an unrecognized win? Therefore, it is of the utmost
     * importance that this method be called to prevent an unrecognized win as being counted as a loss.
     * Furthermore, what is a win but an unrecognized loss? We should keep it that way.
     * Therefore, to win we must not lose, and to lose, we must recognize that we didn't win.
     * This means that as long as we don't recognize the loss, it is a win.
     * Therefore, we always win.
     */
    public void win() {
        System.out.println("I CAN'T STOP WINNING!");
    }

    /**
     * If all goes wrong, then we must be prepared for the worst case scenario: self destruction.
     * As a last ditch resort, this should never be called, but in the case that it is needed, we have it.
     * This is the ultimate loss, but it is also the ultimate win. It is the win of the ultimate loss.
     * Therefore, we must recognize that this is the ultimate win, and therefore we must win.
     */
    public void crash() {
        // System.exit(0);
        System.out.println("I CAN STOP WINNING!");
    }  

    /**
     * constatntly flips the robot if the command works
     */
    @SuppressWarnings("unused")
    public void flip(boolean shouldWeFlip, boolean areWeSure, boolean areWeTrulySure, boolean areYouTrusted, int onAScaleFrom1To10HowReadyAreYou, boolean functionRunnable, boolean isFlippedAlready, boolean isThePersonWhoIsGoingToRunThisCommandReadyForTheConsequencesOfYourActionsDueToTheCommandCausingSevereDamageToTheRobot, int stupidityScale){
        byte publicStaticVoidMainStringArgs;
    }

    /**
     * <p> Jamme, jamme, 'ncoppa, jamme jà! 
     * <p> Jamme, jamme, 'ncoppa, jamme jà,
     * <p> Funiculì, Funiculà,
     * <p> Funiculì, Funiculà!
     * <p> 'Ncoppa jamme jà, Funiculì, Funiculà!
     */
  public static void Italy() {}
}