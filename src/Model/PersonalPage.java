package Model;

import Controller.System_Controller;
import Model.UsersTypes.Fan;
import Model.UsersTypes.Subscriber;

import java.util.HashMap;
import java.util.Map;


public class PersonalPage extends Page {
    Subscriber subscriber;

    public PersonalPage(String ownerID) throws Exception {
        super(ownerID);
    }
    public PersonalPage(String ownerID,Subscriber subscriber) throws Exception {
        super(ownerID);
        this.subscriber = subscriber;
    }

}
