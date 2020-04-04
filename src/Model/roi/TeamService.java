package Model.roi;

import Model.Team;
import Model.UsersTypes.Player;

public class TeamService {
    private TeamDb teamDb;
    private PlayerDb playerDb;

    public TeamService() {
        teamDb = new TeamDbInMemory();
        playerDb = new PlayerDbInMemory();
    }

    void createTeam(String teamName) throws Exception {
        teamDb.createTeam(teamName);
    }

    void addPlayer(String teamName, Integer playerId) throws Exception {
        Player player = playerDb.getPlayer(playerId);
        Team team = player.getTeam();
        if(team != null) {
            throw new Exception("Player associated with a team");
        }
        teamDb.addPlayer(teamName, player);
    }

    void removePlayer(String teamName, Integer playerId) throws Exception {
        Player player = playerDb.getPlayer(playerId);
        Team team = player.getTeam();
        if(team == null || !teamName.equals(team.getTeamName())) {
            throw new Exception("Player is not part of the team");
        }
        teamDb.removePlayer(teamName, playerId);
    }
}
