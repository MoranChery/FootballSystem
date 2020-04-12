package Data;

import Model.League;

public interface LeagueDb
{
    /**
     * Will receive from the Controller the league's name, want to create League.
     * @param leagueName-name of the new League.
     * @throws Exception-if details are incorrect.
     */
    void createLeague(String leagueName) throws Exception;

    /**
     * Will receive from the Controller the league's id, return the League.
     * @param leagueId-id of the League.
     * @return the League.
     * @throws Exception-if details are incorrect.
     */
    League getLeague(Integer leagueId) throws Exception;
}
