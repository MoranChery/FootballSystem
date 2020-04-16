package Controller;

import org.junit.Test;

import static org.junit.Assert.*;

public class System_ControllerTest {

    System_Controller system_controller;

    @Test
    public void isIsInitialize() throws Exception {
        assertEquals(system_controller.isIsInitialize(),false);
        System_Controller.startInitializeTheSystem();
        String[] allDetails = {"username","password","123","firstName","lastName"};
        System_Controller.initialAdministratorRegistration(allDetails);
        assertTrue(system_controller.isIsInitialize());
    }

    @Test
    public void getInstance() {



    }

    @Test
    public void startInitializeTheSystem() {
    }

    @Test
    public void displayHomeScreen() {
    }

    @Test
    public void initialAdministratorRegistration() {
    }

    @Test
    public void createLog() {
    }

    @Test
    public void main() {
    }
}