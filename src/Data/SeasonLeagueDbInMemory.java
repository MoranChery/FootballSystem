package Data;

import Model.JudgeSeasonLeague;
import Model.SeasonLeague;

import java.util.HashMap;
import java.util.Map;

public class SeasonLeagueDbInMemory implements SeasonLeagueDb
{
    /*structure like the DB of SeasonLeagues*/
    private Map<Integer, SeasonLeague> seasonLeagueMap;

    private static SeasonLeagueDbInMemory ourInstance = new SeasonLeagueDbInMemory();

    public static SeasonLeagueDbInMemory getInstance() { return ourInstance; }

    public SeasonLeagueDbInMemory() { seasonLeagueMap = new HashMap<>(); }

    /**
     * Will receive from the Controller the SeasonLeague, add SeasonLeague to Data.
     *
     * for the tests - create SeasonLeague in DB
     *
     * @param seasonLeague-the new SeasonLeague.
     * @throws Exception-if details are incorrect.
     */
    public void createSeasonLeague(SeasonLeague seasonLeague) throws Exception
    {
        if(seasonLeagueMap.containsKey(seasonLeague.getSeasonLeagueId()))
        {
            throw new Exception("SeasonLeague already exist in the system");
        }
        seasonLeagueMap.put(seasonLeague.getSeasonLeagueId(), seasonLeague);
    }

    /**
     * Will receive from the Controller the seasonLeague's id, return the SeasonLeague.
     *
     * "pull" SeasonLeague from DB.
     *
     * @param seasonLeagueId-id of the SeasonLeague.
     * @return the SeasonLeague.
     * @throws Exception-if details are incorrect.
     */
    public SeasonLeague getSeasonLeague(Integer seasonLeagueId) throws Exception
    {
        if (!seasonLeagueMap.containsKey(seasonLeagueId))
        {
            throw new Exception("SeasonLeague not found");
        }
        return seasonLeagueMap.get(seasonLeagueId);
    }

    /**
     * Will receive from the Controller the JudgeSeasonLeague,
     * add to judgeEmailAddress_JudgeSeasonLeagueId Map the judgeEmailAddres and the judgeSeasonLeagueId of the specific SeasonLeague.
     * @param judgeSeasonLeague-the new JudgeSeasonLeague.
     * @throws Exception-if details are incorrect.
     */
    public void createJudgeSeasonLeague(JudgeSeasonLeague judgeSeasonLeague) throws Exception
    {
        if (!seasonLeagueMap.containsKey(judgeSeasonLeague.getSeasonLeagueId()))
        {
            throw new Exception("SeasonLeague not found");
        }
        seasonLeagueMap.get(judgeSeasonLeague.getSeasonLeagueId()).getJudgeEmailAddress_JudgeSeasonLeagueId().put(judgeSeasonLeague.getJudgeEmailAddress(), judgeSeasonLeague.getJudgeSeasonLeagueId());
    }

    /**
     * Will receive from the Service the seasonLeague's id and the calculateLeaguePoints's id,
     * want to set Policy CalculateLeaguePointsId of thr SeasonLeague.
     *
     * @param seasonLeagueId-id of the SeasonLeague.
     * @param calculateLeaguePointsId-id of the new Policy CalculateLeaguePoints.
     */
    public void changeCalculateLeaguePointsPolicy(Integer seasonLeagueId, Integer calculateLeaguePointsId) throws Exception
    {
        if(!seasonLeagueMap.containsKey(seasonLeagueId))
        {
            throw new Exception("SeasonLeague not found");
        }
        seasonLeagueMap.get(seasonLeagueId).setCalculateLeaguePointsId(calculateLeaguePointsId);
    }
}
