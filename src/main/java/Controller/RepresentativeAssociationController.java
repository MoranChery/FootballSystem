package Controller;

import Data.*;
import Model.Enums.CalculateLeaguePoints;
import Model.Enums.InlayGames;
import Model.Enums.QualificationJudge;
import Model.Enums.RoleType;
import Model.*;
import Model.UsersTypes.Judge;
import Model.UsersTypes.RepresentativeAssociation;

import java.util.*;

public class RepresentativeAssociationController extends Observable implements Observer
{
    private RepresentativeAssociationDb representativeAssociationDb;
    private SubscriberDb subscriberDb;
    private RoleDb roleDb;
    private LeagueDb leagueDb;
    private SeasonDb seasonDb;
    private SeasonLeagueDb seasonLeagueDb;
    private JudgeDb judgeDb;
    private JudgeSeasonLeagueDb judgeSeasonLeagueDb;
    private GameDb gameDb;

    public RepresentativeAssociationController()
    {
        this.representativeAssociationDb = RepresentativeAssociationDbInMemory.getInstance();
        this.subscriberDb = SubscriberDbInMemory.getInstance();
        this.roleDb = RoleDbInMemory.getInstance();
        this.leagueDb = LeagueDbInMemory.getInstance();
//        this.leagueDb = LeagueDbInServer.getInstance();
        this.seasonDb = SeasonDbInMemory.getInstance();
//        this.seasonDb = SeasonDbInServer.getInstance();
        this.seasonLeagueDb = SeasonLeagueDbInMemory.getInstance();
//        this.seasonLeagueDb = SeasonLeagueDbInServer.getInstance();
        this.judgeDb = JudgeDbInMemory.getInstance();
//        this.judgeDb = JudgeDbInServer.getInstance();
        this.judgeSeasonLeagueDb = JudgeSeasonLeagueDbInMemory.getInstance();
//        this.judgeSeasonLeagueDb = JudgeSeasonLeagueDbInServer.getInstance();
        this.gameDb = GameDbInMemory.getInstance();
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
        representativeAssociationDb.insertRepresentativeAssociation(representativeAssociation);
        roleDb.createRoleInSystem(representativeAssociation.getEmailAddress(), RoleType.REPRESENTATIVE_ASSOCIATION);
    }

    /**
     * Will receive from the Service the league's name, create the League.
     * Will continue to Data.
     * @param representativeAssociationEmailAddress-username/emailAddress of the online RepresentativeAssociation.
     * @param leagueName-name of the new League.
     * @throws Exception-if details are incorrect.
     */
    public void createLeague(String representativeAssociationEmailAddress,String leagueName) throws Exception
    {
        if(representativeAssociationEmailAddress == null || !checkPermissionOfRepresentativeAssociation(representativeAssociationEmailAddress))
        {
            throw new Exception("Only RepresentativeAssociation has permissions to this action!");
        }
        if(leagueName == null)
        {
            throw new NullPointerException("One or more of the League details incorrect");
        }
        League league = new League(leagueName);
        leagueDb.insertLeague(league);
    }

    /**
     * Will receive from the Service the season's name, create the Season.
     * Will continue to Data.
     * @param representativeAssociationEmailAddress-username/emailAddress of the online RepresentativeAssociation.
     * @param seasonName-name of the new Season.
     * @throws Exception-if details are incorrect.
     */
    public void createSeason(String representativeAssociationEmailAddress, String seasonName) throws Exception
    {
        if(representativeAssociationEmailAddress == null || !checkPermissionOfRepresentativeAssociation(representativeAssociationEmailAddress))
        {
            throw new Exception("Only RepresentativeAssociation has permissions to this action!");
        }
        if(seasonName == null)
        {
            throw new NullPointerException("One or more of the Season details incorrect");
        }
        Season season = new Season(seasonName);
        seasonDb.insertSeason(season);
    }

    /**
     * Will receive from the Service the league's name the season's name the policy-calculateLeaguePoints and the policy-inlayGames, create SeasonLeague.
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
        if(representativeAssociationEmailAddress == null || !checkPermissionOfRepresentativeAssociation(representativeAssociationEmailAddress))
        {
            throw new Exception("Only RepresentativeAssociation has permissions to this action!");
        }
        if(leagueName == null || seasonName == null || calculateLeaguePoints == null || inlayGames == null)
        {
            throw new NullPointerException("One or more of the SeasonLeague details incorrect");
        }
        SeasonLeague seasonLeague = new SeasonLeague(seasonName, leagueName, calculateLeaguePoints, inlayGames);
        seasonDb.addSeasonLeague(seasonLeague);
        leagueDb.addSeasonLeague(seasonLeague);
        seasonLeagueDb.insertSeasonLeague(seasonLeague);
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
     * @throws Exception-if details are incorrect.
     */
    public void createJudge(String representativeAssociationEmailAddress, String username, String password, Integer id, String firstName, String lastName, QualificationJudge qualificationJudge) throws Exception
    {
        if(representativeAssociationEmailAddress == null || !checkPermissionOfRepresentativeAssociation(representativeAssociationEmailAddress))
        {
            throw new Exception("Only RepresentativeAssociation has permissions to this action!");
        }
        if(username == null || password == null || id == null || firstName == null || lastName == null || qualificationJudge == null)
        {
            throw new NullPointerException("One or more of the Judge details incorrect");
        }
        Judge judge = new Judge(username, password, id, firstName, lastName, qualificationJudge);
        try
        {
            subscriberDb.insertSubscriber(judge);
            try
            {
                judgeDb.insertJudge(judge);
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
     * @param representativeAssociationEmailAddress-username/emailAddress of the online RepresentativeAssociation.
     * @param judgeEmailAddress-emailAddress of the Judge.
     * @throws Exception-if details are incorrect.
     */
    public void removeJudge(String representativeAssociationEmailAddress, String judgeEmailAddress) throws Exception
    {
        if(representativeAssociationEmailAddress == null || !checkPermissionOfRepresentativeAssociation(representativeAssociationEmailAddress))
        {
            throw new Exception("Only RepresentativeAssociation has permissions to this action!");
        }
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
     * @param representativeAssociationEmailAddress-username/emailAddress of the online RepresentativeAssociation.
     * @param seasonLeagueName-name of the SeasonLeague.
     * @param judgeEmailAddress-emailAddress of the Judge.
     * @throws Exception-if details are incorrect.
     */
    public void createJudgeSeasonLeague(String representativeAssociationEmailAddress, String seasonLeagueName, String judgeEmailAddress) throws Exception
    {
        if(representativeAssociationEmailAddress == null || !checkPermissionOfRepresentativeAssociation(representativeAssociationEmailAddress))
        {
            throw new Exception("Only RepresentativeAssociation has permissions to this action!");
        }
        if(seasonLeagueName == null || judgeEmailAddress == null)
        {
            throw new NullPointerException("One or more of the JudgeSeasonLeague details incorrect");
        }
        JudgeSeasonLeague judgeSeasonLeague = new JudgeSeasonLeague(seasonLeagueName, judgeEmailAddress);
        seasonLeagueDb.createJudgeSeasonLeague(judgeSeasonLeague);
        judgeDb.createJudgeSeasonLeague(judgeSeasonLeague);
        judgeSeasonLeagueDb.insertJudgeSeasonLeague(judgeSeasonLeague);
    }

    /**
     * Will receive from the Service the seasonLeague's name, policy-calculateLeaguePoints,
     * want to set Policy CalculateLeaguePoints of the SeasonLeague.
     * Will continue to Date.
     * @param representativeAssociationEmailAddress-username/emailAddress of the online RepresentativeAssociation.
     * @param seasonLeagueName-name of SeasonLeague.
     * @param calculateLeaguePoints-Policy CalculateLeaguePoints.
     * @throws Exception-if details are incorrect.
     */
    public void changeCalculateLeaguePointsPolicy(String representativeAssociationEmailAddress, String seasonLeagueName, CalculateLeaguePoints calculateLeaguePoints) throws Exception
    {
        if(representativeAssociationEmailAddress == null || !checkPermissionOfRepresentativeAssociation(representativeAssociationEmailAddress))
        {
            throw new Exception("Only RepresentativeAssociation has permissions to this action!");
        }
        if(seasonLeagueName == null || calculateLeaguePoints == null)
        {
            throw new NullPointerException("SeasonLeague or CalculateLeaguePointsPolicy details incorrect");
        }
        seasonLeagueDb.updateCalculateLeaguePointsPolicy(seasonLeagueName, calculateLeaguePoints);
    }

    /**
     * Check if the online Subscriber has RoleType of Representative_Association
     * If yes, he allowed to use RepresentativeAssociationController functions-return TRUE.
     * Else (no), he doesn't allow to use RepresentativeAssociationController functions-return FALSE.
     * @param representativeAssociationEmailAddress-username/emailAddress of the online RepresentativeAssociation.
     * @return if the online Subscriber has RoleType of Representative_Association.
     * @throws Exception
     */
    protected boolean checkPermissionOfRepresentativeAssociation(String representativeAssociationEmailAddress) throws Exception
    {
        if(representativeAssociationEmailAddress != null)
        {
            List<Role> subscriberRoleList = roleDb.getRoles(representativeAssociationEmailAddress);
            if(subscriberRoleList.size() > 0)
            {
                for (Role role : subscriberRoleList)
                {
                    if (role.getRoleType().equals(RoleType.REPRESENTATIVE_ASSOCIATION))
                    {
                        return true;
                    }
                }
            }
        }
        return false;
    }


    public void changeGameLocation(String repMail, String newLocation, String gameID) throws Exception {

        if(repMail.isEmpty() || newLocation.isEmpty() || gameID.isEmpty()){
            throw new Exception("The value is empty");
        }
        if(repMail == null || newLocation == null || gameID == null){
            throw new NullPointerException("bad input");
        }
        Game game = gameDb.getGame(gameID);
        if(game == null){
            throw new NotFoundException("game not in DB");
        }
        Court theGameCourt = game.getCourt();
        if(theGameCourt.getCourtCity().equals(newLocation)){
            throw new Exception("same location");
        }
        gameDb.changeGameLocation(newLocation,gameID);

        Set<String> judgesOfThisGame = game.getJudgesOfTheGameList();
        Object[] data = new Object[3];
        data[0] = "location";
        data[1] = game;
        data[2] = judgesOfThisGame;
        setChanged();
        notifyObservers(data);
    }
    public void changeGameDate(String repMail, Date newDate, String gameID) throws Exception {
        if(repMail.isEmpty() || gameID.isEmpty()){
            throw new Exception("The value is empty");
        }
        if(newDate == null){
            throw new NullPointerException("bad input");
        }
        Game game = gameDb.getGame(gameID);
        if(game == null){
            throw new NotFoundException("game not in DB");
        }
        Date theOriginalDate = game.getGameDate();
        if(theOriginalDate.equals(newDate)){
            throw new Exception("same date");
        }
        gameDb.changeGameDate(repMail, newDate, gameID);
        Set<String> judgesOfThisGame = game.getJudgesOfTheGameList();
        Object[] data = new Object[4];
        data[0] = "date";
        data[1] = game;
        data[2] = judgesOfThisGame;
        data[3] = newDate;
        setChanged();
        notifyObservers(data);
    }

    @Override
    public void update(Observable o, Object arg) {

    }
}
