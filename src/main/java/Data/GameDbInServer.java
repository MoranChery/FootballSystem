package Data;

import Model.Court;
import Model.Enums.QualificationJudge;
import Model.Enums.Status;
import Model.Game;
import Model.SeasonLeague;
import Model.Team;
import Model.UsersTypes.Judge;
import Model.PageType;
import Model.Season;
import Model.UsersTypes.TeamOwner;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.*;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import java.text.ParseException;
import java.text.SimpleDateFormat;

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
            java.sql.Timestamp timestampStart = new java.sql.Timestamp((game.getGameDate().getTime()));

            preparedStmt.setTimestamp(2,timestampStart);
            preparedStmt.setString (3, game.getSeasonLeague());
            preparedStmt.setString (4, game.getHostTeam());
            preparedStmt.setString (5, game.getGuestTeam());
            preparedStmt.setString (6, game.getCourt());
            preparedStmt.setInt(7, game.getHostTeamScore());
            preparedStmt.setInt(8, game.getGuestTeamScore());
            preparedStmt.setString(9, game.getMajorJudge());
            if (game.getEndGameTime() != null)
            {
                java.sql.Timestamp timestampEnd = new java.sql.Timestamp((game.getEndGameTime().getTime()));
//                preparedStmt.setTimestamp(10, new java.sql.Date(game.getEndGameTime().getTime()));
                preparedStmt.setTimestamp(10, timestampEnd);
            }
            else
            {
                preparedStmt.setTimestamp(10, null);
            }

            // execute the preparedStatement
            preparedStmt.execute();

            if (game.getJudgesOfTheGameList() != null)
            {
                Set<String> judgesOfTheGameList = game.getJudgesOfTheGameList();
                String majorJudge = game.getMajorJudge();
                if(majorJudge != null)
                {
                    judgesOfTheGameList.add(majorJudge);
                    game.setJudgesOfTheGameList(judgesOfTheGameList);
                }

                GameJudgesListDbInServer.getInstance().insertGameJudgeList(game.getGameID(), game.getJudgesOfTheGameList());
            }
            updateGameDate(null, game.getGameDate(), game.getGameID());
        }
        catch (Exception e)
        {
            throw new Exception("Game already exist in system");
        }
        finally
        {
            conn.close();
        }
    }

    @Override
    public void addJudgeToGame(Integer gameID, Judge judgeToAdd) throws Exception
    {
        //todo
        throw new NotImplementedException();
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
        String gameDate = rs.getString("game_date");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date time = sdf.parse(gameDate);
        Date game_date = time;
//        Date game_date = rs.getDate("game_date");
        String season_league = rs.getString("season_league");
        String host_team = rs.getString("host_team");
        String guest_team = rs.getString("guest_team");
        String court = rs.getString("court");
        Integer host_team_score = rs.getInt("host_team_score");
        Integer guest_team_score = rs.getInt("guest_team_score");
        String major_judge = rs.getString("major_judge");

        Date end_game_time = rs.getTimestamp("end_game_time");
//        Date end_game_time = rs.getDate("end_game_time");

        conn.close();

        Set<String> judgesOfTheGameList = getJudgesOfTheGameList(gameID);

        Game game = new Game(game_id, game_date, season_league, host_team, guest_team, court, judgesOfTheGameList, major_judge, end_game_time);
        game.setHostTeamScore(host_team_score);
        game.setGuestTeamScore(guest_team_score);

        return game;
    }

    private Set<String> getJudgesOfTheGameList(String gameID) throws Exception
    {
        return GameJudgesListDbInServer.getInstance().getListJudgeEmailAddress_ByGameID(gameID);
    }

    @Override
    public List<Game> getAllGames() throws Exception
    {
        List<Game> games = new ArrayList<>();
        String game_id;
        Game game;

        Connection conn = DbConnector.getConnection();

        // create the mysql select resultSet
        String query = "select * from game";

        Statement preparedStmt = conn.createStatement();
        ResultSet rs = preparedStmt.executeQuery(query);

        // checking if ResultSet is empty
//        if (rs.next() == false) {
//            throw new NotFoundException("no games in db");
//        }


        if (rs.next() != false)
        {
            game_id = rs.getString("game_id");
            game = getGame(game_id);
            games.add(game);

            while (rs.next() != false)
            {
                game_id = rs.getString("game_id");
                game = getGame(game_id);
                games.add(game);            }
        }

//        List<Game> games = new ArrayList<>();
//        while(rs.next()){
//            String game_id = rs.getString("game_id");
//            String game_date = rs.getString("game_date");
//            String season_league = rs.getString("season_league");
//            String host_team = rs.getString("host_team");
//            String guest_team = rs.getString("guest_team");
//            String court = rs.getString("court");
//
////            Date date = new SimpleDateFormat("dd/MM/yyyy").parse(game_date);
//            Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(game_date);
//
////            Game game = new Game(game_id,date,season_league,host_team,guest_team,court);
//            Game game = getGame(game_id);
//            games.add(game);
        return games;
    }















    @Override
    public void updateGameLocation(String newLocation, String gameID) throws Exception
    {
        Connection conn = DbConnector.getConnection();
        try
        {

            // the mysql update statement
            String query = " update game "
                    + "set court = \'" + newLocation + "\' "
                    + "where game_id = \'" + gameID + "\'";

            // create the mysql insert preparedStatement
            PreparedStatement preparedStmt = conn.prepareStatement(query);

            // execute the preparedStatement
            preparedStmt.execute();
        }
//        catch (NotFoundException e)
//        {
//            throw new Exception("Game not found");
//        }
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
    public void updateGameDate(String repMail, Date newDate, String gameID) throws Exception
    {
        Connection conn = DbConnector.getConnection();
        try
        {
            getGame(gameID);

            Date endGameTime = new Date();

            int minute = newDate.getMinutes();
            int hour = newDate.getHours();
            if(minute > 30)
            {
                minute = minute - 30;
                hour = hour + 2;
            }
            else
            {
                minute = minute + 30;
                hour = hour + 1;
            }

            endGameTime = new Date();

            endGameTime.setHours(hour);
            endGameTime.setMinutes(minute);

            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
            String time = sdf.format(endGameTime);
            LocalTime timePart = LocalTime.parse(time);
            String startingDate = new SimpleDateFormat("yyyy-MM-dd").format(newDate);
            LocalDate datePart = LocalDate.parse(startingDate);
            LocalDateTime dt = LocalDateTime.of(datePart, timePart);
            endGameTime = convertToDateViaInstant(dt);


            java.sql.Timestamp timestampStart = new java.sql.Timestamp((newDate.getTime()));

            java.sql.Timestamp timestampEnd = new java.sql.Timestamp((endGameTime.getTime()));


            // the mysql update statement
            String query = " update game "
                    + "set game_date = \'" + timestampStart + "\' "
                    + ", end_game_time = \'" + timestampEnd + "\' "
                    + "where game_id = \'" + gameID + "\'";

            // create the mysql insert preparedStatement
            PreparedStatement preparedStmt = conn.prepareStatement(query);

            // execute the preparedStatement
            preparedStmt.execute();
        }
        catch (NotFoundException e)
        {
            throw new Exception("Game not found");
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

    private Date convertToDateViaInstant(LocalDateTime dateToConvert)
    {
        return java.util.Date.from(dateToConvert.atZone(ZoneId.systemDefault()).toInstant());
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
        String query = "delete from game";

        // create the mysql delete Statement
        statement.executeUpdate(query);
        conn.close();
    }





    public static void main(String[] args) throws Exception {
        SeasonDbInServer seasonDbInServer = new SeasonDbInServer();
        GameDbInServer gameDbInServer = new GameDbInServer();
        Game game1  = new Game("gameNoy",new Date(),"sl1", "team1", "team2","court");
//        Game game2  = new Game("game2",new Date(),"sl1", "team1", "team2","court");
        gameDbInServer.insertGame(game1);
//        gameDbInServer.insertGame(game2);

        List<Game> allGames = gameDbInServer.getAllGames();
        System.out.println(allGames);
    }
}

