package Controller;

import Data.*;
import Model.Enums.RoleType;
import Model.UsersTypes.SystemAdministrator;
import Service.SystemService;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class SystemControllerTest extends BaseEmbeddedSQL  {

    private SystemController systemController = SystemController.getInstance();
    private static SubscriberDb subscriberDb = SubscriberDbInServer.getInstance();
    private static RoleDb roleDb = RoleDbInServer.getInstance();
    private static SystemAdministratorDb systemAdministratorDb = SystemAdministratorDbInServer.getInstance();

    //    @Before
//    public void init() throws SQLException {
//        final List<Db> dbs = new ArrayList<>();
//        dbs.add(subscriberDb);
//        dbs.add(roleDb);
////        dbs.add(systemAdministratorDb);
//        for (Db db : dbs) {
//            db.deleteAll();
//        }
//        //systemController = new SystemController();
//    }
    @After
    public void after(){
        systemController = null;
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
            subscriberDb.insertSubscriber(newSystemAdministrator);
            systemAdministratorDb.insertSystemAdministrator(newSystemAdministrator);
            systemController.addSystemAdministrator();
        }
        catch (Exception e){
            Assert.assertEquals("subscriber already exists", e.getMessage());
        }
    }
    @Test
    public void addSystemAdministrator_Legal() throws Exception{

        SystemAdministrator systemAdministrator = systemAdministratorDb.getSystemAdministrator("admin@gmail.com");
        Assert.assertNotNull(systemAdministrator);
    }
    @Test
    public void connectToTheTaxLawSystemTest() throws Exception{
        systemController.isConnectToTheTaxLawSystem();
    }
    @Test
    public void connectToTheAccountingSystemTest() throws Exception{
        systemController.isConnectToTheAccountingSystem();
    }
    @Test
    public void getInstanceTest(){
        systemController.getInstance();
    }

}