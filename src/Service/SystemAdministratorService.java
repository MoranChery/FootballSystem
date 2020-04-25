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

    public void removeSubscriber(String email) throws Exception {
        try {
            systemAdministratorController.removeSubscriber(email);
        } catch (Exception e) {
            throw new Exception("the subscriber with the Email " + email + " doesn't in the system!");
        }
    }

}
