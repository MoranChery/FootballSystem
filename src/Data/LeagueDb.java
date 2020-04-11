package Data;

import Model.League;

public interface LeagueDb
{
    void createLeague(String leagueName) throws Exception;
    League getLeague(Integer leagueId) throws Exception;
}
