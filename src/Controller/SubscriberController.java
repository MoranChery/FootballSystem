package Controller;

import Data.*;

import java.util.Date;

import Model.Enums.*;
import Model.Team;
import Model.UsersTypes.*;

public class SubscriberController {
    private SubscriberDb subscriberDb;
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
     *
     * @param emailAddress
     * @return
     * @throws Exception if the subscriber with the emailAddress isn't in the system
     */
    public Subscriber getSubscriber(String emailAddress) throws Exception {
        if (emailAddress == null) {
            throw new NullPointerException();
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
            throw new NullPointerException("bad input");
        }
        Subscriber subscriber = subscriberDb.getSubscriber(subscriberMail);
        if (subscriber == null){
            throw new NotFoundException("subscriber not found");
        }
        if(subscriber.getStatus().equals(Status.OFFLINE)){
            throw new Exception("You are already disconnected to the system");
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
            throw new NullPointerException("bad input");
        }
        Subscriber subscriber = subscriberDb.getSubscriber(subscriberMail);
        if(subscriber == null){
            throw new NotFoundException("subscriber not found");
        }
        if(subscriber.getPassword().equals(newPassword)){
            throw new Exception("This password is the same as the old one");
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
            throw new NullPointerException("bad input");
        }
        Subscriber subscriber = subscriberDb.getSubscriber(subscriberMail);
        if(subscriber == null){
            throw new NotFoundException("subscriber not found");
        }
        if(subscriber.getFirstName().equals(newFirstName)){
            throw new Exception("This name is the same as the old one");
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
            throw new NullPointerException("bad input");
        }
        Subscriber subscriber = subscriberDb.getSubscriber(subscriberMail);
        if(subscriber == null){
            throw new NotFoundException("subscriber not found");
        }
        if(subscriber.getLastName().equals(newLastName)){
            throw new Exception("This password is the same as the old one");
        }
        subscriberDb.wantToEditLastName(subscriberMail, newLastName);
    }


}
