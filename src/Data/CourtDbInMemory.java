package Data;

import Model.Court;
import Model.UsersTypes.Player;

import java.util.HashMap;
import java.util.Map;

public class CourtDbInMemory implements CourtDb {
    private Map<String,Court> courts;

    private static CourtDbInMemory ourInstance = new CourtDbInMemory();

    public static CourtDbInMemory getInstance() {
        return ourInstance;
    }

    public void setCourts(HashMap<String, Court> courts) {
        this.courts = courts;
    }

    @Override
    public Court getCourt(String courtName) throws Exception {
        if (!courts.containsKey(courtName)) {
            throw new NotFoundException("Court not found");
        }
        return courts.get(courtName);
    }

    @Override
    public void createCourt(Court court) throws Exception {
        if (courts.containsKey(court.getCourtName())) {
            throw new Exception("Court already exists");
        }
        courts.put(court.getCourtName(), court);
    }
}
