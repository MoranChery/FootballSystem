package Data;

import Model.UsersTypes.Coach;

public interface CoachDb {
    Coach getCoach(Integer coachId) throws Exception;

    void createCoach(Coach currCoach) throws Exception;
}
