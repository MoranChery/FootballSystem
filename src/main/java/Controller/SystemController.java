package Controller;

import Data.*;
import Model.AccountingSystem;
import Model.Enums.RoleType;
import Model.LoggerHandler;
import Model.TaxLawSystem;
import Model.UsersTypes.SystemAdministrator;
import Service.OutSystems.IAssociationAccountingSystem;
import Service.OutSystems.ITaxSystem;
import Service.OutSystems.ProxyAssociationAccountingSystem;
import Service.OutSystems.ProxyTaxSystem;
import Service.SystemService;

public class SystemController {
    private static SubscriberDb subscriberDb;
    private static RoleDb roleDb;
    private static SystemAdministratorDb systemAdministratorDb;

    private LoggerHandler loggerHandler;
    private ITaxSystem proxyTaxSystem;
    private IAssociationAccountingSystem proxyAssociationAccountingSystem;

    private static SystemController ourInstance;
    static  {
        ourInstance = new SystemController();
        try {
            addSystemAdministrator();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private SystemController() {
        systemAdministratorDb = SystemAdministratorDbInServer.getInstance();
        subscriberDb = SubscriberDbInServer.getInstance();
        roleDb = RoleDbInServer.getInstance();
        this.proxyTaxSystem = ProxyTaxSystem.getInstance();
        this.proxyAssociationAccountingSystem = ProxyAssociationAccountingSystem.getInstance();
    }

    /**
     * initializing the SystemController for the first and only time
     * add first SystemAdministrator to the system
     * should be privet but for the tests - public
     */

    public static void initialSystem() throws Exception {
        ourInstance = new SystemController();
        addSystemAdministrator();
    }

    public static SystemController getInstance() {
        return ourInstance;
    }


    /**
     * This function represent the connection of the system with the external systems:
     * the AccountingSystem and the TaxLawSystem
     * should be privet but for the tests - public
     * should be not static but for the tests - static
     * @throws Exception if the system couldn't connect with the external systems
     */
    public void isConnectionToExternalSystems() throws Exception {
        if(!(isConnectToTheAccountingSystem() || isConnectToTheTaxLawSystem())){
            throw new Exception("problem in connection with accounting system and with  tax law  system");
        }
        if (!isConnectToTheAccountingSystem()) {
            throw new Exception("problem in connection with accounting system");
        }
        if (!isConnectToTheTaxLawSystem()) {
            throw new Exception("problem in connection with tax law system");
        }
    }

    /**
     * connection to the Tax system with the Proxy
     * @param serverhost
     */
    public void connectToTaxSystem(String serverhost){
        try {
            proxyTaxSystem.connectTo(serverhost);
        } catch (Exception e) {
            System.out.println("something was wrong with tax system connecting");
        }
    }

    /**
     * connection to the Association Accounting system with the Proxy
     * @param serverhost
     */
    public void connectToAssociationAccountingSystem(String serverhost){
        try {
            proxyAssociationAccountingSystem.connectTo(serverhost);
        } catch (Exception e) {
            System.out.println("something was wrong with Association Accounting System connecting");
        }
    }

    /**
     * This function represent the connection of our system with the AccountingSystem
     * next iteration
     * should be privet but for the tests - protected
     * @return boolean - true if the connection ended successfully
     */
    protected boolean isConnectToTheAccountingSystem() {

        return proxyAssociationAccountingSystem.isConnected();

    }

    /**
     * This function represent the connection of our system with the TaxLawSystem
     * next iteration
     * should be privet but for the tests - protected
     * @return boolean - true if the connection ended successfully
     */
    protected boolean isConnectToTheTaxLawSystem() {

        return proxyTaxSystem.isConnected();

    }


    /**
     * This function create the first SystemAdministrator in the system
     * using default id - email address the system try to find if the SystemAdministrator already in the DB
     * if he isn't - create one using default data
     * should be privet but for the tests - public
     * @throws Exception if couldn't create the SystemAdministrator
     */
    public static void addSystemAdministrator() throws Exception {
        String emailAddress = "admin@gmail.com";
        try {
            systemAdministratorDb.getSystemAdministrator(emailAddress);
        }
        catch (NotFoundException notFoundEx) {
            SystemAdministrator systemAdministrator = new SystemAdministrator(emailAddress, "admin123", 111111111, "admin", "admin");
            subscriberDb.insertSubscriber(systemAdministrator);
            systemAdministratorDb.insertSystemAdministrator(systemAdministrator);
            roleDb.createRoleInSystem(emailAddress, RoleType.SYSTEM_ADMINISTRATOR);
        }
    }


}

   







