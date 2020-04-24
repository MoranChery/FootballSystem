package Service;

import Controller.System_Controller;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class SystemServiceTest {
    SystemService systemService;
    Object AccountingSystem;
    Object TaxLawSystem;

    @Rule
    public ExpectedException expectedExceptionOpeningTheSystemByUser= ExpectedException.none();

    @Before
    public void setUp(){
        systemService = new SystemService();
        AccountingSystem = new Object();
        TaxLawSystem = new Object();
    }

    @Test
    public void startInitializeTheSystem() throws Exception {
        systemService.startInitializeTheSystem(AccountingSystem, TaxLawSystem);
    }

    @Rule
    public ExpectedException expectedExceptionStartInitializeTheSystem= ExpectedException.none();
    @Test
    public void startInitializeTheSystemFailAccountingSystem() throws Exception {
        expectedExceptionStartInitializeTheSystem.expectMessage("problem in login accounting system");
        systemService.startInitializeTheSystem(null, TaxLawSystem );
    }
    @Test
    public void startInitializeTheSystemFail() throws Exception {
        expectedExceptionStartInitializeTheSystem.expectMessage("problem in login accounting system");
        systemService.startInitializeTheSystem(null, null );
    }
    @Test
    public void startInitializeTheSystemFailTax() throws Exception {
        expectedExceptionStartInitializeTheSystem.expectMessage("problem in login tax law system");
        systemService.startInitializeTheSystem(AccountingSystem, null );
    }


    @Test
    public void addSystemAdministratorSuccessful() throws Exception {
        systemService.startInitializeTheSystem(AccountingSystem, TaxLawSystem);
        if(!System_Controller.isTheSystemInitialize()) {
            systemService.addPrimarySystemAdministrator();
        }
    }

    @Test
    public void openingTheSystemByUser() throws Exception {
        systemService.startInitializeTheSystem(AccountingSystem, TaxLawSystem);
        systemService.addPrimarySystemAdministrator();
        systemService.openingTheSystemByUser(AccountingSystem,TaxLawSystem);

    }
    @Test
    public void openingTheSystemByUserFail() throws Exception {
        expectedExceptionStartInitializeTheSystem.expectMessage("Problem-initialAdministratorRegistration");
        systemService.addPrimarySystemAdministrator();
        systemService.openingTheSystemByUser(null,TaxLawSystem);

    }


    @Rule
    public ExpectedException exceptionRuleCantCreateLog= ExpectedException.none();

    @Test
    public void createLog() throws Exception {
        if(System_Controller.isTheSystemInitialize()) {
            systemService.createLog("good");
        }
    }

    @Test
    public void createLogFail() throws Exception {
        if(systemService!=null) {
            exceptionRuleCantCreateLog.expectMessage("Can't create Log");
            systemService.createLog(null);
        }
    }
}