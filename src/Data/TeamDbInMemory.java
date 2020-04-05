package Data;

import Model.Team;
import Model.UsersTypes.Player;
import Model.UsersTypes.TeamManager;

import java.util.HashMap;
import java.util.Map;

public class TeamDbInMemory implements TeamDb {
    /*structure like the DB of teams*/
    private Map<String, Team> teams;

    public TeamDbInMemory() {
        teams = new HashMap<>();
    }

    /**
     * create team in DB
     * @param teamName
     * @throws Exception
     */
    @Override
    public void createTeam(String teamName) throws Exception {
        if(teams.containsKey(teamName)) {
            throw new Exception("Team already exist in the system");
        }
        Team team = new Team();
        team.setTeamName(teamName);
        teams.put(teamName, team);
    }

    /**
     * add the player to the team in DB
     * @param teamName
     * @param player
     * @throws Exception
     */
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

    /**
     * remove the player from the team in DB
     * @param teamName
     * @param playerId
     * @throws Exception
     */
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

    @Override
    public void addTeamManager(String teamName, TeamManager player) throws Exception {

    }

    /**
     * "pull" the team from DB
     * @param teamName
     * @return
     * @throws Exception
     */
    @Override
    public Team getTeam(String teamName) throws Exception {
        Team team = teams.get(teamName);
        if(team == null) {
            throw new Exception("Team not found");
        }
        return team;
    }

}
