package Data;

import Model.Season;

import java.util.HashMap;
import java.util.Map;

public class SeasonDbInMemory implements SeasonDb
{
    /*structure like the DB of Seasons*/
    private Map<Integer, Season> seasonMap;

    public SeasonDbInMemory() { seasonMap = new HashMap<Integer, Season>(); }

    /**
     * for the tests - create Season in DB
     * @param seasonName
     * @throws Exception
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
     * "pull" Season from DB
     * @param seasonId
     * @return
     * @throws Exception
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
