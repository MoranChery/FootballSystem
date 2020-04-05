package Controller;

import Data.*;
import Model.Team;
import Model.UsersTypes.Player;
import Model.UsersTypes.TeamManager;

public class TeamController {
    private TeamDb teamDb;
    private PlayerDb playerDb;
    private TeamManagerDb teamManagerDb;

    public TeamController() {
        teamDb = new TeamDbInMemory();
        playerDb = new PlayerDbInMemory();
        teamManagerDb = new TeamManagerDbInMemory();

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
    public void addPlayer(String teamName, Integer playerId) throws Exception {
        if(teamName == null || playerId == null) {
            throw new NullPointerException();
        }
        /*get the player from DB*/
        Player player = playerDb.getPlayer(playerId);
        /*get the team of the player if there is a team already, will throw exception*/
        Team team = player.getTeam();
        if(team != null) {
            throw new Exception("Player associated with a team");
        }
        /* add to DB the player to the team*/
        teamDb.addPlayer(teamName, player);
    }

    /**
     * add teamManager to team - now it's only with teamManager that exists in DB (will continue)
     * @param teamName
     * @param teamManagerId
     * @throws Exception
     */
    public void addTeamManager(String teamName, Integer teamManagerId) throws Exception {
        if(teamName == null || teamManagerId == null) {
            throw new NullPointerException();
        }
        /*get the teamManager from DB*/
        TeamManager teamManager = teamManagerDb.getTeamManager(teamManagerId);
        /*get the team of the team manager if there is a team already, will throw exception*/
        Team team = teamManager.getTeam();
        if(team != null) {
            throw new Exception("Team Manager associated with a team");
        }
        /* add to DB the teamManager to the team*/
        teamDb.addTeamManager(teamName, teamManager);
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
}
