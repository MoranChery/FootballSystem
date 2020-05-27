package Data;

import java.util.ArrayList;
import java.util.Set;

public interface GameJudgesListDb extends Db
{
    void insertGameJudgeList(String gameID, Set<String> judgesOfTheGameList) throws Exception;

    Set<String> getListJudgeEmailAddress_ByGameID (String gameID) throws Exception;

    Set<String> getListGameID_ByJudgeEmailAddress (String judgeEmailAddress) throws Exception;

}
