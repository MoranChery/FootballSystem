package Data;

import Model.GameEvent;

import java.util.Map;

public interface GameEventsDb {

    void createGameEvent(GameEvent gameEvent);
    void addEvent(GameEvent gameEvent) throws NotFoundException;

    GameEvent getEvent(String eventId) throws Exception;

    void setUpdatedDetails(GameEvent gameEvent) throws Exception;

    Map<String, GameEvent> getGameEvents(String gameId);
}
