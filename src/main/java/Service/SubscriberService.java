package Service;

import Controller.SubscriberController;
import Model.Alert;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class SubscriberService{
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

    @ResponseStatus(HttpStatus.OK)
    @CrossOrigin(origins = "http://localhost:63342")
    @GetMapping(value = "/getAlerts/{userEmail}")
    public Map<String, Alert> getAlerts(@PathVariable String userEmail) {
        try {
            if (!subscriberController.wantedByMail(userEmail)) {
                Map<String, Alert> messageAndValue = new HashMap<>();
                List<Alert> alertList = subscriberController.getAlerts(userEmail);
                if (alertList != null && alertList.size() > 0) {
                    for (int i = 0; i < alertList.size(); i++) {
                        String message = String.valueOf(i);
                        messageAndValue.put(message, alertList.get(i));
                    }
                    return messageAndValue;
                } else {
                    throw new ResponseStatusException(HttpStatus.OK);
                }
            } else {
                throw new ResponseStatusException(HttpStatus.OK);
            }
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.OK);
        }
    }



    @ResponseStatus(HttpStatus.OK)
    @CrossOrigin(origins = "http://localhost:63342")
    @GetMapping(value = "/setSubscriberWantAlertInMail/{userEmail}")
    public void setSubscriberWantAlertInMail(@PathVariable String userEmail){
        try{

         subscriberController.setSubscriberWantAlert(userEmail);

        }
        catch (Exception e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

    }
}
