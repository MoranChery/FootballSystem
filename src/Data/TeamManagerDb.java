package Data;

import Model.Team;
import Model.UsersTypes.Subscriber;
import Model.UsersTypes.TeamManager;

public interface TeamManagerDb extends Db {

    void createTeamManager(TeamManager teamManager) throws Exception;
    TeamManager getTeamManager(String teamManagerEmailAddress) throws Exception;


    void subscriptionTeamManager(Team team, String teamOwnerId, Subscriber subscriber) throws Exception;

    void removeSubscriptionTeamManager(String managerToRemoveEmail) throws Exception;
}
