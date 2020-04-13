package Data;

import Model.UsersTypes.Coach;
import Model.UsersTypes.TeamManager;

import java.util.HashMap;

public class CoachDbInMemory implements CoachDb {


    HashMap<String,Coach> coaches;

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
    public void createCoach(Coach coach) throws Exception {
        String emailAddress = coach.getEmailAddress();
        if(coaches.containsKey(emailAddress)) {
            throw new Exception("Coach already exists");
        }
        coaches.put(emailAddress, coach);
    }

    @Override
    public void deleteAll() {
        coaches.clear();
    }
}
