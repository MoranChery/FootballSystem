package Controller;

import Model.UsersTypes.SystemAdministrator;

import java.util.ArrayList;
import java.util.List;

public class System_Controller {
    private static boolean isInitialize = false;
    private List<SystemAdministrator> systemAdministrators;
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

    private static void startInitializeTheSystem() throws Exception {
        if(connectionToExternalSystems()){

//            SystemAdministrator systemAdministrator= addSystemManger(username, password,id, firstName, lastName);
//            if(systemAdministrator!=null){
//                systemAdministrators.add(systemAdministrator);
//                return true;
//            }
        }
        else{

        }

    }
    //todo
    private boolean addFirstSystemAdministrator(){
        String[] allDetails =getDetailsFromUser();
        if(allDetails!=null) {
            boolean[] isDetailsCorrect = checkDetails(allDetails);
            boolean isProblem = false;
            for (int i = 0; i < isDetailsCorrect.length; i++) {
                if (!isDetailsCorrect[i]) {
                    isProblem = true;
                    break;
                }
            }
            if (!isProblem) {
                isInitialize = true;

                //todo

                return true;
            } else {
                problemWithTheDetails(isDetailsCorrect);
            }
        }
        return false;
    }

    //todo
    private static void problemWithTheDetails(boolean[] isDetailsCorrect) {

    }

    //todo
    private static boolean[] checkDetails(String[] allDetails) {
        return null;
    }

    //todo
    private static String[] getDetailsFromUser() {
        return null;
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

    private SystemAdministrator addSystemManger(String username, String password,Integer id, String firstName, String lastName) {
        SystemAdministrator systemAdministratorToRetern = new SystemAdministrator(username, password,id, firstName, lastName);
        return systemAdministratorToRetern;
    }

    private static boolean logInToTheTaxLawSystem() {
        return true;
    }

    private static boolean logInToTheAccountingSystem() {
        return true;
    }

    public static void main(String[] args) throws Exception {
        if(isInitialize){
            //todo
            // Display appropriate interface
            // Probably a front page or something
        }
        else {
            startInitializeTheSystem();
        }

    }


}
