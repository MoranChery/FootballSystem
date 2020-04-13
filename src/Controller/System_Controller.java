package Controller;

import Model.UsersTypes.SystemAdministrator;

import java.util.ArrayList;
import java.util.List;

public class System_Controller {
    private static boolean isInitialize = false;
    List<SystemAdministrator> systemAdministrators;
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

    private System_Controller startInitializeTheSystem(){
        String[] allDetails =getDetailsFromUser();
        if(allDetails!=null) {
            boolean[] isDetailsCorrect = (checkDetails(allDetails));
            boolean isProblem = false;
            for (int i = 0; i < isDetailsCorrect.length; i++) {
                if (!isDetailsCorrect[i]) {
                    isProblem = true;
                }
            }
            if (!isProblem) {
                isInitialize = true;
            } else {
                problemWithTheDetails(isDetailsCorrect);

            }
        }
        return null;
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


    private System_Controller(String username, String password,Integer id, String firstName, String lastName) throws Exception {
        systemAdministrators = new ArrayList<>();
        initialization(username, password,id, firstName, lastName);
    }
    private boolean initialization(String username, String password,Integer id, String firstName, String lastName) throws Exception {
        if(!logInToTheAccountingSystem()){
            throw new Exception("problem in login accounting system");
        }
        if(logInToTheTaxLawSystem()){
            throw new Exception("problem in login tax law system");
        }
        SystemAdministrator systemAdministrator= addSystemManger(username, password,id, firstName, lastName);
        if(systemAdministrator!=null){
            systemAdministrators.add(systemAdministrator);
            return true;
        }
        else{
            throw new Exception("problem in registering system administrator");
        }

    }

    private SystemAdministrator addSystemManger(String username, String password,Integer id, String firstName, String lastName) {
        SystemAdministrator systemAdministratorToRetern = new SystemAdministrator(username, password,id, firstName, lastName);
        return systemAdministratorToRetern;
    }

    private boolean logInToTheTaxLawSystem() {
        return true;
    }

    private boolean logInToTheAccountingSystem() {
        return true;
    }

    public static void main(String[] args){

    }


}
