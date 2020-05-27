package Data;

import Model.Enums.PermissionType;
import Model.Team;
import Model.UsersTypes.Subscriber;
import Model.UsersTypes.TeamManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TeamManagerDbInMemory implements TeamManagerDb {

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
    public void insertTeamManager(TeamManager teamManager) throws Exception {
        if(teamManager == null) {
            throw new NullPointerException("bad input");
        }
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
    public void subscriptionTeamManager(String team, String teamOwnerEmail, Subscriber subscriber, List<PermissionType> permissionTypes) throws Exception {
        if(team == null || teamOwnerEmail == null || subscriber == null || permissionTypes == null){
            throw new NullPointerException("bad input");
        }
        if(teamManagers.containsKey(subscriber.getEmailAddress())){
            throw new Exception("Team Manager to add already exists");
        }
        TeamManager teamManager = new TeamManager(team,subscriber,teamOwnerEmail,permissionTypes);
        String managerEmailAddress = teamManager.getEmailAddress();
        teamManagers.put(managerEmailAddress,teamManager);
//        Map<String, TeamManager> teamManagers = team.getTeamManagers();
//        teamManagers.put(managerEmailAddress,teamManager);
    }

    @Override
    public void removeSubscriptionTeamManager(String managerToRemoveEmail) throws Exception {
        if (managerToRemoveEmail == null) {
            throw new NullPointerException();
        }
        if (!teamManagers.containsKey(managerToRemoveEmail)) {
            throw new Exception("TeamManager not found");
        }
        TeamManager teamManager = teamManagers.remove(managerToRemoveEmail);
//        Map<String, TeamManager> teamManagers = teamManager.getTeam().getTeamManagers();
//        teamManagers.remove(managerToRemoveEmail);
    }

    @Override
    public List<String> getAllTeamManagersOwnedBy(String teamOwnerEmail){
        List<String> teamManagersOwnedBy = new ArrayList<>();
        for (TeamManager teamManager: teamManagers.values()) {
            if(teamOwnerEmail.equals(teamManager.getOwnedByEmail())){
                teamManagersOwnedBy.add(teamManager.getEmailAddress());
            }
        }
        return teamManagersOwnedBy;
    }
    @Override
    public void updateTeamManagerDetails(String teamManagerEmailAddress, String firstName, String lastName, List<PermissionType> permissionTypes) throws NotFoundException {
        if(!teamManagers.containsKey(teamManagerEmailAddress)){
            throw new NotFoundException("TeamManager not found");
        }
        TeamManager teamManager = teamManagers.get(teamManagerEmailAddress);
        teamManager.setFirstName(firstName);
        teamManager.setLastName(lastName);
        List<PermissionType> permissionTypesList = teamManager.getPermissionTypes();
        teamManager.setPermissionTypes(permissionTypes);
    }

    @Override
    public void deleteAll() {
        teamManagers.clear();
    }
}
