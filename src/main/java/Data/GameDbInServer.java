package Data;

import Model.Court;
import Model.Enums.QualificationJudge;
import Model.Enums.Status;
import Model.Game;
import Model.SeasonLeague;
import Model.Team;
import Model.UsersTypes.Judge;

import java.sql.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class GameDbInServer implements GameDb
{
    private static GameDbInServer ourInstance = new GameDbInServer();

    public static GameDbInServer getInstance() { return ourInstance; }

    @Override
    public void insertGame(Game game) throws Exception
    {
        Connection conn = DbConnector.getConnection();
        try
        {
            // the mysql insert statement
            String query = " insert into game (game_id, game_date, season_league, host_team, guest_team, court, host_team_score, guest_team_score, major_judge, end_game_time)"
                    + " values (?,?,?,?,?,?,?,?,?,?)";

            // create the mysql insert preparedStatement
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setString(1, game.getGameID());
            preparedStmt.setDate(2, new java.sql.Date(game.getGameDate().getTime()));
            preparedStmt.setString(3, game.getSeasonLeague().getSeasonLeagueName());
            preparedStmt.setString(4, game.getHostTeam().getTeamName());
            preparedStmt.setString(5, game.getGuestTeam().getTeamName());
            preparedStmt.setString(6, game.getCourt().getCourtName());
            preparedStmt.setInt(7, game.getHostTeamScore());
            preparedStmt.setInt(8, game.getGuestTeamScore());
            preparedStmt.setString(9, game.getMajorJudge());
            preparedStmt.setDate(10, new java.sql.Date(game.getEndGameTime().getTime()));

            // execute the preparedStatement
            preparedStmt.execute();
        }
        catch (Exception e)
        {
            throw new Exception("game already exist in system");
        }
        finally
        {
            conn.close();
        }
    }

    @Override
    public void addJudgeToGame(Integer gameID, Judge judgeToAdd) throws Exception
    {

    }

    @Override
    public Game getGame(String gameID) throws Exception
    {
        if (gameID == null)
        {
            throw new NullPointerException("Game not found");
        }

        Connection conn = DbConnector.getConnection();

        // the mysql select statement
        String query = "select * from game where game.game_id = \'" + gameID + "\'";

        // create the mysql select resultSet
        Statement preparedStmt = conn.createStatement();
        ResultSet rs = preparedStmt.executeQuery(query);

        // checking if ResultSet is empty
        if (rs.next() == false)
        {
            throw new NotFoundException("Game not found");
        }

        String game_id = rs.getString("game_id");
        Date game_date = rs.getDate("game_date");
        SeasonLeague season_league = SeasonLeagueDbInServer.getInstance().getSeasonLeague(rs.getString("season_league"));
        Team host_team = TeamDbInServer.getInstance().getTeam(rs.getString("host_team"));
        Team guest_team = TeamDbInServer.getInstance().getTeam(rs.getString("guest_team"));
        Court court = CourtDbInServer.getInstance().getCourt(rs.getString("court"));
        Integer host_team_score = rs.getInt("host_team_score");
        Integer guest_team_score = rs.getInt("guest_team_score");
        String major_judge = rs.getString("major_judge");
        Date end_game_time = rs.getDate("end_game_time");

        conn.close();

        Set<String> judgesOfTheGameList = getJudgesOfTheGameList(gameID);

        Game game = new Game(game_id, game_date, season_league, host_team, guest_team, court, judgesOfTheGameList, major_judge, end_game_time);
        game.setHostTeamScore(host_team_score);
        game.setGuestTeamScore(guest_team_score);
        //todo-check what about the event log
//        game.setEventLog();

        return game;
    }

    private Set<String> getJudgesOfTheGameList(String gameID)
    {
        //todo-all function-using table game_judges_list
        Set<String> judgesGameList = new HashSet<>();


        return judgesGameList;
    }

    @Override
    public void changeGameLocation(String newLocation, String gameID) throws Exception
    {

    }

    @Override
    public void changeGameDate(String repMail, Date newDate, String gameID) throws Exception
    {

    }

    @Override
    public void deleteAll() throws SQLException
    {

    }
}
