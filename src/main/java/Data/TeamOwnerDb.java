package Data;

import Model.Team;
import Model.UsersTypes.Subscriber;
import Model.UsersTypes.TeamOwner;

import java.sql.SQLException;
import java.util.List;
import java.util.Set;

public interface TeamOwnerDb extends Db {
    void insertTeamOwner(TeamOwner teamOwner) throws Exception;

    void updateTeamOwnerTeam(String team, String teamOwnerEmailAddress) throws Exception;

    TeamOwner getTeamOwner(String teamOwnerEmailAddress) throws Exception;

    void subscriptionTeamOwner(String team, String teamOwnerId, Subscriber subscriber) throws Exception;

    void removeSubscriptionTeamOwner(String ownerToRemoveEmail) throws Exception;

    List<String> getAllTeamOwnersOwnedBy(String teamOwnerEmail) throws SQLException;

    Set<String> getAllTeamOwnersInDB();
}
