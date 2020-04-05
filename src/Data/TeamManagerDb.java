package Data;

import Model.UsersTypes.TeamManager;

public interface TeamManagerDb {

    void createTeamManager(TeamManager teamManager) throws Exception;
    TeamManager getTeamManager(Integer teamManagerId) throws Exception;


}
