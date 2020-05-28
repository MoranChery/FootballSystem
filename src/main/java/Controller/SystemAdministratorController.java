package Controller;

import Data.*;
import Model.Enums.RoleType;
import Model.Enums.TeamStatus;
import Model.Game;
import Model.Page;
import Model.Role;
import Model.Team;
import Model.UsersTypes.*;

import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Set;

public class SystemAdministratorController extends Observable {
    private TeamDb teamDb;
    private SubscriberDb subscriberDb;
    private CoachDb coachDb;
    private CourtDb courtDb;
    private JudgeDb judgeDb;
    private PlayerDb playerDb;
    private TeamManagerDb teamManagerDb;
    private TeamOwnerDb teamOwnerDb;
    private PageDb pageDb;
    private FanDb fanDb;
    private SeasonLeagueDb seasonalLeagueDB;
    private JudgeSeasonLeagueDb judgeSeasonLeagueDb;
    private RoleDb roleDb;
    private SystemAdministratorDb systemAdministratorDb;
    private RepresentativeAssociationDb representativeAssociationDb;
    private PermissionDb permissionDb;
    private GameDb gameDb;

    public SystemAdministratorController() {
        teamDb = TeamDbInServer.getInstance();
        subscriberDb = SubscriberDbInServer.getInstance();
        coachDb = CoachDbInServer.getInstance();
        courtDb = CourtDbInServer.getInstance();
        judgeDb = JudgeDbInServer.getInstance();
        playerDb = PlayerDbInServer.getInstance();
        teamManagerDb = TeamManagerDbInServer.getInstance();
        teamOwnerDb = TeamOwnerDbInServer.getInstance();
        pageDb = PageDbInServer.getInstance();
        fanDb = FanDbInMemory.getInstance();
        seasonalLeagueDB = SeasonLeagueDbInServer.getInstance();
        judgeSeasonLeagueDb = JudgeSeasonLeagueDbInServer.getInstance();
        roleDb = RoleDbInServer.getInstance();
        systemAdministratorDb = SystemAdministratorDbInServer.getInstance();
        representativeAssociationDb = RepresentativeAssociationDbInServer.getInstance();
        permissionDb = PermissionDbInServer.getInstance();
    }

    //use case 8.1

    /**
     * @param teamName that the system administrator want to close for ever
     */
    public void closeTeamForEver(String teamName) throws Exception {
        if(teamName.isEmpty()){
            throw new Exception("Not valid name");
        }
        Team teamToDelete = teamDb.getTeam(teamName);
        Map <String, TeamOwner> teamOwnerList = teamToDelete.getTeamOwners();
        Map<String, TeamManager> teamManagerList = teamToDelete.getTeamManagers();
        if(teamManagerList == null || teamOwnerList == null){
            throw new NullPointerException("one or more of the lists is null");
        }
        teamDb.closeTeamForever(teamName);
        Object[] data = new Object[4];
        data[0] = "close";
        data[1] = teamName;
        data[2] = teamOwnerList;
        data[3] = teamManagerList;
        setChanged();
        notifyObservers(data);

    }

    //use case 8.2

    /**
     *
     * @param emailToRemove of the subscriber that the system administrator want to remove
     * @param emailOfSA email of the system administrator
     * @throws Exception
     */
    public void removeSubscriber(String emailToRemove,String emailOfSA) throws Exception {
        if(emailToRemove==null || emailOfSA==null || !checkPermissionOfSystemAdministrator( emailOfSA)){
            throw new Exception("null email");
        }
            Subscriber subscriberToRemove = subscriberDb.getSubscriber(emailToRemove);
            //remove the subscriber from subscriberDB
            subscriberDb.removeSubscriberFromDB(subscriberToRemove);

            if (subscriberToRemove instanceof Coach) {
                removeCoach(subscriberToRemove);
            }
            if (subscriberToRemove instanceof Judge) {
                removeJudge(subscriberToRemove);
            }
            if (subscriberToRemove instanceof Player) {
                removePlayer(subscriberToRemove);
            }
            if (subscriberToRemove instanceof TeamManager) {
                removeTeamManager(subscriberToRemove);
            }
            if (subscriberToRemove instanceof TeamOwner) {
                removeTeamOwner(subscriberToRemove);
            }
            if (subscriberToRemove instanceof Fan) {
                removeFan(subscriberToRemove);
            }
            if (subscriberToRemove instanceof RepresentativeAssociation) {
                removeRepresentativeAssociation(subscriberToRemove);
            }
            if(subscriberToRemove instanceof SystemAdministrator){
                removeSystemAdministrator(subscriberToRemove);
            }
            System.out.println("the chosen subscriber with the Email " + emailToRemove + " deleted successfully :)");
    }

    /**
     *
     *
     * @param subscriberToRemove subscriberToRemove that is also RepresentativeAssociation
     * @throws Exception if the RepresentativeAssociation is already removed from fanDB
     */
    private void removeRepresentativeAssociation(Subscriber subscriberToRemove) throws Exception {
        RepresentativeAssociation representativeAssociation= (RepresentativeAssociation) subscriberToRemove;
        //remove the representativeAssociation from representativeAssociationDB
        representativeAssociationDb.removeRepresentativeAssociation(representativeAssociation.getEmailAddress());
        //remove systemAdministrator from roleDB
        roleDb.removeRole(representativeAssociation.getEmailAddress(), RoleType.REPRESENTATIVE_ASSOCIATION);
    }

    /**
     *
     * @param subscriberToRemove that is also system manager
     * @throws Exception if the system administrator is not in the SystemAdministratorDB
     *                    or if there is only one SystemAdministrator
     */
    private void removeSystemAdministrator(Subscriber subscriberToRemove) throws Exception{
        //casting
        SystemAdministrator systemAdministrator= (SystemAdministrator) subscriberToRemove;
        //check the constraint if there is more than one systemAdministrator
        if(systemAdministratorDb.getAllSystemAdministrators().size()==1){
            System.out.println("one systemAdministrator EXCEPTION");
            throw new Exception();
        }
        //remove the system administrator from systemAdministratorDb
        systemAdministratorDb.removeSystemAdministratorFromDB(systemAdministrator);
        //remove systemAdministrator from roleDB
        roleDb.removeRole(systemAdministrator.getEmailAddress(), RoleType.SYSTEM_ADMINISTRATOR);
    }


    /**
     * @param subscriberToRemove that is also fan
     * @throws Exception if the fan is already removed from fanDB
     */
    private void removeFan(Subscriber subscriberToRemove) throws Exception {
        //casting
        Fan fan = (Fan) subscriberToRemove;
        //remove the fan from personalPages that he followed after
        Set set= fan.getMyPages();
        for (Object pageID : set) {
            Page pageString=(Page)pageID;
//            Page page = pageDb.getPage(pageString);
            pageString.getFansFollowingThisPage().remove(fan);
        }
        //remove fan from fanDB
        fanDb.removeFan(fan);
        //remove fan from roleDB
        roleDb.removeRole(fan.getEmailAddress(), RoleType.FAN);
    }

    /**
     * recursive way to remove all the owners of the owner that we remove
     *
     * @param subscriberToRemove the root of the tree of teamOwners
     * @throws Exception is someone from the "subscriberToRemove" owners has been deleted
     */
    private void removeTeamOwner(Subscriber subscriberToRemove) throws Exception {
        if (subscriberToRemove == null)
            return;
        //check constraints if there is more than one team owner in the system
        if(teamOwnerDb.getAllTeamOwnersInDB().size()==1){
            throw new Exception();
        }
        //casting
        TeamOwner teamOwnerToRemove = (TeamOwner) subscriberToRemove;
        String team = teamOwnerToRemove.getTeam();
        checkTeamStatusIsActive(team);
        //remove all the teamOwner's subscribers
        for (String owner : teamOwnerToRemove.getTeamOwnersByThis()) {
            TeamOwner teamOwner = teamOwnerDb.getTeamOwner(owner);
            removeTeamOwner(teamOwner);
        }
        //remove all the teamManager's subscribers
        List<String> allTeamManagersOwnedBy = teamManagerDb.getAllTeamManagersOwnedBy(teamOwnerToRemove.getEmailAddress());
        for (String emailToRemove: allTeamManagersOwnedBy) {
            teamManagerDb.removeSubscriptionTeamManager(emailToRemove);
            roleDb.removeRole(emailToRemove,RoleType.TEAM_MANAGER);
        }
        //remove teamOwner from teamOwnerDb
        teamOwnerDb.removeSubscriptionTeamOwner(teamOwnerToRemove.getEmailAddress());
        //remove teamOwner from roleDB
        roleDb.removeRole(teamOwnerToRemove.getEmailAddress(), RoleType.TEAM_OWNER);
        try {
            subscriberDb.removeSubscriberFromDB(teamOwnerToRemove);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param subscriberToRemove that is also coach
     * @throws Exception if the coach is already removed from coachDB
     */
    private void removeCoach(Subscriber subscriberToRemove) throws Exception {
        //casting
        Coach coach = (Coach) subscriberToRemove;
        checkTeamStatusIsActive(coach.getTeam());
        //remove the personalPage from its followers
        Page coachPage = coach.getCoachPage();
        for (String fanEmail : coachPage.getFansFollowingThisPage().keySet()) {
            fanDb.getFan(fanEmail).getMyPages().remove(coachPage);
        }
        if(coach.getTeam()!=null){
            teamDb.removeCoach(coach.getTeam(), coach.getEmailAddress());
        }
        //remove the personal page of the coach from PersonalPageDb
        pageDb.removePersonalPageFromDb(coachPage.getPageID());
        //remove the coach from the coachDB
        coachDb.removeCoach(coach);
        //remove coach from roleDB
        roleDb.removeRole(coach.getEmailAddress(), RoleType.COACH);
    }

    /**
     * @param subscriberToRemove that is als judge
     * @throws Exception if the judge is already removed from judgeDB
     */
    private void removeJudge(Subscriber subscriberToRemove) throws Exception {
        //casting
        Judge judge = (Judge) subscriberToRemove;
        //remove the judge from his connected games
        for (String game : judge.getTheJudgeGameList()) {
            Game gameToRemove = gameDb.getGame(game);
            gameToRemove.getJudgesOfTheGameList().remove(judge.getId());
        }
        // remove judge from association class of league and Season
        Map<String, String> seasonLeagueName_JudgeSeasonLeagueName = judge.getSeasonLeagueName_JudgeSeasonLeagueName();
        //remove the judge from his connected seasonLeague
        for (String seasonLeagueName : seasonLeagueName_JudgeSeasonLeagueName.keySet()) {
            seasonalLeagueDB.getSeasonLeague(seasonLeagueName).getJudgeEmailAddress_JudgeSeasonLeagueName().remove(judge.getEmailAddress());
        }
        //remove judge and his JudgeSeasonLeagueId from judgeSeasonLeagueDB
        for (String judgeSeasonLeagueName : seasonLeagueName_JudgeSeasonLeagueName.values()) {
            judgeSeasonLeagueDb.removeJudgeSeasonLeague(judgeSeasonLeagueName);
        }
        //remove the judge from judgeDb
        judgeDb.removeJudge(judge.getEmailAddress());
    }

    /**
     * @param subscriberToRemove that is also player
     * @throws Exception if the player is already removed from playerDB
     */
    private void removePlayer(Subscriber subscriberToRemove) throws Exception {
        //casting
        Player player = (Player) subscriberToRemove;
        checkTeamStatusIsActive(player.getTeam());
        //remove the personalPage from its followers
        Page playerPage = player.getPlayerPage();
        for (String fanEmail : playerPage.getFansFollowingThisPage().keySet()) {
            fanDb.getFan(fanEmail).getMyPages().remove(playerPage);
        }
        //remove the personal page of the player from PersonalPageDb
        pageDb.removePersonalPageFromDb(player.getPlayerPage().getPageID());
        //remove the player from his team
        teamDb.removePlayer(player.getTeam(), player.getEmailAddress());
        //remove the player from the playerDB
        playerDb.removePlayerFromDb(player);
        //remove player from roleDB
        roleDb.removeRole(player.getEmailAddress(), RoleType.PLAYER);
    }

    /**
     *
     * @param subscriber (team manager) to remove
     * @throws Exception
     */
    private void removeTeamManager(Subscriber subscriber) throws Exception {
        TeamManager teamManagerToRemove = (TeamManager)subscriber;
        String team = teamManagerToRemove.getTeam();
        checkTeamStatusIsActive(team);
        teamDb.removeTeamManager(team,teamManagerToRemove.getEmailAddress());
        roleDb.removeRoleFromTeam(teamManagerToRemove.getEmailAddress(),team, RoleType.TEAM_MANAGER);
    }

    /**
     * in case that the status is INACTIVE, will not can do any functions
     * @param team
     * @throws Exception
     */
    private void checkTeamStatusIsActive(String team) throws Exception {
        if(TeamStatus.INACTIVE.equals(teamDb.getTeam(team).getTeamStatus())){
            throw new Exception("This Team's status - Inactive");
        }
    }

    /**
     * Check if the online Subscriber has RoleType of SystemAdministrator
     * If yes, he allowed to use SystemAdministrator functions-return TRUE.
     * Else (no), he doesn't allow to use SystemAdministrator functions-return FALSE.
     * @param systemAdministratorEmailAddress-username/emailAddress of the online SystemAdministrator.
     * @return if the online Subscriber has RoleType of SystemAdministrator.
     * @throws Exception
     */
    private boolean checkPermissionOfSystemAdministrator(String systemAdministratorEmailAddress) throws Exception
    {
        if(systemAdministratorEmailAddress != null)
        {
            List<Role> subscriberRoleList = roleDb.getRoles(systemAdministratorEmailAddress);
            if(subscriberRoleList.size() > 0)
            {
                for (Role role : subscriberRoleList)
                {
                    if (role.getRoleType().equals(RoleType.SYSTEM_ADMINISTRATOR))
                    {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
