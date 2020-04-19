package Data;

import Model.Enums.PermissionType;
import Model.Team;
import Model.UsersTypes.Subscriber;
import Model.UsersTypes.TeamManager;

import java.util.List;

public interface TeamManagerDb extends Db {

    void createTeamManager(TeamManager teamManager) throws Exception;
    TeamManager getTeamManager(String teamManagerEmailAddress) throws Exception;


    void subscriptionTeamManager(Team team, String teamOwnerId, Subscriber subscriber, List<PermissionType> permissionTypes) throws Exception;

    void removeSubscriptionTeamManager(String managerToRemoveEmail) throws Exception;

    List<String> getAllTeamManagersOwnedBy(String ownerToRemove);
}
