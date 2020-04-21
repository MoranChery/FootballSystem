package Controller;

import Data.CoachDb;
import Data.CoachDbInMemory;
import Data.PlayerDb;
import Data.PlayerDbInMemory;
import Model.UsersTypes.Coach;
import Model.UsersTypes.Player;

public class CoachController {

    private CoachDb coachDb;

    public CoachController() {
        coachDb = CoachDbInMemory.getInstance();
    }

    public void createCoach(Coach coach) throws Exception {
        if(coach == null) {
            throw new NullPointerException("bad input");
        }
        coachDb.createCoach(coach);
    }
}
