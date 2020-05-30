package Data;

import Model.Court;
import Model.Team;
import Model.UsersTypes.Coach;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CourtDbInMemory implements CourtDb {
    private Map<String,Court> courts;

    private CourtDbInMemory() {
        courts = new HashMap<>();
        Court coach1 = new Court("courtName1", "city1");
        Court coach2 = new Court("courtName2", "city2");
        courts.put(coach1.getCourtName(), coach1);
        courts.put(coach2.getCourtName(), coach2);

    }

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
    public void insertCourt(Court court) throws Exception {
        if (courts.containsKey(court.getCourtName())) {
            throw new Exception("Court already exists");
        }
        courts.put(court.getCourtName(), court);
    }


    public void addTeamToCourt(Court court, Team team) throws Exception {
        if (!courts.containsKey(court.getCourtName())) {
            throw new Exception("Court not exists");
        }
        Court fromDb = courts.get(court.getCourtName());
        List<String>  teams = court.getTeams();
        teams.add(team.getTeamName());
    }

    public void updateCourtDetails(String courtName, String courtCity) throws NotFoundException {
        if(!courts.containsKey(courtName)){
            throw new NotFoundException("Court not found");
        }
        Court court = courts.get(courtName);
        court.setCourtCity(courtCity);
    }

    @Override
    public List<String> getTeams(String teamName) throws SQLException {
        return null;
    }

    @Override
    public List<String> getAllCourtsNames() throws SQLException {
        return null;
    }


    @Override
    public void deleteAll() {
        courts.clear();
    }
}
