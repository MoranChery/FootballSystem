package Data;

import Model.Game;
import Model.UsersTypes.Judge;

import java.sql.SQLException;
import java.util.Date;

public class GameDbInServer implements GameDb {
    @Override
    public void insertGame(Game game) throws Exception {

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

    @Override
    public void deleteAll() throws SQLException {

    }
}
