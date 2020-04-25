package Model;

import Model.UsersTypes.Subscriber;


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
