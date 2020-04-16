package Data;

import Model.League;
import Model.SeasonLeague;

import java.util.HashMap;
import java.util.Map;

public class LeagueDbInMemory implements LeagueDb
{
    /*structure like the DB of Leagues*/
    private Map<Integer, League> leagueMap;

    private static LeagueDbInMemory ourInstance = new LeagueDbInMemory();

    public static LeagueDbInMemory getInstance() { return ourInstance; }

    public LeagueDbInMemory() { leagueMap = new HashMap<>(); }

    /**
     * Will receive from the Controller the League, add League to Data.
     *
     * for the tests - create League in DB
     *
     * @param league-the new League.
     * @throws Exception-if details are incorrect.
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

    /**
     * Will receive from the Controller the SeasonLeague,
     * add to seasonId_SeasonLeagueId Map the seasonId and the seasonLeagueId of the specific League.
     * @param seasonLeague-the new SeasonLeague.
     * @throws Exception-if details are incorrect.
     */
    public void addSeasonLeague(SeasonLeague seasonLeague) throws Exception
    {
        if (!leagueMap.containsKey(seasonLeague.getSeasonId()))
        {
            throw new Exception("League not found");
        }
        leagueMap.get(seasonLeague.getLeagueId()).getSeasonId_SeasonLeagueId().put(seasonLeague.getSeasonId(), seasonLeague.getSeasonLeagueId());
    }

    /**
     * Will receive from the Controller the season's id and the league's id, return the seasonLeague's id.
     * @param seasonId-the season's id.
     * @param leagueId-the league's id.
     * @return the seasonLeague's id.
     * @throws Exception-if details are incorrect.
     */
    public Integer getSeasonLeagueIdBySeasonAndByLeague(Integer seasonId, Integer leagueId) throws Exception
    {
        if (!leagueMap.containsKey(leagueId))
        {
            throw new Exception("Season not found");
        }
        return leagueMap.get(leagueId).getSeasonId_SeasonLeagueId().get(seasonId);
    }
}
