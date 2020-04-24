package Service;

import Controller.GuestController;

public class GuestService {
    private GuestController guestController;

    public GuestService() {
        this.guestController = new GuestController();
    }


    public void registerSubscriber(String userType) {
       guestController.registerSubscriber(userType);
    }


    public boolean login(String emailAddress, String password) throws Exception {
        return guestController.login(emailAddress, password);
    }
}
