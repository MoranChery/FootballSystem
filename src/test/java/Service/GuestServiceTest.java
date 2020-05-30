package Service;

import Controller.BaseEmbeddedSQL;
import Controller.GuestController;
import Data.Db;
import Data.FanDbInMemory;
import Data.SubscriberDbInMemory;
import Data.SubscriberDbInServer;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GuestServiceTest extends BaseEmbeddedSQL
{
    private GuestService guestService = new GuestService();
    private GuestController guestController = guestService.getGuestController();

    public void init() throws SQLException {
        final List<Db> dbs = new ArrayList<>();
        dbs.add(SubscriberDbInServer.getInstance());
        dbs.add(FanDbInMemory.getInstance());
        for (Db db : dbs)
        {
            db.deleteAll();
        }
    }

    @Test
    public void registerTest(){
        //todo: this is UI Function
        guestService.registerSubscriber("Fan");
    }

    @Test
    public void loginTest() throws Exception {
        try {
            guestController.registerRepresentativeAssociation("registerFan@gmail.com","hi123",123456789,"noy","harari");
        } catch (Exception e) {
            Assert.assertEquals(0,1);
        }
        //accept test
        try {
            guestService.login("registerFan@gmail.com","hi123");
            Assert.assertEquals(0,0);
        } catch (Exception e) {
            Assert.assertEquals(0,1);
        }
        //wrong password
        try {
            guestService.login("registerFan@gmail.com","hi1135156");
        } catch (ResponseStatusException e) {
            Assert.assertEquals(e.getMessage(),"404 NOT_FOUND \"Try again\"");
        }
        //unexist email
        try {
            guestService.login("Fan@gmail.com","hi1135156");
        }catch (Exception e){
            Assert.assertEquals(e.getMessage(),"404 NOT_FOUND \"Try again\"");
        }
    }
}
