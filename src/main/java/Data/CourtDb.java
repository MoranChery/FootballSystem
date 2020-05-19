package Data;

import Model.Court;
import Model.Team;

public interface CourtDb extends Db {
    Court getCourt(String courtName) throws Exception;

    void insertCourt(Court court) throws Exception;

    void addTeamToCourt(Court court, Team team) throws Exception;

    void updateCourtDetails(String courtName, String courtCity) throws NotFoundException;
}
