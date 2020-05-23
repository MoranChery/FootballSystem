package Data;

import Model.Season;
import Model.SeasonLeague;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SeasonDbInServer implements SeasonDb
{
    private static SeasonDbInServer ourInstance = new SeasonDbInServer();

    public static SeasonDbInServer getInstance() { return ourInstance; }

    @Override
    public void insertSeason(Season season) throws Exception
    {
        Connection conn = DbConnector.getConnection();
        try
        {
            // the mysql insert statement
            String query = " insert into season (season_name)"
                    + " values (?)";

            // create the mysql insert preparedStatement
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setString (1, season.getSeasonName());

            // execute the preparedStatement
            preparedStmt.execute();
        }
        catch (Exception e)
        {
            throw new Exception("Season already exists in the system");
        }
        finally
        {
            conn.close();
        }
    }

    @Override
    public Season getSeason(String seasonName) throws Exception
    {
        if (seasonName == null)
        {
            throw new Exception("Season not found");
        }

        Connection conn = DbConnector.getConnection();

        // the mysql select statement
        String query = "select * from season where season_name = \'" + seasonName + "\'";

        // create the mysql select resultSet
        Statement preparedStmt = conn.createStatement();
        ResultSet rs = preparedStmt.executeQuery(query);

        // checking if ResultSet is empty
        if (rs.next() == false)
        {
            throw new NotFoundException("Season not found");
        }
        String season_name = rs.getString("season_name");

        conn.close();

        Season season = new Season(season_name);
        season.setLeagueName_SeasonLeagueName(addMap_leagueName_seasonLeagueName(season_name));
        return season;
    }

    private Map<String, String> addMap_leagueName_seasonLeagueName(String seasonName) throws SQLException
    {
        Map<String, String> leagueName_seasonLeagueName = new HashMap<>();
        String key_league_name;
        String value_season_league_name;

        Connection conn = DbConnector.getConnection();

        // the mysql select statement
        String query = "select league_name, season_league_name from season_league where season_name = \'" + seasonName + "\'";

        // create the mysql select resultSet
        Statement preparedStmt = conn.createStatement();
        ResultSet rs = preparedStmt.executeQuery(query);

        // checking if ResultSet is empty
        if (rs.next() != false)
        {
            key_league_name = rs.getString("league_name");
            value_season_league_name = rs.getString("season_league_name");

            leagueName_seasonLeagueName.put(key_league_name, value_season_league_name);

            while (rs.next() != false)
            {
                key_league_name = rs.getString("league_name");
                value_season_league_name = rs.getString("season_league_name");

                leagueName_seasonLeagueName.put(key_league_name, value_season_league_name);
            }
        }
        conn.close();
        return leagueName_seasonLeagueName;
    }

    @Override
    public void addSeasonLeague(SeasonLeague seasonLeague) throws Exception
    {
        //todo
        throw new NotImplementedException();
    }

    @Override
    public String getSeasonLeagueNameBySeasonAndByLeague(String seasonName, String leagueName) throws Exception
    {
        //todo
        throw new NotImplementedException();
//        return null;
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
        String query = "delete from season";

        // create the mysql delete Statement
        statement.executeUpdate(query);
        conn.close();
    }

    @Override
    public ArrayList<String> getAllSeasonNames() throws Exception
    {
        ArrayList<String> seasonNames = new ArrayList<>();

        Connection conn = DbConnector.getConnection();

        try
        {
            // the mysql select statement
            String query = "select season_name from season";

            // create the mysql select resultSet
            Statement preparedStmt = conn.createStatement();
            ResultSet rs = preparedStmt.executeQuery(query);

            // checking if ResultSet is empty
            if (rs.next() != false)
            {
                seasonNames.add(rs.getString("season_name"));

                while (rs.next() != false)
                {
                    seasonNames.add(rs.getString("season_name"));
                }
            }
        }
        finally
        {
            conn.close();
        }
        return seasonNames;
    }

    @Override
    public ArrayList<Season> getAllSeasonObjects() throws Exception
    {
        ArrayList<Season> seasonObjects = new ArrayList<>();

        ArrayList<String> seasonNames = getAllSeasonNames();

        for (String season_name : seasonNames)
        {
            seasonObjects.add(getSeason(season_name));
        }
        return seasonObjects;
    }

    public static void main(String[] args) throws Exception
    {
        Season season1 = new Season("seasonName1");
        Season seasonWithoutLeague1 = new Season("seasonWithoutLeague1");
        SeasonDbInServer seasonDbInServer = new SeasonDbInServer();
        try
        {
            seasonDbInServer.insertSeason(season1);
            seasonDbInServer.insertSeason(seasonWithoutLeague1);
//
//            //Data.NotFoundException: Season not found
//            System.out.println(seasonDbInServer.getSeason(""));
//            //season1
//            System.out.println(seasonDbInServer.getSeason("season1").getSeasonName());
//            //Data.NotFoundException: Season not found
//            System.out.println(seasonDbInServer.getSeason("season2").getSeasonName());
//
//            seasonDbInServer.deleteAll();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
