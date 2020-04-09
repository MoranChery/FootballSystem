package Data;

import Model.Team;
import Model.UsersTypes.Subscriber;
import Model.UsersTypes.TeamOwner;

public interface TeamOwnerDb {
    void createTeamOwner(TeamOwner teamOwner) throws Exception;

    TeamOwner getTeamOwner(Integer teamOwnerId) throws Exception;

    void addTeamOwner(Team team, Integer teamOwnerId, Subscriber subscriber) throws Exception;
}
