package Data;

import Model.Team;
import Model.UsersTypes.Subscriber;
import Model.UsersTypes.TeamManager;
import Model.UsersTypes.TeamOwner;

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

    @Override
    public void subscriptionTeamManager(Team team, Integer teamOwnerId, Subscriber subscriber) throws Exception {
        if(team == null || teamOwnerId == null || subscriber == null){
            throw new NullPointerException();
        }
        if(teamManagers.containsKey(subscriber.getId())){
            throw new Exception("Team Manager to add already exists");
        }
        TeamManager teamManager = new TeamManager(team,subscriber,teamOwnerId);
        Integer managerId = teamManager.getId();
        teamManagers.put(managerId,teamManager);
    }

    @Override
    public void removeSubscriptionTeamManager(Integer managerToRemove) throws Exception {
        if (managerToRemove == null) {
            throw new NullPointerException();
        }
        if (!teamManagers.containsKey(managerToRemove)) {
            throw new Exception("TeamManager not found");
        }
        teamManagers.remove(managerToRemove);
    }
}
