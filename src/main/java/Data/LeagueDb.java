package Data;

import Model.League;
import Model.SeasonLeague;

import java.util.ArrayList;

public interface LeagueDb extends Db
{
    /**
     * Will receive from the Controller the League, add League to Data.
     * @param league-the new League.
     * @throws Exception-if details are incorrect.
     */
    void insertLeague(League league) throws Exception;

    /**
     * Will receive from the Controller the league's name, return the League.
     * @param leagueName-name of the League.
     * @return the League.
     * @throws Exception-if details are incorrect.
     */
    League getLeague(String leagueName) throws Exception;

    /**
     * Will receive from the Controller the SeasonLeague,
     * add to seasonName_SeasonLeagueId Map the seasonName and the seasonLeagueId of the specific League.
     * @param seasonLeague-the new SeasonLeague.
     * @throws Exception-if details are incorrect.
     */
    void addSeasonLeague(SeasonLeague seasonLeague) throws Exception;

    /**
     * Will receive from the Controller the season's name and the league's name, return the seasonLeague's name.
     * @param seasonName-the season's name.
     * @param leagueName-the league's name.
     * @return the seasonLeague's name.
     * @throws Exception-if details are incorrect.
     */
    String getSeasonLeagueNameBySeasonAndByLeague(String seasonName, String leagueName) throws Exception;

    ArrayList<String> getAllLeagueNames() throws Exception;

    ArrayList<League> getAllLeagueObjects() throws Exception;
}
