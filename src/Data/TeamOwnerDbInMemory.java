package Data;

import Model.Court;
import Model.Team;
import Model.UsersTypes.Subscriber;
import Model.UsersTypes.TeamManager;
import Model.UsersTypes.TeamOwner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TeamOwnerDbInMemory implements TeamOwnerDb{

    /*structure like the DB of teamOwners*/
    private Map<Integer, TeamOwner> teamOwners;

    public TeamOwnerDbInMemory() {
        teamOwners = new HashMap<>();
    }

    private static TeamOwnerDbInMemory ourInstance = new TeamOwnerDbInMemory();

    public static TeamOwnerDbInMemory getInstance() {
        return ourInstance;
    }
    @Override
    public void createTeamOwner(TeamOwner teamOwner) throws Exception {
        Integer teamOwnerId = teamOwner.getId();
        if (teamOwners.containsKey(teamOwnerId)) {
            throw new Exception("TeamOwner already exists");
        }
        teamOwners.put(teamOwnerId, teamOwner);
    }

    @Override
    public TeamOwner getTeamOwner(Integer teamOwnerId) throws Exception {
        if(teamOwnerId == null || !teamOwners.containsKey(teamOwnerId)){
            throw new Exception("TeamOwner not found");
        }
        return teamOwners.get(teamOwnerId);
    }

    @Override
    public void subscriptionTeamOwner(Team team, Integer teamOwnerId, Subscriber subscriber) throws Exception {
        if(team == null || teamOwnerId == null || subscriber == null){
            throw new NullPointerException();
        }
        if(teamOwners.containsKey(subscriber.getId())){
            throw new Exception("TeamOwner to add already exists");
        }
        if(!teamOwners.containsKey(teamOwnerId)){
            throw new Exception("Major Team Owner not found");
        }
        TeamOwner teamOwner = new TeamOwner(team,subscriber,teamOwnerId);
        Integer ownerId = teamOwner.getId();
        TeamOwner teamOwnerMajor = teamOwners.get(teamOwnerId);
        teamOwnerMajor.getTeamOwnersByThis().put(ownerId,teamOwner);
        teamOwners.put(ownerId,teamOwner);
    }

    @Override
    public void removeSubscriptionTeamOwner(Integer ownerToRemove) throws Exception {
        if(ownerToRemove == null){
            throw new NullPointerException();
        }
        if(!teamOwners.containsKey(ownerToRemove)){
            throw new Exception("TeamOwner not found");
        }
        teamOwners.remove(ownerToRemove);
   }

    @Override
    public List<Integer> getAllTeamOwnersOwnedBy(Integer teamOwner) {
        List<Integer> teamOwnersOwnedBy = new ArrayList<>();
        for (TeamOwner tOwner: teamOwners.values()) {
            if(teamOwner.equals(tOwner.getOwnedById())){
                teamOwnersOwnedBy.add(tOwner.getId());
            }
        }
        return teamOwnersOwnedBy;
    }

    @Override
    public void deleteAll() {
        teamOwners.clear();
    }

}
