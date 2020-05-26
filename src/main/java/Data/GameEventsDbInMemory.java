package Data;

import Model.GameEvent;

import java.util.HashMap;
import java.util.Map;

public class GameEventsDbInMemory implements GameEventsDb, Db {
    private Map<String, GameEvent> gameEvents;
    private static GameEventsDbInMemory ourInstance = new GameEventsDbInMemory();

    public static GameEventsDbInMemory getInstance() {
        return ourInstance;
    }

    public GameEventsDbInMemory() {
        this.gameEvents = new HashMap<>();
    }

    public Map<String, GameEvent> getGameEvents() {
        return gameEvents;
    }

    public void setGameEvents(Map<String, GameEvent> gameEvents) {
        this.gameEvents = gameEvents;
    }

   public void insertGameEvent(GameEvent gameEvent){
        if(gameEvent == null){
            throw new NullPointerException("bad input");
        }
       String eventId = gameEvent.getEventId();
        gameEvents.put(eventId,gameEvent);
   }

    @Override
    public void addEvent(GameEvent gameEvent) throws NotFoundException {
        if (gameEvent == null) {
            throw new NullPointerException();
        }
        String eventId = gameEvent.getEventId();
        gameEvents.put(eventId, gameEvent);
    }

    @Override
    public GameEvent getGameEvent(String eventId) throws Exception {
        if (!gameEvents.containsKey(eventId)) {
            throw new Exception("This event doesnt associated with game");
        }
        return gameEvents.get(eventId);
    }

    @Override
    public void updateGameEventDetails(GameEvent gameEvent) throws Exception {
        String eventId = gameEvent.getEventId();
        if (!gameEvents.containsKey(eventId)) {
            throw new Exception("This event doesnt associated with game");
        }
        GameEvent gameEventToUpdate = gameEvents.get(eventId);
        gameEventToUpdate.setGameEventType(gameEvent.getGameEventType());
        gameEventToUpdate.setDescription(gameEvent.getDescription());
        gameEventToUpdate.setEventTime(gameEvent.getEventTime());
        gameEventToUpdate.setEventMinute(gameEvent.getEventMinute());
    }

    @Override
    public Map<String, GameEvent> getMap_eventId_GameEvent_ByGameId(String gameId) {
        Map<String, GameEvent> gameEventMap = new HashMap<>();
        for (GameEvent gameEvent : gameEvents.values()) {
            if (gameId.equals(gameEvent.getGameId())) {
                gameEventMap.put(gameEvent.getEventId(), gameEvent);
            }
        }
        return gameEventMap;
    }


    @Override
    public void deleteAll() {
        gameEvents.clear();
    }
}
