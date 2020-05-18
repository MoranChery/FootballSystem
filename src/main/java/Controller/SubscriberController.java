package Controller;

import Data.AlertDb;
import Data.AlertDbInMemory;
import Data.SubscriberDb;
import Data.SubscriberDbInMemory;
import Model.Alert;
import Model.Enums.Status;
import Model.UsersTypes.Subscriber;

import java.util.List;

public class SubscriberController {
    private SubscriberDb subscriberDb;
    private boolean wantAlertsInMail;
    private AlertDb alertDb;
//    private CoachDb coachDb;
//    private JudgeDb judgeDb;
//    private PlayerDb playerDb;
//    private TeamManagerDb teamManagerDb;
//    private TeamOwnerDb teamOwnerDb;
//    private FanDb fanDb;
//    private RoleDb roleDb;
//    private SystemAdministratorDb systemAdministratorDb;
//    private RepresentativeAssociationDb representativeAssociationDb;

    public SubscriberController() {
        subscriberDb = SubscriberDbInMemory.getInstance();
        alertDb = AlertDbInMemory.getInstance();
        wantAlertsInMail = false;
//        coachDb = CoachDbInMemory.getInstance();
//        judgeDb = JudgeDbInMemory.getInstance();
//        playerDb = PlayerDbInMemory.getInstance();
//        teamManagerDb = TeamManagerDbInMemory.getInstance();
//        teamOwnerDb = TeamOwnerDbInMemory.getInstance();
//        fanDb = FanDbInMemory.getInstance();
//        roleDb= RoleDbInMemory.getInstance();
//        systemAdministratorDb= SystemAdministratorDbInMemory.getInstance();
//        representativeAssociationDb = RepresentativeAssociationDbInMemory.getInstance();
    }

    /**
     * This function creates new subscriber in the DB
     * @param subscriber Subscriber - the subscriber you want to add to the DB
     * @throws Exception NullPointerException if input is null
     */
    public void createSubscriber(Subscriber subscriber) throws Exception{
        if (subscriber == null){
            throw new NullPointerException("Can't create this subscriber");
        }
        subscriberDb.createSubscriber(subscriber);
    }

    /**
     * This function get string that represent subscriber id - his email address and returns subscriber class instance
     * @param emailAddress String - the id of the subscriber - his email address
     * @return Subscriber - the instance of the subscriber in the db
     * @throws Exception if the subscriber with the given emailAddress isn't in the system
     */
    public Subscriber getSubscriber(String emailAddress) throws Exception {
        if (emailAddress == null) {
            throw new NullPointerException("Subscriber not found");
        }
        return subscriberDb.getSubscriber(emailAddress);
    }

    /**
     * function for the subscriber to logout of the system
     * the function set the subscriber's status to offline
     * @param subscriberMail String the subscriber id - subscriber email
     * @throws Exception nullPointerException if the input is null
     * NotFoundException if the subscriber is not in the db
     * Exception if the subscriber's status is already OFFLINE
     */
    public void logOut(String subscriberMail) throws Exception {
        if(subscriberMail == null){
            throw new NullPointerException("subscriber not found");
        }
        Subscriber subscriber = subscriberDb.getSubscriber(subscriberMail);
        if(subscriber.getStatus().equals(Status.OFFLINE)){
            throw new Exception("You are already disconnected from the system");
        }
        subscriberDb.logOut(subscriberMail);
    }

    /**
     * this function enable the subscriber to edit his password
     * @param subscriberMail String the subscriber id- email address
     * @param newPassword String the new password the subscriber want to change to
     * @throws Exception NullPointerException - if one or more of the inputs is null
     * NotFoundException - if the subscriber is not in the db
     * Exception - if the new password is equal to the current password of the subscriber
     */
    public void wantToEditPassword(String subscriberMail, String newPassword) throws Exception {
        if(subscriberMail == null || newPassword == null){
            throw new NullPointerException("Something went wrong in editing subscriber the password");
        }
        Subscriber subscriber = subscriberDb.getSubscriber(subscriberMail);
        if(subscriber.getPassword().equals(newPassword)){
            throw new Exception("You are already using this password");
        }
        subscriberDb.wantToEditPassword(subscriberMail, newPassword);
    }

    /**
     * this function enable the subscriber to edit his first name
     * @param subscriberMail String the subscriber id- email address
     * @param newFirstName String the new first name the subscriber want to change to
     * @throws Exception NullPointerException - if one or more of the inputs is null
     * NotFoundException - if the subscriber is not in the db
     * Exception - if the new first name is equal to the current first name of the subscriber
     */
    public void wantToEditFirstName(String subscriberMail, String newFirstName) throws Exception {
        if(subscriberMail == null || newFirstName == null){
            throw new NullPointerException("Something went wrong in editing subscriber's the first name");
        }
        Subscriber subscriber = subscriberDb.getSubscriber(subscriberMail);
        if(subscriber.getFirstName().equals(newFirstName)){
            throw new Exception("You are already using this name as first name");
        }
        subscriberDb.wantToEditFirstName(subscriberMail, newFirstName);
    }

    /**
     * this function enable the subscriber to edit his last name
     * @param subscriberMail String the subscriber id- email address
     * @param newLastName String the new last name the subscriber want to change to
     * @throws Exception NullPointerException - if one or more of the inputs is null
     * NotFoundException - if the subscriber is not in the db
     * Exception - if the new last name is equal to the current last name of the subscriber
     */
    public void wantToEditLastName(String subscriberMail, String newLastName) throws Exception {
        if(subscriberMail == null || newLastName == null){
            throw new NullPointerException("Something went wrong in editing the last name of the subscriber");
        }
        Subscriber subscriber = subscriberDb.getSubscriber(subscriberMail);
        if(subscriber.getLastName().equals(newLastName)){
            throw new Exception("You are already using this name as last name");
        }
        subscriberDb.wantToEditLastName(subscriberMail, newLastName);
    }

    public void afterLoginSendAlerts(String subscriberMail) throws Exception {
        if(alertDb.haveAlertInDB(subscriberMail)){
            List<Alert> userAlerts = alertDb.getAlertsForUser(subscriberMail);
            for (Alert a: userAlerts) {
                if(wantAlertsInMail){
                    //TODO: send alert by mail
                }
                else {
                    //TODO: send alert by system
                }
            }
        }
    }
    public void userWantAlertInMail(String userMail) throws Exception {
        if(userMail.isEmpty()){
            throw new Exception("bad input");
        }
        setWantAlertsInMail(true);
    }

    public boolean isWantAlertsInMail() {
        return wantAlertsInMail;
    }

    public void setWantAlertsInMail(boolean wantAlertsInMail) {
        this.wantAlertsInMail = wantAlertsInMail;
    }
}
