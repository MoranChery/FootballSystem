package Data;

import Model.SeasonLeague;

import java.util.HashMap;
import java.util.Map;

public class SeasonLeagueDbInMemory implements SeasonLeagueDb
{
    /*structure like the DB of SeasonLeagues*/
    private Map<Integer, SeasonLeague> seasonLeagueMap;

    public SeasonLeagueDbInMemory() { seasonLeagueMap = new HashMap<Integer, SeasonLeague>(); }

    /**
     * for the tests - create SeasonLeague in DB
     * @param leagueId
     * @param seasonId
     * @throws Exception
     */
    public void createSeasonLeague(Integer leagueId, Integer seasonId, Integer calculateLeaguePointsId, Integer inlayGamesId) throws Exception
    {
        for (SeasonLeague seasonLeague : seasonLeagueMap.values())
        {
            if(seasonLeague.getLeagueId() == leagueId && seasonLeague.getSeasonId() == seasonId)
            {
                throw new Exception("SeasonLeague already exist in the system");
            }
        }
        SeasonLeague seasonLeague = new SeasonLeague(seasonId, leagueId, calculateLeaguePointsId, inlayGamesId);
        seasonLeagueMap.put(seasonLeague.getSeasonLeagueId(), seasonLeague);
    }

    /**
     * "pull" SeasonLeague from DB
     * @param seasonLeagueId
     * @return
     * @throws Exception
     */
    public SeasonLeague getSeasonLeague(Integer seasonLeagueId) throws Exception
    {
        if (!seasonLeagueMap.containsKey(seasonLeagueId))
        {
            throw new Exception("SeasonLeague not found");
        }
        return seasonLeagueMap.get(seasonLeagueId);
    }
}
