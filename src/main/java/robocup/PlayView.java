/**
 * File:   PlayView.java
 * Author: Onyedinma Chidiebere
 * Date:   05/04/19
 **/
package robocup;

/**
 * The PlayView class has various methods
 * that tell the different perceptions as viewed
 * by the agent
 * **/

public class PlayView {
    private boolean isGoalie;
    private Memory memory;
    private int number;
    private SendCommand player;
    private char side;
    private String team;

    public PlayView(Brain brain) {
        this.memory = brain.getMemory();
        this.team = brain.getTeam();
        this.side = brain.getSide();
        this.isGoalie = brain.isGoalie();
        player = brain.getBeliever();
        number = brain.getNumber();
    }

    public boolean ballInGoalArea() {
        ObjectInfo postTop = SoccerUtil.getPostTop(memory, side, false);
        ObjectInfo postCentre = SoccerUtil.getPostCentre(memory, side, false);
        ObjectInfo postBottom = SoccerUtil.getPostBottom(memory, side, false);
        ObjectInfo ball = memory.getObject(Constants.BALL);

        try {
            if ((ball != null && postTop != null && ball.distance <= postTop.distance || ball != null && postCentre != null && ball.distance <= postCentre.distance || ball != null && postBottom != null && ball.distance <= postBottom.distance) ||
                    (ball != null && postTop != null && (ball.distance - postTop.distance) <= 3) || (ball != null && postCentre != null && (ball.distance - postCentre.distance) <= 3) || (ball != null && postBottom != null && (ball.distance - postBottom.distance) <= 3) ||
                    ball.distance <= 8) {
                return true;
            }
        } catch (NullPointerException e) {
            return false;
        }

        return false;
    }

    public boolean canSeeBall() {
        ObjectInfo ball = memory.getObject(Constants.BALL);
        return ball != null;
    }

    public boolean canSeeGoal() {
        return SoccerUtil.getOpponentsGoal(memory, side) != null;
    }


    public boolean canSeeTeamMate() {
        PlayerInfo player = (PlayerInfo) memory.getObject(Constants.PLAYER);
        return player != null && player.getTeamName().equals(team);
    }

    public boolean facingMyGoal() {
        ObjectInfo myGoal = SoccerUtil.getMyGoal(memory, side);
        return myGoal != null;
    }

    public boolean farFromGoal() {
        ObjectInfo goal = SoccerUtil.getOpponentsGoal(memory, side);
        return goal != null && goal.distance >= 20;
    }

    public boolean hasBall() {
        ObjectInfo ball = memory.getObject(Constants.BALL);
        if (ball != null) {
            return !(ball.distance > 1);
        }
        return false;
    }

    public boolean isGoalie() {
        return this.isGoalie;
    }

    public boolean isInGoalArea() {
        ObjectInfo postTop = SoccerUtil.getPostTop(memory, side, false);
        ObjectInfo postCentre = SoccerUtil.getPostCentre(memory, side, false);
        ObjectInfo postBottom = SoccerUtil.getPostBottom(memory, side, false);

        ObjectInfo goalTop = SoccerUtil.getGoalTop(memory, side, false);
        ObjectInfo goal = SoccerUtil.getMyGoal(memory, side);
        ObjectInfo goalBottom = SoccerUtil.getGoalBottom(memory, side, false);

        try {
            return (postTop != null || postCentre != null || postBottom != null || goalTop != null || goal != null || goalBottom != null) &&
                    (goal == null || !(goal.distance > 10)) && (goalTop == null || !(goalTop.distance > 10)) && (goalBottom == null || !(goalBottom.distance > 10)) &&
                    (postTop == null || postCentre != null || postBottom != null || goalTop != null) && (postBottom == null || postCentre != null || postTop != null || goalBottom != null);
        } catch (Exception e) {
            return false;
        }

    }

    public boolean teamMateHasBall() {
        PlayerInfo player = (PlayerInfo) memory.getObject(Constants.PLAYER);
        ObjectInfo ball = memory.getObject(Constants.BALL);
        if (player != null && ball != null && player.getTeamName().equals(team)) {
            return ball.distance > player.distance;
        }
        return false;
    }

    public boolean teamMateIsCloserToGoal() {
        PlayerInfo player = SoccerUtil.getTeamMember(memory, team);
        ObjectInfo goal = SoccerUtil.getOpponentsGoal(memory, side);
        return player != null && goal != null && goal.distance > player.distance;
    }

    public enum PlayerView {
        BALL_NOT_VISIBLE,
        CAN_SEE_BALL,
        CAN_SEE_GOAL,
        CAN_SEE_TEAM_MATE,
        FAR_FROM_GOAL,
        HAS_BALL,
        TEAM_MATE_HAS_BALL,
        TEAM_MATE_NOT_VISIBLE,
        FACING_MY_GOAL,
        NOT_WITH_BALL,
        GOAL_NOT_VISIBLE,
        BALL_IN_GOAL_AREA,
        IS_IN_GOAL_AREA,
        TEAM_MATE_IS_CLOSER_TO_GOAL,
        IS_GOALIE,
    }
}
