package Data;

import Model.SeasonLeague;

import java.util.HashMap;
import java.util.Map;

public class SeasonLeagueDbInMemory implements SeasonLeagueDb
{
    /*structure like the DB of SeasonLeagues*/
    private Map<Integer, SeasonLeague> seasonLeagueMap;

    private static SeasonLeagueDbInMemory ourInstance = new SeasonLeagueDbInMemory();

    public static SeasonLeagueDbInMemory getInstance() { return ourInstance; }

    public SeasonLeagueDbInMemory() { seasonLeagueMap = new HashMap<Integer, SeasonLeague>(); }

    /**
     * Will receive from the Controller the league's id and the season's id, want to create SeasonLeague-
     * combine exists League to exists Season, and define both kind of Policy to this specific SeasonLeague.
     *
     * for the tests - create SeasonLeague in DB
     *
     * @param leagueId-id of the League.
     * @param seasonId-id of the Season.
     * @param calculateLeaguePointsId-id of the Policy CalculateLeaguePoints.
     * @param inlayGamesId-name of the Policy InlayGamesId.
     * @throws Exception-if details are incorrect.
     */
    public void createSeasonLeague(Integer leagueId, Integer seasonId, Integer calculateLeaguePointsId, Integer inlayGamesId) throws Exception
    {
        for (SeasonLeague seasonLeague : seasonLeagueMap.values())
        {
            if(seasonLeague.getLeagueId().equals(leagueId) && seasonLeague.getSeasonId().equals(seasonId))
            {
                throw new Exception("SeasonLeague already exist in the system");
            }
        }
        SeasonLeague seasonLeague = new SeasonLeague(seasonId, leagueId, calculateLeaguePointsId, inlayGamesId);
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
     * Will receive from the Service the season's Id, the league's Id and return the seasonLeague's id.
     *
     * "pull" SeasonLeagueId from DB
     *
     * @param seasonId-id of the Season.
     * @param leagueId-id of the League.
     * @return the id of the SeasonLeague.
     * @throws Exception-if details are incorrect.
     */
    public Integer getSeasonLeagueIdBySeasonAndByLeague(Integer seasonId, Integer leagueId) throws Exception
    {
        for (SeasonLeague seasonLeague : seasonLeagueMap.values())
        {
            if(seasonLeague.getSeasonId().equals(seasonId) && seasonLeague.getLeagueId().equals(leagueId))
            {
                return seasonLeague.getSeasonLeagueId();
            }
        }
        throw new Exception("SeasonLeague already exist in the system");
    }

    /**
     * Will receive from the Controller the seasonLeague's Id and the judge's id,
     * want to inlay Judge to SeasonLeague.
     *
     * combine judge to the seasonLeague
     *
     * @param seasonLeagueId-id of the SeasonLeague.
     * @param judgeId-id of the Judge.
     * @throws Exception-if details are incorrect.
     */
    public void inlayJudgeToSeasonLeague(Integer seasonLeagueId, Integer judgeId) throws Exception
    {
        if(!seasonLeagueMap.containsKey(seasonLeagueId))
        {
            throw new Exception("SeasonLeague not found");
        }
        SeasonLeague seasonLeague = seasonLeagueMap.get(seasonLeagueId);
        seasonLeague.getInlayJudgeIdList().add(judgeId);
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
        SeasonLeague seasonLeague = seasonLeagueMap.get(seasonLeagueId);
        seasonLeague.setCalculateLeaguePointsId(calculateLeaguePointsId);
    }
}
