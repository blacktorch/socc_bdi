package main;

import java.util.HashMap;
import java.util.Map;

public class Action {

    private Map<Actions, ExecutableAction> actionMap;
    private SendCommand actor;
    private Brain brain;
    private Memory memory;
    private char side;
    private String team;

    public Action(Brain brain) {
        this.actor = brain.getBeliever();
        this.memory = brain.getMemory();
        this.side = brain.getSide();
        this.team = brain.getTeam();
        this.brain = brain;
        createActionMap();
    }

    /**
     * Populates the map which maps enums received from JASON to blocks of code to execute.
     */
    private void createActionMap() {
        this.actionMap = new HashMap<>();
        this.actionMap.put(Actions.pass_ball, () -> {
            printExecutableCall(Actions.pass_ball);
            passBall();
        });
        this.actionMap.put(Actions.dash_forward, () -> {
            printExecutableCall(Actions.dash_forward);
            dashForward();
        });
        this.actionMap.put(Actions.dash_towards_goal, () -> {
            printExecutableCall(Actions.dash_towards_goal);
            dashTowardsGoal();
        });
        this.actionMap.put(Actions.dash_towards_ball, () -> {
            printExecutableCall(Actions.dash_towards_ball);
            dashTowardsBall();
        });
        this.actionMap.put(Actions.look_around, () -> {
            printExecutableCall(Actions.look_around);
            lookAround();
        });
        this.actionMap.put(Actions.do_nothing, () -> printExecutableCall(Actions.do_nothing));
        this.actionMap.put(Actions.kick_towards_goal, () -> {
            printExecutableCall(Actions.kick_towards_goal);
            kickTowardsGoal();
        });
    }

    private void printExecutableCall(Actions action) {
        System.out.println("Call to " + action.name());
    }

    public void passBall() {
        PlayerInfo player = (PlayerInfo) memory.getObject(Constants.PLAYER);

        if (player != null) {
            if (player.direction != 0) {
                actor.turn(player.direction);
            } else {
                actor.kick(5 * player.distance, player.direction);
            }
        } else {
            lookAround();
        }

    }

    public void dashForward() {
        ObjectInfo line = SoccerUtil.getOpponentsSide(memory, side);
        if (line == null) {
            lookAround();
        } else {
            if (line.direction != 0) {
                actor.turn(line.direction);
            } else {
                actor.dash(ActionDefaultValues.defaultDash * line.distance);
            }
        }
    }

    public void dashTowardsGoal() {
        ObjectInfo goal = SoccerUtil.getOpponentsGoal(memory, side);
        ObjectInfo ball = memory.getObject(Constants.BALL);
        if (goal.direction != 0) {
            actor.turn(goal.direction);
        } else {

            if (new PlayView(brain).hasBall()) {
                actor.kick(ActionDefaultValues.defaultKick, goal.direction);
            } else if (ball != null) {
                dashTowardsBall();
            }
        }
    }

    public void dashTowardsBall() {
        ObjectInfo ball = memory.getObject(Constants.BALL);
        PlayerInfo player = (PlayerInfo) memory.getObject(Constants.PLAYER);
        if (ball != null && ball.direction != 0) {
            actor.turn(ball.direction);
        } else {
            if (!(player != null && player.getTeamName().equals(team) && player.distance <= 6)) {
                actor.dash(ActionDefaultValues.defaultDash * ball.distance);
            }
        }
    }

    public void lookAround() {
        // If you don't know where is ball then find it.
        actor.turn(ActionDefaultValues.defaultTurn);
        memory.waitForNewInfo();
    }

    public void kickTowardsGoal() {
        actor.kick(ActionDefaultValues.defaultKickToShoot, SoccerUtil.getOpponentsGoal(memory, side).direction);
    }

    /**
     * Executes the next action to perform.
     */
    public void perform() {
        Actions actionLiteral = brain.getActionToPerform();
        if (this.actionMap.containsKey(actionLiteral)) {
            this.actionMap.get(actionLiteral).call();
        } else {
            // This is useful for debugging. If a typo is made in JASON then it should appear in the standard error rather than going unnoticed.
            System.err.println("The key " + actionLiteral.name() + " could not be found in the action map.");
        }
    }

    /**
     * All of the actions as they appear in the .asl file.
     */
    public enum Actions {
        dash_towards_ball,
        dash_towards_goal,
        kick_towards_goal,
        look_around,
        pass_ball,
        dash_forward,
        do_nothing,
    }

    /**
     * Provides default values for the common actions.
     */
    private class ActionDefaultValues {
        static final int defaultDash = 10;
        static final int defaultKick = 20;
        static final int defaultKickToShoot = 100;
        static final int defaultTurn = 40;
    }

}
