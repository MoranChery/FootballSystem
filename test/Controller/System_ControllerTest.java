package Controller;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.*;

public class System_ControllerTest {

    System_Controller system_controller;
    Object AccountingSystem= new Object();
    Object TaxLawSystem =new Object();


    @Test
    public void isTheSystemInitialize() throws Exception {
        assertEquals(system_controller.isTheSystemInitialize() ,false);
        System_Controller.startInitializeTheSystem(AccountingSystem, TaxLawSystem);
        String[] allDetails = {"username","password","123","firstName","lastName"};
        System_Controller.initialAdministratorRegistration(allDetails);
        assertTrue(system_controller.isTheSystemInitialize());
    }

    @Rule
    public ExpectedException exceptionRuleGetInstance= ExpectedException.none();

    @Test
    public void getInstance() throws Exception {
        exceptionRuleGetInstance.expect(Exception.class);
        exceptionRuleGetInstance.expectMessage("The system must be rebooted first");
        system_controller.getInstance();
    }

    @Rule
    public ExpectedException exceptionRuleConnectionToExternalSystemsTaxLawSystemIsNull= ExpectedException.none();

    @Test
    public void connectionToExternalSystemsTaxLawSystemIsNull() throws Exception {
        exceptionRuleConnectionToExternalSystemsTaxLawSystemIsNull.expectMessage("problem in login tax law system");
        System_Controller.startInitializeTheSystem(AccountingSystem, null);
    }


    @Rule
    public ExpectedException exceptionRuleConnectionToExternalSystemsAccountingSystemIsNull= ExpectedException.none();

    @Test
    public void connectionToExternalSystemsAccountingSystemIsNull() throws Exception {
        exceptionRuleConnectionToExternalSystemsAccountingSystemIsNull.expectMessage("problem in login accounting system");
        System_Controller.startInitializeTheSystem(null, TaxLawSystem);
    }


    @Test
    public void connectionToExternal() throws Exception {
        System_Controller.startInitializeTheSystem(AccountingSystem, TaxLawSystem);
    }




    @Test
    public void getInstanceValid() throws Exception {
        String[] allDetails = {"username","password","123","firstName","lastName"};
        System_Controller.startInitializeTheSystem(AccountingSystem, TaxLawSystem);
        system_controller.initialAdministratorRegistration(allDetails);
        assertNotNull(system_controller.getInstance());
    }

    @Test
    public void startInitializeTheSystem() throws Exception {
        system_controller.startInitializeTheSystem(AccountingSystem, TaxLawSystem);
    }

    @Rule
    public ExpectedException exceptionRuleDisplayHomeScreen= ExpectedException.none();
    @Test
    public void displayHomeScreen() throws Exception {
        exceptionRuleDisplayHomeScreen.expect(NullPointerException.class);
        system_controller.displayHomeScreen();

    }

    @Rule
    public ExpectedException exceptionRuleInitialAdministratorRegistrationIsNull= ExpectedException.none();
    @Test
    public void initialAdministratorRegistrationIsNull() throws Exception {
        exceptionRuleInitialAdministratorRegistrationIsNull.expect(Exception.class);
        System_Controller.startInitializeTheSystem(AccountingSystem, TaxLawSystem);
        String[] allDetails1 =null;
        System_Controller.initialAdministratorRegistration(allDetails1);
    }


    @Rule
    public ExpectedException exceptionRuleInitialAdministratorRegistrationIdNotNum= ExpectedException.none();
    @Test
    public void initialAdministratorRegistrationIdNotNum() throws Exception {
        System_Controller.startInitializeTheSystem(AccountingSystem, TaxLawSystem);
        String[] allDetails = {"username","password","aa","firstName","lastName"};
        System_Controller.initialAdministratorRegistration(allDetails);
    }


    @Rule
    public ExpectedException exceptionRuleInitialAdministratorRegistrationProblem= ExpectedException.none();
    @Test
    public void initialAdministratorRegistrationProblem() throws Exception {
        exceptionRuleInitialAdministratorRegistrationProblem.expectMessage("Problem-initialAdministratorRegistration");
        System_Controller.startInitializeTheSystem(AccountingSystem, TaxLawSystem);
        String[] allDetails2 = {"username","password","123","firstName"};
        System_Controller.initialAdministratorRegistration(allDetails2);
    }

    @Test
    public void createLog() throws Exception {
        System_Controller.startInitializeTheSystem(AccountingSystem, TaxLawSystem);
        String[] allDetails = {"username","password","123","firstName","lastName"};
        System_Controller.initialAdministratorRegistration(allDetails);
        system_controller=System_Controller.getInstance();
        system_controller.createLog("good");
    }


    @Rule
    public ExpectedException exceptionRuleCantCreateLogIsNull= ExpectedException.none();
    @Test
    public void cantCreateLogIsNull() throws Exception {
        exceptionRuleCantCreateLogIsNull.expectMessage("Cant create log");
        System_Controller.startInitializeTheSystem(AccountingSystem, TaxLawSystem);
        String[] allDetails = {"username","password","123","firstName","lastName"};
        System_Controller.initialAdministratorRegistration(allDetails);
        system_controller=System_Controller.getInstance();
        system_controller.createLog(null);
    }

    @Rule
    public ExpectedException exceptionRuleCantCreateLog= ExpectedException.none();
    @Test
    public void cantCreateLog() throws Exception {
        exceptionRuleCantCreateLog.expectMessage("Cant create log");
        System_Controller.startInitializeTheSystem(AccountingSystem, TaxLawSystem);
        String[] allDetails = {"username","password","123","firstName","lastName"};
        System_Controller.initialAdministratorRegistration(allDetails);
        system_controller=System_Controller.getInstance();
        system_controller.createLog("");
    }

}