package Controller;

import Model.System;
import Model.UsersTypes.SystemAdministrator;

import java.util.ArrayList;
import java.util.List;

public class System_Controller {

    static CoachController coachController;
    static FanController fanController;
    static JudgeController judgeController;
    static PlayerController playerController;
    static RepresentativeAssociationController representativeAssociationController;
    static SubscriberController subscriberController;
    static TeamController teamController;
    static TeamManagerController teamManagerController;
    static TeamOwnerController teamOwnerController;
    static SystemAdministrator systemAdministrator;

    private static boolean isInitialize= false;
    private System system;

    public static boolean isIsInitialize() {
        return isInitialize;
    }

    private static List<SystemAdministrator> systemAdministrators;
    private static System_Controller ourInstance= new System_Controller();

    private System_Controller() {
        system = System.getInstance();
        systemAdministrators = system.getAllSystemAdministrators();
    }

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
    //todo
    public void displayHomeScreen() {

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
                systemAdministrators.add(systemAdministrator);
                //systemAdministrator = new SystemAdministrator(systemAdministrator);
                //todo
                coachController = new CoachController();
                fanController= new FanController();
                judgeController = new JudgeController();
                playerController= new PlayerController();
                representativeAssociationController= new RepresentativeAssociationController();
                subscriberController= new SubscriberController();
                teamController= new TeamController();
                teamManagerController= new TeamManagerController();
                teamOwnerController= new TeamOwnerController();
                isInitialize = true;
            } else {
                ArrayList whereIsDetailsProblem= problemWithTheDetails(isDetailsCorrect);
                ShowAgainAdminRegistrationForm(whereIsDetailsProblem);
            }
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

    //todo
    private static boolean logInToTheTaxLawSystem() {
        return true;
    }

    //todo
    private static boolean logInToTheAccountingSystem() {
        return true;
    }

    /**
     * This method creates the log file
     * @param path - The path where to save the log file
     */
    public void createLog(String path) {
        system.creteLog(path);

    }

    public static void main(String[] args){

    }



}
