package Data;

import Model.SeasonLeague;

public interface SeasonLeagueDb
{
    /**
     * Will receive from the Controller the league's id and the season's id, want to create SeasonLeague-
     * combine exists League to exists Season, and define both kind of Policy to this specific SeasonLeague.
     * @param leagueId-id of the League.
     * @param seasonId-id of the Season.
     * @param calculateLeaguePointsId-id of the Policy CalculateLeaguePoints.
     * @param inlayGamesId-name of the Policy InlayGamesId.
     * @throws Exception-if details are incorrect.
     */
    void createSeasonLeague(Integer leagueId, Integer seasonId, Integer calculateLeaguePointsId, Integer inlayGamesId) throws Exception;

    /**
     * Will receive from the Controller the seasonLeague's id, return the SeasonLeague.
     * @param seasonLeagueId-id of the SeasonLeague.
     * @return the SeasonLeague.
     * @throws Exception-if details are incorrect.
     */
    SeasonLeague getSeasonLeague(Integer seasonLeagueId) throws Exception;

    /**
     * Will receive from the Service the season's Id, the league's Id and return the seasonLeague's id.
     * @param seasonId-id of the Season.
     * @param leagueId-id of the League.
     * @return the id of the SeasonLeague.
     * @throws Exception-if details are incorrect.
     */
    Integer getSeasonLeagueIdBySeasonAndByLeague(Integer seasonId, Integer leagueId) throws Exception;

    /**
     * Will receive from the Controller the seasonLeague's Id and the judge's id,
     * want to inlay Judge to SeasonLeague.
     * @param seasonLeagueId-id of the SeasonLeague.
     * @param judgeId-id of the Judge.
     * @throws Exception-if details are incorrect.
     */
    void inlayJudgeToSeasonLeague(Integer seasonLeagueId, Integer judgeId) throws Exception;

    /**
     * Will receive from the Service the seasonLeague's id and the calculateLeaguePoints's id,
     * want to set Policy CalculateLeaguePointsId of thr SeasonLeague.
     * Will continue to Data.
     * @param seasonLeagueId-id of the SeasonLeague.
     * @param calculateLeaguePointsId-id of the new Policy CalculateLeaguePoints.
     */
    void changeCalculateLeaguePointsPolicy(Integer seasonLeagueId, Integer calculateLeaguePointsId) throws Exception;
}
