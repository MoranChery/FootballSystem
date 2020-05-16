package Model;

import Controller.RepresentativeAssociationController;
import Controller.SubscriberController;
import Data.GameDb;
import Data.SubscriberDb;
import Model.Enums.Status;
import Model.UsersTypes.Judge;
import Model.UsersTypes.Subscriber;

import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;

public class Notification extends Observable implements Observer {

    private SubscriberDb subscriberDb;
    private Map<String, Subscriber> allSubscribersThatNeedToGetAlerts;
    private Map<String,Alert> alertMapToSave; // <subscriberMail, Alert>
    private RepresentativeAssociationController repControll;
    private SubscriberController subscriberController;


    @Override
    public void update(Observable o, Object arg) {
        if (o == repControll){
            Object[] theValues = (Object[]) arg;
            Alert alert = createAlert(theValues[0].toString());
            if(theValues[0].equals("location")){
                // there was change in the location of the game
                Set<Judge> judges = (Set<Judge>) theValues[2];
                for (Judge j: judges) {
                    if(j.getStatus().equals(Status.ONLINE)){
                        sendMessage();
                    }
                    else{
                        alertMapToSave.put(j.getEmailAddress(), alert);
                    }
                }
            }
            if(theValues[0].equals("date")){
                Set<Judge> judges = (Set<Judge>) theValues[2];
                for (Judge j: judges) {
                    if(j.getStatus().equals(Status.ONLINE)){
                        sendMessage();
                    }
                    else {
                        alertMapToSave.put(j.getEmailAddress(), alert);
                    }
                }
            }
        }
    }


    public Alert createAlert(String typeOfMessage){
        Alert alertToSend = null;
        if(typeOfMessage.equals("location")){
            String header = "Dear judge";
        }
        return alertToSend;
    }

    public void sendMessage(){

    }
}
