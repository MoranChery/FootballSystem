package Data;

import Model.Enums.CalculateLeaguePoints;
import Model.Enums.InlayGames;
import Model.JudgeSeasonLeague;
import Model.League;
import Model.Season;
import Model.SeasonLeague;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SeasonLeagueDbInServer implements SeasonLeagueDb
{
    private static SeasonLeagueDbInServer ourInstance = new SeasonLeagueDbInServer();

    public static SeasonLeagueDbInServer getInstance() { return ourInstance; }

    @Override
    public void insertSeasonLeague(SeasonLeague seasonLeague) throws Exception
    {
        Connection conn = DbConnector.getConnection();
        try
        {
            // the mysql insert statement
            String query = " insert into season_league (season_league_name, season_name, league_name, calculate_league_points, inlay_games)"
                    + " values (?,?,?,?,?)";

            // create the mysql insert preparedStatement
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setString (1, seasonLeague.getSeasonLeagueName());
            preparedStmt.setString (2, seasonLeague.getSeasonName());
            preparedStmt.setString (3, seasonLeague.getLeagueName());
            preparedStmt.setString (4, seasonLeague.getCalculateLeaguePoints().toString());
            preparedStmt.setString (5, seasonLeague.getInlayGames().toString());

            try
            {
                LeagueDbInServer.getInstance().getLeague(seasonLeague.getLeagueName());
            }
            catch (Exception e)
            {
                throw new NotFoundException("League not found");
            }

            try
            {
                SeasonDbInServer.getInstance().getSeason(seasonLeague.getSeasonName());
            }
            catch (Exception e)
            {
                throw new NotFoundException("Season not found");
            }
            // execute the preparedStatement
            preparedStmt.execute();
        }
        catch (NotFoundException e)
        {
            throw new Exception(e.getMessage());
        }
        catch (Exception e)
        {
            throw new Exception("SeasonLeague already exists in the system");
        }
        finally
        {
            conn.close();
        }
    }

    @Override
    public SeasonLeague getSeasonLeague(String seasonLeagueName) throws Exception
    {
        if (seasonLeagueName == null)
        {
            throw new Exception("SeasonLeague not found");
        }

        Connection conn = DbConnector.getConnection();

        // the mysql select statement
        String query = "select * from season_league where season_league_name = \'" + seasonLeagueName + "\'";

        // create the mysql select resultSet
        Statement preparedStmt = conn.createStatement();
        ResultSet rs = preparedStmt.executeQuery(query);

        // checking if ResultSet is empty
        if (rs.next() == false)
        {
            throw new NotFoundException("SeasonLeague not found");
        }
        String season_name = rs.getString("season_name");
        String league_name = rs.getString("league_name");
        String season_league_name = rs.getString("season_league_name");
        CalculateLeaguePoints calculate_league_points = CalculateLeaguePoints.valueOf(rs.getString("calculate_league_points"));
        InlayGames inlay_games = InlayGames.valueOf(rs.getString("inlay_games"));

        conn.close();

        SeasonLeague seasonLeague = new SeasonLeague(season_name, league_name, calculate_league_points, inlay_games);
        seasonLeague.setJudgeEmailAddress_JudgeSeasonLeagueName(addMap_judgeEmailAddress_judgeSeasonLeagueName(seasonLeagueName));
        return seasonLeague;
    }

    private Map<String, String> addMap_judgeEmailAddress_judgeSeasonLeagueName(String seasonLeagueName) throws SQLException
    {
        Map<String, String> judgeEmailAddress_judgeSeasonLeagueName = new HashMap<>();
        String key_judge_email_address;
        String value_judge_season_league_name;

        Connection conn = DbConnector.getConnection();

        // the mysql select statement
        String query = "select judge_email_address, judge_season_league_name from judge_season_league where season_league_name = \'" + seasonLeagueName + "\'";

        // create the mysql select resultSet
        Statement preparedStmt = conn.createStatement();
        ResultSet rs = preparedStmt.executeQuery(query);

        // checking if ResultSet is empty
        if (rs.next() != false)
        {
            key_judge_email_address = rs.getString("judge_email_address");
            value_judge_season_league_name = rs.getString("judge_season_league_name");

            judgeEmailAddress_judgeSeasonLeagueName.put(key_judge_email_address, value_judge_season_league_name);

            while (rs.next() != false)
            {
                key_judge_email_address = rs.getString("judge_email_address");
                value_judge_season_league_name = rs.getString("judge_season_league_name");

                judgeEmailAddress_judgeSeasonLeagueName.put(key_judge_email_address, value_judge_season_league_name);
            }
        }
        conn.close();
        return judgeEmailAddress_judgeSeasonLeagueName;
    }

    @Override
    public void createJudgeSeasonLeague(JudgeSeasonLeague judgeSeasonLeague) throws Exception
    {
        //todo
        throw new NotImplementedException();
    }

    @Override
    public void updateCalculateLeaguePointsPolicy(String seasonLeagueName, CalculateLeaguePoints calculateLeaguePoints) throws Exception
    {
        if(calculateLeaguePoints == null)
        {
            throw new NullPointerException("CalculateLeaguePoints not found");
        }
        Connection conn = DbConnector.getConnection();
        try
        {
            getSeasonLeague(seasonLeagueName);

            // the mysql update statement
            String query = " update season_league "
                + "set calculate_league_points = \'" + calculateLeaguePoints.toString() + "\' "
                + "where season_league_name = \'" + seasonLeagueName + "\'";

            // create the mysql insert preparedStatement
            PreparedStatement preparedStmt = conn.prepareStatement(query);

            // execute the preparedStatement
            preparedStmt.execute();
        }
        catch (NotFoundException e)
        {
            throw new Exception("SeasonLeague not found");
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
    public void deleteAll() throws SQLException
    {
        Connection conn = DbConnector.getConnection();
        Statement statement = conn.createStatement();
        /* TRUNCATE is faster than DELETE since
         * it does not generate rollback information and does not
         * fire any delete triggers
         */

        // the mysql delete statement
        String query = "delete from season_league";

        // create the mysql delete Statement
        statement.executeUpdate(query);
        conn.close();
    }

    @Override
    public ArrayList<String> getAllSeasonLeagueNames() throws Exception
    {
        ArrayList<String> seasonLeagueNames = new ArrayList<>();

        Connection conn = DbConnector.getConnection();

        try
        {
            // the mysql select statement
            String query = "select season_league_name from season_league";

            // create the mysql select resultSet
            Statement preparedStmt = conn.createStatement();
            ResultSet rs = preparedStmt.executeQuery(query);

            // checking if ResultSet is empty
            if (rs.next() != false)
            {
                seasonLeagueNames.add(rs.getString("season_league_name"));

                while (rs.next() != false)
                {
                    seasonLeagueNames.add(rs.getString("season_league_name"));
                }
            }
        }
        finally
        {
            conn.close();
        }
        return seasonLeagueNames;
    }

    @Override
    public ArrayList<SeasonLeague> getAllSeasonLeagueObjects() throws Exception
    {
        ArrayList<SeasonLeague> seasonLeagueObjects = new ArrayList<>();

        ArrayList<String> seasonLeagueNames = getAllSeasonLeagueNames();

        for (String season_league_name : seasonLeagueNames)
        {
            seasonLeagueObjects.add(getSeasonLeague(season_league_name));
        }
        return seasonLeagueObjects;
    }

    public static void main(String[] args) throws Exception
    {
        SeasonLeague seasonLeague11 = new SeasonLeague("seasonName1", "leagueName1", CalculateLeaguePoints.WIN_IS_2_TIE_IS_1_LOSE_IS_0, InlayGames.EACH_TWO_TEAMS_PLAY_ONE_TIME);
        SeasonLeague seasonLeague12 = new SeasonLeague("seasonName1", "leagueName2", CalculateLeaguePoints.WIN_IS_2_TIE_IS_1_LOSE_IS_0, InlayGames.EACH_TWO_TEAMS_PLAY_ONE_TIME);
        SeasonLeague seasonLeague13 = new SeasonLeague("seasonName1", "leagueName3", CalculateLeaguePoints.WIN_IS_2_TIE_IS_1_LOSE_IS_0, InlayGames.EACH_TWO_TEAMS_PLAY_ONE_TIME);
        SeasonLeague seasonLeague21 = new SeasonLeague("seasonName2", "leagueName1", CalculateLeaguePoints.WIN_IS_2_TIE_IS_1_LOSE_IS_0, InlayGames.EACH_TWO_TEAMS_PLAY_ONE_TIME);
        SeasonLeague seasonLeague22 = new SeasonLeague("seasonName2", "leagueName2", CalculateLeaguePoints.WIN_IS_2_TIE_IS_1_LOSE_IS_0, InlayGames.EACH_TWO_TEAMS_PLAY_ONE_TIME);
        SeasonLeague seasonLeague23 = new SeasonLeague("seasonName2", "leagueName3", CalculateLeaguePoints.WIN_IS_2_TIE_IS_1_LOSE_IS_0, InlayGames.EACH_TWO_TEAMS_PLAY_ONE_TIME);

        SeasonDbInServer seasonDbInServer = new SeasonDbInServer();

        LeagueDbInServer leagueDbInServer = new LeagueDbInServer();

        SeasonLeagueDbInServer seasonLeagueDbInServer = new SeasonLeagueDbInServer();
        try
        {
//            seasonLeagueDbInServer.insertSeasonLeague(seasonLeague11);
//            seasonLeagueDbInServer.insertSeasonLeague(seasonLeague12);
//            seasonLeagueDbInServer.insertSeasonLeague(seasonLeague13);
//            seasonLeagueDbInServer.insertSeasonLeague(seasonLeague21);
//            seasonLeagueDbInServer.insertSeasonLeague(seasonLeague22);
//            seasonLeagueDbInServer.insertSeasonLeague(seasonLeague23);

//            seasonDbInServer.insertSeason(season1);
//
//            //Data.NotFoundException: SeasonLeague not found
//            System.out.println(seasonLeagueDbInServer.getSeasonLeague(""));
//            //seasonName1_leagueName1
//            System.out.println(seasonLeagueDbInServer.getSeasonLeague("seasonName1_leagueName1").getSeasonLeagueName());
//            //Data.NotFoundException: SeasonLeague not found
//            System.out.println(seasonLeagueDbInServer.getSeasonLeague("seasonLeague2").getSeasonLeagueName());
//
//            seasonLeagueDbInServer.changeCalculateLeaguePointsPolicy("seasonName_leagueName", CalculateLeaguePoints.WIN_IS_1_TIE_IS_0_LOSE_IS_MINUS1);
//
//            seasonLeagueDbInServer.deleteAll();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

//        //Season season2 = seasonDbInServer.getSeason("seasonWithoutLeague1");
//        Season season2 = seasonDbInServer.getSeason("seasonName1");
//        for (String k : season2.getLeagueName_SeasonLeagueId().keySet())
//        {
//            System.out.println(k);
//            System.out.println(season2.getLeagueName_SeasonLeagueId().get(k));
//        }

//        League league2 = leagueDbInServer.getLeague("leagueWithoutSeason1");
        League league2 = leagueDbInServer.getLeague("leagueName1");
        for (String k : league2.getSeasonName_SeasonLeagueId().keySet())
        {
            System.out.println(k);
            System.out.println(league2.getSeasonName_SeasonLeagueId().get(k));
        }
    }
}
