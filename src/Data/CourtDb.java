package Data;

import Model.Court;

public interface CourtDb extends Db {
    Court getCourt(String courtName) throws Exception;

    void createCourt(Court court) throws Exception;
}
