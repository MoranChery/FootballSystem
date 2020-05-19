package Data;

import Model.Enums.CoachRole;
import Model.Enums.QualificationCoach;
import Model.UsersTypes.Coach;

import java.util.HashMap;

public class CoachDbInMemory implements CoachDb {


    HashMap<String, Coach> coaches;

    private static CoachDbInMemory ourInstance = new CoachDbInMemory();

    public static CoachDbInMemory getInstance() {
        return ourInstance;
    }


    public CoachDbInMemory() {
        coaches = new HashMap<>();
    }

    @Override
    public Coach getCoach(String coachEmailAddress) throws Exception {
        if (!coaches.containsKey(coachEmailAddress)) {
            throw new NotFoundException("Coach not found");
        }
        return coaches.get(coachEmailAddress);
    }

    @Override
    public void insertCoach(Coach coach) throws Exception {
        if(coach == null) {
            throw new NullPointerException("bad input");
        }
        String emailAddress = coach.getEmailAddress();
        if(coaches.containsKey(emailAddress)) {
            throw new Exception("Coach already exists");
        }
        coaches.put(emailAddress, coach);
    }

    @Override
    public void removeCoach(Coach coachToRemove) throws Exception {
        coaches.remove(coachToRemove.getEmailAddress());
    }

    @Override
    public void updateCoachDetails(String coachEmailAddress, String firstName, String lastName, CoachRole coachRole, QualificationCoach qualificationCoach) throws NotFoundException {
        if(!coaches.containsKey(coachEmailAddress)){
            throw new NotFoundException("Player not found");
        }
        Coach coach = coaches.get(coachEmailAddress);
        coach.setFirstName(firstName);
        coach.setLastName(lastName);
        coach.setCoachRole(coachRole);
        coach.setQualificationCoach(qualificationCoach);
    }


    @Override
    public void deleteAll() {
        coaches.clear();
    }
}
