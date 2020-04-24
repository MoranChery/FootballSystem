package Controller;

import Data.LeagueDbInMemory;

import Data.RoleDb;
import Data.SubscriberDb;
import Data.SystemAdministratorDb;
import Model.Enums.RoleType;
import Model.LogFunctionality;
import Model.UsersTypes.SystemAdministrator;

public class System_Controller {

    private static SubscriberController subscriberController;
    private static LeagueDbInMemory leagueDbInMemory;
    private static SystemAdministratorDb systemAdministratorDb;
    private LogFunctionality log;
    private static boolean isInitialize = false;
    private static System_Controller ourInstance;
    private static SubscriberDb subscriberDb;
    private static RoleDb roleDb;

    private System_Controller() { }

    /**
     * if we want to know if the system initialized
     * @return is the system initialized- return true, else- false
     */
    public static boolean isTheSystemInitialize() {
        return isInitialize;
    }

    /**
     * get the System Controller
     * @return System_Controller
     * @throws Exception - if the system were not initialized
     */
    public static System_Controller getInstance() throws Exception {
        if(isInitialize){
            return ourInstance;
        }
        else{
            throw new Exception("The system must be rebooted first");
        }
    }

    /**
     * When the user presses the system reboot for the first time, we use this method
     * @throws Exception - Something went wrong with this method
     */
    public static void startInitializeTheSystem(Object AccountingSystem, Object TaxLawSystem ) throws Exception {
        connectionToExternalSystems(AccountingSystem, TaxLawSystem);
    }


    /**
     *When the user clicks the submit button
     * To register the administrator, use this method
     * @param allDetails - All parameters on the administrator
     * @throws Exception - If the registry could not be made from any error, this error will cause an appropriate message
     */
    public static void addSystemAdministrator(String[] allDetails) throws Exception {
        try {
            if (allDetails != null && allDetails.length == 5) {
                boolean[] isDetailsCorrect = checkDetails(allDetails);
                boolean isProblem = false;
                for (boolean b : isDetailsCorrect) {
                    if (!b) {
                        isProblem = true;
                        break;
                    }
                }
                if (!isProblem) {
                    String emailAddress = allDetails[0];
                    String password = allDetails[1];
                    Integer id = Integer.parseInt(allDetails[2]);
                    String firstName = allDetails[3];
                    String lastName = allDetails[4];
                    SystemAdministrator systemAdministrator = new SystemAdministrator(emailAddress, password, id, firstName, lastName);
                    systemAdministratorDb = Data.SystemAdministratorDbInMemory.getInstance();
                    systemAdministratorDb.createSystemAdministrator(systemAdministrator);
                    subscriberDb= Data.SubscriberDbInMemory.getInstance();
                    subscriberDb.createSubscriber(systemAdministrator);
                    roleDb = Data.RoleDbInMemory.getInstance();
                    roleDb.createRoleInSystem( emailAddress, RoleType.SYSTEM_ADMINISTRATOR);
                    leagueDbInMemory = LeagueDbInMemory.getInstance();
                    ourInstance = new System_Controller();
                    isInitialize = true;
                } else {
                    throw new Exception("Problem-initialAdministratorRegistration");
                }
            }
            else {
                throw new Exception("Problem-initialAdministratorRegistration");
            }
        }
        catch (Exception e){
            throw new Exception("Problem-initialAdministratorRegistration");
        }

    }

    /**
     * This method creates the log file
     * @param path - The path where to save the log file
     */
    public void createLog(String path) throws Exception {
        if(path!=null && path.length()>0){
            this.log = new LogFunctionality(path);
        }
        else {
            throw new Exception("Cant create log");
        }
    }

    private static boolean[] checkDetails(String[] allDetails){
            boolean[] isCorrect= new boolean[allDetails.length];
            for (int i = 0 ; i<allDetails.length ; i++){
                if(i!=2){
                    isCorrect[i]=true;
                }
                else {
                    try {
                        Double.parseDouble(allDetails[i]);
                        isCorrect[i]= true;
                    } catch (NumberFormatException nfe) {
                        isCorrect[i]= false;
                    }
                }
            }
            return isCorrect;
    }

    private static boolean connectionToExternalSystems(Object AccountingSystem, Object TaxLawSystem ) throws Exception {
        if(!logInToTheAccountingSystem(AccountingSystem)){
            throw new Exception("problem in login accounting system");
        }
        if(!logInToTheTaxLawSystem(TaxLawSystem)){
            throw new Exception("problem in login tax law system");
        }
        return true;
    }

    private static boolean logInToTheTaxLawSystem(Object o){
        if(o!=null) {
            //todo
            return true;
        }
        else {
            return false;
        }

    }

    private static boolean logInToTheAccountingSystem(Object o) {
        if(o!=null) {
            //todo
            return true;
        }
        else {
            return false;
        }
    }

}
