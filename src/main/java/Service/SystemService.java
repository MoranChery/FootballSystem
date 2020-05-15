package Service;

import Controller.SystemController;

public class SystemService{

    private static SystemController systemController;

    public SystemService() {
        systemController = SystemController.getInstance();
        systemController.connectionToExternalSystems();
    }

    public static void createLog(String path) throws Exception {
        systemController.createLog(path);
    }

}
