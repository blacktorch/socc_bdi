
/**
 * File:   Action.java
 * Author: Onyedinma Chidiebere
 * Date:   05/04/19
 * **/
package robocup;

import java.util.List;

/**
 * The Action class has various methods
 * that the robocup agents uses to perform various actions.
 * The names of the methods vividly describes the actions
 * **/

public class Action {

    private String team;
    private SendCommand actor;
    private Memory memory;
    private char side;
    private Brain brain;
    private List<PlayView.PlayerView> preConditions;

    public Action(Brain brain) {
        this.actor = brain.getBeliever();
        this.memory = brain.getMemory();
        this.side = brain.getSide();
        this.team = brain.getTeam();
        this.brain = brain;
    }

    public void lookAround() {
        // If you don't know where is ball then find it
        actor.turn(40);
        memory.waitForNewInfo();
    }

    public void dashTowardsBall() {
        ObjectInfo ball = memory.getObject(Constants.BALL);

        dashToObject(ball, 20);
    }

    public void kickTowardsGoal() {
        actor.kick(100, SoccerUtil.getOpponentsGoal(memory, side).direction);
    }

    public void passBall() {
        PlayerInfo player = (PlayerInfo) memory.getObject(Constants.PLAYER);

        if (player != null && player.getTeamName().equals(team)) {
            if (player.direction != 0) {
                actor.turn(player.direction);
            } else {
                actor.kick(3 * player.distance, player.direction);
            }
        } else {
            lookAround();
        }

    }

    public void dashTowardsGoal() {
        ObjectInfo goal = SoccerUtil.getOpponentsGoal(memory, side);
        ObjectInfo ball = memory.getObject(Constants.BALL);
        if (goal.direction != 0) {
            actor.turn(goal.direction);
        } else {

            if (new PlayView(brain).hasBall()) {
                actor.kick(20, goal.direction);
            } else if (ball != null) {
                dashTowardsBall();
            }
        }
    }

    private void dashToObject(ObjectInfo objectInfo){
        dashToObject(objectInfo,30);
    }

    private void dashToObject(ObjectInfo objectInfo, int power) {
        dashToObject(objectInfo, power, objectInfo.direction);
    }

    private void dashToObject(ObjectInfo objectInfo, int power, float direction){
        try {
            if (objectInfo == null){
                lookAround();
            } else {
                if (direction != 0){
                    actor.turn(direction);
                } else {
                    actor.dash(power * objectInfo.distance);
                }
            }
        } catch (NullPointerException e){
            System.out.println("Can't perceive object");
        }

    }

    public void dashForward() {
        ObjectInfo c = memory.getObject("flag c");
        ObjectInfo p = SoccerUtil.getPostCentre(memory, side, true);
        ObjectInfo g = SoccerUtil.getOpponentsGoal(memory, side);
        switch (brain.getNumber()) {
            case 2:
                if (g != null) {
                    dashToObject(g, 30, g.direction-6);
                } else if (p != null) {
                    dashToObject(p, 30, p.direction-6);
                } else {
                    dashToObject(c);
                }
                break;
            case 3:
                if (g != null) {
                    dashToObject(g, 30, g.direction+6);
                } else if (p != null) {
                    dashToObject(p, 30, p.direction+6);
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

    public void returnToGoalArea() {
        ObjectInfo myGoal = SoccerUtil.getMyGoal(memory, side);
        dashToObject(myGoal, 30);
    }

    public void goalieKickAway() {
        ObjectInfo centerField = memory.getObject(Constants.FLAG + Constants.SPACE + Constants.CENTRE);
        ObjectInfo centerFieldTop = memory.getObject(Constants.FLAG + Constants.SPACE + Constants.CENTRE + Constants.SPACE + Constants.TOP);
        ObjectInfo centerFieldBottom = memory.getObject(Constants.FLAG + Constants.SPACE + Constants.CENTRE + Constants.SPACE + Constants.BOTTOM);
        if (centerField != null) {
            actor.kick(100, centerField.direction);
        } else if (centerFieldTop != null) {
            actor.kick(100, centerFieldTop.direction);
        } else if (centerFieldBottom != null) {
            actor.kick(100, centerFieldBottom.direction);
        } else {
            actor.turn(40);
        }
    }

    public void perform(Actions actionToPerform) {
        if (brain.getRefereeMessage().equals("play_on") || brain.getRefereeMessage().equals("drop_ball")) {
            switch (actionToPerform) {
                case PASS_BALL:
                    passBall();
                    break;
                case LOOK_AROUND:
                    lookAround();
                    break;
                case DASH_TOWARDS_BALL:
                    dashTowardsBall();
                    break;
                case DASH_TOWARDS_GOAL:
                    dashTowardsGoal();
                    break;
                case KICK_TOWARDS_GOAL:
                    kickTowardsGoal();
                    break;
                case DASH_FORWARD:
                    dashForward();
                    break;
                case GOALIE_KICK_AWAY:
                    goalieKickAway();
                    break;
                case RETURN_TO_GOAL_AREA:
                    returnToGoalArea();
                    break;
                case DO_NOTHING:
                    break;
                default:
                    lookAround();
            }
        }
    }

    public void perform() {
        perform(brain.getActionToPerform());
    }

    /**
     * The Actions enum is an enumeration of all possible actions
     * that the Action class offers.
     *
     * **/

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

}
