package Data;

import Model.Team;
import Model.UsersTypes.Subscriber;
import Model.UsersTypes.TeamOwner;

import java.util.List;
import java.util.Set;

public interface TeamOwnerDb extends Db {
    void createTeamOwner(TeamOwner teamOwner) throws Exception;

    void updateTeamOwnerTeam(Team team, String teamOwnerEmailAddress) throws Exception;

    TeamOwner getTeamOwner(String teamOwnerEmailAddress) throws Exception;

    void subscriptionTeamOwner(Team team, String teamOwnerId, Subscriber subscriber) throws Exception;

    void removeSubscriptionTeamOwner(String ownerToRemoveEmail) throws Exception;

    List<String> getAllTeamOwnersOwnedBy(String teamOwnerEmail);

    Set<String> getAllTeamOwnersInDB();
}
