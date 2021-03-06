package bdi;

import jason.architecture.AgArch;
import jason.asSemantics.ActionExec;
import jason.asSemantics.Agent;
import jason.asSemantics.Message;
import jason.asSemantics.TransitionSystem;
import jason.asSyntax.Literal;
import jason.asSyntax.Structure;
import robocup.Action;
import robocup.Brain;
import robocup.PlayView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Example of an agent that only uses Jason bdi engine. It runs without all
 * Jason IDE stuff. (see Jason FAQ for more information about this example)
 * <p>
 * The class must extend AgArch class to be used by the Jason engine.
 */
public class AgentBridge extends AgArch {

    private static Logger logger = Logger.getLogger(AgentBridge.class.getName());
    private PlayView playView;
    private String agentName;
    private Brain brain;
    private boolean isActionDone;
    private boolean isNewPerception;
    private List<Literal> previousPerceptions;

    public AgentBridge(Brain brain) {
        // set up the Jason agent
        agentName = brain.getPlayerName();
        try {
            Agent ag = new Agent();
            new TransitionSystem(ag, null, null, this);
            ag.initAg("robocup.asl");
            playView = new PlayView(brain);
            this.brain = brain;
            previousPerceptions = new ArrayList<>();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Init error", e);
        }
    }

    // this method just add some perception for the agent
    @Override
    public synchronized List<Literal> perceive() {
        brain.setPlayerPositions();
        ArrayList<Literal> l = new ArrayList<>(brain.getPerceptions());
        //System.out.println("Old Perception");
        //brain.updateAction(Action.Actions.DO_NOTHING, false);
        //System.out.println("New Perception");
        isNewPerception = !l.equals(previousPerceptions);
        previousPerceptions = (List<Literal>) l.clone();

        return l;
    }

    @Override
    public void checkMail() {
        super.checkMail();
        Iterator im = getTS().getC().getMailBox().iterator();
        while (im.hasNext()) {
            Message m = (Message) im.next();
            System.out.println(m.toString());
        }
    }

    // this method get the agent actions
    @Override
    public synchronized void act(ActionExec action) {
        //getTS().getLogger().info("Agent " + getAgName() + " is doing: " + action.getActionTerm());

        Structure actionTerm = action.getActionTerm();
        //System.out.println(actionTerm.toString());

        brain.updateAction(Action.Actions.valueOf(actionTerm.toString().toUpperCase()), true, System.currentTimeMillis());
        // set that the execution was ok
        action.setResult(true);
        actionExecuted(action);
    }

    @Override
    public boolean canSleep() {
        return !isNewPerception;
    }

    public String getAgName() {
        return agentName;
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
    public boolean isRunning() {
        return true;
    }

    public void run() {
        try {
            while (isRunning()) {
                // calls the Jason engine to perform one reasoning cycle
                //logger.fine("Reasoning....");
                getTS().reasoningCycle();
                if (getTS().canSleep()) {
                    sleep();
                }
            }
        } catch (NullPointerException e) {
            System.out.println("Waiting for next reasoning cycle...");
        }
    }

    // a very simple implementation of sleep
    public void sleep() {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
        }
    }

}