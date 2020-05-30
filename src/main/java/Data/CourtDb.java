package Data;

import Model.Court;
import Model.Team;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

public interface CourtDb extends Db {
    Court getCourt(String courtName) throws Exception;

    void insertCourt(Court court) throws Exception;

    void updateCourtDetails(String courtName, String courtCity) throws NotFoundException, SQLException;

    List<String> getTeams(String courtName) throws SQLException;

    List<String> getAllCourtsNames() throws SQLException;
}
