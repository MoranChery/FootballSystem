package Model.roi;

import Model.Team;
import Model.UsersTypes.Player;

import java.util.HashMap;
import java.util.Map;

public class TeamDbInMemory implements TeamDb {
    private Map<String, Team> teams;

    public TeamDbInMemory() {
        teams = new HashMap<>();
    }

    @Override
    public void createTeam(String teamName) throws Exception {
        if(teams.containsKey(teamName)) {
            throw new Exception("Team already exist in the system");
        }

        Team team = new Team();
        team.setTeamName(teamName);
        teams.put(teamName, team);
    }

    @Override
    public void addPlayer(String teamName, Player player) throws Exception {
        Team team = teams.get(teamName);
        if(team == null) {
            throw new Exception("Team not found");
        }

        Map<Integer, Player> players = team.getPlayers();
        Integer id = player.getId();

        if(players.containsKey(id)) {
            throw new Exception("Player already part of the team");
        }
        players.put(id, player);
    }

    @Override
    public void removePlayer(String teamName, Integer playerId) throws Exception {
        Team team = teams.get(teamName);
        if(team == null) {
            throw new Exception("Team not found");
        }

        Map<Integer, Player> players = team.getPlayers();
        if(!players.containsKey(playerId)) {
            throw new Exception("Player not part of the team");
        }
        Player player = players.remove(playerId);
        // check if needed
        player.setTeam(null);
    }
}
