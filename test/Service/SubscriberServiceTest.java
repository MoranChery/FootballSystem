package Service;

import Controller.SubscriberController;
import Data.*;
import Model.Enums.Status;
import Model.UsersTypes.Fan;
import Model.UsersTypes.Subscriber;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class SubscriberServiceTest {
    private SubscriberService subscriberService = new SubscriberService();
    private SubscriberDb subscriberDb = SubscriberDbInMemory.getInstance();

    @Before
    public void init() {
        final List<Db> dbs = new ArrayList<>();
        dbs.add(SubscriberDbInMemory.getInstance());
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
        subscriberDb.createSubscriber(newSubscriber);
        newSubscriber.setStatus(Status.ONLINE);
        subscriberService.logOut(newSubscriber.getEmailAddress());
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
        subscriberDb.createSubscriber(newSubscriber);
        subscriberService.wantToEditPassword(newSubscriber.getEmailAddress(), "newPsw");
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
        subscriberDb.createSubscriber(newSubscriber);
        subscriberService.wantToEditFirstName(newSubscriber.getEmailAddress(), "newName");
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
        subscriberDb.createSubscriber(newSubscriber);
        subscriberService.wantToEditLastName(newSubscriber.getEmailAddress(), "newName");
        Assert.assertEquals("newName",newSubscriber.getLastName());
    }
}
