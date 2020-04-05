package Data;

import Model.UsersTypes.Player;
import Model.UsersTypes.TeamManager;

import java.util.HashMap;
import java.util.Map;

public class TeamManagerDbInMemory implements TeamManagerDb{

    /*structure like the DB of players*/
    private Map<Integer, TeamManager> teamManagers;

    public TeamManagerDbInMemory() {
        teamManagers = new HashMap<>();
    }

    /**
     * for the tests - create TeamManager in DB
     * @param teamManager
     * @throws Exception
     */
    @Override
    public void createTeamManager(TeamManager teamManager) throws Exception {
        Integer id = teamManager.getId();
        if(teamManagers.containsKey(id)) {
            throw new Exception("Team Manager already exists");
        }
        teamManagers.put(id, teamManager);
    }

    @Override
    public TeamManager getTeamManager(Integer teamManagerId) throws Exception {
        if (!teamManagers.containsKey(teamManagerId)) {
                throw new Exception("Team Manager not found");
        }
        return teamManagers.get(teamManagerId);
    }
}
