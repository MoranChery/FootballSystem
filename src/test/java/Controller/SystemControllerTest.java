package Controller;

import Data.*;
import Model.Enums.RoleType;
import Model.UsersTypes.SystemAdministrator;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class SystemControllerTest {

    private SystemController systemController;
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
//            db.deleteAll();
        }
        systemController = new SystemController();
    }
    @After
    public void after(){
        systemController = null;
    }
    @Test
    public void initialSystemExist() throws Exception{
        SystemAdministrator newSystemAdministrator = new SystemAdministrator("admin@gmail.com","psw",11,"first","last");
        systemAdministratorDb.insertSystemAdministrator(newSystemAdministrator);
        systemController.initialSystem();
    }
    @Test
    public void initialSystemLegal() throws Exception{
        systemController.initialSystem();
    }
    @Test
    public void connectionToExternalSystems_Legal() throws Exception {

    }

    @Test
    public void addSystemAdministrator_isExist() throws Exception {
        try {
            SystemAdministrator newSystemAdministrator = new SystemAdministrator("admin@gmail.com","psw",11,"first","last");
            systemAdministratorDb.insertSystemAdministrator(newSystemAdministrator);
            systemController.addSystemAdministrator();
        }
        catch (Exception e){
            Assert.assertEquals("SystemAdministrator already exists", e.getMessage());
        }
    }
    @Test
    public void addSystemAdministrator_Legal() throws Exception {
        systemController.addSystemAdministrator();
        Set<String> admins = systemAdministratorDb.getAllSystemAdministrators();
        Assert.assertEquals(1, admins.size());
        Assert.assertTrue(admins.contains("admin@gmail.com"));
        Assert.assertEquals(RoleType.SYSTEM_ADMINISTRATOR, roleDb.getRole("admin@gmail.com").getRoleType());
    }
    @Test
    public void createLogTest() throws Exception{
        systemController.createLog("path");
    }
    @Test
    public void connectToTheTaxLawSystemTest() throws Exception{
        systemController.connectToTheTaxLawSystem();
    }
    @Test
    public void connectToTheAccountingSystemTest() throws Exception{
        systemController.connectToTheAccountingSystem();
    }
    @Test
    public void getInstanceTest(){
        systemController.getInstance();
    }

}
