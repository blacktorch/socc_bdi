/**
 * File:   Action.java
 * Author: Onyedinma Chidiebere
 * Date:   05/04/19
 **/
package robocup;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

/**
 * The Action class has various methods
 * that the robocup agents uses to perform various actions.
 * The names of the methods vividly describes the actions
 **/

public class Action {

    private SendCommand actor;
    private Brain brain;
    private Memory memory;
    private List<PlayView.PlayerView> preConditions;
    private char side;
    private String team;

    public Action(Brain brain) {
        this.actor = brain.getBeliever();
        this.memory = brain.getMemory();
        this.side = brain.getSide();
        this.team = brain.getTeam();
        this.brain = brain;
    }

    public void DASH_FORWARD() {
        ObjectInfo c = memory.getObject("flag c");
        ObjectInfo p = SoccerUtil.getPostCentre(memory, side, true);
        ObjectInfo g = SoccerUtil.getOpponentsGoal(memory, side);
        switch (brain.getNumber()) {
            case 2:
                if (g != null) {
                    dashToObject(g, ActionConstants.defaultDashPower, g.direction - 6);
                } else if (p != null) {
                    dashToObject(p, ActionConstants.defaultDashPower, p.direction - 6);
                } else {
                    dashToObject(c);
                }
                break;
            case 3:
                if (g != null) {
                    dashToObject(g, ActionConstants.defaultDashPower, g.direction + 6);
                } else if (p != null) {
                    dashToObject(p, ActionConstants.defaultDashPower, p.direction + 6);
                } else {
                    dashToObject(c);
                }
                break;
            case 4:
                ObjectInfo gt = SoccerUtil.getGoalTop(memory, side, true);
                ObjectInfo pt = SoccerUtil.getPostTop(memory, side, true);
                ObjectInfo ct = memory.getObject("flag c t");
                if (gt != null) {
                    dashToObject(gt);
                } else if (pt != null) {
                    dashToObject(pt);
                } else {
                    dashToObject(ct);
                }
                break;
            case 5:
                ObjectInfo gb = SoccerUtil.getGoalBottom(memory, side, true);
                ObjectInfo pb = SoccerUtil.getPostBottom(memory, side, true);
                ObjectInfo cb = memory.getObject("flag c b");
                if (gb != null) {
                    dashToObject(gb);
                } else if (pb != null) {
                    dashToObject(pb);
                } else {
                    dashToObject(cb);
                }
                break;
        }
    }

    private void dashToObject(ObjectInfo objectInfo, int power, float direction) {
        try {
            if (objectInfo == null) {
                LOOK_AROUND();
            } else {
                if (direction != 0) {
                    actor.turn(direction);
                } else {
                    actor.dash(power * objectInfo.distance);
                }
            }
        } catch (NullPointerException e) {
            System.out.println("Can't perceive object");
        }

    }

    private void dashToObject(ObjectInfo objectInfo) {
        dashToObject(objectInfo, ActionConstants.defaultDashPower);
    }

    public void LOOK_AROUND() {
        // If you don't know where is ball then find it
        actor.turn(ActionConstants.defaultTurnPower);
        memory.waitForNewInfo();
    }

    private void dashToObject(ObjectInfo objectInfo, int power) {
        dashToObject(objectInfo, power, objectInfo.direction);
    }

    public void DASH_TOWARDS_GOAL() {
        ObjectInfo goal = SoccerUtil.getOpponentsGoal(memory, side);
        ObjectInfo ball = memory.getObject(Constants.BALL);
        if (goal.direction != 0) {
            actor.turn(goal.direction);
        } else {

            if (new PlayView(brain).hasBall()) {
                actor.kick(ActionConstants.dashToBallPower, goal.direction);
            } else if (ball != null) {
                DASH_TOWARDS_BALL();
            }
        }
    }

    public void DASH_TOWARDS_BALL() {
        ObjectInfo ball = memory.getObject(Constants.BALL);
        dashToObject(ball, ActionConstants.dashToBallPower);
    }

    private Actions DO_NOTHING() {
        return null;
    }

    public void GOALIE_KICK_AWAY() {
        ObjectInfo centerField = memory.getObject(Constants.FLAG + Constants.SPACE + Constants.CENTRE);
        ObjectInfo centerFieldTop = memory.getObject(Constants.FLAG + Constants.SPACE + Constants.CENTRE + Constants.SPACE + Constants.TOP);
        ObjectInfo centerFieldBottom = memory.getObject(Constants.FLAG + Constants.SPACE + Constants.CENTRE + Constants.SPACE + Constants.BOTTOM);
        if (centerField != null) {
            actor.kick(ActionConstants.defaultKickPower, centerField.direction);
        } else if (centerFieldTop != null) {
            actor.kick(ActionConstants.defaultKickPower, centerFieldTop.direction);
        } else if (centerFieldBottom != null) {
            actor.kick(ActionConstants.defaultKickPower, centerFieldBottom.direction);
        } else {
            actor.turn(ActionConstants.defaultTurnPower);
        }
    }

    public void KICK_TOWARDS_GOAL() {
        actor.kick(ActionConstants.defaultKickPower, SoccerUtil.getOpponentsGoal(memory, side).direction);
    }

    public void PASS_BALL() {
        PlayerInfo player = (PlayerInfo) memory.getObject(Constants.PLAYER);

        if (player != null && player.getTeamName().equals(team)) {
            if (player.direction != 0) {
                actor.turn(player.direction);
            } else {
                actor.kick(3 * player.distance, player.direction);
            }
        } else {
            LOOK_AROUND();
        }

    }

    public void RETURN_TO_GOAL_AREA() {
        ObjectInfo myGoal = SoccerUtil.getMyGoal(memory, side);
        dashToObject(myGoal, ActionConstants.defaultDashPower);
    }

    public void perform() {
        perform(brain.getActionToPerform());
    }

    /**
     * Calls a method in this class using reflection.
     * @param action The action enum to be mapped to a function.
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    private void callMethod(Actions action) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        String methodName = action.name().toUpperCase();
        Method method = this.getClass().getMethod(methodName);
        method.invoke(this);
    }

    public void perform(Actions actionToPerform) {
        if (brain.getRefereeMessage().equals("play_on") || brain.getRefereeMessage().equals("drop_ball")) {
            try {
                callMethod(actionToPerform);
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                LOOK_AROUND();
            }
        }
    }

    /**
     * The Actions enum is an enumeration of all possible actions
     * that the Action class offers.
     **/

    public enum Actions {
        DASH_TOWARDS_BALL,
        DASH_TOWARDS_GOAL,
        KICK_TOWARDS_GOAL,
        LOOK_AROUND,
        PASS_BALL,
        DASH_FORWARD,
        GOALIE_KICK_AWAY,
        RETURN_TO_GOAL_AREA,
        DO_NOTHING,
    }

    /**
     * Stores constants which are repeated so that they can be changed globally.
     */
    private class ActionConstants {
        static final int dashToBallPower = 20;
        static final int defaultDashPower = 30;
        static final int defaultKickPower = 100;
        static final int defaultTurnPower = 40;
    }

}
