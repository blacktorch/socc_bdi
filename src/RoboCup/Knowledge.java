package RoboCup;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Knowledge {
    private Map<PlayView.Environments, Boolean> isPerceived = new HashMap<>();


    public Knowledge(List<PlayView.Environments> perceptions){
        for (PlayView.Environments perception : perceptions){
            isPerceived.put(perception, false);
        }
    }


    public Map<PlayView.Environments, Boolean> getIsPerceived (){
        return isPerceived;
    }
}
