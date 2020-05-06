package Model;

import java.util.HashMap;
import java.util.Map;

public class GameEventsLog {

  private  Map<String, GameEvent> eventsLog;

    public GameEventsLog() {
        this.eventsLog = new HashMap<>();
    }

    public Map<String, GameEvent> getEventsLog() {
        return eventsLog;
    }

    public void setEventsLog(Map<String, GameEvent> eventsLog) {
        this.eventsLog = eventsLog;
    }
}
