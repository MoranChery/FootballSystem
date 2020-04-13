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
        if(leagueName == null)
        {
            throw new NullPointerException("One or more of the League details incorrect");
        }
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
        if(seasonName == null)
        {
            throw new NullPointerException("One or more of the Season details incorrect");
        }
        representativeAssociationController.createSeason(seasonName);
    }

    /**
     * Will receive from the UI the league's id the season's id the policy-calculateLeaguePoints's id and the policy-inlayGames's id , want to create SeasonLeague.
     * Will continue to Controller.
     * @param leagueId-id of the League.
     * @param seasonId-id of the Season.
     * @param calculateLeaguePointsId-id of the Policy CalculateLeaguePoints.
     * @param inlayGamesId-name of the Policy InlayGamesId.
     * @throws Exception-if details are incorrect.
     */
    public void createSeasonLeague(Integer leagueId, Integer seasonId, Integer calculateLeaguePointsId, Integer inlayGamesId) throws Exception
    {
        if(leagueId == null || seasonId == null || calculateLeaguePointsId == null || inlayGamesId == null)
        {
            throw new NullPointerException("One or more of the SeasonLeague details incorrect");
        }
        representativeAssociationController.createSeasonLeague(leagueId, seasonId, calculateLeaguePointsId, inlayGamesId);
    }

    /**
     * Will receive from the Service the judge's details, want to create Judge.
     * Will continue to Data.
     * @param username-username of the new Judge.
     * @param password-password of the new Judge.
     * @param id-id of the new Judge.
     * @param firstName-firstName of the new Judge.
     * @param lastName-lastName of the new Judge.
     * @param qualificationJudge-qualification of the new Judge.
     * @throws Exception-if details are incorrect.
     */
    public void createJudge(String username, String password,Integer id, String firstName, String lastName, QualificationJudge qualificationJudge) throws Exception
    {
        if(username == null || password == null || id == null || firstName == null || lastName == null || qualificationJudge == null)
        {
            throw new NullPointerException("One or more of the Judge details incorrect");
        }
        representativeAssociationController.createJudge(username, password, id, firstName, lastName, qualificationJudge);
    }

    /**
     * Will receive from the UI the judge's id, want to remove Judge.
     * Will continue to Controller.
     * @param judgeEmailAddress-emailAddress of the Judge.
     * @throws Exception-if details are incorrect.
     */
    public void removeJudge(String judgeEmailAddress) throws Exception
    {
        if(judgeEmailAddress == null)
        {
            throw new NullPointerException("One or more of the Judge details incorrect");
        }
        representativeAssociationController.removeJudge(judgeEmailAddress);
    }

    /**
     * Will receive from the UI the season's id, the league's id and the judge's id, want to create JudgeSeasonLeague.
     * Will continue to Controller.
     * @param seasonId-id of the Season.
     * @param leagueId-id of the League.
     * @param judgeEmailAddress-emailAddress of the Judge.
     * @throws Exception-if details are incorrect.
     */
    public void createJudgeSeasonLeague(Integer seasonId, Integer leagueId, String judgeEmailAddress) throws Exception
    {
        if(seasonId == null || leagueId == null || judgeEmailAddress == null)
        {
            throw new NullPointerException("One or more of the JudgeSeasonLeague details incorrect");
        }
        representativeAssociationController.createJudgeSeasonLeague(seasonId, leagueId, judgeEmailAddress);
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

