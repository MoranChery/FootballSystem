package Data;

import Model.Enums.CoachRole;
import Model.Enums.QualificationCoach;
import Model.UsersTypes.Coach;

import java.sql.SQLException;

public interface CoachDb extends Db {
    Coach getCoach(String coachEmailAddress) throws Exception;
    void insertCoach(Coach currCoach) throws Exception;
    void removeCoach(Coach coachToRemove) throws Exception;

    void updateCoachDetails(String coachEmailAddress, String firstName, String lastName, CoachRole coachRole, QualificationCoach qualificationCoach) throws NotFoundException, SQLException;
}
