package Data;

import Model.JudgeSeasonLeague;
import Model.SeasonLeague;

public interface SeasonLeagueDb
{
    /**
     * Will receive from the Controller the SeasonLeague, add SeasonLeague to Data.
     * @param seasonLeague-the new SeasonLeague.
     * @throws Exception-if details are incorrect.
     */
    void createSeasonLeague(SeasonLeague seasonLeague) throws Exception;

    /**
     * Will receive from the Controller the seasonLeague's id, return the SeasonLeague.
     * @param seasonLeagueId-id of the SeasonLeague.
     * @return the SeasonLeague.
     * @throws Exception-if details are incorrect.
     */
    SeasonLeague getSeasonLeague(Integer seasonLeagueId) throws Exception;

    /**
     * Will receive from the Controller the JudgeSeasonLeague,
     * add to judgeEmailAddress_JudgeSeasonLeagueId Map the judgeEmailAddres and the judgeSeasonLeagueId of the specific SeasonLeague.
     * @param judgeSeasonLeague-the new JudgeSeasonLeague.
     * @throws Exception-if details are incorrect.
     */
    void createJudgeSeasonLeague(JudgeSeasonLeague judgeSeasonLeague) throws Exception;

    /**
     * Will receive from the Service the seasonLeague's id and the calculateLeaguePoints's id,
     * want to set Policy CalculateLeaguePointsId of thr SeasonLeague.
     * Will continue to Data.
     * @param seasonLeagueId-id of the SeasonLeague.
     * @param calculateLeaguePointsId-id of the new Policy CalculateLeaguePoints.
     */
    void changeCalculateLeaguePointsPolicy(Integer seasonLeagueId, Integer calculateLeaguePointsId) throws Exception;
}
