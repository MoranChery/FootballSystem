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
     * Will receive from the Controller the League, add it to Data.
     *
     * for the tests - create League in DB
     *
     * @param league-the new League.
     * @throws Exception
     */
    public void createLeague(League league) throws Exception
    {
        if(leagueMap.containsKey(league.getLeagueId()))
        {
            throw new Exception("League already exist in the system");
        }
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
