package Data;

import Model.Game;
import Model.UsersTypes.Judge;

import java.util.Date;

public interface GameDb extends Db{

    void createGame(Game game) throws Exception;
    void addJudgeToGame(Integer gameID, Judge judgeToAdd) throws Exception;
    Game getGame(String gameID) throws Exception;
    void changeGameLocation(String newLocation, String gameID) throws Exception;
    void changeGameDate(String repMail, Date newDate, String gameID) throws Exception;

}