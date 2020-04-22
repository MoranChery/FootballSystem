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
    public void startInitializeTheSystem(Object AccountingSystem, Object TaxLawSystem ){
        try {
            system_controller.startInitializeTheSystem(AccountingSystem, TaxLawSystem);
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
    private void displayHomeScreen() throws Exception {
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
    public void openingTheSystemByUser() throws Exception {
        if(system_controller.isTheSystemInitialize()){
            displayHomeScreen();
        }
        else{
            alert("System not booted - Unable to connect");
        }
    }

    /**
     * after we creat the first System Administrator we ask the user to add log path
     * @param path the path where to save the log file
     */
    public void createLog(String path){
        try{
            system_controller.createLog(path);
            alert("Log created");
        }
        catch (Exception e){
            alert("Can't create Log");
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
