package Data;

import Model.UsersTypes.RepresentativeAssociation;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RepresentativeAssociationDbInServer implements RepresentativeAssociationDb
{
    private static RepresentativeAssociationDbInServer ourInstance = new RepresentativeAssociationDbInServer();

    public static RepresentativeAssociationDbInServer getInstance() { return ourInstance; }

    @Override
    public void insertRepresentativeAssociation(RepresentativeAssociation representativeAssociation) throws Exception
    {

    }

    @Override
    public RepresentativeAssociation getRepresentativeAssociation(String representativeAssociationEmailAddress) throws Exception
    {
        return null;
    }

    @Override
    public void removeRepresentativeAssociation(String email) throws Exception
    {
        //todo
        throw new NotImplementedException();
    }

    @Override
    public void deleteAll()
    {
        Connection conn = DbConnector.getConnection();
        try
        {
            // the mysql delete statement
            String query = " delete from representative_association";

            // create the mysql delete preparedStatement
            PreparedStatement preparedStmt = conn.prepareStatement(query);

            // execute the preparedStatement
            preparedStmt.execute();
        }
        catch (SQLException throwables)
        {
            throwables.printStackTrace();
        }
        finally
        {
            try
            {
                conn.close();
            }
            catch (SQLException throwables)
            {
                throwables.printStackTrace();
            }
        }
    }
}
