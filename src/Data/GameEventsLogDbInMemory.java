package Data;

import Model.GameEvent;
import Model.GameEventsLog;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class GameEventsLogDbInMemory implements GameEventsLogDb {
    private Map<String, GameEvent> gameEventLog;
    private static GameEventsLogDbInMemory ourInstance = new GameEventsLogDbInMemory();
    public static GameEventsLogDbInMemory getInstance() { return ourInstance;}

    public GameEventsLogDbInMemory() {
        this.gameEventLog = new HashMap<>();
    }

    public Map<String, GameEvent> getGameEventLog() {
        return gameEventLog;
    }

    public void setGameEventLog(Map<String, GameEvent> gameEventLog) {
        this.gameEventLog = gameEventLog;
    }

    public void addEvent(GameEvent gameEvent) throws NotFoundException {
        if(gameEvent == null){
            throw new NullPointerException();
        }
        String eventId = gameEvent.getEventId();
        gameEventLog.put(eventId,gameEvent);
    }

    public GameEvent getEvent(String eventId) throws Exception {
        if(!gameEventLog.containsKey(eventId)){
            throw new Exception("This event doesnt associated with game");
        }
        return gameEventLog.get(eventId);
    }

    public void setUpdatedDetails(GameEvent gameEvent) throws Exception {
        String eventId = gameEvent.getEventId();
        if(!gameEventLog.containsKey(eventId)){
                throw new Exception("This event doesnt associated with game");
        }
        GameEvent gameEventToUpdate = gameEventLog.get(eventId);
        gameEventToUpdate.setGameEventType(gameEvent.getGameEventType());
        gameEventToUpdate.setDescription(gameEvent.getDescription());
        gameEventToUpdate.setEventTime(gameEvent.getEventTime());
        gameEventToUpdate.setEventMinute(gameEvent.getEventMinute());
    }


}
