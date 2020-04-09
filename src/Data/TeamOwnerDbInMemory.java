package Data;

import Model.Court;
import Model.Team;
import Model.UsersTypes.Subscriber;
import Model.UsersTypes.TeamManager;
import Model.UsersTypes.TeamOwner;

import java.util.HashMap;
import java.util.Map;

public class TeamOwnerDbInMemory implements TeamOwnerDb{

    /*structure like the DB of players*/
    private Map<Integer, TeamOwner> teamOwners;

    public TeamOwnerDbInMemory() {
        teamOwners = new HashMap<>();
    }


    @Override
    public void createTeamOwner(TeamOwner teamOwner) throws Exception {
        Integer teamOwnerId = teamOwner.getId();
        if (teamOwners.containsKey(teamOwnerId)) {
            throw new Exception("teamOwner already exists");
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
    public void addTeamOwner(Team team, Integer teamOwnerId, Subscriber subscriber) throws Exception {
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

}
