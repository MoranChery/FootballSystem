package Data;

import Model.Season;
import Model.SeasonLeague;

import java.util.HashMap;
import java.util.Map;

public class SeasonDbInMemory implements SeasonDb
{
    /*structure like the DB of Seasons*/
    private Map<Integer, Season> seasonMap;

    private static SeasonDbInMemory ourInstance = new SeasonDbInMemory();

    public static SeasonDbInMemory getInstance() { return ourInstance; }

    public SeasonDbInMemory() { seasonMap = new HashMap<>(); }

    /**
     * Will receive from the Controller the Season, add Season to Data.
     *
     * for the tests - create Season in DB
     *
     * @param season-the new Season.
     * @throws Exception-if details are incorrect.
     */
    public void createSeason(Season season) throws Exception
    {
        if(seasonMap.containsKey(season.getSeasonId()))
        {
            throw new Exception("Season already exist in the system");
        }
        seasonMap.put(season.getSeasonId(), season);
    }

    /**
     * Will receive from the Controller the season's id, return the Season.
     *
     * "pull" Season from DB.
     *
     * @param seasonId-id of the Season.
     * @return the Season.
     * @throws Exception-if details are incorrect.
     */
    public Season getSeason(Integer seasonId) throws Exception
    {
        if (!seasonMap.containsKey(seasonId))
        {
            throw new Exception("Season not found");
        }
        return seasonMap.get(seasonId);
    }

    /**
     * Will receive from the Controller the SeasonLeague,
     * add to leagueId_SeasonLeagueId Map the leagueId and the seasonLeagueId of the specific Season.
     * @param seasonLeague-the new SeasonLeague.
     * @throws Exception-if details are incorrect.
     */
    public void addSeasonLeague(SeasonLeague seasonLeague) throws Exception
    {
        if (!seasonMap.containsKey(seasonLeague.getSeasonId()))
        {
            throw new Exception("Season not found");
        }
        seasonMap.get(seasonLeague.getSeasonId()).getLeagueId_SeasonLeagueId().put(seasonLeague.getLeagueId(), seasonLeague.getSeasonLeagueId());
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
        if (!seasonMap.containsKey(seasonId))
        {
            throw new Exception("Season not found");
        }
        return seasonMap.get(seasonId).getLeagueId_SeasonLeagueId().get(leagueId);
    }
}
