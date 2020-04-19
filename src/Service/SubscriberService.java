package Service;

import Controller.SubscriberController;

public class SubscriberService {
    private SubscriberController subscriberController;

    public SubscriberService() {
        this.subscriberController = new SubscriberController();
    }

    public void registerSubscriber(String userType) {
        try {
            subscriberController.registerSubscriber(userType);
        } catch (Exception e) {
            System.out.println("try again!");
        }
    }

    public void login(String emailAddress, String password) {
        try {
            subscriberController.login(emailAddress, password);
        } catch (Exception e) {
            System.out.println("try again to login!");
        }
    }

    public void showInformation(String subject) {
        subscriberController.showInformation(subject);
    }

    public void searchInformation(String input) {
        if (!subscriberController.searchInformation(input))
            System.out.println("no found results with the search input: " + input);
    }
}
