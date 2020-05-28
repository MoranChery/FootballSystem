package Service.OutSystems;

import Controller.TeamOwnerController;
import Model.LoggerHandler;
import Service.TeamOwnerService;

import java.util.logging.Level;
import java.util.logging.Logger;

public class ProxyAssociationAccountingSystem implements IAssociationAccountingSystem {

    private Logger logger = Logger.getLogger("AccountingSystem");

    private static IAssociationAccountingSystem associationAccountingSystem = new ProxyAssociationAccountingSystem();

    private IAssociationAccountingSystem accountingSystem;

    public ProxyAssociationAccountingSystem() {
        this.accountingSystem = new RealAssociationAccountingSystem();
        logger.addHandler(LoggerHandler.loggerErrorFileHandler);
        logger.addHandler(LoggerHandler.loggerEventFileHandler);
    }

    public static IAssociationAccountingSystem getInstance() {
        return associationAccountingSystem;
    }

    @Override
    public boolean addPayment(String teamName, String date, double amount) {
        if (teamName == null || date == null)
            return false;
        return accountingSystem.addPayment(teamName, date, amount);
    }

    @Override
    public void connectTo(String serverHost) throws Exception {
        try {
            accountingSystem.connectTo(serverHost);
            logger.log(Level.INFO, "Association Accounting System is connected!");
        }catch (Exception e){
            logger.log(Level.WARNING, "Association Accounting System is NOT connected!");

        }
    }

    @Override
    public boolean isConnected() {
        return accountingSystem.isConnected();
    }

}
