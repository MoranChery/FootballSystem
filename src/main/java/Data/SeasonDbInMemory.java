package Data;

import Model.Season;
import Model.SeasonLeague;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SeasonDbInMemory implements SeasonDb
{
    /*structure like the DB of Seasons*/
    private Map<String, Season> seasonMap;

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
    @Override
    public void insertSeason(Season season) throws Exception
    {
        if(seasonMap.containsKey(season.getSeasonName()))
        {
            throw new Exception("Season already exists in the system");
        }
        seasonMap.put(season.getSeasonName(), season);
    }

    /**
     * Will receive from the Controller the season's name, return the Season.
     *
     * "pull" Season from DB.
     *
     * @param seasonName-name of the Season.
     * @return the Season.
     * @throws Exception-if details are incorrect.
     */
    @Override
    public Season getSeason(String seasonName) throws Exception
    {
        if (!seasonMap.containsKey(seasonName))
        {
            throw new Exception("Season not found");
        }
        return seasonMap.get(seasonName);
    }

    /**
     * Will receive from the Controller the SeasonLeague,
     * add to leagueId_SeasonLeagueId Map the leagueId and the seasonLeagueId of the specific Season.
     * @param seasonLeague-the new SeasonLeague.
     * @throws Exception-if details are incorrect.
     */
    @Override
    public void addSeasonLeague(SeasonLeague seasonLeague) throws Exception
    {
        if (!seasonMap.containsKey(seasonLeague.getSeasonName()))
        {
            throw new Exception("Season not found");
        }
        seasonMap.get(seasonLeague.getSeasonName()).getLeagueName_SeasonLeagueId().put(seasonLeague.getLeagueName(), seasonLeague.getSeasonLeagueName());
    }

    /**
     * Will receive from the Controller the season's id and the league's id, return the seasonLeague's name.
     * @param seasonName-the season's name.
     * @param leagueName-the league's name.
     * @return the seasonLeague's name.
     * @throws Exception-if details are incorrect.
     */
    @Override
    public String getSeasonLeagueNameBySeasonAndByLeague(String seasonName, String leagueName) throws Exception
    {
        if (!seasonMap.containsKey(seasonName))
        {
            throw new Exception("Season not found");
        }
        return seasonMap.get(seasonName).getLeagueName_SeasonLeagueId().get(leagueName);
    }

    @Override
    public ArrayList<String> getAllSeasonNames() throws Exception
    {
        //todo
        throw new NotImplementedException();
//        return null;
    }

    @Override
    public ArrayList<Season> getAllSeasonObjects() throws Exception
    {
        return null;
    }

    /**
     * For the tests-Clear the Season Map from the DB.
     */
    @Override
    public void deleteAll()
    {
        seasonMap.clear();
    }
}
