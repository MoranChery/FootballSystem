package Service;

import Controller.SystemController;

public class SystemService{

    private static SystemController systemController;

    public SystemService() {
        systemController = SystemController.getInstance();
        systemController.connectToAssociationAccountingSystem("AssociationAccountingSystemPort");
        systemController.connectToTaxSystem("TaxSystemPort");
    }

    public static void createLog(String path) throws Exception {
        systemController.createLog(path);
    }

}
