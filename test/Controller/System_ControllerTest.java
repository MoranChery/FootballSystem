package Controller;

import org.junit.*;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.*;

public class System_ControllerTest {
    Object AccountingSystem;
    Object TaxLawSystem;

    @Before
    public void setUp() throws Exception {
        AccountingSystem= new Object();
        TaxLawSystem =new Object();
    }


    @Test
    public void isTheSystemInitialize(){
        assertFalse(System_Controller.isTheSystemInitialize());
    }

    @Rule
    public ExpectedException exceptionRuleGetInstance= ExpectedException.none();
    @Test
    public void getInstance() throws Exception {
        exceptionRuleGetInstance.expectMessage("The system must be rebooted first");
        System_Controller.getInstance();
    }

    @Rule
    public ExpectedException expectedExceptionStartInitializeTheSystem= ExpectedException.none();
    @Test
    public void startInitializeTheSystemAllNull() throws Exception {
        expectedExceptionStartInitializeTheSystem.expectMessage("problem in login accounting system");
        System_Controller.startInitializeTheSystem(null, null);
    }


    @Test
    public void startInitializeTheSystemAccountingSystemNull() throws Exception {
        expectedExceptionStartInitializeTheSystem.expectMessage("problem in login tax law system");
        System_Controller.startInitializeTheSystem(AccountingSystem, null);
        System_Controller.startInitializeTheSystem(null, TaxLawSystem);
    }

    @Test
    public void startInitializeTheSystemTaxLawSystemNull() throws Exception {
        expectedExceptionStartInitializeTheSystem.expectMessage("problem in login tax law system");
        System_Controller.startInitializeTheSystem(AccountingSystem, null);
    }


    @Test
    public void startInitializeTheSystem() throws Exception {
        System_Controller.startInitializeTheSystem(AccountingSystem, TaxLawSystem);
    }
    @Rule
    public ExpectedException exceptionRuleInitialAdministratorRegistration= ExpectedException.none();

    @Test
    public void initialAdministratorRegistrationDetailsNull() throws Exception {
        exceptionRuleInitialAdministratorRegistration.expectMessage("Problem-initialAdministratorRegistration");
        System_Controller.startInitializeTheSystem(AccountingSystem, TaxLawSystem);
        String[] allDetails =null;
        System_Controller.addSystemAdministrator(allDetails);
    }
    @Test
    public void initialAdministratorRegistrationProblemDetails() throws Exception {
        exceptionRuleInitialAdministratorRegistration.expectMessage("Problem-initialAdministratorRegistration");
        System_Controller.startInitializeTheSystem(AccountingSystem, TaxLawSystem);
        String[] allDetails = {"username","password","aa","firstName","lastName"};
        System_Controller.addSystemAdministrator(allDetails);
    }

    @Test
    public void initialAdministratorRegistrationLesThen5() throws Exception {
        exceptionRuleInitialAdministratorRegistration.expectMessage("Problem-initialAdministratorRegistration");
        System_Controller.startInitializeTheSystem(AccountingSystem, TaxLawSystem);
        String[] allDetails = {"username","password","firstName","lastName"};
        System_Controller.addSystemAdministrator(allDetails);
    }

    @Test
    public void initialAdministratorRegistrationMoreThen5() throws Exception {
        exceptionRuleInitialAdministratorRegistration.expectMessage("Problem-initialAdministratorRegistration");
        System_Controller.startInitializeTheSystem(AccountingSystem, TaxLawSystem);
        String[] allDetails = {"username","password","123","firstName","lastName", "222"};
        System_Controller.addSystemAdministrator(allDetails);
    }
    @Test
    public void initialAdministratorRegistration() throws Exception {
        System_Controller.startInitializeTheSystem(AccountingSystem, TaxLawSystem);
        String[] allDetails = {"username","password","123","firstName","lastName"};
        System_Controller.addSystemAdministrator(allDetails);
        assertTrue(System_Controller.isTheSystemInitialize());
        assertNotNull(System_Controller.getInstance());
    }


    @Rule
    public ExpectedException exceptionRuleCantCreateLog= ExpectedException.none();

    @Test
    public void createLogPathNull() throws Exception {
        exceptionRuleCantCreateLog.expectMessage("Cant create log");
        System_Controller system_controller = System_Controller.getInstance();
        system_controller.createLog(null);
    }

    @Test
    public void createLogPathEmpty() throws Exception {
        exceptionRuleCantCreateLog.expectMessage("Cant create log");
        System_Controller system_controller = System_Controller.getInstance();
        system_controller.createLog("");
    }

    @Test
    public void createLog() throws Exception {
        System_Controller system_controller = System_Controller.getInstance();
        system_controller.createLog("good");
    }
}