package RoboCup;

import java.util.ArrayList;
import java.util.List;

public class Rule {
    private String rule;
    private boolean hasAction;
    private static final String THEN = "_THEN_";
    private static final String IF = "IF_";
    private static final String AND = "_AND_";
    private PlayView.Environments condition;
    private Action.Actions action;
    private List<PlayView.Environments> preConditions;

    public Rule(String rule){
        this.rule = rule;
        String[] literal = this.rule.split(THEN);
        this.condition = null;
        this.action = null;
        try {
            if (PlayView.Environments.valueOf(literal[1]) instanceof PlayView.Environments){
                hasAction = false;
                condition = PlayView.Environments.valueOf(literal[1]);
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
                if (PlayView.Environments.valueOf(condition) instanceof PlayView.Environments){
                    preConditions.add(PlayView.Environments.valueOf(condition));
                }
            }
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public List<PlayView.Environments> getPreConditions(){
        return preConditions;
    }

    public boolean isHasAction() {
        return hasAction;
    }

    public PlayView.Environments getCondition(){
        return condition;
    }

    public Action.Actions getAction(){
        return action;
    }

    public String toString(){
        return rule;
    }
}
