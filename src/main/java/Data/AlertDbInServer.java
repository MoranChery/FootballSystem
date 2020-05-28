package Data;

import Model.Alert;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AlertDbInServer implements AlertDb
{
    private static AlertDbInServer ourInstance = new AlertDbInServer();

    public static AlertDbInServer getInstance() { return ourInstance; }

    @Override
    public void insertAlertInDb(String email, Alert alert) throws Exception
    {
        Connection conn = DbConnector.getConnection();
        try
        {
            // the mysql insert statement
            String query = " insert into alert (email_address, alert_id, msg_header, msg_body)"
                    + " values (?,?,?,?)";

            // create the mysql insert preparedStatement
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setString (1, email);
            preparedStmt.setString (2, alert.getAlertId());
            preparedStmt.setString (3, alert.getMsgHeader());
            preparedStmt.setString (4, alert.getMsgBody());

            // execute the preparedStatement
            preparedStmt.execute();
        }
        catch (Exception e)
        {
            throw new Exception("Alert already exists in the system");
        }
        finally
        {
            conn.close();
        }
    }

    @Override
    public Alert getAlert(String email, String alertID) throws Exception
    {
        if (email == null || alertID == null)
        {
            throw new Exception("Alert not found");
        }

        Connection conn = DbConnector.getConnection();

        // the mysql select statement
        String query = "select * from alert where email_address = \'" + email + "\' and alert_id = \'" + alertID + "\'";

        // create the mysql select resultSet
        Statement preparedStmt = conn.createStatement();
        ResultSet rs = preparedStmt.executeQuery(query);

        // checking if ResultSet is empty
        if (rs.next() == false)
        {
            throw new NotFoundException("Alert not found");
        }
        String email_address = rs.getString("email_address");
        String alert_id = rs.getString("alert_id");
        String msg_header = rs.getString("msg_header");
        String msg_body = rs.getString("msg_body");

        conn.close();

        Alert alert = new Alert(msg_header, msg_body);
        alert.setAlertId(alert_id);
        return alert;
    }

    @Override
    public List<Alert> getAlertsForUser(String emailAddress) throws Exception
    {
        List<Alert> alertsListForUser = null;
        Alert alert;
        String alert_id;
        String msg_header;
        String msg_body;

        Connection conn = DbConnector.getConnection();

        try
        {
            // the mysql select statement
            String query = "select alert_id, msg_header, msg_body from alert where email_address = \'" + emailAddress + "\'";

            // create the mysql select resultSet
            Statement preparedStmt = conn.createStatement();
            ResultSet rs = preparedStmt.executeQuery(query);

            // checking if ResultSet is empty
            if (rs.next() != false)
            {
                alertsListForUser = new ArrayList<>();

                alert_id = rs.getString("alert_id");
                msg_header = rs.getString("msg_header");
                msg_body = rs.getString("msg_body");

                alert = new Alert(msg_header, msg_body);
                alert.setAlertId(alert_id);

                alertsListForUser.add(alert);

                while (rs.next() != false)
                {
                    alert_id = rs.getString("alert_id");
                    msg_header = rs.getString("msg_header");
                    msg_body = rs.getString("msg_body");

                    alert = new Alert(msg_header, msg_body);
                    alert.setAlertId(alert_id);

                    alertsListForUser.add(alert);
                }
            }
        }
        finally
        {
            conn.close();
        }
        return alertsListForUser;
    }

    @Override
    public boolean haveAlertInDB(String email) throws Exception
    {
        if(getAlertsForUser(email) != null)
        {
            return true;
        }
        return false;
    }

    @Override
    public void removeUserFromTheAlertDB(String userMail) throws Exception
    {
        Connection conn = DbConnector.getConnection();

        // the mysql delete statement
        String query = "delete from alert where email_address = \'" + userMail + "\'";

        // create the mysql select resultSet
        Statement preparedStmt = conn.createStatement();

        preparedStmt.executeUpdate(query);

        conn.close();
    }

    @Override
    public void removeAllTheAlertTheUserHave(String userMail) throws Exception
    {
        Connection conn = DbConnector.getConnection();

        // the mysql delete statement
        String query = "delete from alert where email_address = \'" + userMail + "\'";

        // create the mysql select resultSet
        Statement preparedStmt = conn.createStatement();

        preparedStmt.executeUpdate(query);

        conn.close();
    }

    @Override
    public void removeAlertFromAllUsersInTheDB(String alertID) throws Exception
    {

    }

    @Override
    public void removeAlertFromTheUserListOfAlerts(String userMail, String alertID) throws Exception
    {

    }

    @Override
    public void deleteAll() throws SQLException
    {
        Connection conn = DbConnector.getConnection();
        Statement statement = conn.createStatement();
        /* TRUNCATE is faster than DELETE since
         * it does not generate rollback information and does not
         * fire any delete triggers
         */

        // the mysql delete statement
        String query = "delete from alert";

        // create the mysql delete Statement
        statement.executeUpdate(query);
        conn.close();
    }
}
