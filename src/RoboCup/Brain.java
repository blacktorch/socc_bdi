//
//	File:			Brain.java
//	Author:		Krzysztof Langner
//	Date:			1997/04/28
//
//    Modified by:	Paul Marlow

//    Modified by:      Edgar Acosta
//    Date:             March 4, 2008
//    Modified by:      Chidiebere Onyedinma
//    Date:             March 2, 2019
package RoboCup;

import BDI.AgentBridge;
import jason.asSyntax.Literal;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class Brain extends Thread implements SensorInput {

    //===========================================================================
    // Private members
    private SendCommand believer;          // robot which is controled by this brain
    private Memory memory;                // place where all information is stored
    private char side;
    volatile private boolean timeOver;
    private String playMode;
    private int number;
    private String team;
    private Perception perception;
    private long runNumber;
    private Action.Actions actionToPerform;
    private Action.Actions previousAction;
    private boolean actionUpdated;
    private List<Literal> perceptions;
    private String playerName;
    private boolean isGoalie;
    private long actionTimeStamp;

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
        start();

    }


    public void run() {
        setPlayerPositions();

        Environment environment = new Environment(perception, this);
        Action action = new Action(this);

        startBDIEngine();

        while (!timeOver) {

            environment.updatePerceptions();
            if (actionUpdated){

                if (actionToPerform != previousAction){
                    long newId = perception.getId();
                    newId++;
                    perception.setId(newId);
                }
                try {
                    synchronized (action){
                        action.perform();
                    }

                    previousAction = actionToPerform;

                } catch (Exception e){
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


    //===========================================================================
    // Here are supporting functions for implement logic
    public SendCommand getBeliever(){
        return believer;
    }

    public char getSide(){
        return side;
    }

    public Memory getMemory(){
        return memory;
    }

    public String getTeam(){
        return team;
    }

    public int getNumber() {
        return number;
    }

    public long getRunNumber(){
        return runNumber;
    }

    public String getPlayerName(){
        return playerName;
    }

    public boolean isGoalie(){
        return this.isGoalie;
    }

    public synchronized List<Literal> getPerceptions(){
        return perceptions;
    }

    public void setPlayerPositions(){
        // set player formation.
        if (Pattern.matches("^before_kick_off.*", playMode)) {
            switch (number){
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

    private void startBDIEngine(){
        try {
            new Thread(() -> {
                AgentBridge agent = new AgentBridge(Brain.this);
                agent.run();
            }).start();
        } catch (Exception e){
            System.out.println("Encountered problem running agent reasoning!");
        }
    }


    //===========================================================================
    // Implementation of SensorInput Interface

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
        if (message.compareTo("time_over") == 0) {
            timeOver = true;
        }

    }

    public void updateAction(Action.Actions action, boolean isUpdated, long timeStamp){
        actionToPerform = action;
        actionUpdated = isUpdated;
        actionTimeStamp = timeStamp;
    }

    public Action.Actions getActionToPerform(){
        return actionToPerform;
    }

}
