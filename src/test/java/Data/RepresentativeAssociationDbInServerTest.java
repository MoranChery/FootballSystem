package Data;

import Controller.BaseEmbeddedSQL;
import Model.UsersTypes.RepresentativeAssociation;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RepresentativeAssociationDbInServerTest
{
    private RepresentativeAssociationDbInServer representativeAssociationDbInServer = RepresentativeAssociationDbInServer.getInstance();

    private SubscriberDbInServer subscriberDbInServer = SubscriberDbInServer.getInstance();

    @Before
    public void init() throws SQLException
    {
        final List<Db> dbs = new ArrayList<>();

        dbs.add(RepresentativeAssociationDbInServer.getInstance());
//        dbs.add(RoleDbInMemory.getInstance());
        dbs.add(SubscriberDbInServer.getInstance());
//        dbs.add(LeagueDbInServer.getInstance());
//        dbs.add(SeasonDbInServer.getInstance());
//        dbs.add(SeasonLeagueDbInServer.getInstance());
//        dbs.add(JudgeDbInServer.getInstance());
//        dbs.add(JudgeSeasonLeagueDbInServer.getInstance());
//
        for (Db db : dbs)
        {
            db.deleteAll();
        }
    }

    //region insertRepresentativeAssociation_Tests
    @Test
    public void insertRepresentativeAssociation_null() throws Exception
    {
        RepresentativeAssociation representativeAssociation1 = null;

        try
        {
            representativeAssociationDbInServer.insertRepresentativeAssociation(representativeAssociation1);
        }
        catch (Exception e)
        {
            Assert.assertTrue(e instanceof NullPointerException);
            Assert.assertEquals("No RepresentativeAssociation been created", e.getMessage());
        }
    }

    @Test
    public void insertRepresentativeAssociation_legal() throws Exception
    {
        RepresentativeAssociation representativeAssociation1 = new RepresentativeAssociation("representativeAssociation1@gmail.com","password", 12345, "firstName", "lastName");

        try
        {
            subscriberDbInServer.insertSubscriber(representativeAssociation1);
            representativeAssociationDbInServer.insertRepresentativeAssociation(representativeAssociation1);
            Assert.assertEquals("representativeAssociation1@gmail.com", representativeAssociation1.getEmailAddress());
            Assert.assertEquals("representativeAssociation1@gmail.com", representativeAssociationDbInServer.getRepresentativeAssociation("representativeAssociation1@gmail.com").getEmailAddress());
        }
        catch (Exception e)
        {
            Assert.assertEquals("No RepresentativeAssociation been created", e.getMessage());
        }
    }

    @Test
    public void insertRepresentativeAssociation_subscriberExists() throws Exception
    {
        RepresentativeAssociation representativeAssociation1 = new RepresentativeAssociation("representativeAssociation1@gmail.com","password", 12345, "firstName", "lastName");
        RepresentativeAssociation representativeAssociation2 = new RepresentativeAssociation("representativeAssociation1@gmail.com","password", 12345, "firstName", "lastName");

        try
        {
            subscriberDbInServer.insertSubscriber(representativeAssociation1);
            representativeAssociationDbInServer.insertRepresentativeAssociation(representativeAssociation1);
            subscriberDbInServer.insertSubscriber(representativeAssociation2);
        }
        catch (Exception e)
        {
            Assert.assertEquals("Duplicate entry '" + representativeAssociation2.getEmailAddress() + "' for key 'subscriber.PRIMARY'", e.getMessage());
        }
    }

    @Test
    public void insertRepresentativeAssociation_representativeAssociationExists() throws Exception
    {
        RepresentativeAssociation representativeAssociation1 = new RepresentativeAssociation("representativeAssociation1@gmail.com","password", 12345, "firstName", "lastName");
        RepresentativeAssociation representativeAssociation2 = new RepresentativeAssociation("representativeAssociation1@gmail.com","password", 12345, "firstName", "lastName");

        try
        {
            subscriberDbInServer.insertSubscriber(representativeAssociation1);
            representativeAssociationDbInServer.insertRepresentativeAssociation(representativeAssociation1);
            representativeAssociationDbInServer.insertRepresentativeAssociation(representativeAssociation2);
        }
        catch (Exception e)
        {
            Assert.assertEquals("RepresentativeAssociation already exists in the system", e.getMessage());
        }
    }
    //endregion

    //region getRepresentativeAssociation_Tests
    @Test
    public void getRepresentativeAssociation_null() throws Exception
    {
        RepresentativeAssociation representativeAssociation1 = new RepresentativeAssociation("representativeAssociation1@gmail.com","password", 12345, "firstName", "lastName");

        String representativeAssociationNameNull = null;
        try
        {
            subscriberDbInServer.insertSubscriber(representativeAssociation1);
            representativeAssociationDbInServer.insertRepresentativeAssociation(representativeAssociation1);

            representativeAssociationDbInServer.getRepresentativeAssociation(representativeAssociationNameNull);
        }
        catch (Exception e)
        {
            Assert.assertEquals("RepresentativeAssociation not found", e.getMessage());
        }
    }

    @Test
    public void getRepresentativeAssociation_notExists() throws Exception
    {
        RepresentativeAssociation representativeAssociation1 = new RepresentativeAssociation("representativeAssociation1@gmail.com","password", 12345, "firstName", "lastName");

        String representativeAssociationNameNotExists = "representativeAssociationNameNotExists";
        try
        {
            subscriberDbInServer.insertSubscriber(representativeAssociation1);
            representativeAssociationDbInServer.insertRepresentativeAssociation(representativeAssociation1);

            representativeAssociationDbInServer.getRepresentativeAssociation(representativeAssociationNameNotExists);
        }
        catch (Exception e)
        {
            Assert.assertEquals("RepresentativeAssociation not found", e.getMessage());
        }
    }

    @Test
    public void getRepresentativeAssociation_exists() throws Exception
    {
        RepresentativeAssociation representativeAssociation1 = new RepresentativeAssociation("representativeAssociation1@gmail.com","password", 12345, "firstName", "lastName");

        String representativeAssociationName = representativeAssociation1.getEmailAddress();

        RepresentativeAssociation returnRepresentativeAssociation1 = null;
        try
        {
            subscriberDbInServer.insertSubscriber(representativeAssociation1);
            representativeAssociationDbInServer.insertRepresentativeAssociation(representativeAssociation1);

            returnRepresentativeAssociation1 = representativeAssociationDbInServer.getRepresentativeAssociation(representativeAssociationName);

            Assert.assertEquals("representativeAssociation1@gmail.com", returnRepresentativeAssociation1.getEmailAddress());
        }
        catch (Exception e)
        {
            Assert.assertEquals("RepresentativeAssociation not found", e.getMessage());
        }
    }
    //endregion

    //region getAllRepresentativeAssociationEmailAddress_Tests
    @Test
    public void getAllRepresentativeAssociationEmailAddress_noRepresentativeAssociation()
    {
        ArrayList<String> representativeAssociationEmailAddress = new ArrayList<>();

        try
        {
            representativeAssociationEmailAddress = representativeAssociationDbInServer.getAllRepresentativeAssociationEmailAddress();

            Assert.assertEquals(0, representativeAssociationEmailAddress.size());
        }
        catch (Exception e)
        {
            Assert.assertEquals("No RepresentativeAssociation been created", e.getMessage());
        }
    }

    @Test
    public void getAllRepresentativeAssociationEmailAddress_listOfRepresentativeAssociation()
    {
        RepresentativeAssociation representativeAssociation1 = new RepresentativeAssociation("representativeAssociation1@gmail.com","password", 12345, "firstName", "lastName");
        RepresentativeAssociation representativeAssociation2 = new RepresentativeAssociation("representativeAssociation2@gmail.com","password", 12345, "firstName", "lastName");

        ArrayList<String> representativeAssociationEmailAddress = new ArrayList<>();

        try
        {
            subscriberDbInServer.insertSubscriber(representativeAssociation1);
            representativeAssociationDbInServer.insertRepresentativeAssociation(representativeAssociation1);

            subscriberDbInServer.insertSubscriber(representativeAssociation2);
            representativeAssociationDbInServer.insertRepresentativeAssociation(representativeAssociation2);

            representativeAssociationEmailAddress = representativeAssociationDbInServer.getAllRepresentativeAssociationEmailAddress();

            Assert.assertEquals(2, representativeAssociationEmailAddress.size());
            Assert.assertEquals(true, representativeAssociationEmailAddress.contains(representativeAssociation1.getEmailAddress()));
            Assert.assertEquals(true, representativeAssociationEmailAddress.contains(representativeAssociation2.getEmailAddress()));
        }
        catch (Exception e)
        {
            Assert.assertEquals("No RepresentativeAssociation been created", e.getMessage());
        }
    }
    //endregion

    //region deleteAll_Tests
    @Test
    public void deleteAll_noRepresentativeAssociation()
    {
        ArrayList<String> representativeAssociationEmailAddress = new ArrayList<>();

        try
        {
            representativeAssociationEmailAddress = representativeAssociationDbInServer.getAllRepresentativeAssociationEmailAddress();

            Assert.assertEquals(0, representativeAssociationEmailAddress.size());

            representativeAssociationDbInServer.deleteAll();

            representativeAssociationEmailAddress = representativeAssociationDbInServer.getAllRepresentativeAssociationEmailAddress();

            Assert.assertEquals(0, representativeAssociationEmailAddress.size());
        }
        catch (Exception e)
        {
            Assert.assertEquals("No RepresentativeAssociation been created", e.getMessage());
        }
    }

    @Test
    public void deleteAll_listOfRepresentativeAssociation()
    {
        RepresentativeAssociation representativeAssociation1 = new RepresentativeAssociation("representativeAssociation1@gmail.com","password", 12345, "firstName", "lastName");
        RepresentativeAssociation representativeAssociation2 = new RepresentativeAssociation("representativeAssociation2@gmail.com","password", 12345, "firstName", "lastName");

        ArrayList<String> representativeAssociationEmailAddress = new ArrayList<>();

        try
        {
            subscriberDbInServer.insertSubscriber(representativeAssociation1);
            representativeAssociationDbInServer.insertRepresentativeAssociation(representativeAssociation1);

            subscriberDbInServer.insertSubscriber(representativeAssociation2);
            representativeAssociationDbInServer.insertRepresentativeAssociation(representativeAssociation2);

            representativeAssociationEmailAddress = representativeAssociationDbInServer.getAllRepresentativeAssociationEmailAddress();

            Assert.assertEquals(2, representativeAssociationEmailAddress.size());
            Assert.assertEquals(true, representativeAssociationEmailAddress.contains(representativeAssociation1.getEmailAddress()));
            Assert.assertEquals(true, representativeAssociationEmailAddress.contains(representativeAssociation2.getEmailAddress()));

            representativeAssociationDbInServer.deleteAll();

            representativeAssociationEmailAddress = representativeAssociationDbInServer.getAllRepresentativeAssociationEmailAddress();

            Assert.assertEquals(0, representativeAssociationEmailAddress.size());
            Assert.assertEquals(false, representativeAssociationEmailAddress.contains(representativeAssociation1.getEmailAddress()));
            Assert.assertEquals(false, representativeAssociationEmailAddress.contains(representativeAssociation2.getEmailAddress()));
        }
        catch (Exception e)
        {
            Assert.assertEquals("No RepresentativeAssociation been created", e.getMessage());
        }
    }
    //endregion
}
