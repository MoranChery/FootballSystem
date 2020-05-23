package Data;

import Model.Enums.Status;
import Model.Game;
import Model.PageType;
import Model.Season;
import Model.SeasonLeague;
import Model.UsersTypes.Judge;
import Model.UsersTypes.TeamOwner;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

public class GameDbInServer implements GameDb {
    @Override
    public void insertGame(Game game) throws Exception {
        Connection conn = DbConnector.getConnection();
        try
        {
            // the mysql insert statement
            String query = " insert into game (game_id,game_date,season_league,host_team,guest_team,court)"
                    + " values (?,?,?,?,?,?)";

            // create the mysql insert preparedstatement
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setString (1, game.getGameID());
            SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
            String date = df.format(game.getGameDate());
            preparedStmt.setString (2,date);
            preparedStmt.setString (3, game.getSeasonLeague());
            preparedStmt.setString (4, game.getHostTeam());
            preparedStmt.setString (5, game.getGuestTeam());
            preparedStmt.setString (6, game.getCourt());

            // execute the preparedstatement
            preparedStmt.execute();

        } catch(SQLIntegrityConstraintViolationException e) {
            throw new Exception("game already exist in the system");
        }
        finally {
            conn.close();
        }
    }

    @Override
    public void addJudgeToGame(Integer gameID, Judge judgeToAdd) throws Exception {

    }

    @Override
    public Game getGame(String gameID) throws Exception {
        return null;
    }

    @Override
    public void changeGameLocation(String newLocation, String gameID) throws Exception {

    }

    @Override
    public void changeGameDate(String repMail, Date newDate, String gameID) throws Exception {

    }

    public List<Game> getAllGames() throws SQLException, NotFoundException, ParseException {
        Connection conn = DbConnector.getConnection();

        String query = "select * from game";

        Statement preparedStmt = conn.createStatement();
        ResultSet rs = preparedStmt.executeQuery(query);

        // checking if ResultSet is empty
        if (rs.next() == false) {
            throw new NotFoundException("no games in db");
        }

        List<Game> games = new ArrayList<>();
        while(rs.next()){
            String game_id = rs.getString("game_id");
            String game_date = rs.getString("game_date");
            String season_league = rs.getString("season_league");
            String host_team = rs.getString("host_team");
            String guest_team = rs.getString("guest_team");
            String court = rs.getString("court");

            Date date = new SimpleDateFormat("dd/MM/yyyy").parse(game_date);

            Game game = new Game(game_id,date,season_league,host_team,guest_team,court);
            games.add(game);
        }
        return games;
    }

    @Override
    public void deleteAll() throws SQLException {

    }

    public static void main(String[] args) throws Exception {
        SeasonDbInServer seasonDbInServer = new SeasonDbInServer();
        GameDbInServer gameDbInServer = new GameDbInServer();
        Game game1  = new Game("game1",new Date(),"sl1", "team1", "team2","court");
        Game game2  = new Game("game2",new Date(),"sl1", "team1", "team2","court");
        gameDbInServer.insertGame(game1);
        gameDbInServer.insertGame(game2);

        List<Game> allGames = gameDbInServer.getAllGames();
        System.out.println(allGames);

    }
    }
