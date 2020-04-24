package Service;

import Controller.System_Controller;

public class SystemService{

    private System_Controller system_controller;

    public SystemService(){
    }

    /**
     * Initial boot of the system, reboot the external systems,
     * and finally display an initial administrator registration form
     */
    public void startInitializeTheSystem(Object AccountingSystem, Object TaxLawSystem ) throws Exception {
        try {
            system_controller.startInitializeTheSystem(AccountingSystem, TaxLawSystem);
        }
        catch (Exception e){
            throw new Exception(e.getMessage());

        }
    }

    /**
     * Add a primary administrator
     */
    public void addPrimarySystemAdministrator() throws Exception {
        String[] allDetails={"primaryAdmin","admin","123456789","adminName","adminLastName"};
        System_Controller.addSystemAdministrator(allDetails);
    }

    /**
     * This method will be called when a user attempts to access the system
     */
    public void openingTheSystemByUser(Object AccountingSystem, Object TaxLawSystem) throws Exception {
        System_Controller.startInitializeTheSystem(AccountingSystem, TaxLawSystem);
    }
    /**
     * after we creat the first System Administrator we ask the user to add log path
     * @param path the path where to save the log file
     */
    public void createLog(String path) throws Exception {
        try{
            System_Controller.getInstance().createLog(path);
        }
        catch (Exception e){
            throw new Exception("Can't create Log");
        }
    }



}
