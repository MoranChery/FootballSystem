package Data;

import Model.Court;
import Model.Enums.PlayerRole;
import Model.Enums.Status;
import Model.Team;
import Model.UsersTypes.Player;

import java.sql.*;
import java.util.*;
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
        try{
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

            query = "select * from team where team.court = \'" + court_name + "\'";
            preparedStmt = conn.createStatement();
            rs = preparedStmt.executeQuery(query);
            List<String> teams = new ArrayList<>();
            while(rs.next()){
                String currTeam = rs.getString("team_name");
                teams.add(currTeam);
            }
            court.setTeams(teams);

            return court;
        } finally {
            conn.close();
        }
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
    public void updateCourtDetails(String courtName, String courtCity) throws NotFoundException, SQLException {
        Connection conn = DbConnector.getConnection();
        try{
            String query = "UPDATE court SET court_city = \'" + courtCity + "\'  WHERE court.court_name =  \'"+  courtName + "\'" ;
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.executeUpdate();
        } finally {
            conn.close();
        }
    }

    @Override
    public List<String> getTeams(String courtName) throws SQLException {
        Connection conn = DbConnector.getConnection();
        try{
            String query = "select * from  team where team.court = \'" + courtName + "\'";

            Statement preparedStmt = conn.createStatement();
            ResultSet rs = preparedStmt.executeQuery(query);
            List<String> teamsOfCourt = new ArrayList<> ();

            while(rs.next()){
                String team = rs.getString("team_name");
                teamsOfCourt.add(team);
            }
            return teamsOfCourt;
        } finally {
            conn.close();
        }
    }

    @Override
    public void deleteAll() throws SQLException {
        Connection conn = DbConnector.getConnection();
        try{
            Statement statement = conn.createStatement();
            statement.executeUpdate("delete from court");
        } finally {
            conn.close();
        }
    }

    @Override
    public List<String> getAllCourtsNames() throws SQLException {
        Connection conn = DbConnector.getConnection();
        try{
            String query = "select * from  court";

            Statement preparedStmt = conn.createStatement();
            ResultSet rs = preparedStmt.executeQuery(query);
            List<String> courtsNames = new ArrayList<> ();

            while(rs.next()){
                String court = rs.getString("court_name");
                courtsNames.add(court);
            }
            return courtsNames;
        } finally {
            conn.close();
        }
    }

    public static void main(String[] args) throws Exception {
        CourtDbInServer courtDbInServer  = new CourtDbInServer();
        Court court1 = new Court("courtName1", "courtCity");
        Court court2 = new Court("courtName2", "courtCity");
        Court court3 = new Court("courtName3", "courtCity");

        courtDbInServer.insertCourt(court1);
        courtDbInServer.insertCourt(court2);
        courtDbInServer.insertCourt(court3);
        System.out.println(courtDbInServer.getAllCourtsNames());
    }
}
