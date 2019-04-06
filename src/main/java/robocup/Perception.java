/**
 * File:   Perception.java
 * Author: Onyedinma Chidiebere
 * Date:   05/04/19
 **/
package robocup;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Perception {
    private long id;
    private Map<PlayView.PlayerView, Boolean> isPerceived = new HashMap<>();


    public Perception(List<PlayView.PlayerView> perceptions) {
        this.id = 0;
        for (PlayView.PlayerView perception : perceptions) {
            isPerceived.put(perception, false);
        }
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Map<PlayView.PlayerView, Boolean> getIsPerceived() {
        return isPerceived;
    }
}
