package Data;

import Model.League;
import Model.SeasonLeague;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class LeagueDbInMemory implements LeagueDb
{
    /*structure like the DB of Leagues*/
    private Map<String, League> leagueMap;

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
    @Override
    public void insertLeague(League league) throws Exception
    {
        if(leagueMap.containsKey(league.getLeagueName()))
        {
            throw new Exception("League already exists in the system");
        }
        leagueMap.put(league.getLeagueName(), league);
    }

    /**
     * Will receive from the Controller the league's name, return the League.
     *
     * "pull" League from DB.
     *
     * @param leagueName-name of the League.
     * @return the League.
     * @throws Exception-if details are incorrect.
     */
    @Override
    public League getLeague(String leagueName) throws Exception
    {
        if (!leagueMap.containsKey(leagueName))
        {
            throw new Exception("League not found");
        }
        return leagueMap.get(leagueName);
    }

    /**
     * Will receive from the Controller the SeasonLeague,
     * add to seasonId_SeasonLeagueId Map the seasonId and the seasonLeagueId of the specific League.
     * @param seasonLeague-the new SeasonLeague.
     * @throws Exception-if details are incorrect.
     */
    @Override
    public void addSeasonLeague(SeasonLeague seasonLeague) throws Exception
    {
        if (!leagueMap.containsKey(seasonLeague.getLeagueName()))
        {
            throw new Exception("League not found");
        }
        leagueMap.get(seasonLeague.getLeagueName()).getSeasonName_SeasonLeagueId().put(seasonLeague.getSeasonName(), seasonLeague.getSeasonLeagueName());
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
        if (!leagueMap.containsKey(leagueName))
        {
            throw new Exception("Season not found");
        }
        return leagueMap.get(leagueName).getSeasonName_SeasonLeagueId().get(seasonName);
    }

    @Override
    public ArrayList<String> getAllLeagueNames() throws Exception
    {
        //todo
        throw new NotImplementedException();
//        return null;
    }

    @Override
    public ArrayList<League> getAllLeagueObjects() throws Exception
    {
        //todo
        throw new NotImplementedException();
//        return null;
    }

    /**
     * For the tests-Clear the League Map from the DB.
     */
    @Override
    public void deleteAll()
    {
        leagueMap.clear();
    }
}
