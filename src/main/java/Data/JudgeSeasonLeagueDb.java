package Data;

import Model.JudgeSeasonLeague;

import java.util.ArrayList;

public interface JudgeSeasonLeagueDb extends Db
{
    /**
     * Will receive from the Controller the JudgeSeasonLeague, add JudgeSeasonLeague to Data.
     * @param judgeSeasonLeague-the new JudgeSeasonLeague.
     * @throws Exception-if details are incorrect.
     */
    void insertJudgeSeasonLeague(JudgeSeasonLeague judgeSeasonLeague) throws Exception;

    /**
     * Will receive from the Controller the judgeSeasonLeague's name, return the JudgeSeasonLeague.
     * @param judgeSeasonLeagueName-name of the JudgeSeasonLeague.
     * @return the JudgeSeasonLeague.
     * @throws Exception-if details are incorrect.
     */
    JudgeSeasonLeague getJudgeSeasonLeague(String judgeSeasonLeagueName) throws Exception;

    void removeJudgeSeasonLeague(String judgeSeasonLeagueName)throws Exception;

    ArrayList<String> getAllJudgeSeasonLeagueNames() throws Exception;
}
