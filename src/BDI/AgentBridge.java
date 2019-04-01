package BDI;

import RoboCup.Action;
import RoboCup.Brain;
import RoboCup.PlayView;
import jason.architecture.AgArch;
import jason.asSemantics.ActionExec;
import jason.asSemantics.Agent;
import jason.asSemantics.TransitionSystem;
import jason.asSyntax.Literal;
import jason.asSyntax.Structure;
import jason.asSyntax.Term;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Example of an agent that only uses Jason BDI engine. It runs without all
 * Jason IDE stuff. (see Jason FAQ for more information about this example)
 *
 * The class must extend AgArch class to be used by the Jason engine.
 */
public class AgentBridge extends AgArch {

    private static Logger logger = Logger.getLogger(AgentBridge.class.getName());
    PlayView playView;
    private Brain brain;
    private String agentName;

    public AgentBridge(Brain brain) {
        // set up the Jason agent
        agentName = brain.getPlayerName();
        try {
            Agent ag = new Agent();
            new TransitionSystem(ag, null, null, this);
            ag.initAg("robocup.asl");
            playView = new PlayView(brain);
            this.brain = brain;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Init error", e);
        }
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
        List<Literal> l = new ArrayList<>(brain.getPerceptions());

        return l;
    }

    // this method get the agent actions
    @Override
    public void act(ActionExec action) {
        getTS().getLogger().info("Agent " + getAgName() + " is doing: " + action.getActionTerm());

        Structure actionTerm = action.getActionTerm();
        Term dashToBall = Literal.parseLiteral("dash_towards_ball");
        Term lookAround = Literal.parseLiteral("look_around");

        if (actionTerm.equals(dashToBall)){
            brain.updateAction(Action.Actions.valueOf(dashToBall.toString().toUpperCase()), true);
        } else if (actionTerm.equals(lookAround)){
            brain.updateAction(Action.Actions.valueOf(lookAround.toString().toUpperCase()), true);
        }

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