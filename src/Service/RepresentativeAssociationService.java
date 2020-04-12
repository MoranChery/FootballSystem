package Service;

import Controller.RepresentativeAssociationController;
import Model.Enums.QualificationJudge;

public class RepresentativeAssociationService
{
    RepresentativeAssociationController representativeAssociationController;

    /**
     * Will receive from the UI the league's name, want to create League.
     * Will continue to Controller.
     * @param leagueName-name of the new League.
     * @throws Exception-if details are incorrect.
     */
    public void createLeague(String leagueName) throws Exception
    {
        representativeAssociationController.createLeague(leagueName);
    }

    /**
     * Will receive from the UI the season's name, want to create Season.
     * Will continue to Controller.
     * @param seasonName-name of the new Season.
     * @throws Exception-if details are incorrect.
     */
    public void createSeason(String seasonName) throws Exception
    {
        representativeAssociationController.createSeason(seasonName);
    }

    /**
     * Will receive from the UI the league's id and the season's id, want to create SeasonLeague-
     * combine exists League to exists Season, and define both kind of Policy to this specific SeasonLeague.
     * Will continue to Controller.
     * @param leagueId-id of the League.
     * @param seasonId-id of the Season.
     * @param calculateLeaguePointsId-id of the Policy CalculateLeaguePoints.
     * @param inlayGamesId-name of the Policy InlayGamesId.
     * @throws Exception-if details are incorrect.
     */
    public void createSeasonLeague(Integer leagueId, Integer seasonId, Integer calculateLeaguePointsId, Integer inlayGamesId) throws Exception
    {
        representativeAssociationController.createSeasonLeague(leagueId, seasonId, calculateLeaguePointsId, inlayGamesId);
    }

    /**
     * Will receive from the UI the judge's name, want to create Judge.
     * Will continue to Controller.
     * @param judgeName-name of the new Judge.
     * @param qualificationJudge-qualification of the new Judge.
     * @throws Exception-if details are incorrect.
     */
    public void createJudge(String judgeName, QualificationJudge qualificationJudge) throws Exception
    {
        representativeAssociationController.createJudge(judgeName, qualificationJudge);
    }

    /**
     * Will receive from the UI the judge's id, want to remove Judge.
     * Will continue to Controller.
     * @param judgeId-id of the Judge.
     * @throws Exception-if details are incorrect.
     */
    public void removeJudge(Integer judgeId) throws Exception
    {
        representativeAssociationController.removeJudge(judgeId);
    }

    /**
     * Will receive from the UI the season's id, the league's id and the judge's id,
     * want to inlay Judge to SeasonLeague.
     * Will continue to Controller.
     * @param seasonId-id of the Season.
     * @param leagueId-id of the League.
     * @param judgeId-id of the Judge.
     * @throws Exception-if details are incorrect.
     */
    public void inlayJudgeToSeasonLeague(Integer seasonId, Integer leagueId, Integer judgeId) throws Exception
    {
        representativeAssociationController.inlayJudgeToSeasonLeague(seasonId, leagueId, judgeId);
    }

    /**
     * Will receive from the UI the season's id, the league's id and the calculateLeaguePoints's id,
     * want to set Policy CalculateLeaguePointsId of thr SeasonLeague.
     * Will continue to Controller.
     * @param seasonId-id of the Season.
     * @param leagueId-id of the League.
     * @param calculateLeaguePointsId-id of the new Policy CalculateLeaguePoints.
     * @throws Exception-if details are incorrect.
     */
    public void changeCalculateLeaguePointsPolicy(Integer seasonId, Integer leagueId, Integer calculateLeaguePointsId) throws Exception
    {
        representativeAssociationController.changeCalculateLeaguePointsPolicy(seasonId, leagueId, calculateLeaguePointsId);
    }
}

