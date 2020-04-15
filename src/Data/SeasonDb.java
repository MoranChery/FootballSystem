package Data;

import Model.Season;
import Model.SeasonLeague;

public interface SeasonDb
{
    /**
     * Will receive from the Controller the Season, add Season to Data.
     * @param season-the new Season.
     * @throws Exception-if details are incorrect.
     */
    void createSeason(Season season) throws Exception;

    /**
     * Will receive from the Controller the season's id, return the Season.
     * @param seasonId-id of the Season.
     * @return the Season.
     * @throws Exception-if details are incorrect.
     */
    Season getSeason(Integer seasonId) throws Exception;

    /**
     * Will receive from the Controller the SeasonLeague,
     * add to leagueId_SeasonLeagueId Map the leagueId and the seasonLeagueId of the specific Season.
     * @param seasonLeague-the new SeasonLeague.
     * @throws Exception-if details are incorrect.
     */
    void addSeasonLeague(SeasonLeague seasonLeague) throws Exception;

    /**
     * Will receive from the Controller the season's id and the league's id, return the seasonLeague's id.
     * @param seasonId-the season's id.
     * @param leagueId-the league's id.
     * @return the seasonLeague's id.
     * @throws Exception-if details are incorrect.
     */
    Integer getSeasonLeagueIdBySeasonAndByLeague(Integer seasonId, Integer leagueId) throws Exception;
}
