package frc.robot.auto;

import edu.wpi.first.wpilibj2.command.Command;

/**
 * Guess what this does? It... SAYS "Hi"! Crazy, I know.
 * 
 * <p> Crazy? I was crazy once. They locked me in a room. A rubber room. A rubber room with rats. And rats make me crazy.
 * <p> Crazy? I was crazy once. They locked me in a room. A rubber room. A rubber room with rats. And rats make me crazy.
 * <p> Crazy? I was cra- *dies*
 */
public class SayHiCommand extends Command {

    @Override
    public void initialize() {
        System.out.println("Hi");
    }

    
    /**
     * Finishes instantly cause there's nothing else needed to do.
     */
    @Override
    public boolean isFinished() {
        return true;
    }
    
}
