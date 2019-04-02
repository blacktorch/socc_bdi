package RoboCup;

public class PlayView {
    private Memory memory;
    private String team;
    private char side;
    private boolean isGoalie;

    public PlayView(Brain brain){
        this.memory = brain.getMemory();
        this.team = brain.getTeam();
        this.side = brain.getSide();
        this.isGoalie = brain.isGoalie();
    }

    public boolean canSeeBall(){
        ObjectInfo ball = memory.getObject(Constants.BALL);
        if (ball == null){
            return false;
        } else {
            return true;
        }
    }

    public boolean hasBall(){
        ObjectInfo ball = memory.getObject(Constants.BALL);
        if (ball != null){
            if (ball.distance > 1){
                return false;
            } else {
                return true;
            }
        }
       return false;
    }

    public boolean canSeeGoal(){
        if (SoccerUtil.getOpponentsGoal(memory, side) == null){
            return false;
        } else {
            return true;
        }
    }


    public boolean canSeeTeamMate() {
        PlayerInfo player = (PlayerInfo) memory.getObject(Constants.PLAYER);
        if (player != null && player.getTeamName().equals(team)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean teamMateHasBall(){
        PlayerInfo player = (PlayerInfo) memory.getObject(Constants.PLAYER);
        ObjectInfo ball = memory.getObject(Constants.BALL);
        if (player != null && ball != null && player.getTeamName().equals(team)) {
            if ((player.distance - ball.distance) >= -4 && (player.distance - ball.distance) <= 4){
                return true;
            }
        }
        return false;
    }

    public boolean farFromGoal(){
        ObjectInfo goal = SoccerUtil.getOpponentsGoal(memory, side);
        if ( goal != null && goal.distance >= 20){
            return true;
        } else {
            return false;
        }
    }

    public boolean facingMyGoal(){
        ObjectInfo myGoal = SoccerUtil.getMyGoal(memory, side);
        if (myGoal != null){
            return true;
        } else {
            return false;
        }
    }

    public boolean ballInGoalArea(){
        ObjectInfo postTop = SoccerUtil.getPostTop(memory,side);
        ObjectInfo postCentre = SoccerUtil.getPostCentre(memory,side);
        ObjectInfo postBottom = SoccerUtil.getPostBottom(memory,side);
        ObjectInfo ball = memory.getObject(Constants.BALL);

        try {
            if ((ball != null && postTop != null && ball.distance <= postTop.distance || ball != null && postCentre != null && ball.distance <= postCentre.distance || ball != null && postBottom != null && ball.distance <= postBottom.distance) ||
                    (ball != null && postTop != null && (ball.distance - postTop.distance) <=3) || (ball != null && postCentre != null && (ball.distance - postCentre.distance) <= 3) || (ball != null && postBottom != null && (ball.distance - postBottom.distance) <= 3) ||
                    ball.distance <= 8){
                return true;
            }
        } catch (NullPointerException e){
            return false;
        }

        return false;
    }

    public boolean isInGoalArea(){
        ObjectInfo postTop = SoccerUtil.getPostTop(memory,side);
        ObjectInfo postCentre = SoccerUtil.getPostCentre(memory,side);
        ObjectInfo postBottom = SoccerUtil.getPostBottom(memory,side);

        ObjectInfo goalTop = SoccerUtil.getGoalTop(memory,side);
        ObjectInfo goal = SoccerUtil.getMyGoal(memory,side);
        ObjectInfo goalBottom = SoccerUtil.getGoalBottom(memory,side);

        try {
            if ((postTop != null && postCentre != null && postBottom != null && goalTop != null && goal != null && goalBottom != null) ||
                    (postTop == null && postCentre == null && postBottom == null && goalTop == null && goal == null && goalBottom == null)){
                return false;
            } else {
                return true;
            }
        } catch (Exception e){
            return false;
        }

    }

    public boolean isGoalie(){
        return this.isGoalie;
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
        IS_GOALIE,
    }
}
