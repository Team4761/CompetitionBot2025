package frc.robot.auto;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.PrintCommand;
import frc.robot.subsystems.arm.ScoreL2Command;

/**
 * This is supposed to get to the reef, rotate the arm, and score the coral.
 */
public class ScoreOneCoralAuto {

    /**
     * Here's a list of every april tag to line up for when scoring (the parenthesis show where our robot wants to be in field coordinates):
     * 
     * BLUE ALLIANCE:
     *  LEFT - 20   (5.26097, )
     *  CENTER - 21 (5.67690, 4.02590)
     *  RIGHT - 22  (5.26097, )
     * 
     * RED ALLIANCE:
     *  LEFT - 11
     *  CENTER - 10
     *  RIGHT - 9
     * 
     * CORAL STATIONS:
     *  BLUE:
     *   LEFT - 13
     *   RIGHT - 12
     * 
     *  RED:
     *   LEFT - 1
     *   RIGHT - 2
     */

    /**
     * Because of Algae, here is a graphic of available coral ([] = accessible coral, # = algae-blocked coral, A = algae):
     * 
     * L4: ## ##   [] []   ## ##
     *       A               A
     * L3: ## ##   ## ##   ## ##
     *               A
     * L2: [] []   ## ##   [] []
     * 
     * Based on this graphic, if we're in the center, we can ONLY score on either L4 or L1 (unless our teammates let us take one of their coral instead)
     */

    /**
     * DO NOT USE THE CONSTRUCTOR! Use ScoreOneCoralAuto.create() instead.
     */
    private ScoreOneCoralAuto() {}
    
    /**
     * This is a bit complicated because it takes into account multiple starting positions and is dependent on side.
     * @param startingPosition The starting position of our bot on the field facing the opposite alliance wall.
     * @param onRedAlliance If we on red alliance, we be true. If no, false. Blam bada bop boom, pow.
     * @return
     */
    public static Command create(StartingPosition startingPosition, boolean onRedAlliance) {
        return new PrintCommand("WIP");
    }


    /**
     * This reads the values that are selected on the dashboard and in Driverstation to determine which path to follow and where to score.
     * @return
     */
    public Command create() {
        return null;
        // return create(
        //     AutoHandler.getStartingPosition(), 
        //     (DriverStation.getAlliance().isPresent()) ? (DriverStation.getAlliance().get() == DriverStation.Alliance.Red) : (false)
        // );
    }
}
