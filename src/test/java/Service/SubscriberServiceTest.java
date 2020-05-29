package Service;

import Controller.BaseEmbeddedSQL;
import Data.Db;
import Data.SubscriberDb;
import Data.SubscriberDbInMemory;
import Data.SubscriberDbInServer;
import Model.Enums.Status;
import Model.UsersTypes.Fan;
import Model.UsersTypes.Subscriber;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SubscriberServiceTest extends BaseEmbeddedSQL
{
    private SubscriberService subscriberService = new SubscriberService();
    private SubscriberDb subscriberDb = SubscriberDbInServer.getInstance();

    @Before
    public void init() throws SQLException {
        final List<Db> dbs = new ArrayList<>();
        dbs.add(subscriberDb);
        for (Db db : dbs) {
            db.deleteAll();
        }
    }

    @Test
    public void logOutNullInput() throws Exception {
        try {
            subscriberService.logOut( null);
        }
        catch (Exception e){
            Assert.assertTrue(e instanceof NullPointerException);
            Assert.assertEquals("subscriber not found", e.getMessage());
        }
    }
    @Test
    public void logOutLegal() throws Exception {
        Subscriber newSubscriber = new Fan("email@gmail.com", "fan1234", 1, "first", "last");
        subscriberDb.insertSubscriber(newSubscriber);
        subscriberDb.changeStatusToOnline(newSubscriber);
        subscriberService.logOut(newSubscriber.getEmailAddress());
        newSubscriber = subscriberDb.getSubscriber("email@gmail.com");
        Assert.assertEquals(newSubscriber.getStatus(), Status.OFFLINE);
    }
    @Test
    public void wantToEditPasswordNullInput() throws Exception{
        try {
            subscriberService.wantToEditPassword("id", null);
        }
        catch (Exception e){
            Assert.assertTrue(e instanceof NullPointerException);
            Assert.assertEquals("Something went wrong in editing subscriber the password", e.getMessage());
        }
    }
    @Test
    public void wantToEditPasswordLegal() throws Exception {
        Subscriber newSubscriber = new Fan("email@gmail.com", "fan1234", 1, "first", "last");
        subscriberDb.insertSubscriber(newSubscriber);
        subscriberService.wantToEditPassword(newSubscriber.getEmailAddress(), "newPsw");
        newSubscriber = subscriberDb.getSubscriber("email@gmail.com");
        Assert.assertEquals("newPsw", newSubscriber.getPassword());
    }
    @Test
    public void wantToEditFirstNameNullInput() throws Exception{
        try {
            subscriberService.wantToEditFirstName("id", null);
        }
        catch (Exception e){
            Assert.assertTrue(e instanceof NullPointerException);
            Assert.assertEquals("Something went wrong in editing subscriber's the first name", e.getMessage());
        }
    }
    @Test
    public void wantToEditFirstNameLegal() throws Exception {
        Subscriber newSubscriber = new Fan("email@gmail.com", "fan1234", 1, "first", "last");
        subscriberDb.insertSubscriber(newSubscriber);
        subscriberService.wantToEditFirstName(newSubscriber.getEmailAddress(), "newName");
        newSubscriber = subscriberDb.getSubscriber("email@gmail.com");
        Assert.assertEquals("newName",newSubscriber.getFirstName());
    }
    @Test
    public void wantToEditLastNameNullInput() throws Exception{
        try {
            subscriberService.wantToEditLastName("id", null);
        }
        catch (Exception e){
            Assert.assertTrue(e instanceof NullPointerException);
            Assert.assertEquals("Something went wrong in editing the last name of the subscriber", e.getMessage());
        }
    }
    @Test
    public void wantToEditLastNameLegal() throws Exception {
        Subscriber newSubscriber = new Fan("email@gmail.com", "fan1234", 1, "first", "last");
        subscriberDb.insertSubscriber(newSubscriber);
        subscriberService.wantToEditLastName(newSubscriber.getEmailAddress(), "newName");
        newSubscriber = subscriberDb.getSubscriber("email@gmail.com");
        Assert.assertEquals("newName",newSubscriber.getLastName());
    }
}
