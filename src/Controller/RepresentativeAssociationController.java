package Controller;

import Data.*;
import Model.Enums.*;
import Model.JudgeSeasonLeague;
import Model.League;
import Model.Season;
import Model.SeasonLeague;
import Model.UsersTypes.Judge;
import Model.UsersTypes.RepresentativeAssociation;

public class RepresentativeAssociationController
{
    private RepresentativeAssociationDb representativeAssociationDb;
    private SubscriberDb subscriberDb;
    private RoleDb roleDb;
    private LeagueDb leagueDb;
    private SeasonDb seasonDb;
    private SeasonLeagueDb seasonLeagueDb;
    private JudgeDb judgeDb;
    private JudgeSeasonLeagueDb judgeSeasonLeagueDb;

    public RepresentativeAssociationController()
    {
        this.representativeAssociationDb = RepresentativeAssociationDbInMemory.getInstance();
        this.subscriberDb = SubscriberDbInMemory.getInstance();
        this.roleDb = RoleDbInMemory.getInstance();
        this.leagueDb = LeagueDbInMemory.getInstance();
        this.seasonDb = SeasonDbInMemory.getInstance();
        this.seasonLeagueDb = SeasonLeagueDbInMemory.getInstance();
        this.judgeDb = JudgeDbInMemory.getInstance();
        this.judgeSeasonLeagueDb = JudgeSeasonLeagueDbInMemory.getInstance();
    }

    /**
     * Will receive the RepresentativeAssociation, add RepresentativeAssociation to Data.
     * Will continue to Data.
     * @param representativeAssociation-the new RepresentativeAssociation.
     * @throws Exception-if details are incorrect.
     */
    public void createRepresentativeAssociation(RepresentativeAssociation representativeAssociation) throws Exception
    {
        if(representativeAssociation == null)
        {
            throw new NullPointerException("No RepresentativeAssociation been created");
        }
        representativeAssociationDb.createRepresentativeAssociation(representativeAssociation);
    }

    /**
     * Will receive from the Service the league's name, create the League.
     * Will continue to Data.
     * @param leagueName-name of the new League.
     * @throws Exception-if details are incorrect.
     */
    public void createLeague(String leagueName) throws Exception
    {
        if(leagueName == null)
        {
            throw new NullPointerException("One or more of the League details incorrect");
        }
        League league = new League(leagueName);
        leagueDb.createLeague(league);
    }

    /**
     * Will receive from the Service the season's name, create the Season.
     * Will continue to Data.
     * @param seasonName-name of the new Season.
     * @throws Exception-if details are incorrect.
     */
    public void createSeason(String seasonName) throws Exception
    {
        if(seasonName == null)
        {
            throw new NullPointerException("One or more of the Season details incorrect");
        }
        Season season = new Season(seasonName);
        seasonDb.createSeason(season);
    }

    /**
     * Will receive from the Service the league's name the season's name the policy-calculateLeaguePoints and the policy-inlayGames, create SeasonLeague.
     * Will continue to Data.
     * @param leagueName-name of the League.
     * @param seasonName-name of the Season.
     * @param calculateLeaguePoints-Policy CalculateLeaguePoints.
     * @param inlayGames-Policy InlayGames.
     * @throws Exception-if details are incorrect.
     */
    public void createSeasonLeague(String leagueName, String seasonName, CalculateLeaguePoints calculateLeaguePoints, InlayGames inlayGames) throws Exception
    {
        if(leagueName == null || seasonName == null || calculateLeaguePoints == null || inlayGames == null)
        {
            throw new NullPointerException("One or more of the SeasonLeague details incorrect");
        }
        SeasonLeague seasonLeague = new SeasonLeague(seasonName, leagueName, calculateLeaguePoints, inlayGames);
        seasonDb.addSeasonLeague(seasonLeague);
        leagueDb.addSeasonLeague(seasonLeague);
        seasonLeagueDb.createSeasonLeague(seasonLeague);
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
    public void createJudge(String username, String password, Integer id, String firstName, String lastName, QualificationJudge qualificationJudge, JudgeType theJudgeType) throws Exception
    {
        if(username == null || password == null || id == null || firstName == null || lastName == null || qualificationJudge == null)
        {
            throw new NullPointerException("One or more of the Judge details incorrect");
        }
        Judge judge = new Judge(username, password, id, firstName, lastName, qualificationJudge,theJudgeType);
        try
        {
            subscriberDb.createSubscriber(judge);
            try
            {
                judgeDb.createJudge(judge);
            }
            catch (Exception e)
            {
                throw new Exception("Judge already exists in the system");
            }
        }
        catch (Exception e)
        {
            throw new Exception("Judge already exists in the system");
        }
        roleDb.createRoleInSystem(username, RoleType.JUDGE);
    }

    /**
     * Will receive from the Service the judge's emailAddress, want to remove Judge.
     * Will continue to Data.
     * @param judgeEmailAddress-emailAddress of the Judge.
     * @throws Exception-if details are incorrect.
     */
    public void removeJudge(String judgeEmailAddress) throws Exception
    {
        if(judgeEmailAddress == null)
        {
            throw new NullPointerException("One or more of the Judge details incorrect");
        }
        Judge judge = judgeDb.getJudge(judgeEmailAddress);
        if(subscriberDb.removeSubscriberFromDB(judge) == false)
        {
            throw new Exception("Judge not found");
        }
        else
        {
            judgeDb.removeJudge(judgeEmailAddress);
            roleDb.removeRoleFromSystem(judgeEmailAddress);
        }
    }

    /**
     * Will receive from the Service the seasonLeague's name and the judge's emailAddress, want to create JudgeSeasonLeague.
     * Will continue to Data.
     * @param seasonLeagueName-name of the SeasonLeague.
     * @param judgeEmailAddress-emailAddress of the Judge.
     * @throws Exception-if details are incorrect.
     */
    public void createJudgeSeasonLeague(String seasonLeagueName, String judgeEmailAddress) throws Exception
    {
        if(seasonLeagueName == null || judgeEmailAddress == null)
        {
            throw new NullPointerException("One or more of the JudgeSeasonLeague details incorrect");
        }
        JudgeSeasonLeague judgeSeasonLeague = new JudgeSeasonLeague(seasonLeagueName, judgeEmailAddress);
        seasonLeagueDb.createJudgeSeasonLeague(judgeSeasonLeague);
        judgeDb.createJudgeSeasonLeague(judgeSeasonLeague);
        judgeSeasonLeagueDb.createJudgeSeasonLeague(judgeSeasonLeague);
    }

    /**
     * Will receive from the Service the seasonLeague's name, policy-calculateLeaguePoints,
     * want to set Policy CalculateLeaguePoints of the SeasonLeague.
     * Will continue to Date.
     * @param seasonLeagueName-name of SeasonLeague.
     * @param calculateLeaguePoints-Policy CalculateLeaguePoints.
     * @throws Exception-if details are incorrect.
     */
    public void changeCalculateLeaguePointsPolicy(String seasonLeagueName, CalculateLeaguePoints calculateLeaguePoints) throws Exception
    {
        if(seasonLeagueName == null || calculateLeaguePoints == null)
        {
            throw new NullPointerException("SeasonLeague or CalculateLeaguePointsPolicy details incorrect");
        }
        seasonLeagueDb.changeCalculateLeaguePointsPolicy(seasonLeagueName, calculateLeaguePoints);
    }
}
