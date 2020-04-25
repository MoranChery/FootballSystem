package Service;

import Controller.SystemController;
import Data.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.ArrayList;
import java.util.List;

public class SystemServiceTest {

    private SystemController systemController;
    private SystemService systemService = new SystemService();
    private static SubscriberDb subscriberDb = SubscriberDbInMemory.getInstance();
    private static RoleDb roleDb = RoleDbInMemory.getInstance();
    private static SystemAdministratorDb systemAdministratorDb = SystemAdministratorDbInMemory.getInstance();


    @Before
    public void init() {
        final List<Db> dbs = new ArrayList<>();
        dbs.add(SubscriberDbInMemory.getInstance());
        dbs.add(RoleDbInMemory.getInstance());
        dbs.add(SystemAdministratorDbInMemory.getInstance());
        for (Db db : dbs) {
            db.deleteAll();
        }
        systemController = new SystemController();
    }
    @After
    public void after(){
        systemController = null;
    }

    @Test
    public void createLogTest() throws Exception{
        systemService.createLog("path");
    }






}