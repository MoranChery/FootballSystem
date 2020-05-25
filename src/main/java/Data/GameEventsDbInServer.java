package Data;

import Model.Enums.GameEventType;
import Model.GameEvent;

import java.sql.*;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class GameEventsDbInServer implements GameEventsDb
{
    private static GameEventsDbInServer ourInstance = new GameEventsDbInServer();

    public static GameEventsDbInServer getInstance() { return ourInstance; }

    @Override
    public void insertGameEvent(GameEvent gameEvent) throws Exception
    {
        Connection conn = DbConnector.getConnection();
        try
        {
            // the mysql insert statement
            String query = " insert into game_event (game_id, event_id, game_date, event_time, event_minute, game_event_type, description)"
                    + " values (?,?,?,?,?,?,?)";

            // create the mysql insert preparedStatement
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setString(1, gameEvent.getGameId());
            preparedStmt.setString(2, gameEvent.getEventId());
            preparedStmt.setDate(3, new java.sql.Date(gameEvent.getGameDate().getTime()));
            preparedStmt.setDate(4, new java.sql.Date(gameEvent.getEventTime().getTime()));
            preparedStmt.setInt (5, gameEvent.getEventMinute());
            preparedStmt.setString (6, gameEvent.getGameEventType().toString());
            preparedStmt.setString (7, gameEvent.getDescription());

            // execute the preparedStatement
            preparedStmt.execute();
        }
        catch (Exception e)
        {
            throw new Exception("GameEvent already exist in system");
        }
        finally
        {
            conn.close();
        }
    }

    @Override
    public void addEvent(GameEvent gameEvent) throws Exception
    {
        insertGameEvent(gameEvent);
    }

    @Override
    public GameEvent getGameEvent(String eventId) throws Exception
    {
        if (eventId == null)
        {
            throw new NullPointerException("GameEvent not found");
        }

        Connection conn = DbConnector.getConnection();

        // the mysql select statement
        String query = "select * from game_event where game_event.event_id = \'" + eventId + "\'";

        // create the mysql select resultSet
        Statement preparedStmt = conn.createStatement();
        ResultSet rs = preparedStmt.executeQuery(query);

        // checking if ResultSet is empty
        if (rs.next() == false)
        {
            throw new NotFoundException("GameEvent not found");
        }

        String game_id = rs.getString("game_id");
        String event_id = rs.getString("event_id");
        Date game_date = rs.getDate("game_date");
        Date event_time = rs.getDate("event_time");
        Integer event_minute = rs.getInt("event_minute");
        GameEventType game_event_type = GameEventType.valueOf(rs.getString("game_event_type"));
        String description = rs.getString("description");

        conn.close();

        GameEvent gameEvent = new GameEvent(game_id, game_date, event_time, event_minute, game_event_type, description);
        gameEvent.setEventId(eventId);

        return gameEvent;
    }

    @Override
    public void updateGameEventDetails(GameEvent gameEvent) throws Exception
    {
        String game_event_type = gameEvent.getGameEventType().toString();
        String description = gameEvent.getDescription();

//        new java.sql.Date(gameEvent.getGameDate().getTime());

        Date event_time = new java.sql.Date(gameEvent.getEventTime().getTime());
        Integer event_minute = gameEvent.getEventMinute();

        Connection conn = DbConnector.getConnection();
        try
        {
            getGameEvent(gameEvent.getEventId());

            // the mysql update statement
            String query = " update game_event "
                    + "set game_event_type = \'" + game_event_type + "\' "
                    + ", description = \'" + description + "\' "
                    + ", event_time = \'" + event_time + "\' "
                    + ", event_minute = \'" + event_minute + "\' "
                    + "where event_id = \'" + gameEvent.getEventId() + "\'";

            // create the mysql insert preparedStatement
            PreparedStatement preparedStmt = conn.prepareStatement(query);

            // execute the preparedStatement
            preparedStmt.execute();
        }
        catch (NotFoundException e)
        {
            throw new Exception("This event doesnt associated with game");
        }
//        catch (Exception e)
//        {
//            throw new Exception("SeasonLeague not found455546486551");
//        }
        finally
        {
            conn.close();
        }
    }

    @Override
    public Map<String, GameEvent> getMap_eventId_GameEvent_ByGameId(String gameId) throws Exception
    {
        Map<String, GameEvent> gameEventMap = new HashMap<>();
        String event_id;
        GameEvent gameEvent;

        Connection conn = DbConnector.getConnection();
        try
        {
            // the mysql select statement
            String query = "select event_id from game_event where game_event.game_id = \'" + gameId + "\'";

            // create the mysql select resultSet
            Statement preparedStmt = conn.createStatement();
            ResultSet rs = preparedStmt.executeQuery(query);

            // checking if ResultSet is empty
            // checking if ResultSet is empty
            if (rs.next() != false)
            {
                event_id = rs.getString("event_id");
                gameEvent = getGameEvent(event_id);
                gameEventMap.put(event_id, gameEvent);

                while (rs.next() != false)
                {
                    event_id = rs.getString("event_id");
                    gameEvent = getGameEvent(event_id);
                    gameEventMap.put(event_id, gameEvent);
                }
            }
        }
        catch (Exception e)
        {
            throw new Exception("GameEvent not found");
        }
        finally
        {
            conn.close();
        }

        return gameEventMap;
    }

    @Override
    public void deleteAll() throws SQLException
    {
        Connection conn = DbConnector.getConnection();
        Statement statement = conn.createStatement();
        /* TRUNCATE is faster than DELETE since
         * it does not generate rollback information and does not
         * fire any delete triggers
         */

        // the mysql delete statement
        String query = "delete from game_event";

        // create the mysql delete Statement
        statement.executeUpdate(query);
        conn.close();
    }
}
