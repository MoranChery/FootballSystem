package Controller;

import Model.UsersTypes.SystemAdministrator;

import java.util.ArrayList;
import java.util.List;

public class System_Controller {
    private static boolean isInitialize = false;
    private static List<SystemAdministrator> systemAdministrators;
    private static System_Controller ourInstance= new System_Controller();

    private System_Controller() {
        systemAdministrators = new ArrayList<>();
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
    private static void startInitializeTheSystem() throws Exception {
        if(connectionToExternalSystems()){
            ShowAdminRegistrationForm();
        }
        else{
            throw new Exception("Something got wrong- function: startInitializeTheSystem in System_Controller");

        }

    }

    private void displaysMessageSuccessfulAdministratorRegistration() {
        //todo- Adds a successful administrator registration alert
        //  then- displayHomeScreen
        displayHomeScreen();
    }

    /**
     * This method will show the user the home screen
     */
    //todo
    private static void displayHomeScreen() {
    }

    /**
     * This method will close the displayed user window
     */
    //todo
    private void closeWindow() {

    }

    /**
     * An initial administrator registration form is displayed to the user
     */
    private static void ShowAdminRegistrationForm() {
        //todo
    }


    /**
     *When the user clicks the submit button
     * To register the administrator, use this method
     * @param allDetails - All parameters on the administrator
     * @throws Exception - If the registry could not be made from any error, this error will cause an appropriate message
     */
    private void initialAdministratorRegistration(String[] allDetails) throws Exception {
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
                SystemAdministrator systemAdministrator= new SystemAdministrator(username, password,id, firstName, lastName);
                systemAdministrators.add(systemAdministrator);
                closeWindow();
                displaysMessageSuccessfulAdministratorRegistration();
                isInitialize = true;
            } else {
                ArrayList whereIsDetailsProblem= problemWithTheDetails(isDetailsCorrect);
                closeWindow();
                ShowAgainAdminRegistrationForm(whereIsDetailsProblem);
            }
        }
    }

    private void ShowAgainAdminRegistrationForm(ArrayList<Integer> whereIsDetailsProblem) {
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
        if(logInToTheTaxLawSystem()){
            throw new Exception("problem in login tax law system");
        }
        else{
            throw new Exception("problem in registering system administrator");
        }

    }

    //todo
    private static boolean logInToTheTaxLawSystem() {
        return true;
    }
    //todo
    private static boolean logInToTheAccountingSystem() {
        return true;
    }

    public static void main(String[] args) throws Exception {
        if(isInitialize){
            displayHomeScreen();
        }
        else {
            startInitializeTheSystem();
        }

    }


}
