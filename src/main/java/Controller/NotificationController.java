package Controller;

import Controller.RepresentativeAssociationController;
import Controller.SubscriberController;
import Data.*;
import Model.Alert;
import Model.Enums.Status;
import Model.Enums.TeamStatus;
import Model.Game;
import Model.Team;
import Model.UsersTypes.Judge;
import Model.UsersTypes.Subscriber;
import Model.UsersTypes.TeamManager;
import Model.UsersTypes.TeamOwner;
import com.sun.mail.smtp.SMTPTransport;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.awt.*;
import java.util.*;
import java.util.List;

public class NotificationController extends Observable implements Observer {

    private SubscriberDb subscriberDb;
    private RepresentativeAssociationController repControll;
    private SubscriberController subscriberController;
    private SystemAdministratorController saController;
    private TeamOwnerController teamOwnerController;
    private AlertDb alertDb;
    //private TeamDb teamDb;

    public NotificationController() {
    }

    public NotificationController(RepresentativeAssociationController repControll, SubscriberController subscriberController, SystemAdministratorController saController, TeamOwnerController teamOwnerController) {
        this.repControll = repControll;
        this.subscriberController = subscriberController;
        this.saController = saController;
        this.teamOwnerController = teamOwnerController;
        //this.teamDb = TeamDbInMemory.getInstance();
        alertDb = AlertDbInMemory.getInstance();
    }

    @Override
    public void update(Observable o, Object arg) {
        if (o == repControll){
            Object[] theValues = (Object[]) arg;
            Alert alert = createAlert(theValues[0].toString(), theValues[1], theValues[2]);
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
        if(o == teamOwnerController){
            Object[] theValues = (Object[]) arg;
            Alert alert = createAlert(theValues[0].toString(), theValues[1], theValues[2]);
            if(theValues[0].equals("status")){
                Team theTeam = (Team) theValues[1];
                Map <String, TeamOwner> allTeamOwners = theTeam.getTeamOwners();
                Map<String, TeamManager> allTeamManagers = theTeam.getTeamManagers();
                for (String ownerEmail: allTeamOwners.keySet()) {
                    TeamOwner teamOwner = allTeamOwners.get(ownerEmail);
                    if(teamOwner.isWantAlertInMail()){
                        sendMessageInMail(ownerEmail, alert);
                    }
                    else {
                        try {
                            alertDb.createAlertInDb(ownerEmail, alert);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
                for (String managerEmail: allTeamManagers.keySet()) {
                    TeamManager teamManager = allTeamManagers.get(managerEmail);
                    if (teamManager.isWantAlertInMail()){
                        sendMessageInMail(managerEmail, alert);
                    }
                    else {
                        try {
                            alertDb.createAlertInDb(managerEmail, alert);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
            if(theValues[0].equals("removed")){
                try {
                    TeamOwner teamOwnerRemoved = (TeamOwner) theValues[2];
                    if(teamOwnerRemoved.isWantAlertInMail()){
                        sendMessageInMail(teamOwnerRemoved.getEmailAddress(), alert);
                    }
                    else {
                        alertDb.createAlertInDb(teamOwnerRemoved.getEmailAddress(), alert);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        if (o == saController){
            Object[] theValues = (Object[]) arg;
            Alert alert = createAlert(theValues[0].toString(), theValues[1], theValues[2]);

        }
    }


    /**
     * This function create instance of alert that need to send.
     * It's uses the info and create the head and the body of the alert
     * @param typeOfMessage - String - the type of event that need to send alert
     * @param theObject
     * @return Alert - the alert that created
     */
    public Alert createAlert(String typeOfMessage, Object theObject, Object theChange){
        Alert alertToSend = null;
        if(typeOfMessage.equals("location")){
            String header = "There was change in the location of a game you assigned to";
            Game game = (Game)theObject;
            String body = "Dear judge, \n The Game " + game.getGameID() + " between " + game.getHostTeam().getTeamName() + " And"
                    + game.getGuestTeam().getTeamName() + " have new location. The new court is" + theChange.toString();
            alertToSend = new Alert(header, body);
        }
        if (typeOfMessage.equals("date")){
            String header = "There was change in the date of a game you assigned to";
            Game game = (Game)theObject;
            Date gameDate = (Date) theChange;
            String body = "Dear judge, \n The Game " + game.getGameID() + " between " + game.getHostTeam().getTeamName() + " And"
                    + game.getGuestTeam().getTeamName() + " have new date. The new date is" + gameDate.getTime();
            alertToSend.setMsgHeader(header);
            alertToSend.setMsgBody(body);
        }
        if(typeOfMessage.equals("status")){
            Team team = (Team)theObject;
            String header = "The status of your team have been changed";
            TeamStatus teamStatus = (TeamStatus)theChange;
            String body = "The Team " + team.getTeamName() + " new status is " + teamStatus;
            alertToSend.setMsgHeader(header);
            alertToSend.setMsgBody(body);
        }
        if(typeOfMessage.equals("removed")){
            TeamOwner teamOwner = (TeamOwner) theChange;
            String head = "You'r account have changed";
            String body = "Dear " + teamOwner.getFirstName() + "  " + teamOwner.getLastName() + "\n" +
            "We are sorry to inform you that your subscription have been removed. \n You are now no longer " +
                    teamOwner.getTeam() + " owner.";
            alertToSend.setMsgHeader(head);
            alertToSend.setMsgBody(body);

        }
        return alertToSend;
    }

    /**
     * This function send an alert to subscribers that need to get this message
     * @param theAlert Alert - the message this function need to send
     */
    public boolean sendMessageInMail(String userMail, Alert theAlert){
        // Get a Properties object
        Properties props = System.getProperties();
        props.put("mail.smtp.host", "smtp.gmail.com"); //SMTP Host
        props.put("mail.smtp.port", "465");                 //TLS Port 465
        props.put("mail.smtp.auth", "true");                //enable authentication
        props.put("mail.smtp.starttls.enable", "true");     //enable STARTTLS
        props.put("mail.smtp.ssl.enable", "true");          // enable ssl
        props.put("mail.smtp.auth", "true");
        final String username = "group1.footballsystem@gmail.com";
        final String password = "Admin@1234";
        try {
            Session session = Session.getDefaultInstance(props, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(username, password);
                }
            });
            // -- Create a new message --
            Message msg = new MimeMessage(session);

            // -- Set the FROM and TO fields --
            msg.setFrom(new InternetAddress(username));
            msg.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(userMail, false));
            msg.setSubject(theAlert.getMsgHeader());
            msg.setText(theAlert.getMsgBody());
            Transport.send(msg);

            System.out.println("The mail sent successfully");
            return true;
        }
        catch (Exception e){
            e.printStackTrace();
            return false;
        }
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



