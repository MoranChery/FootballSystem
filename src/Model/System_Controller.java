package Model;

import Model.UsersTypes.SystemAdministrator;

import java.util.ArrayList;
import java.util.List;

public class System_Controller {

    List<SystemAdministrator> systemAdministrators;

    private static System_Controller ourInstance = new System_Controller();

    public static System_Controller getInstance() {
        return ourInstance;
    }

    private System_Controller() {
        systemAdministrators = new ArrayList<>();
    }
    private boolean initialization() throws Exception {
        if(!logInToTheAccountingSystem()){
            throw new Exception("problem in login accounting system");
        }
        if(logInToTheTaxLawSystem()){
            throw new Exception("problem in login tax law system");
        }
        SystemAdministrator systemAdministrator= addSystemManger();
        if(systemAdministrator!=null){
            systemAdministrators.add(systemAdministrator);
            return true;
        }
        else{
            throw new Exception("problem in registering system administrator");
        }

    }

    private SystemAdministrator addSystemManger() {

        return null;
    }

    private boolean logInToTheTaxLawSystem() {
        return true;
    }

    private boolean logInToTheAccountingSystem() {
        return true;
    }

    public static void main(String[] args){
        System.out.println("try");
    }


}
