package Controller;

import Data.SubscriberDb;
import Data.SubscriberDbInMemory;

import java.Model.Enums.Status;

import Model.UsersTypes.Subscriber;

public class SubscriberController {
    private SubscriberDb subscriberDb;

    public SubscriberController() {
        subscriberDb = new SubscriberDbInMemory();
    }

    //use case 2.2
    public void registerSubscriber(String username, String password, Integer id, String firstName, String lastName, String userType) throws Exception {
        Subscriber subscriber = null;
        switch (userType) {
            case "Coach":
                subscriber = new Model.UsersTypes.Coach();
                break;
            case "Fan":
                subscriber = new Model.UsersTypes.Fan();
                break;
            case "Judge":
                subscriber = new Model.UsersTypes.Judge();
                break;
            case "MajorJudge":
                subscriber = new Model.UsersTypes.MajorJudge();
                break;
            case "Player":
                subscriber = new Model.UsersTypes.Player();
                break;
            case "RepresentativeAssociation":
                subscriber = new Model.UsersTypes.RepresentativeAssociation();
                break;
            case "SystemAdministrator":
                subscriber = new Model.UsersTypes.SystemAdministrator();
                break;
            case "TeamManager":
                subscriber = new Model.UsersTypes.TeamManager();
                break;
            case "TeamOwner":
                subscriber = new Model.UsersTypes.TeamOwner();
                break;
        }
        //the password/username is not include only letters and numbers
        if (!checkAllInputDetails(username, password, id, firstName, lastName)) {
            throw new Exception("you should use only letters and numbers!");
        }
        subscriber.setRegisteringDetails(username, password, id, firstName, lastName);
        subscriberDb.createSubscriber(subscriber);
    }

    public Subscriber getSubscriber(String username) throws Exception {
        if (username == null) {
            throw new NullPointerException();
        }
        return subscriberDb.getSubscriber(username);
    }

    //use case 2.3

    /**
     * @param username
     * @param password
     * @return true if the login successfully
     * @throws Exception
     */
    public boolean login(String username, String password) throws Exception {
        if (username == null || password == null) {
            return false;
        }
        Subscriber subscriber = subscriberDb.getSubscriber(username);
        if (subscriber != null && subscriber.getPassword().equals(password)) {
            subscriber.setStatus(Status.ONLINE);
            return true;
        } else return false;
    }

    //todo:use case 2.4
    public void showInformation(String subject) {

    }

    //todo: use case 2.5

    /**
     * @param input
     * @return true if there are results that match to the search input
     */
    public boolean searchInformation(String input) {
        return false;
    }

    /**
     * @param username
     * @param password
     * @param id
     * @param firstName
     * @param lastName
     * @return if all the details are meet the requirements
     */
    private boolean checkAllInputDetails(String username, String password, Integer id, String firstName, String lastName) {
        if (!isLegalName(firstName) || !isLegalName(lastName) ||
                !isLegalUsernameAndPassword(username) || !isLegalUsernameAndPassword(password) ||
                id.toString().length() != 9) {
            return false;
        }
        return true;
    }

    /**
     * @param name first name or last name
     * @return if the name is include only from letters
     */
    private boolean isLegalName(String name) {
        if (name == null || name.length() == 0 || name.equals("")) {
            return false;
        }
        for (int i = 0; i < name.length(); i++) {
            char ch = name.charAt(i);
            if ((!(ch >= 'A' && ch <= 'Z')) && (!(ch >= 'a' && ch <= 'z'))) {
                return false;
            }
        }
        return true;
    }

    /**
     * @param word: username or password
     * @return true if the terms to the username and the password are good
     */
    private boolean isLegalUsernameAndPassword(String word) {
        if (word == null || word.length() == 0 || word.equals("")) {
            return false;
        }
        for (int i = 0; i < word.length(); i++) {
            char ch = word.charAt(i);
            if ((!(ch >= 'A' && ch <= 'Z')) && (!(ch >= 'a' && ch <= 'z')) && (!(ch >= '0' && ch <= '9'))) {
                return false;
            }
        }
        return true;
    }
}
