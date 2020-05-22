package Data;

import Model.Enums.PermissionType;
import Model.Team;
import Model.UsersTypes.Subscriber;
import Model.UsersTypes.TeamManager;

import java.sql.SQLException;
import java.util.List;

public interface TeamManagerDb extends Db {

    void insertTeamManager(TeamManager teamManager) throws Exception;
    TeamManager getTeamManager(String teamManagerEmailAddress) throws Exception;


    void subscriptionTeamManager(String team, String teamOwnerId, Subscriber subscriber, List<PermissionType> permissionTypes) throws Exception;

    void removeSubscriptionTeamManager(String managerToRemoveEmail) throws Exception;

    List<String> getAllTeamManagersOwnedBy(String ownerToRemove) throws SQLException;

    void updateTeamManagerDetails(String teamManagerEmailAddress, String firstName, String lastName, List<PermissionType> permissionTypes) throws NotFoundException, SQLException;
}
