package Model;

import Controller.RepresentativeAssociationController;
import Data.SubscriberDb;
import Model.UsersTypes.Subscriber;

import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;

public class Notification extends Observable implements Observer {

    private SubscriberDb subscriberDb;
    private Map<String, Subscriber> allSubscribersThatNeedToGetAlerts;
    private Map<String,Alert> alertMapToSave;
    private RepresentativeAssociationController repControll;



    @Override
    public void update(Observable o, Object arg) {
        if (o == repControll){
            String[] theValues = (String[])arg;
            if(theValues[0].equals("location")){
//                Game gameForData =
            }
        }
    }

    public void createAlert(){

    }
}
