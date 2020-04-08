package Data;

import Model.Court;

public interface CourtDb {
    Court getCourt(String courtName) throws Exception;

    void createCourt(Court court) throws Exception;
}
