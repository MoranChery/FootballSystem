package Service;

import Controller.GuestController;
import Data.Db;
import Data.FanDbInMemory;
import Data.PageDbInMemory;
import Data.SubscriberDbInMemory;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class GuestServiceTest {
    private GuestService guestService = new GuestService();
    private GuestController guestController=new GuestController();

    public void init()
    {
        final List<Db> dbs = new ArrayList<>();
        dbs.add(SubscriberDbInMemory.getInstance());
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
            guestController.registerFan("registerFan@gmail.com","hi123",123456789,"noy","harary");
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
        Assert.assertEquals(guestService.login("registerFan@gmail.com","hi1135156"),false);
        //unexist email
        try {
            guestService.login("Fan@gmail.com","hi1135156");
        }catch (Exception e){
            Assert.assertEquals(e.getMessage(),"subscriber not found");
        }
        //invalid email
        Assert.assertEquals(guestService.login("registerFan.com","hi1135156"),false);
    }
}
