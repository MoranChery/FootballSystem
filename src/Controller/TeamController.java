package Controller;

import Data.*;
import Model.Court;
import Model.Enums.CoachRole;
import Model.Enums.PlayerRole;
import Model.Enums.Qualification;
import Model.Team;
import Model.UsersTypes.Coach;
import Model.UsersTypes.Player;
import Model.UsersTypes.TeamManager;

import java.util.Date;

public class TeamController {
    private TeamDb teamDb;
    private PlayerDb playerDb;
    private TeamManagerDb teamManagerDb;
    private CourtDb courtDb;
    private CoachDb coachDb;

    public TeamController() {
        teamDb = new TeamDbInMemory();
        playerDb = new PlayerDbInMemory();
        teamManagerDb = new TeamManagerDbInMemory();
        coachDb = new CoachDbInMemory();
        courtDb = new CourtDbInMemory();
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
     * add player to team - now it's only with player that exists in DB (will continue)
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
        if(teamName == null || playerId == null) {
            throw new NullPointerException();
        }
        Player player = playerDb.getPlayer(playerId);
        Team team = player.getTeam();
        if(team == null || !teamName.equals(team.getTeamName())) {
            throw new Exception("Player is not part of the team");
        }
        teamDb.removePlayer(teamName, playerId);
    }


    public void addFinancialActivity(String teamName, Double financialActivityAmount, String description){
        if(teamName == null || financialActivityAmount == null || description == null) {
            throw new NullPointerException();
        }

    }
}
