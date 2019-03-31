package RoboCup;

import jason.asSyntax.Literal;

import java.util.Map;

public class InferenceEngine {
    private Knowledge perception;
    private Brain brain;

    public InferenceEngine(Knowledge perception, Brain brain){
        this.perception = perception;
        this.brain = brain;
    }

    public void updatePerceptions(){
        PlayView playView = new PlayView(brain);
        for (Map.Entry<PlayView.Environments, Boolean> entry : perception.getIsPerceived().entrySet()){
            switch (entry.getKey()){
                case HAS_BALL:
                    perception.getIsPerceived().replace(entry.getKey(), entry.getValue(), playView.hasBall());
                    //System.out.println(entry.getKey().toString() + " : " + playView.hasBall());
                    break;
                case CAN_SEE_BALL:
                    perception.getIsPerceived().replace(entry.getKey(), entry.getValue(), playView.canSeeBall());
                    //System.out.println(entry.getKey().toString() + " : " + playView.canSeeBall());
                    break;
                case CAN_SEE_GOAL:
                    perception.getIsPerceived().replace(entry.getKey(), entry.getValue(), playView.canSeeGoal());
                    //System.out.println(entry.getKey().toString() + " : " + playView.canSeeGoal());
                    break;
                case BALL_NOT_VISIBLE:
                    perception.getIsPerceived().replace(entry.getKey(), entry.getValue(), !playView.canSeeBall());
                    //System.out.println(entry.getKey().toString() + " : " + !playView.canSeeBall());
                    break;
                case CAN_SEE_TEAM_MATE:
                    perception.getIsPerceived().replace(entry.getKey(), entry.getValue(), playView.canSeeTeamMate());
                    //System.out.println(entry.getKey().toString() + " : " + playView.canSeeTeamMate());
                    break;
                case FAR_FROM_GOAL:
                    perception.getIsPerceived().replace(entry.getKey(), entry.getValue(), playView.farFromGoal());
                    //System.out.println(entry.getKey().toString() + " : " + playView.farFromGoal());
                    break;
                case TEAM_MATE_HAS_BALL:
                    perception.getIsPerceived().replace(entry.getKey(), entry.getValue(), playView.teamMateHasBall());
                    //System.out.println(entry.getKey().toString() + " : " + playView.teamMateHasBall());
                    break;
                case NOT_WITH_BALL:
                    perception.getIsPerceived().replace(entry.getKey(), entry.getValue(), !playView.hasBall());
                    //System.out.println(entry.getKey().toString() + " : " + !playView.hasBall());
                    break;
                case GOAL_NOT_VISIBLE:
                    perception.getIsPerceived().replace(entry.getKey(), entry.getValue(), !playView.canSeeGoal());
                    //System.out.println(entry.getKey().toString() + " : " + !playView.canSeeGoal());
                    break;
                case FACING_MY_GOAL:
                    perception.getIsPerceived().replace(entry.getKey(), entry.getValue(), playView.facingMyGoal());
                    //System.out.println(entry.getKey().toString() + " : " + playView.facingMyGoal());
                    break;
                case TEAM_MATE_NOT_VISIBLE:
                    perception.getIsPerceived().replace(entry.getKey(), entry.getValue(), !playView.canSeeTeamMate());
                    //System.out.println(entry.getKey().toString() + " : " + !playView.canSeeTeamMate());
                    break;
            }
        }

        brain.getPerceptions().clear();

        for(PlayView.Environments condition : PlayView.Environments.values()){
            if (perception.getIsPerceived().get(condition)){
                brain.getPerceptions().add(Literal.parseLiteral(condition.name().toLowerCase()));
            }
        }

    }
}
