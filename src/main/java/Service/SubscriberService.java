package Service;

import Controller.SubscriberController;

public class SubscriberService {
    private SubscriberController subscriberController;

    public SubscriberService() {
        this.subscriberController = new SubscriberController();
    }

    public void logOut(String subscriberMail) throws Exception{
        subscriberController.logOut(subscriberMail);
    }
    public void wantToEditPassword(String subscriberMail, String newPassword) throws Exception {
        subscriberController.wantToEditPassword(subscriberMail, newPassword);
    }
    public void wantToEditFirstName(String subscriberMail, String newFirstName) throws Exception {
        subscriberController.wantToEditFirstName(subscriberMail, newFirstName);
    }
    public void wantToEditLastName(String subscriberMail, String newLastName) throws Exception {
        subscriberController.wantToEditLastName(subscriberMail, newLastName);
    }

}
