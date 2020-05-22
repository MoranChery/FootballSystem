package Data;

import Model.Enums.CalculateLeaguePoints;
import Model.Enums.InlayGames;
import Model.JudgeSeasonLeague;
import Model.SeasonLeague;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.sql.*;

public class JudgeSeasonLeagueDbInServer implements JudgeSeasonLeagueDb
{
    private static JudgeSeasonLeagueDbInServer ourInstance = new JudgeSeasonLeagueDbInServer();

    public static JudgeSeasonLeagueDbInServer getInstance() { return ourInstance; }

    @Override
    public void insertJudgeSeasonLeague(JudgeSeasonLeague judgeSeasonLeague) throws Exception
    {
        Connection conn = DbConnector.getConnection();
        try
        {
            // the mysql insert statement
            String query = " insert into judge_season_league (judge_season_league_name, season_league_name, judge_email_address)"
                    + " values (?,?,?)";

            // create the mysql insert preparedStatement
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setString (1, judgeSeasonLeague.getJudgeSeasonLeagueName());
            preparedStmt.setString (2, judgeSeasonLeague.getSeasonLeagueName());
            preparedStmt.setString (3, judgeSeasonLeague.getJudgeEmailAddress());

            // execute the preparedStatement
            preparedStmt.execute();
        }
        catch (Exception e)
        {
            throw new Exception("JudgeSeasonLeague already exists in the system");
        }
        finally
        {
            conn.close();
        }
    }

    @Override
    public JudgeSeasonLeague getJudgeSeasonLeague(String judgeSeasonLeagueName) throws Exception
    {
        if (judgeSeasonLeagueName == null)
        {
            throw new Exception("JudgeSeasonLeague not found");
        }

        Connection conn = DbConnector.getConnection();

        // the mysql select statement
        String query = "select * from judge_season_league where judge_season_league_name = \'" + judgeSeasonLeagueName + "\'";

        // create the mysql select resultSet
        Statement preparedStmt = conn.createStatement();
        ResultSet rs = preparedStmt.executeQuery(query);

        // checking if ResultSet is empty
        if (rs.next() == false)
        {
            throw new Exception("JudgeSeasonLeague not found");
        }
        String judge_season_league_name = rs.getString("judge_season_league_name");
        String season_league_name = rs.getString("season_league_name");
        String judge_email_address = rs.getString("judge_email_address");

        conn.close();

        JudgeSeasonLeague judgeSeasonLeague = new JudgeSeasonLeague(season_league_name, judge_email_address);
        return judgeSeasonLeague;
    }

    @Override
    public void removeJudgeSeasonLeague(String judgeSeasonLeagueName) throws Exception
    {
        //todo
        throw new NotImplementedException();
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
        String query = "delete from judge_season_league";

        // create the mysql delete Statement
        statement.executeUpdate(query);
        conn.close();
    }
}
