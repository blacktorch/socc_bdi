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
public class SimpleJasonAgent extends AgArch {

    private static Logger logger = Logger.getLogger(SimpleJasonAgent.class.getName());
    int env = 10;
    PlayView playView;
    Action actionE;
    private Brain brain;

//    public static void main(String[] a) {
//        //BaseCentralisedMAS.getRunner().setupLogger();
//        SimpleJasonAgent ag = new SimpleJasonAgent();
//        ag.run();
//    }

    public SimpleJasonAgent(Brain brain) {
        // set up the Jason agent
        try {
            Agent ag = new Agent();
            new TransitionSystem(ag, null, null, this);
            ag.initAg("demo.asl");
            playView = new PlayView(brain);
            actionE = new Action(brain);
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
        return "bob";
    }

    // this method just add some perception for the agent
    @Override
    public List<Literal> perceive() {
        //env = (int)(Math.random() * 50 + 1);
        List<Literal> l = new ArrayList<Literal>(brain.getPerceptions());

        //l.clear();

//        if (playView.canSeeBall()){
//            l.clear();
//            l.add(Literal.parseLiteral("canSeeBall"));
//        } else {
//            l.clear();
//            l.add(Literal.parseLiteral("noBall"));
//        }

        return l;
    }

    // this method get the agent actions
    @Override
    public void act(ActionExec action) {
        getTS().getLogger().info("Agent " + getAgName() + " is doing: " + action.getActionTerm());
        // set that the execution was ok

        Structure actionTerm = action.getActionTerm();
        Term dashToBall = Literal.parseLiteral("dashToBall");
        Term lookAround = Literal.parseLiteral("lookAround");
        if (actionTerm.equals(dashToBall)){
            actionE.dashTowardsBall();
        } else if (actionTerm.equals(lookAround)){
            actionE.lookAround();
        }

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
