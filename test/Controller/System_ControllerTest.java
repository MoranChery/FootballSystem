//package Controller;
//
//import org.junit.*;
//import org.junit.rules.ExpectedException;
//
//import static org.junit.Assert.*;
//
//public class System_ControllerTest {
//    Object AccountingSystem;
//    Object TaxLawSystem;
//
//    @Before
//    public void setUp() throws Exception {
//        AccountingSystem= new Object();
//        TaxLawSystem =new Object();
//    }
//
//
//    @Test
//    public void isTheSystemInitialize(){
//        assertFalse(SystemController.isTheSystemInitialize());
//    }
//
//    @Rule
//    public ExpectedException exceptionRuleGetInstance= ExpectedException.none();
//    @Test
//    public void getInstance() throws Exception {
//        exceptionRuleGetInstance.expectMessage("The system must be rebooted first");
//        SystemController.getInstance();
//    }
//
//    @Rule
//    public ExpectedException expectedExceptionStartInitializeTheSystem= ExpectedException.none();
//    @Test
//    public void startInitializeTheSystemAllNull() throws Exception {
//        expectedExceptionStartInitializeTheSystem.expectMessage("problem in login accounting system");
//        SystemController.startInitializeTheSystem(null, null);
//    }
//
//
//    @Test
//    public void startInitializeTheSystemAccountingSystemNull() throws Exception {
//        expectedExceptionStartInitializeTheSystem.expectMessage("problem in login tax law system");
//        SystemController.startInitializeTheSystem(AccountingSystem, null);
//        SystemController.startInitializeTheSystem(null, TaxLawSystem);
//    }
//
//    @Test
//    public void startInitializeTheSystemTaxLawSystemNull() throws Exception {
//        expectedExceptionStartInitializeTheSystem.expectMessage("problem in login tax law system");
//        SystemController.startInitializeTheSystem(AccountingSystem, null);
//    }
//
//
//    @Test
//    public void startInitializeTheSystem() throws Exception {
//        SystemController.startInitializeTheSystem(AccountingSystem, TaxLawSystem);
//    }
//    @Rule
//    public ExpectedException exceptionRuleInitialAdministratorRegistration= ExpectedException.none();
//
//    @Test
//    public void initialAdministratorRegistrationDetailsNull() throws Exception {
//        exceptionRuleInitialAdministratorRegistration.expectMessage("Problem-initialAdministratorRegistration");
//        SystemController.startInitializeTheSystem(AccountingSystem, TaxLawSystem);
//        String[] allDetails =null;
//        SystemController.addSystemAdministrator(allDetails);
//    }
//    @Test
//    public void initialAdministratorRegistrationProblemDetails() throws Exception {
//        exceptionRuleInitialAdministratorRegistration.expectMessage("Problem-initialAdministratorRegistration");
//        SystemController.startInitializeTheSystem(AccountingSystem, TaxLawSystem);
//        String[] allDetails = {"username","password","aa","firstName","lastName"};
//        SystemController.addSystemAdministrator(allDetails);
//    }
//
//    @Test
//    public void initialAdministratorRegistrationLesThen5() throws Exception {
//        exceptionRuleInitialAdministratorRegistration.expectMessage("Problem-initialAdministratorRegistration");
//        SystemController.startInitializeTheSystem(AccountingSystem, TaxLawSystem);
//        String[] allDetails = {"username","password","firstName","lastName"};
//        SystemController.addSystemAdministrator(allDetails);
//    }
//
//    @Test
//    public void initialAdministratorRegistrationMoreThen5() throws Exception {
//        exceptionRuleInitialAdministratorRegistration.expectMessage("Problem-initialAdministratorRegistration");
//        SystemController.startInitializeTheSystem(AccountingSystem, TaxLawSystem);
//        String[] allDetails = {"username","password","123","firstName","lastName", "222"};
//        SystemController.addSystemAdministrator(allDetails);
//    }
//    @Test
//    public void initialAdministratorRegistration() throws Exception {
//        SystemController.startInitializeTheSystem(AccountingSystem, TaxLawSystem);
//        String[] allDetails = {"username","password","123","firstName","lastName"};
//        SystemController.addSystemAdministrator(allDetails);
//        assertTrue(SystemController.isTheSystemInitialize());
//        assertNotNull(SystemController.getInstance());
//    }
//
//
//    @Rule
//    public ExpectedException exceptionRuleCantCreateLog= ExpectedException.none();
//
//    @Test
//    public void createLogPathNull() throws Exception {
//        exceptionRuleCantCreateLog.expectMessage("Cant create log");
//        SystemController system_controller = SystemController.getInstance();
//        system_controller.createLog(null);
//    }
//
//    @Test
//    public void createLogPathEmpty() throws Exception {
//        exceptionRuleCantCreateLog.expectMessage("Cant create log");
//        SystemController system_controller = SystemController.getInstance();
//        system_controller.createLog("");
//    }
//
//    @Test
//    public void createLog() throws Exception {
//        SystemController system_controller = SystemController.getInstance();
//        system_controller.createLog("good");
//    }
//}