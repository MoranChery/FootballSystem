package Data;

import Model.UsersTypes.Coach;
import Model.UsersTypes.TeamManager;

import java.util.HashMap;

public class CoachDbInMemory implements CoachDb {




    private static CoachDbInMemory ourInstance = new CoachDbInMemory();

    public static CoachDbInMemory getInstance() {
        return ourInstance;
    }

    HashMap<Integer,Coach> coaches;

    public CoachDbInMemory() {
        coaches = new HashMap<>();
    }

    @Override
    public Coach getCoach(Integer coachId) throws Exception {
        if (!coaches.containsKey(coachId)) {
            throw new NotFoundException("Coach not found");
        }
        return coaches.get(coachId);
    }

    @Override
    public void createCoach(Coach coach) throws Exception {
        Integer id = coach.getId();
        if(coaches.containsKey(id)) {
            throw new Exception("Coach already exists");
        }
        coaches.put(id, coach);
    }

    @Override
    public boolean removeCoach(Coach coachToRemove) throws Exception {
        if(!coaches.containsKey(coachToRemove.getUsername())){
           return false;
        }
        coaches.remove(coachToRemove);
        return true;
    }

    @Override
    public void deleteAll() {
        coaches.clear();
    }
}
