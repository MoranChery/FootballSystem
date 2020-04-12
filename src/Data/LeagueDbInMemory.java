package Data;

import Model.League;

import java.util.HashMap;
import java.util.Map;

public class LeagueDbInMemory implements LeagueDb
{
    /*structure like the DB of Leagues*/
    private Map<Integer, League> leagueMap;

    private static LeagueDbInMemory ourInstance = new LeagueDbInMemory();

    public static LeagueDbInMemory getInstance() { return ourInstance; }

    public LeagueDbInMemory() { leagueMap = new HashMap<Integer, League>(); }

    /**
     * Will receive from the Controller the league's name, want to create League.
     *
     * for the tests - create League in DB
     *
     * @param leagueName-name of the new League.
     * @throws Exception-if details are incorrect.
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
     * Will receive from the Controller the league's id, return the League.
     *
     * "pull" League from DB.
     *
     * @param leagueId-id of the League.
     * @return the League.
     * @throws Exception-if details are incorrect.
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
