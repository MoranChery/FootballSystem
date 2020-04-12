package Controller;

import Data.*;
import Model.Court;
import Model.Enums.*;
import Model.FinancialActivity;
import Model.Team;
import Model.UsersTypes.*;
import org.omg.PortableInterceptor.INACTIVE;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public class TeamController {
    private TeamDb teamDb;
    private PlayerDb playerDb;
    private TeamManagerDb teamManagerDb;
    private CourtDb courtDb;
    private CoachDb coachDb;
    private TeamOwnerDb teamOwnerDb;
    private SubscriberDb subscriberDb;
    private TeamRoleDb teamRoleDb;
    private FinancialActivityDb financialActivityDb;

    public TeamController() {
        teamDb =  TeamDbInMemory.getInstance();
        playerDb = PlayerDbInMemory.getInstance();
        teamManagerDb = TeamManagerDbInMemory.getInstance();
        coachDb =  CoachDbInMemory.getInstance();
        courtDb =  CourtDbInMemory.getInstance();
        teamOwnerDb =  TeamOwnerDbInMemory.getInstance();
        subscriberDb =  SubscriberDbInMemory.getInstance();
        teamRoleDb =  TeamRoleDbInMemory.getInstance();
        financialActivityDb =  FinancialActivityDbInMemory.getInstance();
    }

    public void createTeam(String teamName) throws Exception {
        if(teamName == null) {
            throw new NullPointerException();
        }
        teamDb.createTeam(teamName);
    }

    public Team getTeam(String teamName) throws Exception {
        if(teamName == null) {
            throw new NullPointerException();
        }
       return teamDb.getTeam(teamName);
    }

    /**
     * add player to team
     * @param teamName
     * @param playerId
     * @throws Exception
     */
    public void addPlayer(String teamName, Integer playerId, String firstName, String lastName, Date birthDate, PlayerRole playerRole) throws Exception {
        if(teamName == null || playerId == null || firstName == null || lastName == null || birthDate == null || playerRole == null) {
            throw new NullPointerException("bad input");
        }
        Team team = teamDb.getTeam(teamName);
        checkTeamStatusIsActive(team);
        Player currPlayer = new Player(playerId,firstName,lastName,birthDate,playerRole);
        Player player;
        try{
            /*get the player from DB*/
            player = playerDb.getPlayer(playerId);
            /*get the team of the player if there is a team already, will throw exception*/
            if (player.getTeam() != null) {
                throw new Exception("Player associated with a team");
            }
            /*check if the player's details match with the DB details*/
            if(!player.equals(currPlayer)){
                throw new Exception("One or more of the details incorrect");
            }
        }catch(NotFoundException e){
            /*Player doesnt exist in the db - add to players's db*/
            playerDb.createPlayer(currPlayer);
            player = currPlayer;
        }
        /*add to DB the player to the team*/
        teamDb.addPlayer(teamName, player);
        teamRoleDb.createTeamRole(playerId,teamName,TeamRoleType.PLAYER);
    }

    public void addTeamManager(String teamName, Integer teamManagerId, String firstName ,String lastName,Integer ownedById) throws Exception {
        if(teamName == null || teamManagerId == null || firstName == null || lastName == null || ownedById == null) {
            throw new NullPointerException("bad input");
        }
        Team team = teamDb.getTeam(teamName);
        checkTeamStatusIsActive(team);
        TeamOwner teamOwner = teamOwnerDb.getTeamOwner(ownedById);
        TeamManager currTeamManager = new TeamManager(teamManagerId, firstName, lastName,ownedById);
        /*get the teamManager from DB*/
        TeamManager teamManager;
        try{
            teamManager = teamManagerDb.getTeamManager(teamManagerId);
            /*get the team of the teamManager if there is a team already, will throw exception*/
            if (teamManager.getTeam() != null) {
                throw new Exception("Team Manager associated with a team");
            }
            /*check if the teamManager's details match with the DB details*/
            if(!teamManager.equals(currTeamManager)){
                throw new Exception("One or more of the details incorrect");
            }
            if(teamManager.getOwnedById() != null){
                throw new Exception("Team Manager owned by another teamOwner");
            }
        }catch (NotFoundException e){
            /*teamManager doesnt exist in the db - add to teamManagers's db*/
            teamManagerDb.createTeamManager(currTeamManager);
            teamManager = currTeamManager;
        }
        /*add to DB the teamManager to the team*/
        teamDb.addTeamManager(teamName, teamManager,ownedById);
        teamRoleDb.createTeamRole(teamManagerId,teamName,TeamRoleType.MANAGER);
    }


    public void addCoach(String teamName, Integer coachId, String firstName, String lastName, CoachRole coachRole, Qualification qualification) throws Exception {
        if(teamName == null || coachId == null || firstName == null || lastName == null || coachRole == null|| qualification == null) {
            throw new NullPointerException("bad input");
        }
        Team team = teamDb.getTeam(teamName);
        checkTeamStatusIsActive(team);
        Coach currCoach = new Coach(coachId, firstName, lastName, coachRole, qualification);
        /*get the coach from DB*/
        Coach coach;
        try {
            coach = coachDb.getCoach(coachId);
            /*get the team of the coach if there is a team already, will throw exception*/
            if (coach.getTeam() != null) {
                throw new Exception("Coach associated with a team");
            }
            /*check if the coach's details match with the DB details*/
            if(!coach.equals(currCoach)){
                throw new Exception("One or more of the details incorrect");
            }
        }catch (NotFoundException e){
            /*Coach doesnt exist in the db - add to coachs's db*/
            coachDb.createCoach(currCoach);
            coach = currCoach;
        }
        /* add to DB the player to the team*/
        teamDb.addCoach(teamName, coach);
        teamRoleDb.createTeamRole(coachId,teamName,TeamRoleType.COACH);
    }

    public void addCourt(String teamName, String courtName, String courtCity) throws Exception {
        if (teamName == null || courtName == null || courtCity == null) {
            throw new NullPointerException("bad input");
        }
        /*check if the team exists*/
        Team team = teamDb.getTeam(teamName);
        checkTeamStatusIsActive(team);
        if(team.getCourt() != null){
            throw new Exception("team already associated with court");
        }
        Court court;
        try {
            /*check if the court already in the db*/
            court = courtDb.getCourt(courtName);
            if (!courtCity.equals(court.getCourtCity())) {
                throw new Exception("The court name isn't match to the city");
            }
        } catch (NotFoundException e) { //court in the db
//            /*check if the court associated with team*/
//            Team team = court.getTeam();
//            if (team != null) {
//                throw new Exception("There is a court associated with this team");
            court = new Court(courtName, courtCity);
            courtDb.createCourt(court);
        }
        teamDb.addCourt(teamName, court);
    }

    public void removePlayer(String teamName, Integer playerId) throws Exception {
        /*check if one of the inputs null*/
        if(teamName == null || playerId == null) {
            throw new NullPointerException("bad input");
        }
        Team team = teamDb.getTeam(teamName);
        checkTeamStatusIsActive(team);
        /* get the player from the database*/
        Player player = playerDb.getPlayer(playerId);
        /*check if the team that associated with the player match to the player want to delete*/
        Team teamPlayer = player.getTeam();
        if(teamPlayer == null || !teamName.equals(teamPlayer.getTeamName())) {
            throw new Exception("Player is not part with associated team");
        }
        teamDb.removePlayer(teamName, playerId);
        teamRoleDb.removeTeamRole(playerId,teamName,TeamRoleType.PLAYER);
    }

    public void removeTeamManager(String teamName, Integer teamManagerId) throws Exception {
        /*check if one of the inputs null*/
        if(teamName == null || teamManagerId == null) {
            throw new NullPointerException();
        }
        Team team = teamDb.getTeam(teamName);
        checkTeamStatusIsActive(team);
        /* get the teamManager from the database*/
        TeamManager teamManager = teamManagerDb.getTeamManager(teamManagerId);
        /*check if the team that associated with the teamManager match to the teamManager want to delete*/
        Team teamManagerTeam = teamManager.getTeam();
        if(teamManagerTeam == null || !teamName.equals(teamManagerTeam.getTeamName())) {
            throw new Exception("TeamManager is not part of the team");
        }
        teamDb.removeTeamManager(teamName, teamManagerId);
        teamRoleDb.removeTeamRole(teamManagerId,teamName,TeamRoleType.MANAGER);

    }
    public void removeCoach(String teamName, Integer coachId) throws Exception {
        /*check if one of the inputs null*/
        if(teamName == null || coachId == null) {
            throw new NullPointerException("bad input");
        }
        Team team = teamDb.getTeam(teamName);
        checkTeamStatusIsActive(team);
        /* get the coach from the database*/
        Coach coach = coachDb.getCoach(coachId);
        /*check if the team that associated with the coach match to the coach want to delete*/
        Team coachTeam = coach.getTeam();
        if(coachTeam == null || !teamName.equals(coachTeam.getTeamName())) {
            throw new Exception("Coach is not part with associated team");
        }
        teamDb.removeCoach(teamName, coachId);
        teamRoleDb.removeTeamRole(coachId,teamName,TeamRoleType.COACH);
    }

    public void removeCourt(String teamName, String courtName) throws Exception {
        /*check if one of the inputs null*/
        if(teamName == null || courtName == null) {
            throw new NullPointerException("bad input");
        }
        Team team = teamDb.getTeam(teamName);
        checkTeamStatusIsActive(team);
        Court court = courtDb.getCourt(courtName);
        /*check if one of the teams that associated with the court match to the court want to delete*/
        Team courtTeam = court.getTeam(teamName);
        if(courtTeam == null || !teamName.equals(courtTeam.getTeamName())) {
            throw new Exception("Court is not part of the with associated team");
        }
        teamDb.removeCourt(teamName, courtName);
    }

    public void subscriptionTeamOwner(String teamName, Integer teamOwnerId, Integer ownerToAdd) throws Exception {
        if(teamName == null || teamOwnerId == null || ownerToAdd == null) {
            throw new NullPointerException();
        }
        Team team = teamDb.getTeam(teamName);
        checkTeamStatusIsActive(team);
        /*check if the major team owner in db*/
        TeamOwner teamOwner = teamOwnerDb.getTeamOwner(teamOwnerId);
        if(!team.equals(teamOwner.getTeam())){
            throw new Exception("Team owner and team don't match");
        }
        /*check if the subscriber exists*/
        Subscriber subscriber = subscriberDb.getSubscriber(ownerToAdd);
        List<TeamRole> teamRolesOfOwnerToAdd = teamRoleDb.getTeamRoles(ownerToAdd);
        for (TeamRole tr: teamRolesOfOwnerToAdd) {
            if(!teamName.equals(tr.getTeamName())){
                throw new Exception("Owner to Add already associated with other team");
            }
            if(TeamRoleType.OWNER.equals(tr.getTeamRoleType())){
                throw new Exception("This subscriber already teamOwner");
            }
        }
        teamOwnerDb.subscriptionTeamOwner(team,teamOwnerId,subscriber);
        teamRoleDb.createTeamRole(ownerToAdd,teamName,TeamRoleType.OWNER);
    }


    public void subscriptionTeamManager(String teamName, Integer teamOwnerId, Integer managerToAdd) throws Exception {
        if(teamName == null || teamOwnerId == null || managerToAdd == null) {
            throw new NullPointerException();
        }
        Team team = teamDb.getTeam(teamName);
        checkTeamStatusIsActive(team);
        /*check if the major team owner in db*/
        TeamOwner teamOwner = teamOwnerDb.getTeamOwner(teamOwnerId);
        if(!team.equals(teamOwner.getTeam())){
            throw new Exception("Team owner and team don't match");
        }
        Subscriber subscriber = subscriberDb.getSubscriber(managerToAdd);
        List<TeamRole> teamRolesOfTeamManagerToAdd = teamRoleDb.getTeamRoles(managerToAdd);
        for (TeamRole tr: teamRolesOfTeamManagerToAdd) {
            if(!teamName.equals(tr.getTeamName())){
                throw new Exception("Manager to Add already associated with other team");
            }
            TeamRoleType teamRoleType = tr.getTeamRoleType();
            if(TeamRoleType.OWNER.equals(teamRoleType)){
                throw new Exception("This subscriber already teamOwner");
            }
            if(TeamRoleType.MANAGER.equals(teamRoleType)){
                throw new Exception("This subscriber already teamManager");
            }
        }
        teamManagerDb.subscriptionTeamManager(team,teamOwnerId,subscriber);
        teamRoleDb.createTeamRole(managerToAdd,teamName,TeamRoleType.MANAGER);
    }

    public void removeSubscriptionTeamOwner(String teamName, Integer teamOwnerId, Integer ownerToRemove) throws Exception {
        if(teamName == null || teamOwnerId == null || ownerToRemove == null) {
            throw new NullPointerException();
        }
        Team team = teamDb.getTeam(teamName);
        checkTeamStatusIsActive(team);
        /*check if the major team owner in db*/
        TeamOwner teamOwner = teamOwnerDb.getTeamOwner(teamOwnerId);
        if(!team.equals(teamOwner.getTeam())){
            throw new Exception("Team owner and team don't match");
        }
        TeamOwner teamOwnerToRemove = teamOwnerDb.getTeamOwner(ownerToRemove);
        if(!team.equals(teamOwnerToRemove.getTeam())){
            throw new Exception("TeamOwnerToRemove associated with other team");
        }
        if(!teamOwnerId.equals(teamOwnerToRemove.getOwnedById())){
            throw new Exception("TeamOwnerToRemove owned by another teamOwner");
        }
        // todo - move foreach to db
        List<Integer> allTeamOwnersOwnedBy = teamOwnerDb.getAllTeamOwnersOwnedBy(ownerToRemove);
        for (Integer idToRemove: allTeamOwnersOwnedBy) {
            teamOwnerDb.removeSubscriptionTeamOwner(idToRemove);
            teamRoleDb.removeTeamRole(idToRemove,teamName,TeamRoleType.OWNER);
        }
        teamOwnerDb.removeSubscriptionTeamOwner(ownerToRemove);
        teamRoleDb.removeTeamRole(ownerToRemove,teamName,TeamRoleType.OWNER);
    }

    public void removeSubscriptionTeamManager(String teamName, Integer teamOwnerId, Integer managerToRemove) throws Exception {
        if(teamName == null || teamOwnerId == null || managerToRemove == null) {
            throw new NullPointerException();
        }
        Team team = teamDb.getTeam(teamName);
        checkTeamStatusIsActive(team);
        /*check if the major team owner in db*/
        TeamOwner teamOwner = teamOwnerDb.getTeamOwner(teamOwnerId);
        if(!team.equals(teamOwner.getTeam())){
            throw new Exception("Team owner and team don't match");
        }
        TeamManager teamManagerToRemove = teamManagerDb.getTeamManager(managerToRemove);
        if(!team.equals(teamManagerToRemove.getTeam())){
            throw new Exception("TeamManagerToRemove associated with other team");
        }
        if(!teamOwnerId.equals(teamManagerToRemove.getOwnedById())){
            throw new Exception("TeamManagerToRemove owned by another teamOwner");
        }

        teamManagerDb.removeSubscriptionTeamManager(managerToRemove);
        teamRoleDb.removeTeamRole(managerToRemove,teamName,TeamRoleType.MANAGER);
    }


    public void addFinancialActivity(String teamName, Double financialActivityAmount, String description, FinancialActivityType financialActivityType) throws Exception {
        if(teamName == null || financialActivityAmount == null || description == null || financialActivityType == null) {
            throw new NullPointerException("bad input");
        }
        Team team = teamDb.getTeam(teamName);
        checkTeamStatusIsActive(team);
        if(financialActivityType.equals(FinancialActivityType.OUTCOME) ){
            if(team.getBudget() - financialActivityAmount < 0){
                throw new Exception("The financial outcome exceeds from the budget");
            }
        }
        /*for security and unique id*/
        String financialActivityId = UUID.randomUUID().toString();
        FinancialActivity financialActivity = new FinancialActivity(financialActivityId,financialActivityAmount,description,financialActivityType,team);
        financialActivityDb.createFinancialActivity(financialActivity);
        teamDb.addFinancialActivity(teamName,financialActivity);
    }

    public void changeStatus(String teamName,TeamStatus teamStatus) throws Exception {
        if(teamName == null) {
            throw new NullPointerException("bad input");
        }
        Team team = teamDb.getTeam(teamName);
        teamDb.changeStatus(teamName,teamStatus);
    }

    private void checkTeamStatusIsActive(Team team) throws Exception {
        if(TeamStatus.INACTIVE.equals(team.getTeamStatus())){
            throw new Exception("This Team's status - Inactive");
        }
    }
}
