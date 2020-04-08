package Data;

import Model.Court;
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
}
