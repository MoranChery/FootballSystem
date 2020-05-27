package Data;

import Model.Season;
import Model.SeasonLeague;

import java.util.ArrayList;

public interface SeasonDb extends Db
{
    /**
     * Will receive from the Controller the Season, add Season to Data.
     * @param season-the new Season.
     * @throws Exception-if details are incorrect.
     */
    void insertSeason(Season season) throws Exception;

    /**
     * Will receive from the Controller the season's name, return the Season.
     * @param seasonName-name of the Season.
     * @return the Season.
     * @throws Exception-if details are incorrect.
     */
    Season getSeason(String seasonName) throws Exception;

    /**
     * Will receive from the Controller the SeasonLeague,
     * add to leagueName_SeasonLeagueId Map the leagueName and the seasonLeagueId of the specific Season.
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

    ArrayList<String> getAllSeasonNames() throws Exception;

    ArrayList<Season> getAllSeasonObjects() throws Exception;
}
