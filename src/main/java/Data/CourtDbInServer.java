package Data;

import Model.Court;
import Model.Enums.PlayerRole;
import Model.Enums.Status;
import Model.Team;
import Model.UsersTypes.Player;

import java.sql.*;
import java.util.Date;

public class CourtDbInServer implements CourtDb {
    private static CourtDbInServer ourInstance = new CourtDbInServer();

    public static CourtDbInServer getInstance() {
        return ourInstance;
    }


    @Override
    public Court getCourt(String courtName) throws Exception {
        if(courtName == null) {
            throw new NullPointerException("bad input");
        }

        Connection conn = DbConnector.getConnection();

        String query = "select * from court where court.court_name = \'" + courtName + "\'";
        Statement preparedStmt = conn.createStatement();
        ResultSet rs = preparedStmt.executeQuery(query);

        // checking if ResultSet is empty
        if (rs.next() == false) {
            throw new NotFoundException("Court not found");
        }

        String court_name = rs.getString("court_name");
        String court_city = rs.getString("court_city");
        Court court = new Court(court_name,court_city);
        conn.close();
        return court;
    }

    @Override
    public void insertCourt(Court court) throws Exception {
        if(court == null){
            throw new NullPointerException("bad input");
        }
        Connection conn = DbConnector.getConnection();
        try
        {
            // the mysql insert statement
            String query = " insert into court (court_name,court_city)"
                    + " values (?,?)";

            // create the mysql insert preparedstatement
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setString (1, court.getCourtName());
            preparedStmt.setString (2, court.getCourtCity());

            // execute the preparedstatement
            preparedStmt.execute();
        } finally {
            conn.close();
        }
    }

    @Override
    public void addTeamToCourt(Court court, Team team) throws Exception {

    }

    @Override
    public void updateCourtDetails(String courtName, String courtCity) throws NotFoundException {

    }

    @Override
    public void deleteAll() throws SQLException {
        Connection conn = DbConnector.getConnection();
        Statement statement = conn.createStatement();
        /* TRUNCATE is faster than DELETE since

         * it does not generate rollback information and does not

         * fire any delete triggers

         */

        statement.executeUpdate("delete from court");
        conn.close();
    }

    public static void main(String[] args) throws Exception {
        CourtDbInServer courtDbInServer  = new CourtDbInServer();
        Court court = new Court("courtName", "courtCity");

        courtDbInServer.insertCourt(court);
    }
}
