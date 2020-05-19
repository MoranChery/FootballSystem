package Service;

import Controller.SubscriberController;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;

@RestController
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

    @ResponseStatus(HttpStatus.OK)
    @CrossOrigin(origins = "http://localhost:63342")
    @GetMapping(value = "/getAlerts/{userEmail}/")
    public Map<String, String> getAlerts(@PathVariable String userEmail){
        try{

            Map<String, String>  messageAndValue = new HashMap<>();

            // todo = key mast be "message"
        }
        catch (Exception e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "try Not Found", e);
        }
        return null;
    }

}
