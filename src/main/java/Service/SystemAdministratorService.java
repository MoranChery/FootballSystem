package Service;

import Controller.SystemAdministratorController;

public class SystemAdministratorService {
    private SystemAdministratorController systemAdministratorController;

    public SystemAdministratorService() {
        this.systemAdministratorController = new SystemAdministratorController();
    }

    public void closeTeamForEver(String teamName) throws Exception {
        if (teamName == null)
            throw new Exception("null team name exception!");
        try {
            systemAdministratorController.closeTeamForEver(teamName);
        } catch (Exception e) {
            throw new Exception("the team " + teamName + " doesn't exist in the system");
        }
    }

    public void removeSubscriber(String emailToRemove,String emailOfSystemManager) throws Exception {
        try {
            systemAdministratorController.removeSubscriber(emailToRemove,emailOfSystemManager);
        } catch (Exception e) {
            throw new Exception("the subscriber with the Email " + emailToRemove + "or the Email "+emailOfSystemManager+" doesn't in the system!");
        }
    }

}
