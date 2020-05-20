package Data;

import Model.League;
import Model.SeasonLeague;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class LeagueDbInServer implements LeagueDb
{
    @Override
    public void insertLeague(League league) throws Exception
    {
        Connection conn = DbConnector.getConnection();
        try
        {
            // the mysql insert statement
            String query = " insert into league (league_name)"
                    + " values (?)";

            // create the mysql insert preparedStatement
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setString (1, league.getLeagueName());

            // execute the preparedStatement
            preparedStmt.execute();
        }
        catch (Exception e)
        {
            throw new Exception("League already exists in the system");
        }
        finally
        {
            conn.close();
        }
    }

    @Override
    public League getLeague(String leagueName) throws Exception
    {
        if (leagueName == null)
        {
            throw new Exception("League not found");
        }

        Connection conn = DbConnector.getConnection();

        // the mysql select statement
        String query = "select * from league where league_name = \'" + leagueName + "\'";

        // create the mysql select resultSet
        Statement preparedStmt = conn.createStatement();
        ResultSet rs = preparedStmt.executeQuery(query);

        // checking if ResultSet is empty
        if (rs.next() == false)
        {
            throw new NotFoundException("League not found");
        }
        String league_name = rs.getString("league_name");

        conn.close();

        League league = new League(league_name);
        league.setSeasonName_SeasonLeagueName(addMap_seasonName_SeasonLeagueName(league_name));
        return league;
    }

    private Map<String, String> addMap_seasonName_SeasonLeagueName(String leagueName) throws SQLException
    {
        Map<String, String> seasonName_SeasonLeagueName = new HashMap<>();
        String key_season_name;
        String value_season_league_name;

        Connection conn = DbConnector.getConnection();

        // the mysql select statement
        String query = "select season_name, season_league_name from season_league where league_name = \'" + leagueName + "\'";

        // create the mysql select resultSet
        Statement preparedStmt = conn.createStatement();
        ResultSet rs = preparedStmt.executeQuery(query);

        // checking if ResultSet is empty
        if (rs.next() != false)
        {
            key_season_name = rs.getString("season_name");
            value_season_league_name = rs.getString("season_league_name");

            seasonName_SeasonLeagueName.put(key_season_name, value_season_league_name);

            while (rs.next() != false)
            {
                key_season_name = rs.getString("season_name");
                value_season_league_name = rs.getString("season_league_name");

                seasonName_SeasonLeagueName.put(key_season_name, value_season_league_name);
            }
        }
        conn.close();
        return seasonName_SeasonLeagueName;
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
    public void deleteAll()
    {
        Connection conn = DbConnector.getConnection();
        try
        {
            // the mysql delete statement
            String query = " delete from league";

            // create the mysql delete preparedStatement
            PreparedStatement preparedStmt = conn.prepareStatement(query);

            // execute the preparedStatement
            preparedStmt.execute();
        }
        catch (SQLException throwables)
        {
            throwables.printStackTrace();
        }
        finally
        {
            try
            {
                conn.close();
            }
            catch (SQLException throwables)
            {
                throwables.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws Exception
    {
        League league1 = new League("leagueName1");
        League leagueWithoutSeason1 = new League("leagueWithoutSeason1");
        LeagueDbInServer leagueDbInServer = new LeagueDbInServer();
        try
        {
            leagueDbInServer.insertLeague(league1);
            leagueDbInServer.insertLeague(leagueWithoutSeason1);
//
//            //Data.NotFoundException: League not found
//            System.out.println(leagueDbInServer.getLeague(""));
//            //league1
//            System.out.println(leagueDbInServer.getLeague("league1").getLeagueName());
//            //Data.NotFoundException: League not found
//            System.out.println(leagueDbInServer.getLeague("league2").getLeagueName());
//
//            leagueDbInServer.deleteAll();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
