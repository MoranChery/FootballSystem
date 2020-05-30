package Data;

import Model.Enums.CoachRole;
import Model.Enums.PlayerRole;
import Model.Enums.QualificationCoach;
import Model.Enums.Status;
import Model.UsersTypes.*;
import org.junit.Assert;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

public class SubscriberDbInServer implements SubscriberDb{
    private static SubscriberDbInServer ourInstance = new SubscriberDbInServer();

    public static SubscriberDbInServer getInstance() {
        return ourInstance;
    }
    @Override
    public void insertSubscriber(Subscriber subscriber) throws Exception {
        if(subscriber == null){
            throw new NullPointerException("bad input");
        }
        Connection conn = DbConnector.getConnection();
        try
        {
            // the mysql insert statement
            String query = " insert into subscriber (email_address,password,id,first_name,last_name,status, want_alert_in_mail)"
                    + " values (?,?,?,?,?,?,?)";

            // create the mysql insert preparedstatement
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setString (1, subscriber.getEmailAddress());
            preparedStmt.setString (2, subscriber.getPassword());
            preparedStmt.setInt (3, subscriber.getId());
            preparedStmt.setString (4, subscriber.getFirstName());
            preparedStmt.setString (5, subscriber.getLastName());
            Status status = subscriber.getStatus();
            preparedStmt.setString (6, Status.OFFLINE.name());

            int bit_want_alert_in_mail = 0;
            if (subscriber.isWantAlertInMail())
            {
                bit_want_alert_in_mail = 1;
            }
            preparedStmt.setInt (7, bit_want_alert_in_mail);



            // execute the preparedstatement
            preparedStmt.execute();

        } catch(SQLIntegrityConstraintViolationException e) {
            throw new Exception("subscriber already exists");
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
        try{
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
            int bit_want_alert_in_mail = rs.getInt("want_alert_in_mail");

            Subscriber subscriber = new Subscriber(userName,password,id,first_name,last_name, Status.valueOf(status));
            if(bit_want_alert_in_mail == 1)
            {
                subscriber.setWantAlertInMail(true);
            }

            return subscriber;
        } finally {
            conn.close();
        }
    }

    @Override
    public boolean removeSubscriberFromDB(Subscriber subscriber) {
        return false;
    }

    @Override
    public void wantToEditPassword(String subscriberMail, String newPassword) throws Exception {
        Connection conn = DbConnector.getConnection();
        try{
            String query = "UPDATE football_system_db.subscriber SET football_system_db.subscriber.password = \'" + newPassword + "\' WHERE email_address = \'"+ subscriberMail + "\'" ;
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.executeUpdate();
        } finally {
            conn.close();
        }
    }

    @Override
    public void wantToEditFirstName(String subscriberMail, String newFirstName) throws Exception {
        Connection conn = DbConnector.getConnection();
        try{
            String query = "UPDATE football_system_db.subscriber SET football_system_db.subscriber.first_name = \'" + newFirstName + "\' WHERE email_address = \'"+ subscriberMail + "\'" ;
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.executeUpdate();
        } finally {
            conn.close();
        }
    }

    @Override
    public void wantToEditLastName(String subscriberMail, String newLastName) throws Exception {
        Connection conn = DbConnector.getConnection();
        try{
            String query = "UPDATE football_system_db.subscriber SET football_system_db.subscriber.last_name = \'" + newLastName + "\' WHERE email_address = \'"+ subscriberMail + "\'" ;
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.executeUpdate();
        } finally {
            conn.close();
        }
    }

    @Override
    public void logOut(String subscriberMail) throws Exception {
        Connection conn = DbConnector.getConnection();

        String query = "UPDATE football_system_db.subscriber  SET football_system_db.subscriber.status = \'" + Status.OFFLINE.name() +  "\'  WHERE email_address =  \'"+ subscriberMail + "\'" ;
        PreparedStatement preparedStmt = conn.prepareStatement(query);
        preparedStmt.executeUpdate();
        conn.close();
    }

    @Override
    public void changeStatusToOnline(Subscriber subscriber) throws Exception {
        Connection conn = DbConnector.getConnection();

        String query = "UPDATE football_system_db.subscriber  SET football_system_db.subscriber.status = \'" + Status.ONLINE.name() +  "\'  WHERE email_address =  \'"+ subscriber.getEmailAddress() + "\'" ;
        PreparedStatement preparedStmt = conn.prepareStatement(query);
        preparedStmt.executeUpdate();
        conn.close();
    }

    @Override
    public void deleteAll() throws SQLException {
        Connection conn = DbConnector.getConnection();
        try{
            Statement statement = conn.createStatement();
            statement.executeUpdate("delete from subscriber");
        } finally {
            conn.close();
        }
    }

    @Override
    public void setSubscriberWantAlert(String userMail) throws Exception
    {
        Connection conn = DbConnector.getConnection();
        try
        {
            getSubscriber(userMail);

//            int bit_want_alert_in_mail = 1;

            // the mysql update statement
            String query = " update subscriber "
                    + "set want_alert_in_mail = 1 "
                    + "where email_address = \'" + userMail + "\'";

            // create the mysql insert preparedStatement
            PreparedStatement preparedStmt = conn.prepareStatement(query);

            // execute the preparedStatement
            preparedStmt.execute();
        }
        catch (NotFoundException e)
        {
            throw new Exception("Subscriber not found");
        }
        finally
        {
            conn.close();
        }
    }

    public static void main(String[] args) throws Exception {
        SubscriberDbInServer subscriberDbInServer = new SubscriberDbInServer();
        subscriberDbInServer.deleteAll();

        Player player = new Player("player2@gmail.com","12345",111111,"player","last",new Date(), PlayerRole.GOALKEEPER);
        subscriberDbInServer.insertSubscriber(player);

        Player playerYesMail = new Player("playerYesMail@gmail.com","12345",111111,"player","last",new Date(), PlayerRole.GOALKEEPER);
        playerYesMail.setWantAlertInMail(true);
        subscriberDbInServer.insertSubscriber(playerYesMail);

        Subscriber subscriberNoMail = subscriberDbInServer.getSubscriber("player2@gmail.com");
        Subscriber subscriberYesMail = subscriberDbInServer.getSubscriber("playerYesMail@gmail.com");

        Assert.assertEquals(false, subscriberNoMail.isWantAlertInMail());
        Assert.assertEquals(true, subscriberYesMail.isWantAlertInMail());

        subscriberDbInServer.setSubscriberWantAlert("player2@gmail.com");
        subscriberDbInServer.setSubscriberWantAlert("playerYesMail@gmail.com");

        subscriberNoMail = subscriberDbInServer.getSubscriber("player2@gmail.com");
        subscriberYesMail = subscriberDbInServer.getSubscriber("playerYesMail@gmail.com");

        Assert.assertEquals(true, subscriberNoMail.isWantAlertInMail());
        Assert.assertEquals(true, subscriberYesMail.isWantAlertInMail());

//        Subscriber subscriber = subscriberDbInServer.getSubscriber("player3@gmail.com");
//        System.out.println(subscriber.toString());
//        Coach coach = new Coach("coach@gmail.com", "1234",1, "first", "last", CoachRole.MAJOR, QualificationCoach.UEFA_A);
//        subscriberDbInServer.insertSubscriber(coach);

//        Player player3 = new Player("player3@gmail.com","12345",111111,"player","last",new Date(),PlayerRole.GOALKEEPER);
//        subscriberDbInServer.insertSubscriber(player3);

//        String ownerEmail = "ownedByOwner@gmail.com";
//        TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName");

//        subscriberDbInServer.insertSubscriber(teamOwner);
//
//        TeamManager teamManager = new TeamManager( "email@gmail.com","1111", 1, "firstTeamManager", "lastTeamManager", "owner@gmail.com");
//        subscriberDbInServer.insertSubscriber(teamManager);
//        subscriberDbInServer.deleteAll();
    }

}
