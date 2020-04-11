package Controller;

import Data.PlayerDbInMemory;
import Data.TeamManagerDb;
import Data.TeamManagerDbInMemory;
import Model.UsersTypes.Player;
import Model.UsersTypes.TeamManager;

public class TeamManagerController {
    private TeamManagerDb teamManagerDb;

    public TeamManagerController() {
        teamManagerDb = TeamManagerDbInMemory.getInstance();
    }


    public void createTeamManager(TeamManager teamManager) throws Exception {
        if(teamManager == null) {
            throw new NullPointerException();
        }
        teamManagerDb.createTeamManager(teamManager);
    }
}
