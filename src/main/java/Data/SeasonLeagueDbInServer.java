package Data;

import Model.Enums.CalculateLeaguePoints;
import Model.Enums.InlayGames;
import Model.JudgeSeasonLeague;
import Model.League;
import Model.Season;
import Model.SeasonLeague;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.sql.*;

public class SeasonLeagueDbInServer implements SeasonLeagueDb
{
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

            // execute the preparedStatement
            preparedStmt.execute();
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
        return seasonLeague;
    }

    @Override
    public void createJudgeSeasonLeague(JudgeSeasonLeague judgeSeasonLeague) throws Exception
    {
        //todo
        throw new NotImplementedException();
    }

    @Override
    public void changeCalculateLeaguePointsPolicy(String seasonLeagueName, CalculateLeaguePoints calculateLeaguePoints) throws Exception
    {
        Connection conn = DbConnector.getConnection();
        try
        {
            // the mysql update statement
            String query = " update season_league "
                + "set calculate_league_points = \'" + calculateLeaguePoints.toString() + "\' "
                + "where season_league_name = \'" + seasonLeagueName + "\'";

            // create the mysql insert preparedStatement
            PreparedStatement preparedStmt = conn.prepareStatement(query);

            // execute the preparedStatement
            preparedStmt.execute();
        }
        catch (Exception e)
        {
            throw new Exception("SeasonLeague ??? already exists in the system");
        }
        finally
        {
            conn.close();
        }
    }

    @Override
    public void deleteAll()
    {
        Connection conn = DbConnector.getConnection();
        try
        {
            // the mysql delete statement
            String query = " delete from season_league";

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
