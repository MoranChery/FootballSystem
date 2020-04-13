package Data;

import Model.JudgeSeasonLeague;
import Model.SeasonLeague;

public interface JudgeSeasonLeagueDb
{
    /**
     * Will receive from the Controller the JudgeSeasonLeague, add JudgeSeasonLeague to Data.
     * @param judgeSeasonLeague-the new JudgeSeasonLeague.
     * @throws Exception-if details are incorrect.
     */
    void createJudgeSeasonLeague(JudgeSeasonLeague judgeSeasonLeague) throws Exception;

    /**
     * Will receive from the Controller the judgeSeasonLeague's id, return the JudgeSeasonLeague.
     * @param judgeSeasonLeagueId-id of the JudgeSeasonLeague.
     * @return the JudgeSeasonLeague.
     * @throws Exception-if details are incorrect.
     */
    JudgeSeasonLeague getJudgeSeasonLeague(Integer judgeSeasonLeagueId) throws Exception;
}
