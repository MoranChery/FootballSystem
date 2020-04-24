package Service;

import Controller.SystemAdministratorController;

public class SystemAdministratorService {
    private SystemAdministratorController systemAdministratorController;

    public SystemAdministratorService() {
        this.systemAdministratorController = new SystemAdministratorController();
    }

    public void closeTeamForEver(String teamName){
        try {
            systemAdministratorController.closeTeamForEver(teamName);
        } catch (Exception e) {
            System.out.println("the team " + teamName + " doesn't exist in the system");
        }
    }

    public void removeSubscriber(String email){
        try {
            systemAdministratorController.removeSubscriber( email);
        } catch (Exception e) {
            System.out.println("the subscriber with the Email " + email + " doesn't in the system!");
        }
    }

}
