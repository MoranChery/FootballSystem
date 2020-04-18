package Service;

import Controller.System_Controller;

public class SystemService{

    private System_Controller system_controller;

    private SystemService(){
    }

    /**
     * Initial boot of the system, reboot the external systems,
     * and finally display an initial administrator registration form
     */
    public void startInitializeTheSystem(){
        try {
            system_controller.startInitializeTheSystem();
            alert("Successfully connected to external systems");
            displayFormInitialAdministratorRegistration();
        }
        catch (Exception e){
            alert(e.getMessage());
        }
    }

    /**
     * Add a primary administrator
     * @param allDetails - All the details of the manager
     */
    public void addSystemAdministrator(String[] allDetails, String logPath){
        try {
            system_controller.initialAdministratorRegistration(allDetails);
            system_controller.createLog(logPath);
            system_controller= System_Controller.getInstance();
            alert("Administrator registration successfully completed");
            displayHomeScreen();
        }
        catch (Exception e){
            alert("Administrator creation failed - please try again");
        }

    }

    /**
     * This method will display the home screen
     */
    private void displayHomeScreen(){
       system_controller.displayHomeScreen();
    }

    /**
     * This method will display an initial administrator registration form
     */
    private void displayFormInitialAdministratorRegistration(){
        //todo
    }

    /**
     * This method will be called when a user attempts to access the system
     */
    public void openingTheSystemByUser(){
        if(system_controller.isIsInitialize()){
            displayHomeScreen();
        }
        else{
            alert("System not booted - Unable to connect");
        }
    }

    /**
     * This function will display the message - currently only printing the message
     * @param alert- the alert string
     */
    //todo- The way of displaying the message must be changed according to the user interface we will use
    private void alert(String alert){
        System.out.println("Alert: "+alert);
    }

}
