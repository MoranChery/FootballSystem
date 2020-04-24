package Service;

import Controller.GuestController;

public class GuestService {
    private GuestController guestController;

    public GuestService() {
        this.guestController = new GuestController();
    }


    public void registerSubscriber(String userType) {
        try {
            guestController.registerSubscriber(userType);
        } catch (Exception e) {
            System.out.println("try again!");
        }
    }


    public void login(String emailAddress, String password) {
        try {
            guestController.login(emailAddress, password);
        } catch (Exception e) {
            System.out.println("try again to login!");
        }
    }
}
