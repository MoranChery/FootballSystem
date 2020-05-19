package Data;

import Model.League;
import Model.SeasonLeague;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

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

            // create the mysql insert preparedstatement
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setString (1, league.getLeagueName());

            // execute the preparedSstatement
            preparedStmt.execute();
        } finally {
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

        String query = "select * from league"
                + "where league_name = \'" + leagueName + "\'";

        Statement preparedStmt = conn.createStatement();
        ResultSet rs = preparedStmt.executeQuery(query);

        // checking if ResultSet is empty
        if (rs.next() == false)
        {
            throw new NotFoundException("League not found");
        }

        conn.close();

        League league = new League(leagueName);

        return league;
    }

    @Override
    public void addSeasonLeague(SeasonLeague seasonLeague) throws Exception
    {

    }

    @Override
    public String getSeasonLeagueNameBySeasonAndByLeague(String seasonName, String leagueName) throws Exception
    {
        return null;
    }

    @Override
    public void deleteAll()
    {

    }

    public static void main(String[] args) throws Exception
    {
//        League league1 = new League("league1");
        LeagueDbInServer leagueDbInServer = new LeagueDbInServer();
//        leagueDbInServer.insertLeague(league1);

        League league2 = leagueDbInServer.getLeague("league1");
    }
}
