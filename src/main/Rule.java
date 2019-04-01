package robocup;

import java.util.ArrayList;
import java.util.List;

public class Rule {
    private String rule;
    private boolean hasAction;
    private static final String THEN = "_THEN_";
    private static final String IF = "IF_";
    private static final String AND = "_AND_";
    private PlayView.PlayerView condition;
    private Action.Actions action;
    private List<PlayView.PlayerView> preConditions;

    public Rule(String rule){
        this.rule = rule;
        String[] literal = this.rule.split(THEN);
        this.condition = null;
        this.action = null;
        try {
            if (PlayView.PlayerView.valueOf(literal[1]) instanceof PlayView.PlayerView){
                hasAction = false;
                condition = PlayView.PlayerView.valueOf(literal[1]);
                return;
            }
        }
        catch (Exception ex){
            //Now check if its an action
        }

        try {
            if (Action.Actions.valueOf(literal[1]) instanceof Action.Actions){
                hasAction = true;
                action = Action.Actions.valueOf(literal[1]);
                setPreconditions(literal[0].replaceAll(IF,""));
            }
        }
        catch (Exception ex){
            //Now return
        }

    }

    private void setPreconditions(String literal){
        String[] conditions = literal.split(AND);
        preConditions = new ArrayList<>();
        try {
            for (String condition : conditions){
                if (PlayView.PlayerView.valueOf(condition) instanceof PlayView.PlayerView){
                    preConditions.add(PlayView.PlayerView.valueOf(condition));
                }
            }
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public List<PlayView.PlayerView> getPreConditions(){
        return preConditions;
    }

    public boolean isHasAction() {
        return hasAction;
    }

    public PlayView.PlayerView getCondition(){
        return condition;
    }

    public Action.Actions getAction(){
        return action;
    }

    public String toString(){
        return rule;
    }
}
