package Data;

import Model.GameEvent;

import java.util.Map;

public class GameEventsDbInServer implements GameEventsDb
{
    private static GameJudgesListDbInServer ourInstance = new GameJudgesListDbInServer();

    public static GameJudgesListDbInServer getInstance() { return ourInstance; }
    
    @Override
    public void createGameEvent(GameEvent gameEvent)
    {

    }

    @Override
    public void addEvent(GameEvent gameEvent) throws NotFoundException
    {

    }

    @Override
    public GameEvent getEvent(String eventId) throws Exception
    {
        return null;
    }

    @Override
    public void setUpdatedDetails(GameEvent gameEvent) throws Exception
    {

    }

    @Override
    public Map<String, GameEvent> getGameEvents(String gameId)
    {
        return null;
    }
}
