package Controller;

import Data.LeagueDbInMemory;

import Model.LogFunctionality;
import Model.UsersTypes.SystemAdministrator;

import java.util.ArrayList;

public class System_Controller {

    private static SubscriberController subscriberController;
    private static  SystemAdministratorController systemAdministratorController;
    private static LeagueDbInMemory leagueDbInMemory;
    private LogFunctionality log;
    private static boolean isInitialize = false;
    private static System_Controller ourInstance;

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
    public static void startInitializeTheSystem() throws Exception {
        try {
            if (connectionToExternalSystems()) {
            } else {
                throw new Exception("Something got wrong- function: startInitializeTheSystem in System_Controller");
            }
        }
        catch (Exception e){
            throw new Exception("The system must be rebooted first");
        }

    }

    /**
     * This method will show the user the home screen
     */
    public void displayHomeScreen() {
        //todo
    }

    /**
     *When the user clicks the submit button
     * To register the administrator, use this method
     * @param allDetails - All parameters on the administrator
     * @throws Exception - If the registry could not be made from any error, this error will cause an appropriate message
     */
    public static void initialAdministratorRegistration(String[] allDetails) throws Exception {
        if(allDetails!=null && allDetails.length == 5) {
            boolean[] isDetailsCorrect = checkDetails(allDetails);
            boolean isProblem = false;
            for (int i = 0; i < isDetailsCorrect.length; i++) {
                if (!isDetailsCorrect[i]) {
                    isProblem = true;
                    break;
                }
            }
            if (!isProblem) {
                String username= allDetails[0];
                String password= allDetails[1];
                Integer id= Integer.parseInt(allDetails[2]);
                String firstName= allDetails[3];
                String lastName= allDetails[4];
                //todo- Change - used the function from controller of systemManagerController
                SystemAdministrator systemAdministrator= new SystemAdministrator(username, password,id, firstName, lastName);
                subscriberController= new SubscriberController();
                systemAdministratorController = new SystemAdministratorController();
                //todo- systemAdministratorController.create(systemAdministrator)-something like this
                leagueDbInMemory = LeagueDbInMemory.getInstance();
                //todo- ComplaintsDb
                ourInstance = new System_Controller();
                isInitialize = true;
            } else {
                ArrayList whereIsDetailsProblem= problemWithTheDetails(isDetailsCorrect);
                ShowAgainAdminRegistrationForm(whereIsDetailsProblem);
            }
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

    private static void ShowAgainAdminRegistrationForm(ArrayList<Integer> whereIsDetailsProblem) {

        //todo
    }

    private static ArrayList problemWithTheDetails(boolean[] isDetailsCorrect) throws Exception {
        if(isDetailsCorrect!=null) {
            ArrayList<Integer> whereIsDetailsProblem = new ArrayList<>();
            for (int i= 0; i<isDetailsCorrect.length ; i++) {
                if(!isDetailsCorrect[i]){
                    whereIsDetailsProblem.add(i);
                }
            }
            return whereIsDetailsProblem;
        }
        else {
            throw new Exception("Something got wrong- function: problemWithTheDetails in System_Controller");

        }
    }

    private static boolean[] checkDetails(String[] allDetails) throws Exception {
        if(allDetails!=null && allDetails.length==5){
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
        else{
            throw new Exception("Something got wrong- function: checkDetails in System_Controller");
        }
    }

    private static boolean connectionToExternalSystems() throws Exception {
        if(!logInToTheAccountingSystem()){
            throw new Exception("problem in login accounting system");
        }
        if(!logInToTheTaxLawSystem()){
            throw new Exception("problem in login tax law system");
        }
        return true;
    }

    private static boolean logInToTheTaxLawSystem(){
        //todo
        return true;
    }

    private static boolean logInToTheAccountingSystem() {
        //todo
        return true;
    }

}
