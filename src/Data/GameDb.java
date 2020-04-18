package Data;

import Model.Game;
import Model.UsersTypes.Judge;

public interface GameDb {

    void createGame(Game game) throws Exception;
    void addJudgeToGame(Integer gameID, Judge judgeToAdd) throws Exception;
    Game getGame(Integer gameID) throws Exception;

}
