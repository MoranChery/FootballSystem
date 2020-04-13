package Data;

import Model.Team;
import Model.UsersTypes.Subscriber;
import Model.UsersTypes.TeamManager;

public interface TeamManagerDb extends Db {

    void createTeamManager(TeamManager teamManager) throws Exception;
    TeamManager getTeamManager(String teamManagerEmailAddress) throws Exception;


    void subscriptionTeamManager(Team team, Integer teamOwnerId, Subscriber subscriber) throws Exception;

    void removeSubscriptionTeamManager(Integer managerToRemove) throws Exception;
}
