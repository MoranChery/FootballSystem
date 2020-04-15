package Data;

import Model.League;
import Model.SeasonLeague;

public interface LeagueDb
{
    /**
     * Will receive from the Controller the League, add League to Data.
     * @param league-the new League.
     * @throws Exception-if details are incorrect.
     */
    void createLeague(League league) throws Exception;

    /**
     * Will receive from the Controller the league's id, return the League.
     * @param leagueId-id of the League.
     * @return the League.
     * @throws Exception-if details are incorrect.
     */
    League getLeague(Integer leagueId) throws Exception;

    /**
     * Will receive from the Controller the SeasonLeague,
     * add to seasonId_SeasonLeagueId Map the seasonId and the seasonLeagueId of the specific League.
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
