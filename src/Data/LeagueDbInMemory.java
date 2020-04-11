package Data;

import Model.League;

import java.util.HashMap;
import java.util.Map;

public class LeagueDbInMemory implements LeagueDb
{
    /*structure like the DB of Leagues*/
    private Map<Integer, League> leagueMap;

    public LeagueDbInMemory()
    {
        leagueMap = new HashMap<Integer, League>();
    }

    /**
     * for the tests - create League in DB
     * @param leagueName
     * @throws Exception
     */
    public void createLeague(String leagueName) throws Exception
    {
        for (League league : leagueMap.values())
        {
            if(leagueName.equals(league.getLeagueName()))
            {
                throw new Exception("League already exist in the system");
            }
        }
        League league = new League(leagueName);
        leagueMap.put(league.getLeagueId(), league);
    }

    /**
     * "pull" League from DB
     * @param leagueId
     * @return
     * @throws Exception
     */
    public League getLeague(Integer leagueId) throws Exception
    {
        if (!leagueMap.containsKey(leagueId))
        {
            throw new Exception("League not found");
        }
        return leagueMap.get(leagueId);
    }

}
