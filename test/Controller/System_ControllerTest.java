package Controller;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.*;

public class System_ControllerTest {

    System_Controller system_controller;


    @Test
    public void isIsInitialize() throws Exception {
        assertEquals(system_controller.isTheSystemInitialize() ,false);
        System_Controller.startInitializeTheSystem();
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
        String[] allDetails = {"username","password","123","firstName","lastName"};
     //   System_Controller.(allDetails);
        assertNotNull(system_controller.getInstance());
    }

    @Rule
    public ExpectedException exceptionRuleStartInitializeTheSystem= ExpectedException.none();
    @Test
    public void startInitializeTheSystem() throws Exception {
        exceptionRuleGetInstance.expect(Exception.class);
        exceptionRuleGetInstance.expectMessage("The system must be rebooted first");
        system_controller.startInitializeTheSystem();
    }

    @Rule
    public ExpectedException exceptionRuleDisplayHomeScreen= ExpectedException.none();
    @Test
    public void displayHomeScreen() {
        exceptionRuleDisplayHomeScreen.expect(Exception.class);
        exceptionRuleDisplayHomeScreen.expectMessage("The system must be rebooted first");
        system_controller.displayHomeScreen();
    }

    @Test
    public void initialAdministratorRegistration() {
    }

    @Test
    public void createLog() {
    }

}