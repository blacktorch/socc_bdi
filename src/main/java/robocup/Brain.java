//
//	File:			Brain.java
//	Author:		Krzysztof Langner
//	Date:			1997/04/28
//
//    Modified by:	Paul Marlow

//    Modified by:      Edgar Acosta
//    Date:             March 4, 2008

//    Modified by:      Chidiebere Onyedinma
//    Date:             April 5, 2019
package robocup;

import bdi.AgentBridge;
import jason.asSyntax.Literal;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class Brain extends Thread implements SensorInput {

    private long actionTimeStamp;
    private Action.Actions actionToPerform;
    private boolean actionUpdated;
    private SendCommand believer;
    private boolean isGoalie;
    private Memory memory;
    private int number;
    private Perception perception;
    private List<Literal> perceptions;
    private String playMode;
    private String playerName;
    private Action.Actions previousAction;
    private String refereeMessage;
    private long runNumber;
    private char side;
    private String team;
    volatile private boolean timeOver;

    //---------------------------------------------------------------------------
    // This constructor:
    // - stores connection to believer
    // - starts thread for this object
    public Brain(SendCommand believer, String team, char side, int number, String playMode, Perception perception) {
        timeOver = false;
        this.believer = believer;
        memory = new Memory();
        this.team = team;
        this.side = side;
        this.number = number;
        this.playMode = playMode;
        this.perception = perception;
        this.runNumber = 0;
        perceptions = new ArrayList<>();
        actionToPerform = Action.Actions.DO_NOTHING;
        previousAction = actionToPerform;
        actionUpdated = false;
        playerName = "Agent";
        isGoalie = false;
        actionTimeStamp = System.currentTimeMillis();
        refereeMessage = "";
        start();

    }

    public Action.Actions getActionToPerform() {
        return actionToPerform;
    }

    //===========================================================================
    // Here are supporting functions for implement logic
    public SendCommand getBeliever() {
        return believer;
    }

    public Memory getMemory() {
        return memory;
    }

    public int getNumber() {
        return number;
    }

    public synchronized List<Literal> getPerceptions() {
        return perceptions;
    }

    public String getPlayerName() {
        return playerName;
    }

    public String getRefereeMessage() {
        return refereeMessage;
    }

    public long getRunNumber() {
        return runNumber;
    }

    public char getSide() {
        return side;
    }

    public String getTeam() {
        return team;
    }

    public boolean isGoalie() {
        return this.isGoalie;
    }

    public void run() {
        setPlayerPositions();

        Environment environment = new Environment(perception, this);
        Action action = new Action(this);

        startBDIEngine();

        while (!timeOver) {

            environment.updatePerceptions();
            if (actionUpdated) {

                if (actionToPerform != previousAction) {
                    long newId = perception.getId();
                    newId++;
                    perception.setId(newId);
                }
                try {
                    synchronized (this) {
                        action.perform();
                    }

                    previousAction = actionToPerform;

                } catch (Exception e) {
                    System.out.println("Failed to perform action");
                }

            }
            setPlayerPositions();
            // sleep one step to ensure that we will not send
            // two commands in one cycle.
            try {
                Thread.sleep(2 * SoccerParams.simulator_step);
            } catch (Exception e) {
                e.printStackTrace();
            }

            runNumber++;
        }
        believer.bye();
    }

    public void setPlayerPositions() {

        // set player formation.
        if (Pattern.matches("^before_kick_off.*", playMode)) {
            switch (number) {
                case 1:
                    playerName = "Goalie";
                    isGoalie = true;
                    believer.move(-48, 0);
                    believer.changeView("wide", "high");
                    break;
                case 2:
                    playerName = "Griffin";
                    believer.move(-7, -7);
                    break;
                case 3:
                    playerName = "Chidi";
                    believer.move(-7, 7);
                    break;
                case 4:
                    playerName = "Chris";
                    believer.move(-30, -25);
                    break;
                case 5:
                    playerName = "Babak";
                    believer.move(-30, 25);
                    break;
                default:
                    playerName = "Player";
                    believer.move(-Math.random() * 52.5, 34 - Math.random() * 68.0);
            }
        }
    }


    //===========================================================================
    // Implementation of SensorInput Interface

    private void startBDIEngine() {
        try {
            new Thread(() -> {
                AgentBridge agent = new AgentBridge(Brain.this);
                agent.run();
            }).start();
        } catch (Exception e) {
            System.out.println("Encountered problem running agent reasoning!");
        }
    }

    //---------------------------------------------------------------------------
    // This function sends see information
    public void see(VisualInfo info) {
        memory.store(info);
    }

    //---------------------------------------------------------------------------
    // This function receives hear information from player
    public void hear(int time, int direction, String message) {
    }

    //---------------------------------------------------------------------------
    // This function receives hear information from referee
    public void hear(int time, String message) {
        refereeMessage = message;
        System.out.println(message);
        if (message.compareTo("time_over") == 0) {
            timeOver = true;
        }

    }

    public void updateAction(Action.Actions action, boolean isUpdated, long timeStamp) {
        actionToPerform = action;
        actionUpdated = isUpdated;
        actionTimeStamp = timeStamp;
    }

}
