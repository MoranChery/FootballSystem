package Controller;

import Data.PlayerDb;
import Data.PlayerDbInMemory;
import Data.TeamOwnerDb;
import Data.TeamOwnerDbInMemory;
import Model.UsersTypes.Player;
import Model.UsersTypes.TeamOwner;

public class TeamOwnerController {

    private Data.TeamOwnerDb TeamOwnerDb;

    public TeamOwnerController() {
        TeamOwnerDb = TeamOwnerDbInMemory.getInstance();
    }

    public void createTeamOwner(TeamOwner teamOwner) throws Exception {
        if(teamOwner == null) {
            throw new NullPointerException("bad input");
        }
        TeamOwnerDb.createTeamOwner(teamOwner);
    }
}
