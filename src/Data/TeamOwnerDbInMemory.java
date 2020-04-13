package Data;

import Model.Team;
import Model.UsersTypes.Subscriber;
import Model.UsersTypes.TeamOwner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TeamOwnerDbInMemory implements TeamOwnerDb{

    /*structure like the DB of teamOwners*/
    private Map<String, TeamOwner> teamOwners;

    public TeamOwnerDbInMemory() {
        teamOwners = new HashMap<>();
    }

    private static TeamOwnerDbInMemory ourInstance = new TeamOwnerDbInMemory();

    public static TeamOwnerDbInMemory getInstance() {
        return ourInstance;
    }
    @Override
    public void createTeamOwner(TeamOwner teamOwner) throws Exception {
        String teamOwnerEmailAddress = teamOwner.getEmailAddress();
        if (teamOwners.containsKey(teamOwnerEmailAddress)) {
            throw new Exception("TeamOwner already exists");
        }
        teamOwners.put(teamOwnerEmailAddress, teamOwner);
    }

    @Override
    public TeamOwner getTeamOwner(String teamOwnerEmailAddress) throws Exception {
        if(teamOwnerEmailAddress == null || !teamOwners.containsKey(teamOwnerEmailAddress)){
            throw new Exception("TeamOwner not found");
        }
        return teamOwners.get(teamOwnerEmailAddress);
    }

    @Override
    public void subscriptionTeamOwner(Team team, String teamOwnerEmail, Subscriber subscriber) throws Exception {
        if(team == null || teamOwnerEmail == null || subscriber == null){
            throw new NullPointerException();
        }
        if(teamOwners.containsKey(subscriber.getEmailAddress())){
            throw new Exception("TeamOwner to add already exists");
        }
        if(!teamOwners.containsKey(teamOwnerEmail)){
            throw new Exception("Major Team Owner not found");
        }
        TeamOwner teamOwner = new TeamOwner(team,subscriber,teamOwnerEmail);
        Integer ownerId = teamOwner.getId();
        TeamOwner teamOwnerMajor = teamOwners.get(teamOwnerEmail);
        teamOwnerMajor.getTeamOwnersByThis().put(ownerId,teamOwner);
        teamOwners.put(teamOwnerEmail,teamOwner);
    }

    @Override
    public void removeSubscriptionTeamOwner(String ownerToRemoveEmail) throws Exception {
        if(ownerToRemoveEmail == null){
            throw new NullPointerException();
        }
        if(!teamOwners.containsKey(ownerToRemoveEmail)){
            throw new Exception("TeamOwner not found");
        }
        teamOwners.remove(ownerToRemoveEmail);
   }

    @Override
    public List<String> getAllTeamOwnersOwnedBy(String teamOwnerEmail) {
        List<String> teamOwnersOwnedBy = new ArrayList<>();
        for (TeamOwner tOwner: teamOwners.values()) {
            if(teamOwnerEmail.equals(tOwner.getOwnedByEmailAddress())){
                teamOwnersOwnedBy.add(tOwner.getEmailAddress());
            }
        }
        return teamOwnersOwnedBy;
    }

    @Override
    public void deleteAll() {
        teamOwners.clear();
    }

}
