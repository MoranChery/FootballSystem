package Data;

import Model.Enums.CalculateLeaguePoints;
import Model.JudgeSeasonLeague;
import Model.SeasonLeague;

import java.util.ArrayList;

public interface SeasonLeagueDb extends Db
{
    /**
     * Will receive from the Controller the SeasonLeague, add SeasonLeague to Data.
     * @param seasonLeague-the new SeasonLeague.
     * @throws Exception-if details are incorrect.
     */
    void insertSeasonLeague(SeasonLeague seasonLeague) throws Exception;

    /**
     * Will receive from the Controller the seasonLeague's name, return the SeasonLeague.
     * @param seasonLeagueName-id of the SeasonLeague.
     * @return the SeasonLeague.
     * @throws Exception-if details are incorrect.
     */
    SeasonLeague getSeasonLeague(String seasonLeagueName) throws Exception;

    /**
     * Will receive from the Controller the JudgeSeasonLeague,
     * add to judgeEmailAddress_JudgeSeasonLeagueName Map the judgeEmailAddress and the judgeSeasonLeagueName of the specific SeasonLeague.
     * @param judgeSeasonLeague-the new JudgeSeasonLeague.
     * @throws Exception-if details are incorrect.
     */
    void createJudgeSeasonLeague(JudgeSeasonLeague judgeSeasonLeague) throws Exception;

    /**
     * Will receive from the Controller the seasonLeague's name and the calculateLeaguePoints,
     * want to set Policy CalculateLeaguePoints of thr SeasonLeague.
     * @param seasonLeagueName-name of the SeasonLeague.
     * @param calculateLeaguePoints-new Policy CalculateLeaguePoints.
     */
    void updateCalculateLeaguePointsPolicy(String seasonLeagueName, CalculateLeaguePoints calculateLeaguePoints) throws Exception;

    ArrayList<String> getAllSeasonLeagueNames() throws Exception;

    ArrayList<SeasonLeague> getAllSeasonLeagueObjects() throws Exception;
}
