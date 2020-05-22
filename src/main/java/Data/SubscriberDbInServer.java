package Data;

import Model.Enums.CoachRole;
import Model.Enums.PlayerRole;
import Model.Enums.QualificationCoach;
import Model.Enums.Status;
import Model.UsersTypes.*;

import java.sql.*;
import java.util.Date;

public class SubscriberDbInServer implements SubscriberDb{
    private static SubscriberDbInServer ourInstance = new SubscriberDbInServer();

    public static SubscriberDbInServer getInstance() {
        return ourInstance;
    }
    @Override
    public void insertSubscriber(Subscriber subscriber) throws Exception {
        Connection conn = DbConnector.getConnection();
        try
        {
            // the mysql insert statement
            String query = " insert into subscriber (email_address,password,id,first_name,last_name,status)"
                    + " values (?,?,?,?,?,?)";

            // create the mysql insert preparedstatement
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setString (1, subscriber.getEmailAddress());
            preparedStmt.setString (2, subscriber.getPassword());
            preparedStmt.setInt (3, subscriber.getId());
            preparedStmt.setString (4, subscriber.getFirstName());
            preparedStmt.setString (5, subscriber.getLastName());
            Status status = subscriber.getStatus();
            preparedStmt.setString (6, status != null ? status.name(): Status.OFFLINE.name());

            // execute the preparedstatement
            preparedStmt.execute();
        } finally {
            conn.close();
        }
    }

    @Override
    public Subscriber getSubscriber(String username) throws Exception {
        if(username == null){
            throw new NullPointerException("bad input");
        }

        Connection conn = DbConnector.getConnection();

        String query = "select * from subscriber where subscriber.email_address = \'" + username + "\'";
        Statement preparedStmt = conn.createStatement();
        ResultSet rs = preparedStmt.executeQuery(query);

        // checking if ResultSet is empty
         if (rs.next() == false) { throw new NotFoundException("subscriber not found");  }

        String userName = rs.getString("email_address");
        String password = rs.getString("password");
        Integer id = rs.getInt("id");
        String first_name = rs.getString("first_name");
        String last_name = rs.getString("last_name");
        String status = rs.getString("status");
        return new Subscriber(userName,password,id,first_name,last_name, Status.valueOf(status));
    }

    @Override
    public boolean removeSubscriberFromDB(Subscriber subscriber) {
        return false;
    }

    @Override
    public void wantToEditPassword(String subscriberMail, String newPassword) throws Exception {

    }

    @Override
    public void wantToEditFirstName(String subscriberMail, String newFirstName) throws Exception {

    }

    @Override
    public void wantToEditLastName(String subscriberMail, String newLastName) throws Exception {

    }

    @Override
    public void logOut(String subscriberMail) throws Exception {

    }

    @Override
    public void changeStatusToOnline(Subscriber subscriber) throws Exception {

    }

    @Override
    public void deleteAll() throws SQLException {
        Connection conn = DbConnector.getConnection();
        Statement statement = conn.createStatement();
        statement.executeUpdate("delete from subscriber");
        conn.close();
    }

    public static void main(String[] args) throws Exception {
        Player player = new Player("player2@gmail.com","12345",111111,"player","last",new Date(), PlayerRole.GOALKEEPER);
        SubscriberDbInServer subscriberDbInServer = new SubscriberDbInServer();
//        subscriberDbInServer.insertSubscriber(player);
//        Subscriber subscriber = subscriberDbInServer.getSubscriber("player3@gmail.com");
//        System.out.println(subscriber.toString());
//        Coach coach = new Coach("coach@gmail.com", "1234",1, "first", "last", CoachRole.MAJOR, QualificationCoach.UEFA_A);
//        subscriberDbInServer.insertSubscriber(coach);

//        Player player3 = new Player("player3@gmail.com","12345",111111,"player","last",new Date(),PlayerRole.GOALKEEPER);
//        subscriberDbInServer.insertSubscriber(player3);

        String ownerEmail = "ownedByOwner@gmail.com";
        TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName");

//        subscriberDbInServer.insertSubscriber(teamOwner);
//
//        TeamManager teamManager = new TeamManager( "email@gmail.com","1111", 1, "firstTeamManager", "lastTeamManager", "owner@gmail.com");
//        subscriberDbInServer.insertSubscriber(teamManager);
        subscriberDbInServer.deleteAll();
    }
}
