package Service;

import Controller.RepresentativeAssociationController;
import Model.Enums.CalculateLeaguePoints;
import Model.Enums.InlayGames;
import Model.Enums.JudgeType;
import Model.Enums.QualificationJudge;

public class RepresentativeAssociationService
{
    private RepresentativeAssociationController representativeAssociationController;

    public RepresentativeAssociationService()
    {
        this.representativeAssociationController = new RepresentativeAssociationController();
    }

    /**
     * Will receive from the UI the league's name, want to create League.
     * Will continue to Controller.
     * @param representativeAssociationEmailAddress-username/emailAddress of the online RepresentativeAssociation.
     * @param leagueName-name of the new League.
     * @throws Exception-if details are incorrect.
     */
    public void createLeague(String representativeAssociationEmailAddress, String leagueName) throws Exception
    {
        if(representativeAssociationEmailAddress == null)
        {
            throw new Exception("Only RepresentativeAssociation has permissions to this action!");
        }
        if(leagueName == null)
        {
            throw new NullPointerException("One or more of the League details incorrect");
        }
        representativeAssociationController.createLeague(representativeAssociationEmailAddress, leagueName);
    }

    /**
     * Will receive from the UI the season's name, want to create Season.
     * Will continue to Controller.
     * @param representativeAssociationEmailAddress-username/emailAddress of the online RepresentativeAssociation.
     * @param seasonName-name of the new Season.
     * @throws Exception-if details are incorrect.
     */
    public void createSeason(String representativeAssociationEmailAddress, String seasonName) throws Exception
    {
        if(representativeAssociationEmailAddress == null)
        {
            throw new Exception("Only RepresentativeAssociation has permissions to this action!");
        }
        if(seasonName == null)
        {
            throw new NullPointerException("One or more of the Season details incorrect");
        }
        representativeAssociationController.createSeason(representativeAssociationEmailAddress, seasonName);
    }

    /**
     * Will receive from the UI the league's name the season's name the policy-calculateLeaguePoints and the policy-inlayGames, create SeasonLeague.
     * Will continue to Data.
     * @param representativeAssociationEmailAddress-username/emailAddress of the online RepresentativeAssociation.
     * @param leagueName-name of the League.
     * @param seasonName-name of the Season.
     * @param calculateLeaguePoints-Policy CalculateLeaguePoints.
     * @param inlayGames-Policy InlayGames.
     * @throws Exception-if details are incorrect.
     */
    public void createSeasonLeague(String representativeAssociationEmailAddress, String leagueName, String seasonName, CalculateLeaguePoints calculateLeaguePoints, InlayGames inlayGames) throws Exception
    {
        if(representativeAssociationEmailAddress == null)
        {
            throw new Exception("Only RepresentativeAssociation has permissions to this action!");
        }
        if(leagueName == null || seasonName == null || calculateLeaguePoints == null || inlayGames == null)
        {
            throw new NullPointerException("One or more of the SeasonLeague details incorrect");
        }
        representativeAssociationController.createSeasonLeague(representativeAssociationEmailAddress, leagueName, seasonName, calculateLeaguePoints, inlayGames);
    }

    /**
     * Will receive from the Service the judge's details, want to create Judge.
     * Will continue to Data.
     * @param representativeAssociationEmailAddress-username/emailAddress of the online RepresentativeAssociation.
     * @param username-username of the new Judge.
     * @param password-password of the new Judge.
     * @param id-id of the new Judge.
     * @param firstName-firstName of the new Judge.
     * @param lastName-lastName of the new Judge.
     * @param qualificationJudge-qualification of the new Judge.
     * @param judgeType-type of the new Judge.
     * @throws Exception-if details are incorrect.
     */
    public void createJudge(String representativeAssociationEmailAddress, String username, String password, Integer id, String firstName, String lastName, QualificationJudge qualificationJudge, JudgeType judgeType) throws Exception
    {
        if(representativeAssociationEmailAddress == null)
        {
            throw new Exception("Only RepresentativeAssociation has permissions to this action!");
        }
        if(username == null || password == null || id == null || firstName == null || lastName == null || qualificationJudge == null || judgeType == null)
        {
            throw new NullPointerException("One or more of the Judge details incorrect");
        }
        representativeAssociationController.createJudge(representativeAssociationEmailAddress, username, password, id, firstName, lastName, qualificationJudge, judgeType);
    }

    /**
     * Will receive from the UI the judge's id, want to remove Judge.
     * Will continue to Controller.
     * @param representativeAssociationEmailAddress-username/emailAddress of the online RepresentativeAssociation.
     * @param judgeEmailAddress-emailAddress of the Judge.
     * @throws Exception-if details are incorrect.
     */
    public void removeJudge(String representativeAssociationEmailAddress, String judgeEmailAddress) throws Exception
    {
        if(representativeAssociationEmailAddress == null)
        {
            throw new Exception("Only RepresentativeAssociation has permissions to this action!");
        }
        if(judgeEmailAddress == null)
        {
            throw new NullPointerException("One or more of the Judge details incorrect");
        }
        representativeAssociationController.removeJudge(representativeAssociationEmailAddress, judgeEmailAddress);
    }

    /**
     * Will receive from the UI the seasonLeague's name and the judge's emailAddress, want to create JudgeSeasonLeague.
     * Will continue to Controller.
     * @param representativeAssociationEmailAddress-username/emailAddress of the online RepresentativeAssociation.
     * @param seasonLeagueName-name of the SeasonLeague.
     * @param judgeEmailAddress-emailAddress of the Judge.
     * @throws Exception-if details are incorrect.
     */
    public void createJudgeSeasonLeague(String representativeAssociationEmailAddress, String seasonLeagueName, String judgeEmailAddress) throws Exception
    {
        if(representativeAssociationEmailAddress == null)
        {
            throw new Exception("Only RepresentativeAssociation has permissions to this action!");
        }
        if(seasonLeagueName == null || judgeEmailAddress == null)
        {
            throw new NullPointerException("One or more of the JudgeSeasonLeague details incorrect");
        }
        representativeAssociationController.createJudgeSeasonLeague(representativeAssociationEmailAddress, seasonLeagueName, judgeEmailAddress);
    }

    /**
     * Will receive from the UI the seasonLeague's name, policy-calculateLeaguePoints,
     * want to set Policy CalculateLeaguePoints of the SeasonLeague.
     * Will continue to Controller.
     * @param representativeAssociationEmailAddress-username/emailAddress of the online RepresentativeAssociation.
     * @param seasonLeagueName-name of SeasonLeague.
     * @param calculateLeaguePoints-Policy CalculateLeaguePoints.
     * @throws Exception
     */
    public void changeCalculateLeaguePointsPolicy(String representativeAssociationEmailAddress, String seasonLeagueName, CalculateLeaguePoints calculateLeaguePoints) throws Exception
    {
        if(representativeAssociationEmailAddress == null)
        {
            throw new Exception("Only RepresentativeAssociation has permissions to this action!");
        }
        representativeAssociationController.changeCalculateLeaguePointsPolicy(representativeAssociationEmailAddress, seasonLeagueName, calculateLeaguePoints);
    }

    /**
     * Getter for the RepresentativeAssociationController.
     * @return the RepresentativeAssociationController.
     */
    public RepresentativeAssociationController getRepresentativeAssociationController()
    {
        return representativeAssociationController;
    }
}

