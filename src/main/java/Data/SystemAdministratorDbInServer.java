package Data;

import Model.Enums.Status;
import Model.UsersTypes.RepresentativeAssociation;
import Model.UsersTypes.SystemAdministrator;

import java.sql.*;
import java.util.Set;

public class SystemAdministratorDbInServer implements SystemAdministratorDb{

    private static Data.SystemAdministratorDbInServer ourInstance = new Data.SystemAdministratorDbInServer();

    public static Data.SystemAdministratorDbInServer getInstance() {
        return ourInstance;
    }


    @Override
    public void insertSystemAdministrator(SystemAdministrator systemAdministrator) throws Exception {
        if(systemAdministrator == null){
            throw new NullPointerException("bad input");
        }
        Connection conn = DbConnector.getConnection();
        try
        {
            // the mysql insert statement
            String query = " insert into football_system_db.system_administrator (email_address)"
                    + " values (?)";

            // create the mysql insert preparedstatement
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setString (1, systemAdministrator.getEmailAddress());

            // execute the preparedstatement
            preparedStmt.execute();
        } finally {
            conn.close();
        }
    }

    @Override
    public SystemAdministrator getSystemAdministrator(String emailAddress) throws Exception {
        if (emailAddress == null)
        {
            throw new NullPointerException("SystemAdministrator not found");
        }

        Connection conn = DbConnector.getConnection();

        // the mysql select statement
        String query = "select * from subscriber, football_system_db.system_administrator where subscriber.email_address = system_administrator.email_address and subscriber.email_address = \'" + emailAddress + "\'";

        // create the mysql select resultSet
        Statement preparedStmt = conn.createStatement();
        ResultSet rs = preparedStmt.executeQuery(query);

        // checking if ResultSet is empty
        if (rs.next() == false)
        {
            throw new NotFoundException("SystemAdministrator not found");
        }

        String userName = rs.getString("email_address");
        String password = rs.getString("password");
        Integer id = rs.getInt("id");
        String first_name = rs.getString("first_name");
        String last_name = rs.getString("last_name");
        String status = rs.getString("status");
        conn.close();

        SystemAdministrator representativeAssociation = new SystemAdministrator(userName, password, id, first_name, last_name);
        representativeAssociation.setStatus(Status.valueOf(status));
        return representativeAssociation;    }

    @Override
    public boolean removeSystemAdministratorFromDB(SystemAdministrator systemAdministrator) {
        return false;
    }

    @Override
    public Set<String> getAllSystemAdministrators() {
        return null;
    }

    @Override
    public void deleteAll() throws SQLException {

    }
}
