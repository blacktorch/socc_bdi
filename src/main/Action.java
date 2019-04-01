package robocup;

import java.util.List;

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
        PlayerInfo player = (PlayerInfo)memory.getObject(Constants.PLAYER);
        if (ball != null && ball.direction != 0) {
            actor.turn(ball.direction);
        } else {
            if (!(player != null && player.getTeamName().equals(team) && player.distance <= 6)){
                actor.dash(10 * ball.distance);
            }
        }
    }

    public void kickTowardsGoal() {
        actor.kick(100, SoccerUtil.getOpponentsGoal(memory, side).direction);
    }

    public void passBall() {
        PlayerInfo player = (PlayerInfo) memory.getObject(Constants.PLAYER);

        if (player != null){
            if (player.direction != 0) {
                actor.turn(player.direction);
            } else {
                actor.kick(5 * player.distance, player.direction);
            }
        } else {
            lookAround();
        }

    }

    public void dashTowardsGoal(){
        ObjectInfo goal = SoccerUtil.getOpponentsGoal(memory, side);
        ObjectInfo ball = memory.getObject(Constants.BALL);
        if (goal.direction != 0){
            actor.turn(goal.direction);
        } else {

            if (new PlayView(brain).hasBall()){
                actor.kick(20, goal.direction);
            } else if (ball != null){
                dashTowardsBall();
            }
        }
    }

    public void dashForward(){
        ObjectInfo line = SoccerUtil.getOpponentsSide(memory, side);
        if (line == null){
            lookAround();
        } else {
            if (line.direction != 0){
                actor.turn(line.direction);
            } else {
                actor.dash(10 * line.distance);
            }
        }
    }

    public void perform(){
        switch (brain.getActionToPerform()){
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
            case DO_NOTHING:
                break;
                default:
                    lookAround();
        }
    }

    public enum Actions {
        DASH_TOWARDS_BALL,
        DASH_TOWARDS_GOAL,
        KICK_TOWARDS_GOAL,
        LOOK_AROUND,
        PASS_BALL,
        DASH_FORWARD,
        DO_NOTHING,
    }

}
