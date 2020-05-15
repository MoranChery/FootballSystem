package Service;

import Controller.GuestController;
import Model.LoggerHandler;

import java.util.logging.Level;

public class GuestService {
    private GuestController guestController;
    private LoggerHandler loggerHandler;

    public GuestService() {
        this.guestController = new GuestController();
        this.loggerHandler = new LoggerHandler(GuestService.class.getName());
    }

    public void registerSubscriber(String userType) {
        guestController.registerSubscriber(userType);
    }

    public void login(String emailAddress, String password) throws Exception {
        try {
            guestController.login(emailAddress, password);
            loggerHandler.getLoggerEvents().log(Level.INFO, "Created by: " + emailAddress + " Description: Subscriber \"" + emailAddress + "\" was login");
        } catch (Exception e) {
                loggerHandler.getLoggerErrors().log(Level.WARNING, "Created by: " + emailAddress + " Description: Subscriber \"" + emailAddress + "\" wasn't login because: " + e.getMessage());
                throw e;
        }
    }
}
