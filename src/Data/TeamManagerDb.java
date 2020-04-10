package Data;

import Model.Team;
import Model.UsersTypes.Subscriber;
import Model.UsersTypes.TeamManager;

public interface TeamManagerDb {

    void createTeamManager(TeamManager teamManager) throws Exception;
    TeamManager getTeamManager(Integer teamManagerId) throws Exception;


    void subscriptionTeamManager(Team team, Integer teamOwnerId, Subscriber subscriber) throws Exception;

    void removeSubscriptionTeamManager(Integer managerToRemove) throws Exception;
}
