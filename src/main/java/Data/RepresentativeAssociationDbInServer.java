package Data;

import Model.UsersTypes.RepresentativeAssociation;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.sql.*;
import java.util.ArrayList;

public class RepresentativeAssociationDbInServer implements RepresentativeAssociationDb
{
    private static RepresentativeAssociationDbInServer ourInstance = new RepresentativeAssociationDbInServer();

    public static RepresentativeAssociationDbInServer getInstance() { return ourInstance; }

    @Override
    public void insertRepresentativeAssociation(RepresentativeAssociation representativeAssociation) throws Exception
    {
        if (representativeAssociation == null)
        {
            throw new NullPointerException("No RepresentativeAssociation been created");
        }
        Connection conn = DbConnector.getConnection();
        try
        {
            // the mysql insert statement
            String query = " insert into representative_association (email_address)"
                    + " values (?)";

            // create the mysql insert preparedStatement
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setString (1, representativeAssociation.getEmailAddress());

            // execute the preparedStatement
            preparedStmt.execute();
        }
        catch (Exception e)
        {
            throw new Exception("RepresentativeAssociation already exists in the system");
        }
        finally
        {
            conn.close();
        }
    }

    @Override
    public RepresentativeAssociation getRepresentativeAssociation(String representativeAssociationEmailAddress) throws Exception
    {
        if (representativeAssociationEmailAddress == null)
        {
            throw new NullPointerException("RepresentativeAssociation not found");
        }

        Connection conn = DbConnector.getConnection();

        // the mysql select statement
        String query = "select * from subscriber, representative_association where subscriber.email_address = representative_association.email_address and subscriber.email_address = \'" + representativeAssociationEmailAddress + "\'";

        // create the mysql select resultSet
        Statement preparedStmt = conn.createStatement();
        ResultSet rs = preparedStmt.executeQuery(query);

        // checking if ResultSet is empty
        if (rs.next() == false)
        {
            throw new NotFoundException("RepresentativeAssociation not found");
        }

        String userName = rs.getString("email_address");
        String password = rs.getString("password");
        Integer id = rs.getInt("id");
        String first_name = rs.getString("first_name");
        String last_name = rs.getString("last_name");
        String status = rs.getString("status");
        conn.close();

        RepresentativeAssociation representativeAssociation = new RepresentativeAssociation(userName, password, id, first_name, last_name);
        return representativeAssociation;
    }

    @Override
    public void removeRepresentativeAssociation(String email) throws Exception
    {
        //todo
        throw new NotImplementedException();
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
        String query = "delete from representative_association";

        // create the mysql delete Statement
        statement.executeUpdate(query);
        conn.close();
    }

    @Override
    public ArrayList<String> getAllRepresentativeAssociationEmailAddress() throws Exception
    {
        ArrayList<String> representativeAssociationEmailAddress = new ArrayList<>();

        Connection conn = DbConnector.getConnection();

        try
        {
            // the mysql select statement
            String query = "select email_address from representative_association";

            // create the mysql select resultSet
            Statement preparedStmt = conn.createStatement();
            ResultSet rs = preparedStmt.executeQuery(query);

            // checking if ResultSet is empty
            // checking if ResultSet is empty
            if (rs.next() != false)
            {
                representativeAssociationEmailAddress.add(rs.getString("email_address"));

                while (rs.next() != false)
                {
                    representativeAssociationEmailAddress.add(rs.getString("email_address"));
                }
            }
        }
        finally
        {
            conn.close();
        }
        return representativeAssociationEmailAddress;
    }


}
