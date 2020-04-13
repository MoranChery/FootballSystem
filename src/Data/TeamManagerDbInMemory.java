package Data;

import Model.Team;
import Model.UsersTypes.Subscriber;
import Model.UsersTypes.TeamManager;
import Model.UsersTypes.TeamOwner;

import java.util.HashMap;
import java.util.Map;

public class TeamManagerDbInMemory implements TeamManagerDb{

    /*structure like the DB of players*/
    private Map<String, TeamManager> teamManagers;

    public TeamManagerDbInMemory() {
        teamManagers = new HashMap<>();
    }

    private static TeamManagerDbInMemory ourInstance = new TeamManagerDbInMemory();

    public static TeamManagerDbInMemory getInstance() {
        return ourInstance;
    }

    /**
     * for the tests - create TeamManager in DB
     * @param teamManager
     * @throws Exception
     */
    @Override
    public void createTeamManager(TeamManager teamManager) throws Exception {
        String emailAddress = teamManager.getEmailAddress();
        if(teamManagers.containsKey(emailAddress)) {
            throw new Exception("Team Manager already exists");
        }
        teamManagers.put(emailAddress, teamManager);
    }

    @Override
    public TeamManager getTeamManager(String teamManagerEmailAddress) throws Exception {
        if (!teamManagers.containsKey(teamManagerEmailAddress)) {
                throw new NotFoundException("Team Manager not found");
        }
        return teamManagers.get(teamManagerEmailAddress);
    }

    @Override
    public void subscriptionTeamManager(Team team, String teamOwnerEmail, Subscriber subscriber) throws Exception {
        if(team == null || teamOwnerEmail == null || subscriber == null){
            throw new NullPointerException();
        }
        if(teamManagers.containsKey(subscriber.getEmailAddress())){
            throw new Exception("Team Manager to add already exists");
        }
        TeamManager teamManager = new TeamManager(team,subscriber,teamOwnerEmail);
        String managerEmailAddress = teamManager.getEmailAddress();
        teamManagers.put(managerEmailAddress,teamManager);
    }

    @Override
    public void removeSubscriptionTeamManager(String managerToRemoveEmail) throws Exception {
        if (managerToRemoveEmail == null) {
            throw new NullPointerException();
        }
        if (!teamManagers.containsKey(managerToRemoveEmail)) {
            throw new Exception("TeamManager not found");
        }
        teamManagers.remove(managerToRemoveEmail);
    }

    @Override
    public void deleteAll() {
        teamManagers.clear();
    }
}
