package Data;

import Model.Team;
import Model.UsersTypes.Subscriber;
import Model.UsersTypes.TeamOwner;

import java.util.List;

public interface TeamOwnerDb extends Db {
    void createTeamOwner(TeamOwner teamOwner) throws Exception;

    TeamOwner getTeamOwner(Integer teamOwnerId) throws Exception;

    void subscriptionTeamOwner(Team team, Integer teamOwnerId, Subscriber subscriber) throws Exception;

    void removeSubscriptionTeamOwner(Integer ownerToRemove) throws Exception;

    List<Integer> getAllTeamOwnersOwnedBy(Integer teamOwner);
}
