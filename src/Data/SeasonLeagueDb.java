package Data;

import Model.SeasonLeague;

public interface SeasonLeagueDb
{
    void createSeasonLeague(Integer leagueId, Integer seasonId, Integer calculateLeaguePointsId, Integer inlayGamesId) throws Exception;
    SeasonLeague getSeasonLeague(Integer seasonLeagueId) throws Exception;
}
