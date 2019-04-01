package bdi;

import main.Action;
import main.Brain;
import main.PlayView;
import jason.architecture.AgArch;
import jason.asSemantics.ActionExec;
import jason.asSemantics.Agent;
import jason.asSemantics.TransitionSystem;
import jason.asSyntax.Literal;
import jason.asSyntax.Structure;

import javax.annotation.Resources;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Example of an agent that only uses Jason bdi engine. It runs without all
 * Jason IDE stuff. (see Jason FAQ for more information about this example)
 *
 * The class must extend AgArch class to be used by the Jason engine.
 */
public class AgentBridge extends AgArch {
    private static final String jasonFileName = "robocup.asl";
    private static Logger logger = Logger.getLogger(AgentBridge.class.getName());
    private PlayView playView;
    private Brain brain;
    private String agentName;

    public AgentBridge(Brain brain) {
        // set up the Jason agent
        agentName = brain.getPlayerName();
        try {
            Agent ag = new Agent();
            new TransitionSystem(ag, null, null, this);
            ag.initAg(loadBeliefBase());
            playView = new PlayView(brain);
            this.brain = brain;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Failed to initialize AgentSpeak.", e);
        }
    }

    /**
     * Loads the .asl file from the Java "resources" directory.
     * @return The path to the .asl file.
     */
    private String loadBeliefBase() {
        String fd = null;
        try {
            fd = getClass().getClassLoader().getResource(jasonFileName).getFile();
            System.out.println(fd);
        } catch (NullPointerException e) {
            System.err.println("Failed to load resource file: " + jasonFileName);
            System.exit(-1);
        }
        return fd;
    }

    public void run() {
        try {
            while (isRunning()) {
                // calls the Jason engine to perform one reasoning cycle
                logger.fine("Reasoning....");
                getTS().reasoningCycle();
                if (getTS().canSleep()) {
                    sleep();
                }
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Run error", e);
        }
    }

    public String getAgName() {
        return agentName;
    }

    // this method just add some perception for the agent
    @Override
    public List<Literal> perceive() {
        return new ArrayList<>(brain.getPerceptions());
    }

    // this method get the agent actions
    @Override
    public void act(ActionExec action) {
        getTS().getLogger().info("Agent " + getAgName() + " is doing: " + action.getActionTerm());

        Structure actionTerm = action.getActionTerm();
        String term = actionTerm.toString();


        brain.updateAction(Action.Actions.valueOf(term), true);


        // set that the execution was ok
        action.setResult(true);
        actionExecuted(action);
    }

    @Override
    public boolean canSleep() {
        return true;
    }

    @Override
    public boolean isRunning() {
        return true;
    }

    // a very simple implementation of sleep
    public void sleep() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {}
    }

    // Not used methods
    // This simple agent does not need messages/control/...
    @Override
    public void sendMsg(jason.asSemantics.Message m) throws Exception {
    }

    @Override
    public void broadcast(jason.asSemantics.Message m) throws Exception {
    }

    @Override
    public void checkMail() {
    }

}