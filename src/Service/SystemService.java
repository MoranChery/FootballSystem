package Service;

import Controller.SystemController;
import Model.AccountingSystem;
import Model.TaxLawSystem;

public class SystemService{

    private SystemController systemController;

    public SystemService() throws Exception {
        systemController = SystemController.getInstance();

        systemController.connectionToExternalSystems();
    }

    public static void createLog(String path) throws Exception {
        try{
            SystemController.createLog(path);
        }
        catch (Exception e){
            throw new Exception("Can't create Log");
        }
    }

}
