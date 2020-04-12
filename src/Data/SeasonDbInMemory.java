package Data;

import Model.Season;

import java.util.HashMap;
import java.util.Map;

public class SeasonDbInMemory implements SeasonDb
{
    /*structure like the DB of Seasons*/
    private Map<Integer, Season> seasonMap;

    private static SeasonDbInMemory ourInstance = new SeasonDbInMemory();

    public static SeasonDbInMemory getInstance() { return ourInstance; }

    public SeasonDbInMemory() { seasonMap = new HashMap<Integer, Season>(); }

    /**
     * Will receive from the Controller the season's name, want to create Season.
     *
     * for the tests - create Season in DB
     *
     * @param seasonName-name of the new Season.
     * @throws Exception-if details are incorrect.
     */
    public void createSeason(String seasonName) throws Exception
    {
        for (Season season : seasonMap.values())
        {
            if(seasonName.equals(season.getSeasonName()))
            {
                throw new Exception("Season already exist in the system");
            }
        }
        Season season = new Season(seasonName);
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
}
