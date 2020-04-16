package Data;

import Model.UsersTypes.Coach;

public interface CoachDb extends Db {
    Coach getCoach(String coachEmailAddress) throws Exception;

    void createCoach(Coach currCoach) throws Exception;
}
