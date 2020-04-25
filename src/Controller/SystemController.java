package Controller;

import Data.*;

import Model.*;
import Model.Enums.RoleType;
import Model.UsersTypes.SystemAdministrator;
import Model.UsersTypes.TeamManager;

public class SystemController {
    private static SubscriberDb subscriberDb;
    private static RoleDb roleDb;
    private static SystemAdministratorDb systemAdministratorDb;

    private LogFunctionality log;
    public  AccountingSystem accountingSystem;
    public  TaxLawSystem taxLawSystem;


    public SystemController() {
        systemAdministratorDb = SystemAdministratorDbInMemory.getInstance();
        subscriberDb = SubscriberDbInMemory.getInstance();
        roleDb = RoleDbInMemory.getInstance();
        log = new LogFunctionality();
        accountingSystem = new AccountingSystem();
        taxLawSystem = new TaxLawSystem();
    }

    private static SystemController ourInstance;
    static {
        try {
            ourInstance = new SystemController();
            addSystemAdministrator();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static SystemController getInstance() {
        return ourInstance;
    }


    public boolean connectionToExternalSystems() throws Exception {
        if (!connectToTheAccountingSystem()) {
            throw new Exception("problem in connection with accounting system");
        }
        if (!connectToTheTaxLawSystem()) {
            throw new Exception("problem in connection with tax law system");
        }
        return true;
    }

    private boolean connectToTheAccountingSystem() {
        //todo
        return true;
    }

    private boolean connectToTheTaxLawSystem() {
        //todo
        return true;
    }


    private static void addSystemAdministrator() throws Exception {
        String emailAddress = "admin@gmail.com";
        try {
            systemAdministratorDb.getSystemAdministrator(emailAddress);
        }
        catch (NotFoundException notFoundEx) {
            SystemAdministrator systemAdministrator = new SystemAdministrator(emailAddress, "admin123", 111111111, "admin", "admin");
            subscriberDb.createSubscriber(systemAdministrator);
            systemAdministratorDb.createSystemAdministrator(systemAdministrator);
            roleDb.createRoleInSystem(emailAddress, RoleType.SYSTEM_ADMINISTRATOR);
        }catch(Exception e){
            throw new Exception("Problem-initialAdministratorRegistration");
        }
    }

    /**
     * This method creates the log file
     * @param path - The path where to save the log file
     */
    public static void createLog(String path) throws Exception {
        //todo
    }
}

   







