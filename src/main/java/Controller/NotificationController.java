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
import com.sun.mail.smtp.SMTPTransport;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.awt.*;
import java.util.*;
import java.util.List;

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
                    Subscriber subscriber = subscriberDb.getSubscriber(j);
                    if(subscriber.isWantAlertInMail() == true){
                        sendMessageInMail(j, alert);
                    }
                    else {
                        alertDb.createAlertInDb(j, alert);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }


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
    public void sendMessageInMail(String userMail, Alert theAlert){
//        String SMTP_SERVER = "smtp server ";
//        String USERNAME = "";
//        String PASSWORD = "";
//        String EMAIL_FROM = "noasatla@gmail.com";
//        String EMAIL_TO = userMail;
//        String EMAIL_TO_CC = "";
////        String EMAIL_SUBJECT = theAlert.getMsgHeader();
////        String EMAIL_TEXT = theAlert.getMsgBody();
//        String EMAIL_SUBJECT = "Test Send Email via SMTP";
//        String EMAIL_TEXT = "Hello Java Mail \n ABC123";
//        Properties prop = System.getProperties();
//        prop.put("mail.smtp.host", SMTP_SERVER);
//        prop.put("mail.smtp.auth", "true");
//        prop.put("mail.smtp.port", "25");
//        Session session = Session.getInstance(prop, null);
//        Message msg = new MimeMessage(session);
//        try {
//            // from
//            msg.setFrom(new InternetAddress(EMAIL_FROM));
//
//            // to
//            msg.setRecipients(Message.RecipientType.TO,
//                    InternetAddress.parse(EMAIL_TO, false));
//
//            // cc
//            msg.setRecipients(Message.RecipientType.CC,
//                    InternetAddress.parse(EMAIL_TO_CC, false));
//
//            // subject
//            msg.setSubject(EMAIL_SUBJECT);
//
//            // content
//            msg.setText(EMAIL_TEXT);
//
//            msg.setSentDate(new Date());
//
//            // Get SMTPTransport
//            SMTPTransport t = (SMTPTransport) session.getTransport("smtp");
//
//            // connect
//            t.connect(SMTP_SERVER, USERNAME, PASSWORD);
//
//            // send
//            t.sendMessage(msg, msg.getAllRecipients());
//
//            System.out.println("Response: " + t.getLastServerResponse());
//
//            t.close();
//
//        } catch (MessagingException e) {
//            e.printStackTrace();
//        }



    }
//    public List<Alert> getAlert(String userMail) throws Exception {
//        if(userMail.isEmpty()){
//            throw new Exception("bad input");
//        }
//        List<Alert> userAlerts = null;
//        if(alertDb.haveAlertInDB(userMail)){
//            userAlerts = alertDb.getAlertsForUser(userMail);
//        }
//        return userAlerts;
//    }
}



