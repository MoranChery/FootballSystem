package Service;

import Controller.GuestController;
import Controller.SubscriberController;
import Model.LoggerHandler;
import Model.UsersTypes.Judge;
import Model.UsersTypes.RepresentativeAssociation;
import Model.UsersTypes.Subscriber;
import Model.UsersTypes.TeamOwner;
import components.CreateLogInRequest;
import components.CreateTeamRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

@RestController
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

    @ResponseStatus(HttpStatus.OK)
    @CrossOrigin(origins = "http://localhost:63342")
    @GetMapping(value = "/login/{email}/{pass}/")
    public Map<String, String> login(@PathVariable String email, @PathVariable String pass) throws Exception {
            String emailAddress = email;
            String password = pass;

            try {
                guestController.login(emailAddress, password);
                SubscriberController subscriberController = new SubscriberController();
                Subscriber subscriber= subscriberController.getSubscriber(emailAddress);
                String type="";
                if(subscriber instanceof TeamOwner){
                    type = "teamOwner";
                }
                else if(subscriber instanceof RepresentativeAssociation){
                    type = "representativeAssociation";
                }
                else if(subscriber instanceof Judge){
                    type ="judge";
                }
                else{
                    type = "user";
                }
                Map<String, String> stringStringMap = new HashMap<>();
                stringStringMap.put("UserType",type);
                loggerHandler.getLoggerEvents().log(Level.INFO, "Created by: " + emailAddress + " Description: Subscriber \"" + emailAddress + "\" was login");
                return stringStringMap;
            } catch (Exception e) {
                loggerHandler.getLoggerErrors().log(Level.WARNING, "Created by: " + emailAddress + " Description: Subscriber \"" + emailAddress + "\" wasn't login because: " + e.getMessage());
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Try again");
            }
    }
}
