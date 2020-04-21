package Data;

import Model.Court;
import Model.Team;

public interface CourtDb extends Db {
    Court getCourt(String courtName) throws Exception;

    void createCourt(Court court) throws Exception;

    void addTeamToCourt(Court court, Team team) throws Exception;
}
