package RoboCup;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Perception {
    private Map<PlayView.PlayerView, Boolean> isPerceived = new HashMap<>();


    public Perception(List<PlayView.PlayerView> perceptions){
        for (PlayView.PlayerView perception : perceptions){
            isPerceived.put(perception, false);
        }
    }


    public Map<PlayView.PlayerView, Boolean> getIsPerceived (){
        return isPerceived;
    }
}
