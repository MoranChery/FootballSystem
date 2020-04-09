package Controller;

import Data.*;
import Model.Court;
import Model.Enums.CoachRole;
import Model.Enums.FinancialActivityType;
import Model.Enums.PlayerRole;
import Model.Enums.Qualification;
import Model.FinancialActivity;
import Model.Team;
import Model.UsersTypes.*;

import java.util.Date;
import java.util.UUID;

public class TeamController {
    private TeamDb teamDb;
    private PlayerDb playerDb;
    private TeamManagerDb teamManagerDb;
    private CourtDb courtDb;
    private CoachDb coachDb;
    private TeamOwnerDb teamOwnerDb;
    private SubscriberDb subscriberDb;
    public TeamController() {
        teamDb = new TeamDbInMemory();
        playerDb = new PlayerDbInMemory();
        teamManagerDb = new TeamManagerDbInMemory();
        coachDb = new CoachDbInMemory();
        courtDb = new CourtDbInMemory();
        teamOwnerDb = new TeamOwnerDbInMemory();
        subscriberDb = new SubscriberDbInMemory();
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
            throw new NullPointerException();
        }
        Player currPlayer = new Player(playerId,firstName,lastName,birthDate,playerRole);
        /*get the player from DB*/
        Player player = playerDb.getPlayer(playerId);
        if(player != null) {
            /*get the team of the player if there is a team already, will throw exception*/
            Team team = player.getTeam();
            if (team != null) {
                throw new Exception("Player associated with a team");
            }
            /*check if the player's details match with the DB details*/
            if(!player.equals(currPlayer)){
                throw new Exception("One or more of the details incorrect");
            }
        }else{
            /*Player doesnt exist in the db - add to players's db*/
            playerDb.createPlayer(currPlayer);
            player = currPlayer;
        }
        /* add to DB the player to the team*/
        teamDb.addPlayer(teamName, player);
    }

    public void addTeamManager(String teamName, Integer teamManagerId, String firstName ,String lastName) throws Exception {
        if(teamName == null || teamManagerId == null || firstName == null || lastName == null) {
            throw new NullPointerException();
        }
        TeamManager currTeamManager = new TeamManager(teamManagerId, firstName, lastName);
        /*get the teamManager from DB*/
        TeamManager teamManager = teamManagerDb.getTeamManager(teamManagerId);

        if(teamManager != null) {
            /*get the team of the teamManager if there is a team already, will throw exception*/
            Team team = teamManager.getTeam();
            if (team != null) {
                throw new Exception("TeamManager associated with a team");
            }
            /*check if the teamManager's details match with the DB details*/
            if(!teamManager.equals(currTeamManager)){
                throw new Exception("One or more of the details incorrect");
            }
        }else{
            /*teamManager doesnt exist in the db - add to teamManagers's db*/
            teamManagerDb.createTeamManager(currTeamManager);
            teamManager = currTeamManager;
        }
        /*add to DB the teamManager to the team*/
        teamDb.addTeamManager(teamName, teamManager);
    }


    public void addCoach(String teamName, Integer coachId, String firstName, String lastName, CoachRole coachRole, Qualification qualification) throws Exception {
        if(teamName == null || coachId == null || firstName == null || lastName == null || coachRole == null|| qualification == null) {
            throw new NullPointerException();
        }
        Coach currCoach = new Coach(coachId, firstName, lastName, coachRole, qualification);
        /*get the coach from DB*/
        Coach coach = coachDb.getCoach(coachId);
        if(coach != null) {
            /*get the team of the coach if there is a team already, will throw exception*/
            Team team = coach.getTeam();
            if (team != null) {
                throw new Exception("Coach associated with a team");
            }
            /*check if the coach's details match with the DB details*/
            if(!coach.equals(currCoach)){
                throw new Exception("One or more of the details incorrect");
            }
        }else{
            /*Coach doesnt exist in the db - add to coachs's db*/
            coachDb.createCoach(currCoach);
            coach = currCoach;
        }
        /* add to DB the player to the team*/
        teamDb.addCoach(teamName, coach);
    }

    public void addCourt(String teamName, String courtName, String courtCity) throws Exception {
        if (teamName == null || courtName == null || courtCity == null) {
            throw new NullPointerException();
        }
        /*check if the team exists*/
        Team team = teamDb.getTeam(teamName);
        if(team.getCourt() != null){
            throw new Exception("team already associated with court");
        }
        /*check if the court already in the db*/
        Court court = courtDb.getCourt(courtName);
        if(court != null && !courtCity.equals(court.getCourtCity())) {
            throw new Exception("The court name isn't match to the city");
        }
        if (court == null) { //court in the db
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
            throw new NullPointerException();
        }
        /* get the player from the database*/
        Player player = playerDb.getPlayer(playerId);
        /*check if the team that associated with the player match to the player want to delete*/
        Team team = player.getTeam();
        if(team == null || !teamName.equals(team.getTeamName())) {
            throw new Exception("Player is not part of the team");
        }
        teamDb.removePlayer(teamName, playerId);
    }

    public void removeTeamManager(String teamName, Integer teamManagerId) throws Exception {
        /*check if one of the inputs null*/
        if(teamName == null || teamManagerId == null) {
            throw new NullPointerException();
        }
        /* get the teamManager from the database*/
        TeamManager teamManager = teamManagerDb.getTeamManager(teamManagerId);
        /*check if the team that associated with the teamManager match to the teamManager want to delete*/
        Team team = teamManager.getTeam();
        if(team == null || !teamName.equals(team.getTeamName())) {
            throw new Exception("TeamManager is not part of the team");
        }
        teamDb.removeTeamManager(teamName, teamManagerId);
    }
    public void removeCoach(String teamName, Integer coachId) throws Exception {
        /*check if one of the inputs null*/
        if(teamName == null || coachId == null) {
            throw new NullPointerException();
        }
        /* get the coach from the database*/
        Coach coach = coachDb.getCoach(coachId);
        /*check if the team that associated with the coach match to the coach want to delete*/
        Team team = coach.getTeam();
        if(team == null || !teamName.equals(team.getTeamName())) {
            throw new Exception("coachId is not part of the team");
        }
        teamDb.removeCoach(teamName, coachId);
    }

    public void removeCourt(String teamName, String courtName) throws Exception {
        /*check if one of the inputs null*/
        if(teamName == null || courtName == null) {
            throw new NullPointerException();
        }
        Court court = courtDb.getCourt(courtName);
        /*check if one of the teams that associated with the court match to the court want to delete*/
        Team team = court.getTeam(courtName);
        if(team == null || !teamName.equals(team.getTeamName())) {
            throw new Exception("coach is not part of the team");
        }
        teamDb.removeCourt(teamName, courtName);
    }

    public void addTeamOwner(String teamName, Integer teamOwnerId, Integer ownerToAdd) throws Exception {
        if(teamName == null || teamOwnerId == null || ownerToAdd == null) {
            throw new NullPointerException();
        }
        TeamOwner teamOwner = teamOwnerDb.getTeamOwner(teamOwnerId);
        Team team = teamDb.getTeam(teamName);
        if(!team.equals(teamOwner.getTeam())){
            throw new Exception("Team owner and team don't match");
        }
        Subscriber subscriber = subscriberDb.getSubscriber(ownerToAdd);
        try {
            teamOwnerDb.getTeamOwner(ownerToAdd);

        }catch (Exception e){

            teamOwnerDb.addTeamOwner(team,teamOwnerId,subscriber);
        }
        throw new Exception("TeamOwner to add is already teamOwner");

    }


    public void addFinancialActivity(String teamName, Double financialActivityAmount, String description, FinancialActivityType financialActivityType) throws Exception {
        if(teamName == null || financialActivityAmount == null || description == null || financialActivityType == null) {
            throw new NullPointerException();
        }
        Team team = teamDb.getTeam(teamName);
        if(financialActivityType.equals(FinancialActivityType.OUTCOME) ){
            if(team.getBudget() - financialActivityAmount < 0){
                throw new Exception("The financial outcome exceeds from the budget");
            }
        }
        /*for security and unique id*/
        String financialActivityId = UUID.randomUUID().toString();
        FinancialActivity financialActivity = new FinancialActivity(financialActivityId,financialActivityAmount,description,financialActivityType,team);
        teamDb.addFinancialActivity(team,financialActivityId,financialActivity);
    }

    public void changeStatusToInActive(String teamName) throws Exception {
        if(teamName == null) {
            throw new NullPointerException();
        }
        Team team = teamDb.getTeam(teamName);
        teamDb.changeStatusToInActive(team);
    }

    public void changeStatusToActive(String teamName) throws Exception {
        if(teamName == null) {
            throw new NullPointerException();
        }
        Team team = teamDb.getTeam(teamName);
        teamDb.changeStatusToActive(team);
    }
}
