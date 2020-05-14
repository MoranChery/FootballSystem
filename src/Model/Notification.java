package Model;

import Controller.RepresentativeAssociationController;
import Controller.SubscriberController;
import Data.GameDb;
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
    private SubscriberController subscriberController;


    @Override
    public void update(Observable o, Object arg) {
        if (o == repControll){
            Object[] theValues = (Object[]) arg;
            if(theValues[0].equals("location")){

            }
        }
    }

    public void createAlert(){

    }
}
