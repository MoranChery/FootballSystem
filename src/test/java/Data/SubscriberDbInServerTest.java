package Data;

import Controller.BaseEmbeddedSQL;
import Controller.TeamOwnerController;
import Model.Enums.Status;
import Model.UsersTypes.Subscriber;
import Model.UsersTypes.TeamManager;
import Model.UsersTypes.TeamOwner;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SubscriberDbInServerTest extends BaseEmbeddedSQL {
    private TeamDb teamDb = TeamDbInServer.getInstance();
    private PlayerDb playerDb = PlayerDbInServer.getInstance();
    private TeamManagerDb teamManagerDb = TeamManagerDbInServer.getInstance();
    private TeamOwnerDb teamOwnerDb =  TeamOwnerDbInServer.getInstance();
    private CoachDb coachDb =  CoachDbInServer.getInstance();
    private RoleDb roleDb = RoleDbInServer.getInstance();
    private SubscriberDb subscriberDb = SubscriberDbInServer.getInstance();
    private CourtDb courtDb = CourtDbInServer.getInstance();
    private PermissionDb permissionDb = PermissionDbInServer.getInstance();
    private PageDb pageDb = PageDbInServer.getInstance();
    private FinancialActivityDb financialActivityDb = FinancialActivityDbInServer.getInstance();

    @Before
    public void init() throws SQLException {
        final List<Db> dbs = new ArrayList<>();
        dbs.add(coachDb);
        dbs.add(courtDb);
        dbs.add(financialActivityDb);
        dbs.add(teamOwnerDb);
        dbs.add(playerDb);
        dbs.add(subscriberDb);
        dbs.add(teamManagerDb);
        dbs.add(teamManagerDb);
        dbs.add(roleDb);
        dbs.add(pageDb);
        dbs.add(permissionDb);
        dbs.add(teamDb);
        for (Db db : dbs) {
            db.deleteAll();
        }
    }


    @Test
    public void testInsertSubscriber_null() throws Exception
    {
        try
        {
            subscriberDb.insertSubscriber(null);
        }
        catch (Exception e)
        {
            Assert.assertTrue(e instanceof NullPointerException);
            Assert.assertEquals("bad input", e.getMessage());
        }
    }


    @Test
    public void testInsertSubscriber_legal() throws Exception
    {
        String emailAddress = "email@gmail.com";
        String ownedBy = "ownedBy@gmail.com";
        TeamOwner teamOwner = new TeamOwner(emailAddress, "Pass1234",222222222, "first", "last",null);
        subscriberDb.insertSubscriber(teamOwner);
        Subscriber subscriber = subscriberDb.getSubscriber(emailAddress);
        Assert.assertEquals(emailAddress, subscriber.getEmailAddress());
        Assert.assertEquals(222222222, subscriber.getId().intValue());
        Assert.assertEquals("first", subscriber.getFirstName());
        Assert.assertEquals("last", subscriber.getLastName());
        Assert.assertEquals("Pass1234",subscriber.getPassword());
    }

    @Test
    public void testInsertSubscriber_SubscriberExists() throws Exception
    {

        String emailAddress = "email@gmail.com";
        String ownedBy = "ownedBy@gmail.com";
        TeamOwner teamOwner = new TeamOwner(emailAddress, "Pass1234",222222222, "first", "last",ownedBy);
        try
        {
            subscriberDb.insertSubscriber(teamOwner);
            subscriberDb.insertSubscriber(teamOwner);
        }
        catch (Exception e)
        {
            Assert.assertEquals("subscriber already exists", e.getMessage());
        }
    }


    @Test
    public void testGetSubscriber_null() throws Exception {
        try
        {
            subscriberDb.getSubscriber(null);
        }
        catch (Exception e)
        {
            Assert.assertTrue(e instanceof NullPointerException);
            Assert.assertEquals("bad input", e.getMessage());
        }
    }

    @Test
    public void testGetSubscriber_legal() throws Exception{
        String emailAddress = "email@gmail.com";
        String ownedBy = "ownedBy@gmail.com";

        TeamManager teamManager = new TeamManager(emailAddress, "Pass1234",222222222, "first", "last",ownedBy);
        subscriberDb.insertSubscriber(teamManager);
        Subscriber subscriber = subscriberDb.getSubscriber(emailAddress);
        Assert.assertEquals(emailAddress, subscriber.getEmailAddress());
        Assert.assertEquals(222222222, subscriber.getId().intValue());
        Assert.assertEquals("first", subscriber.getFirstName());
        Assert.assertEquals("last", subscriber.getLastName());
        Assert.assertEquals("Pass1234",subscriber.getPassword());
    }


    @Test
    public void testGetSubscriber_notExists() throws Exception{
        String emailAddress = "email@gmail.com";
        String ownedBy = "ownedBy@gmail.com";
        TeamManager teamManager = new TeamManager(emailAddress, "Pass1234",222222222, "first", "last",ownedBy);
        try
        {
            subscriberDb.getSubscriber(emailAddress);
        }
        catch (Exception e)
        {
            Assert.assertEquals("subscriber not found", e.getMessage());
        }
    }

    @Test
    public void testChangeStatusToOnline() throws Exception {
        String emailAddress = "email@gmail.com";
        TeamManager teamManager = new TeamManager(emailAddress, "Pass1234",222222222, "first", "last",null);
        subscriberDb.insertSubscriber(teamManager);
        Subscriber subscriber = subscriberDb.getSubscriber(emailAddress);
        Assert.assertEquals(Status.OFFLINE,subscriber.getStatus());
        subscriberDb.changeStatusToOnline(subscriber);
        subscriber = subscriberDb.getSubscriber(emailAddress);
        Assert.assertEquals(Status.ONLINE,subscriber.getStatus());
    }

    @Test
    public void testSetSubscriberWantAlert_legal() throws Exception
    {
        String emailAddress = "email@gmail.com";
        String ownedBy = "ownedBy@gmail.com";
        TeamOwner teamOwner = new TeamOwner(emailAddress, "Pass1234",222222222, "first", "last",null);
        subscriberDb.insertSubscriber(teamOwner);
        Subscriber subscriber = subscriberDb.getSubscriber(emailAddress);
        Assert.assertEquals(emailAddress, subscriber.getEmailAddress());
        Assert.assertEquals(222222222, subscriber.getId().intValue());
        Assert.assertEquals("first", subscriber.getFirstName());
        Assert.assertEquals("last", subscriber.getLastName());
        Assert.assertEquals("Pass1234",subscriber.getPassword());
        Assert.assertEquals(false,subscriber.isWantAlertInMail());
        subscriberDb.setSubscriberWantAlert(emailAddress);
        subscriber = subscriberDb.getSubscriber(emailAddress);
        Assert.assertEquals(true,subscriber.isWantAlertInMail());
    }
}
