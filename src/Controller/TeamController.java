package Controller;

import Data.*;
import Model.Court;
import Model.Enums.*;
import Model.FinancialActivity;
import Model.Team;
import Model.UsersTypes.*;

import java.util.*;

public class TeamController {
    private TeamDb teamDb;
    private PlayerDb playerDb;
    private TeamManagerDb teamManagerDb;
    private CourtDb courtDb;
    private CoachDb coachDb;
    private TeamOwnerDb teamOwnerDb;
    private SubscriberDb subscriberDb;
    private RoleDb roleDb;
    private FinancialActivityDb financialActivityDb;

    public TeamController() {
        teamDb =  TeamDbInMemory.getInstance();
        playerDb = PlayerDbInMemory.getInstance();
        teamManagerDb = TeamManagerDbInMemory.getInstance();
        coachDb =  CoachDbInMemory.getInstance();
        courtDb =  CourtDbInMemory.getInstance();
        teamOwnerDb =  TeamOwnerDbInMemory.getInstance();
        subscriberDb =  SubscriberDbInMemory.getInstance();
        roleDb =  RoleDbInMemory.getInstance();
        financialActivityDb =  FinancialActivityDbInMemory.getInstance();
    }

    public void createTeam(String teamName) throws Exception {
        if(teamName == null) {
            throw new NullPointerException("bad input");
        }
        teamDb.createTeam(teamName);
    }

    public Team getTeam(String teamName) throws Exception {
        if(teamName == null) {
            throw new NullPointerException("bad input");
        }
       return teamDb.getTeam(teamName);
    }


    public void createNewTeam(String teamName, String teamOwnerEmail,List<Player> players, List<Coach> coaches, List<TeamManager> teamManagers,Court court) throws Exception {
       if(teamName == null || teamOwnerEmail == null || players == null || coaches == null || teamManagers == null || court == null){
           throw new NullPointerException("bad input");
       }
        TeamOwner teamOwner = teamOwnerDb.getTeamOwner(teamOwnerEmail);
        teamDb.createTeam(teamName);
        teamOwnerDb.updateTeamOwnerTeam(teamDb.getTeam(teamName),teamOwnerEmail);
        for (Player player : players) {
            addPlayer(teamName,player.getEmailAddress(),player.getId(),player.getFirstName(),player.getLastName(),player.getBirthDate(),player.getPlayerRole());
        }
        for (Coach coach : coaches) {
            addCoach(teamName,coach.getEmailAddress(),coach.getId(),coach.getFirstName(),coach.getLastName(),coach.getCoachRole(),coach.getQualificationCoach());
        }
        for (TeamManager teamManager : teamManagers) {
            addTeamManager(teamName,teamManager.getEmailAddress(),teamManager.getId(),teamManager.getFirstName(),teamManager.getLastName(),teamManager.getOwnedByEmail());
        }
        addCourt(teamName,court.getCourtName(),court.getCourtCity());
        // TODO: 14/04/2020 add new personalPage
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
            try {
                /*check if there is other subscriber already*/
                subscriberDb.getSubscriber(emailAddress);
                throw new Exception("The player to added already has other subscriber type");
            } catch(NotFoundException ex) {
                /*give random password to player when open new subscriber*/
                currPlayer.setPassword(UUID.randomUUID().toString());
                subscriberDb.createSubscriber(currPlayer);
                /*Player doesnt exist in the db - add to players's db*/
                playerDb.createPlayer(currPlayer);
                player = currPlayer;
                // TODO: 14/04/2020 add message to the new subscriber
            }
        }
        /*add to DB the player to the team*/
        teamDb.addPlayer(teamName, player);
        roleDb.createRole(emailAddress,teamName, RoleType.PLAYER);
    }

    /**
     * check if all the information about the play want to add match with the db details
     * @param playerInDb
     * @param playerToAdd
     * @return
     */
    private boolean equalsDetailsPlayer(Player playerInDb, Player playerToAdd){
        return Objects.equals(playerInDb.getEmailAddress(), playerToAdd.getEmailAddress()) &&
                playerInDb.getId().equals(playerToAdd.getId()) &&
                playerInDb.getFirstName().equals(playerToAdd.getFirstName()) &&
                playerInDb.getLastName().equals(playerToAdd.getLastName()) &&
                playerInDb.getBirthDate().equals(playerToAdd.getBirthDate()) &&
                playerInDb.getPlayerRole().equals(playerToAdd.getPlayerRole());
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
            if(teamManager.getOwnedByEmail() != null){
                throw new Exception("Team Manager owned by another teamOwner");
            }
        }catch (NotFoundException e){
            try {
                /*check if there is other subscriber already*/
                subscriberDb.getSubscriber(emailAddress);
                throw new Exception("The teamManager to added already has other subscriber type");
            } catch(NotFoundException ex) {
                /*give random password to player when open new subscriber*/
                currTeamManager.setPassword(UUID.randomUUID().toString());
                subscriberDb.createSubscriber(currTeamManager);
                /*teamManager doesnt exist in the db - add to teamManagers's db*/
                teamManagerDb.createTeamManager(currTeamManager);
                teamManager = currTeamManager;
                // TODO: 14/04/2020 add message to the new subscriber
            }
        }
        /*add to DB the teamManager to the team*/
        teamDb.addTeamManager(teamName, teamManager,ownedByEmail);
        roleDb.createRole(emailAddress,teamName, RoleType.TEAM_MANAGER);
    }

    private boolean equalsDetailsTeamManager(TeamManager teamManagerInDb, TeamManager teamManagerToAdd){
        return (teamManagerInDb.getEmailAddress().equals(teamManagerToAdd.getEmailAddress()) &&
                teamManagerInDb.getId().equals(teamManagerToAdd.getId()) &&
                teamManagerInDb.getFirstName().equals(teamManagerToAdd.getFirstName()) &&
                teamManagerInDb.getLastName().equals(teamManagerToAdd.getLastName()));
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
            try {
                /*check if there is other subscriber already*/
                subscriberDb.getSubscriber(emailAddress);
                throw new Exception("The teamManager to added already has other subscriber type - you can to appoint him to team manager");
            } catch(NotFoundException ex) {
                /*give random password to player when open new subscriber*/
                currCoach.setPassword(UUID.randomUUID().toString());
                /*create subscriber in db*/
                subscriberDb.createSubscriber(currCoach);
                /*Coach doesnt exist in the db - add to coachs's db*/
                coachDb.createCoach(currCoach);
                coach = currCoach;
                // TODO: 14/04/2020 add message to the new subscriber
            }
        }
        /* add to DB the player to the team*/
        teamDb.addCoach(teamName, coach);
        roleDb.createRole(emailAddress,teamName, RoleType.COACH);
    }

    private boolean equalsDetailsCoach(Coach coachInDb, Coach coachToAdd){
        return (coachInDb.getEmailAddress().equals(coachToAdd.getEmailAddress()) &&
                coachInDb.getId().equals(coachToAdd.getId()) &&
                coachInDb.getFirstName().equals(coachToAdd.getFirstName()) &&
                coachInDb.getLastName().equals(coachToAdd.getLastName()) &&
                coachInDb.getCoachRole().equals(coachToAdd.getCoachRole()) &&
                coachInDb.getQualificationCoach().equals(coachToAdd.getQualificationCoach()));
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
        roleDb.removeRoleFromTeam(playerEmailAddress,teamName, RoleType.PLAYER);
    }

    public void removeTeamManager(String teamName, String teamManagerEmailAddress) throws Exception {
        /*check if one of the inputs null*/
        if(teamName == null || teamManagerEmailAddress == null) {
            throw new NullPointerException("bad input");
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
        roleDb.removeRoleFromTeam(teamManagerEmailAddress,teamName, RoleType.TEAM_MANAGER);

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
        roleDb.removeRoleFromTeam(coachEmailAddress,teamName, RoleType.COACH);
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
            throw new NullPointerException("bad input");
        }
        Team team = teamDb.getTeam(teamName);
        checkTeamStatusIsActive(team);
        /*check if the major team owner in db*/
        TeamOwner teamOwner = teamOwnerDb.getTeamOwner(teamOwnerEmail);
        if(!team.equals(teamOwner.getTeam())){
            throw new Exception("Teamowner's team does't match");
        }
        /*check if the subscriber exists*/
        Subscriber subscriber = subscriberDb.getSubscriber(ownerToAddEmail);
        List<Role> rolesOfOwnerToAdd = roleDb.getRoles(ownerToAddEmail);
        for (Role tr: rolesOfOwnerToAdd) {
            if(tr.getTeamName() != null && !teamName.equals(tr.getTeamName())){
                throw new Exception("OwnerToAdd already associated with other team");
            }
            if(RoleType.TEAM_OWNER.equals(tr.getRoleType())){
                throw new Exception("This subscriber already teamOwner");
            }
        }
        teamOwnerDb.subscriptionTeamOwner(team,teamOwnerEmail,subscriber);
        roleDb.createRole(ownerToAddEmail,teamName, RoleType.TEAM_OWNER);
    }


    public void subscriptionTeamManager(String teamName, String teamOwnerEmail, String managerToAddEmail) throws Exception {
        if(teamName == null || teamOwnerEmail == null || managerToAddEmail == null) {
            throw new NullPointerException("bad input");
        }
        Team team = teamDb.getTeam(teamName);
        checkTeamStatusIsActive(team);
        /*check if the major team owner in db*/
        TeamOwner teamOwner = teamOwnerDb.getTeamOwner(teamOwnerEmail);
        if(!team.equals(teamOwner.getTeam())){
            throw new Exception("Teamowner's team does't match");
        }
        Subscriber subscriber = subscriberDb.getSubscriber(managerToAddEmail);
        List<Role> teamRolesOfManagerToAdd = roleDb.getRoles(managerToAddEmail);
        for (Role tr: teamRolesOfManagerToAdd) {
            if(tr.getTeamName() != null && !teamName.equals(tr.getTeamName())){
                throw new Exception("ManagerToAdd already associated with other team");
            }
            RoleType roleType = tr.getRoleType();
            if(RoleType.TEAM_OWNER.equals(roleType)){
                throw new Exception("This subscriber already teamOwner");
            }
            if(RoleType.TEAM_MANAGER.equals(roleType)){
                throw new Exception("This subscriber already teamManager");
            }
        }
        teamManagerDb.subscriptionTeamManager(team,teamOwnerEmail,subscriber);
        roleDb.createRole(managerToAddEmail,teamName, RoleType.TEAM_MANAGER);
    }

    public void removeSubscriptionTeamOwner(String teamName, String teamOwnerEmailAddress, String ownerToRemove) throws Exception {
        if(teamName == null || teamOwnerEmailAddress == null || ownerToRemove == null) {
            throw new NullPointerException("bad input");
        }
        Team team = teamDb.getTeam(teamName);
        checkTeamStatusIsActive(team);
        /*check if the major team owner in db*/
        TeamOwner teamOwner = teamOwnerDb.getTeamOwner(teamOwnerEmailAddress);
        if(!team.equals(teamOwner.getTeam())){
            throw new Exception("TeamOwner's team does't match");
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
            removeSubscriptionTeamOwner(teamName,ownerToRemove,emailToRemove);
        }
        List<String> allTeamManagersOwnedBy = teamManagerDb.getAllTeamManagersOwnedBy(ownerToRemove);
        for (String emailToRemove: allTeamManagersOwnedBy) {
            teamManagerDb.removeSubscriptionTeamManager(emailToRemove);
            roleDb.removeRole(emailToRemove,RoleType.TEAM_MANAGER);
        }
        teamOwnerDb.removeSubscriptionTeamOwner(ownerToRemove);
//        roleDb.removeRoleFromTeam(ownerToRemove,teamName, RoleType.TEAM_OWNER);
        roleDb.removeRole(ownerToRemove,RoleType.TEAM_OWNER);

    }

    public void removeSubscriptionTeamManager(String teamName, String teamOwnerEmail, String managerToRemoveEmail) throws Exception {
        if(teamName == null || teamOwnerEmail == null || managerToRemoveEmail == null) {
            throw new NullPointerException("bad input");
        }
        Team team = teamDb.getTeam(teamName);
        checkTeamStatusIsActive(team);
        /*check if the major team owner in db*/
        TeamOwner teamOwner = teamOwnerDb.getTeamOwner(teamOwnerEmail);
        if(!team.equals(teamOwner.getTeam())){
            throw new Exception("TeamOwner's team doesn't match");
        }
        TeamManager teamManagerToRemove = teamManagerDb.getTeamManager(managerToRemoveEmail);
        if(!team.equals(teamManagerToRemove.getTeam())){
            throw new Exception("TeamManagerToRemove associated with other team");
        }
        if(!teamOwnerEmail.equals(teamManagerToRemove.getOwnedByEmail())){
            throw new Exception("TeamManagerToRemove owned by another teamOwner");
        }

        teamManagerDb.removeSubscriptionTeamManager(managerToRemoveEmail);
        roleDb.removeRole(managerToRemoveEmail, RoleType.TEAM_MANAGER);
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

    public void updatePlayerDetails(String ownerEmailAddress,String playerEmailAddress, String firstName, String lastName, Date birthDate, PlayerRole playerRole) throws Exception {
        /*check if the teamOwner in Db, than check if the player want to change is in teamOwner's team*/
        TeamOwner teamOwner = teamOwnerDb.getTeamOwner(ownerEmailAddress);
        Map<String, Player> players = teamOwner.getTeam().getPlayers();
        if(!players.containsKey(playerEmailAddress)) {
            throw new Exception("Player not associated with teamOwner's team");
        }
            Player playerFromDb = playerDb.getPlayer(playerEmailAddress);
            playerFromDb.setFirstName(firstName);
            playerFromDb.setLastName(lastName);
            playerFromDb.setBirthDate(birthDate);
            playerFromDb.setPlayerRole(playerRole);
            playerDb.updatePlayerDetails(playerFromDb);
    }



}
