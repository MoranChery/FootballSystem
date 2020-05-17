package Controller;

import Controller.RepresentativeAssociationController;
import Controller.SubscriberController;
import Data.AlertDb;
import Data.GameDb;
import Data.SubscriberDb;
import Model.Alert;
import Model.Enums.Status;
import Model.Game;
import Model.UsersTypes.Judge;
import Model.UsersTypes.Subscriber;

import java.awt.*;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;

public class NotificationController extends Observable implements Observer {

    private SubscriberDb subscriberDb;
    private Map<String, Subscriber> allSubscribersThatNeedToGetAlerts;
    private RepresentativeAssociationController repControll;
    private SubscriberController subscriberController;
    private AlertDb alertDb;

    @Override
    public void update(Observable o, Object arg) {
        if (o == repControll){
            Object[] theValues = (Object[]) arg;
            Alert alert = createAlert(theValues[0].toString(), theValues[1]);
            Game theGame = (Game) theValues[1];
            Set<String> judges = theGame.getJudgesOfTheGameList();
            for (String j: judges) {
                try {
                    Judge judge = (Judge) subscriberDb.getSubscriber(j);
                    if(judge.getStatus().equals(Status.ONLINE)){
                        sendMessage(alert);
                    }
                    else{
                        alertDb.createAlertInDb(judge.getEmailAddress(), alert);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
//    @Override
//    public void update(Observable o, Object arg) {
//        if (o == repControll){
//            Object[] theValues = (Object[]) arg;
//            Alert alert = createAlert(theValues[0].toString(), theValues[1]);
//            Game theGame = (Game) theValues[1];
//            Set<String> judges = theGame.getJudgesOfTheGameList();
//            if(theValues[0].equals("location")){
//                // there was change in the location of the game
//                for (String j: judges) {
//                    try {
//                        Judge judge = (Judge) subscriberDb.getSubscriber(j);
//                        if(judge.getStatus().equals(Status.ONLINE)){
//                            sendMessage(alert);
//                        }
//                        else{
//                            alertDb.createAlertInDb(judge.getEmailAddress(), alert);
//                        }
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//            if(theValues[0].equals("date")){
//                Set<Judge> judges = (Set<Judge>) theValues[2];
//                for (Judge j: judges) {
//                    if(j.getStatus().equals(Status.ONLINE)){
//                        sendMessage(alert);
//                    }
//                    else {
//                        alertMapToSave.put(j.getEmailAddress(), alert);
//                    }
//                }
//            }
//        }
//    }


    /**
     * This function create instance of alert that need to send.
     * It's uses the info and create the head and the body of the alert
     * @param typeOfMessage - String - the type of event that need to send alert
     * @param theObject
     * @return Alert - the alert that created
     */
    public Alert createAlert(String typeOfMessage, Object theObject){
        Alert alertToSend = null;
        if(typeOfMessage.equals("location")){
            String header = "Dear judge, There was change in the location of a game you assigned to";
            Game game = (Game)theObject;
            String body = "The Game " + game.getGameID() + " between " + game.getHostTeam().getTeamName() + " And"
                    + game.getGuestTeam().getTeamName() + " have new location. The new court is" + game.getCourt().getCourtCity();
            alertToSend = new Alert(header, body);
        }
        if (typeOfMessage.equals("date")){
            String header = "Dear judge, There was change in the date of a game you assigned to";
            Game game = (Game)theObject;
            String body = "The Game " + game.getGameID() + " between " + game.getHostTeam().getTeamName() + " And"
                    + game.getGuestTeam().getTeamName() + " have new date. The new date is" + game.getGameDate();
            alertToSend.setMsgHeader(header);
            alertToSend.setMsgBody(body);
        }
        return alertToSend;
    }

    /**
     * This function send an alert to subscribers that need to get this message
     * @param theAlert Alert - the message this function need to send
     */
    public void sendMessage(Alert theAlert){

    }
}
