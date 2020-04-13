package Data;

import Model.League;

public interface LeagueDb
{
    /**
     * Will receive from the Controller the League, add it to Data.
     * @param league-the new League.
     * @throws Exception
     */
    void createLeague(League league) throws Exception;

    /**
     * Will receive from the Controller the league's id, return the League.
     * @param leagueId-id of the League.
     * @return the League.
     * @throws Exception-if details are incorrect.
     */
    League getLeague(Integer leagueId) throws Exception;
}
