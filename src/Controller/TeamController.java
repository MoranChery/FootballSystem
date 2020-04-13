package Controller;

import Data.*;
import Model.Court;
import Model.Enums.*;
import Model.FinancialActivity;
import Model.Team;
import Model.UsersTypes.*;

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
    public void addPlayer(String teamName, String emailAddress, Integer playerId, String firstName, String lastName, Date birthDate, PlayerRole playerRole) throws Exception {
        if(teamName == null || emailAddress == null || playerId == null || firstName == null || lastName == null || birthDate == null || playerRole == null) {
            throw new NullPointerException("bad input");
        }
        Team team = teamDb.getTeam(teamName);
        checkTeamStatusIsActive(team);
        Player currPlayer = new Player(emailAddress,playerId,firstName,lastName,birthDate,playerRole);
        Player player;
        try{
            /*get the player from DB*/
            player = playerDb.getPlayer(emailAddress);
            /*get the team of the player if there is a team already, will throw exception*/
            if (player.getTeam() != null) {
                throw new Exception("Player associated with a team");
            }
            /*check if the player's details match with the DB details*/
            if(!equalsDetailsPlayer(player,currPlayer)){
                throw new Exception("One or more of the details incorrect");
            }
        }catch(NotFoundException e){
            /*Player doesnt exist in the db - add to players's db*/
            playerDb.createPlayer(currPlayer);
            player = currPlayer;
        }
        /*add to DB the player to the team*/
        teamDb.addPlayer(teamName, player);
        teamRoleDb.createTeamRole(emailAddress,teamName,TeamRoleType.PLAYER);
    }

    /**
     * check if all the information about the play want to add match with the db details
     * @param playerInDb
     * @param playerToAdd
     * @return
     */
    private boolean equalsDetailsPlayer(Player playerInDb, Player playerToAdd){
        return (!(playerInDb.getEmailAddress()).equals(playerToAdd.getEmailAddress()) ||
                !playerInDb.getId().equals(playerToAdd.getId()) ||
                !playerInDb.getFirstName().equals(playerToAdd.getFirstName()) ||
                !playerInDb.getLastName().equals(playerToAdd.getLastName()) ||
                !playerInDb.getBirthDate().equals(playerToAdd.getBirthDate()) ||
                !playerInDb.getPlayerRole().equals(playerToAdd.getPlayerRole()));
    }

    public void addTeamManager(String teamName, String emailAddress, Integer teamManagerId, String firstName ,String lastName,String ownedByEmail) throws Exception {
        if(teamName == null || emailAddress == null ||teamManagerId == null || firstName == null || lastName == null || ownedByEmail == null) {
            throw new NullPointerException("bad input");
        }
        Team team = teamDb.getTeam(teamName);
        checkTeamStatusIsActive(team);
        TeamOwner teamOwner = teamOwnerDb.getTeamOwner(ownedByEmail);
        TeamManager currTeamManager = new TeamManager(emailAddress,teamManagerId, firstName, lastName,ownedByEmail);
        /*get the teamManager from DB*/
        TeamManager teamManager;
        try{
            teamManager = teamManagerDb.getTeamManager(emailAddress);
            /*get the team of the teamManager if there is a team already, will throw exception*/
            if (teamManager.getTeam() != null) {
                throw new Exception("Team Manager associated with a team");
            }
            /*check if the teamManager's details match with the DB details*/
            if(!equalsDetailsTeamManager(teamManager,currTeamManager)){
                throw new Exception("One or more of the details incorrect");
            }
            if(teamManager.getOwnedByIdEmail() != null){
                throw new Exception("Team Manager owned by another teamOwner");
            }
        }catch (NotFoundException e){
            /*teamManager doesnt exist in the db - add to teamManagers's db*/
            teamManagerDb.createTeamManager(currTeamManager);
            teamManager = currTeamManager;
        }
        /*add to DB the teamManager to the team*/
        teamDb.addTeamManager(teamName, teamManager,ownedByEmail);
        teamRoleDb.createTeamRole(emailAddress,teamName,TeamRoleType.MANAGER);
    }

    private boolean equalsDetailsTeamManager(TeamManager teamManagerInDb, TeamManager teamManagerToAdd){
        return (!teamManagerInDb.getEmailAddress().equals(teamManagerToAdd.getEmailAddress()) ||
                !teamManagerInDb.getId().equals(teamManagerToAdd.getId()) ||
                !teamManagerInDb.getFirstName().equals(teamManagerToAdd.getFirstName()) ||
                !teamManagerInDb.getLastName().equals(teamManagerToAdd.getLastName()));
    }

    public void addCoach(String teamName, String emailAddress, Integer coachId, String firstName, String lastName, CoachRole coachRole, QualificationCoach qualificationCoach) throws Exception {
        if(teamName == null || emailAddress == null || coachId == null || firstName == null || lastName == null || coachRole == null|| qualificationCoach == null) {
            throw new NullPointerException("bad input");
        }
        Team team = teamDb.getTeam(teamName);
        checkTeamStatusIsActive(team);
        Coach currCoach = new Coach(emailAddress, coachId, firstName, lastName, coachRole, qualificationCoach);
        /*get the coach from DB*/
        Coach coach;
        try {
            coach = coachDb.getCoach(emailAddress);
            /*get the team of the coach if there is a team already, will throw exception*/
            if (coach.getTeam() != null) {
                throw new Exception("Coach associated with a team");
            }
            /*check if the coach's details match with the DB details*/
            if(!equalsDetailsCoach(coach,currCoach)){
                throw new Exception("One or more of the details incorrect");
            }
        }catch (NotFoundException e){
            /*Coach doesnt exist in the db - add to coachs's db*/
            coachDb.createCoach(currCoach);
            coach = currCoach;
        }
        /* add to DB the player to the team*/
        teamDb.addCoach(teamName, coach);
        teamRoleDb.createTeamRole(emailAddress,teamName,TeamRoleType.COACH);
    }

    private boolean equalsDetailsCoach(Coach coachInDb, Coach coachToAdd){
        return (!coachInDb.getEmailAddress().equals(coachToAdd.getEmailAddress()) ||
                !coachInDb.getId().equals(coachToAdd.getId()) ||
                !coachInDb.getFirstName().equals(coachToAdd.getFirstName()) ||
                !coachInDb.getLastName().equals(coachToAdd.getLastName()) ||
                !coachInDb.getCoachRole().equals(coachToAdd.getCoachRole()) ||
                !coachInDb.getQualificationCoach().equals(coachToAdd.getQualificationCoach()));
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

    public void removePlayer(String teamName, String playerEmailAddress) throws Exception {
        /*check if one of the inputs null*/
        if(teamName == null || playerEmailAddress == null) {
            throw new NullPointerException("bad input");
        }
        Team team = teamDb.getTeam(teamName);
        checkTeamStatusIsActive(team);
        /* get the player from the database*/
        Player player = playerDb.getPlayer(playerEmailAddress);
        /*check if the team that associated with the player match to the player want to delete*/
        Team teamPlayer = player.getTeam();
        if(teamPlayer == null || !teamName.equals(teamPlayer.getTeamName())) {
            throw new Exception("Player is not part with associated team");
        }
        teamDb.removePlayer(teamName, playerEmailAddress);
        teamRoleDb.removeTeamRole(playerEmailAddress,teamName,TeamRoleType.PLAYER);
    }

    public void removeTeamManager(String teamName, String teamManagerEmailAddress) throws Exception {
        /*check if one of the inputs null*/
        if(teamName == null || teamManagerEmailAddress == null) {
            throw new NullPointerException();
        }
        Team team = teamDb.getTeam(teamName);
        checkTeamStatusIsActive(team);
        /* get the teamManager from the database*/
        TeamManager teamManager = teamManagerDb.getTeamManager(teamManagerEmailAddress);
        /*check if the team that associated with the teamManager match to the teamManager want to delete*/
        Team teamManagerTeam = teamManager.getTeam();
        if(teamManagerTeam == null || !teamName.equals(teamManagerTeam.getTeamName())) {
            throw new Exception("TeamManager is not part of the team");
        }
        teamDb.removeTeamManager(teamName, teamManagerEmailAddress);
        teamRoleDb.removeTeamRole(teamManagerEmailAddress,teamName,TeamRoleType.MANAGER);

    }
    public void removeCoach(String teamName, String coachEmailAddress) throws Exception {
        /*check if one of the inputs null*/
        if(teamName == null || coachEmailAddress == null) {
            throw new NullPointerException("bad input");
        }
        Team team = teamDb.getTeam(teamName);
        checkTeamStatusIsActive(team);
        /* get the coach from the database*/
        Coach coach = coachDb.getCoach(coachEmailAddress);
        /*check if the team that associated with the coach match to the coach want to delete*/
        Team coachTeam = coach.getTeam();
        if(coachTeam == null || !teamName.equals(coachTeam.getTeamName())) {
            throw new Exception("Coach is not part with associated team");
        }
        teamDb.removeCoach(teamName, coachEmailAddress);
        teamRoleDb.removeTeamRole(coachEmailAddress,teamName,TeamRoleType.COACH);
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

    public void subscriptionTeamOwner(String teamName, String teamOwnerEmail, String ownerToAddEmail) throws Exception {
        if(teamName == null || teamOwnerEmail == null || ownerToAddEmail == null) {
            throw new NullPointerException();
        }
        Team team = teamDb.getTeam(teamName);
        checkTeamStatusIsActive(team);
        /*check if the major team owner in db*/
        TeamOwner teamOwner = teamOwnerDb.getTeamOwner(teamOwnerEmail);
        if(!team.equals(teamOwner.getTeam())){
            throw new Exception("Team owner and team don't match");
        }
        /*check if the subscriber exists*/
        Subscriber subscriber = subscriberDb.getSubscriber(ownerToAddEmail);
        List<TeamRole> teamRolesOfOwnerToAdd = teamRoleDb.getTeamRoles(ownerToAddEmail);
        for (TeamRole tr: teamRolesOfOwnerToAdd) {
            if(!teamName.equals(tr.getTeamName())){
                throw new Exception("Owner to Add already associated with other team");
            }
            if(TeamRoleType.OWNER.equals(tr.getTeamRoleType())){
                throw new Exception("This subscriber already teamOwner");
            }
        }
        teamOwnerDb.subscriptionTeamOwner(team,teamOwnerEmail,subscriber);
        teamRoleDb.createTeamRole(ownerToAddEmail,teamName,TeamRoleType.OWNER);
    }


    public void subscriptionTeamManager(String teamName, String teamOwnerEmail, String managerToAddEmail) throws Exception {
        if(teamName == null || teamOwnerEmail == null || managerToAddEmail == null) {
            throw new NullPointerException();
        }
        Team team = teamDb.getTeam(teamName);
        checkTeamStatusIsActive(team);
        /*check if the major team owner in db*/
        TeamOwner teamOwner = teamOwnerDb.getTeamOwner(teamOwnerEmail);
        if(!team.equals(teamOwner.getTeam())){
            throw new Exception("Team owner and team don't match");
        }
        Subscriber subscriber = subscriberDb.getSubscriber(managerToAddEmail);
        List<TeamRole> teamRolesOfTeamManagerToAdd = teamRoleDb.getTeamRoles(managerToAddEmail);
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
        teamManagerDb.subscriptionTeamManager(team,teamOwnerEmail,subscriber);
        teamRoleDb.createTeamRole(managerToAddEmail,teamName,TeamRoleType.MANAGER);
    }

    public void removeSubscriptionTeamOwner(String teamName, String teamOwnerEmailAddress, String ownerToRemove) throws Exception {
        if(teamName == null || teamOwnerEmailAddress == null || ownerToRemove == null) {
            throw new NullPointerException();
        }
        Team team = teamDb.getTeam(teamName);
        checkTeamStatusIsActive(team);
        /*check if the major team owner in db*/
        TeamOwner teamOwner = teamOwnerDb.getTeamOwner(teamOwnerEmailAddress);
        if(!team.equals(teamOwner.getTeam())){
            throw new Exception("Team owner and team don't match");
        }
        TeamOwner teamOwnerToRemove = teamOwnerDb.getTeamOwner(ownerToRemove);
        if(!team.equals(teamOwnerToRemove.getTeam())){
            throw new Exception("TeamOwnerToRemove associated with other team");
        }
        if(!teamOwnerEmailAddress.equals(teamOwnerToRemove.getOwnedByEmailAddress())){
            throw new Exception("TeamOwnerToRemove owned by another teamOwner");
        }
        // todo - move foreach to db
        List<String> allTeamOwnersOwnedBy = teamOwnerDb.getAllTeamOwnersOwnedBy(ownerToRemove);
        for (String emailToRemove: allTeamOwnersOwnedBy) {
            teamOwnerDb.removeSubscriptionTeamOwner(emailToRemove);
            teamRoleDb.removeTeamRole(emailToRemove,teamName,TeamRoleType.OWNER);
        }
        teamOwnerDb.removeSubscriptionTeamOwner(ownerToRemove);
        teamRoleDb.removeTeamRole(ownerToRemove,teamName,TeamRoleType.OWNER);
    }

    public void removeSubscriptionTeamManager(String teamName, String teamOwnerEmail, String managerToRemoveEmail) throws Exception {
        if(teamName == null || teamOwnerEmail == null || managerToRemoveEmail == null) {
            throw new NullPointerException();
        }
        Team team = teamDb.getTeam(teamName);
        checkTeamStatusIsActive(team);
        /*check if the major team owner in db*/
        TeamOwner teamOwner = teamOwnerDb.getTeamOwner(teamOwnerEmail);
        if(!team.equals(teamOwner.getTeam())){
            throw new Exception("Team owner and team don't match");
        }
        TeamManager teamManagerToRemove = teamManagerDb.getTeamManager(managerToRemoveEmail);
        if(!team.equals(teamManagerToRemove.getTeam())){
            throw new Exception("TeamManagerToRemove associated with other team");
        }
        if(!teamOwnerEmail.equals(teamManagerToRemove.getOwnedByIdEmail())){
            throw new Exception("TeamManagerToRemove owned by another teamOwner");
        }

        teamManagerDb.removeSubscriptionTeamManager(managerToRemoveEmail);
        teamRoleDb.removeTeamRole(managerToRemoveEmail,teamName,TeamRoleType.MANAGER);
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
