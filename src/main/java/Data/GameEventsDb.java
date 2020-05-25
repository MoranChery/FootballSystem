package Data;

import Model.GameEvent;

import java.sql.SQLException;
import java.util.Map;

public interface GameEventsDb extends Db{

    void insertGameEvent(GameEvent gameEvent) throws Exception;
    void addEvent(GameEvent gameEvent) throws Exception;

    GameEvent getGameEvent(String eventId) throws Exception;

    void updateGameEventDetails(GameEvent gameEvent) throws Exception;

    Map<String, GameEvent> getMap_eventId_GameEvent_ByGameId(String gameId) throws Exception;
}
