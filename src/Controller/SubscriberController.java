package Controller;

import Data.SubscriberDb;
import Data.SubscriberDbInMemory;

import Model.Enums.Status;
import java.util.Date;

import Model.Enums.CoachRole;
import Model.Enums.PlayerRole;
import Model.Enums.QualificationCoach;
import Model.Enums.QualificationJudge;
import Model.Team;
import Model.UsersTypes.*;

public class SubscriberController {
    private SubscriberDb subscriberDb;

    public SubscriberController() {
        subscriberDb =  SubscriberDbInMemory.getInstance();;
    }

    //todo: call use case 2.2 from UI
    public void registerSubscriber(String userType) {
        if ("Coach".equals(userType)) {
        } else if ("Fan".equals(userType)) {
        } else if ("Judge".equals(userType)) {
        } else if ("MajorJudge".equals(userType)) {
        } else if ("Player".equals(userType)) {
        } else if ("RepresentativeAssociation".equals(userType)) {
        } else if ("SystemAdministrator".equals(userType)) {
        } else if ("TeamManager".equals(userType)) {
        } else if ("TeamOwner".equals(userType)) {
        }
    }

    public void registerCoach(String username, String password, Integer id, String firstName, String lastName, CoachRole coachRole, QualificationCoach qualificationCoach) throws Exception {
        if (!checkAllInputDetails(username, password, id, firstName, lastName)) {
            throw new Exception("you should use only letters and numbers!");
        }
        Coach coach = new Coach(username, password, id, firstName, lastName, coachRole, qualificationCoach);
        subscriberDb.createSubscriber(coach);
    }

    public void registerFan(String username, String password, Integer id, String firstName, String lastName) throws Exception {
        if (!checkAllInputDetails(username, password, id, firstName, lastName)) {
            throw new Exception("you should use only letters and numbers!");
        }
        Fan fan = new Fan(username, password, id, firstName, lastName);
        subscriberDb.createSubscriber(fan);
    }


    public void registerJudge(String username, String password, Integer id, String firstName, String lastName, QualificationJudge qualificationJudge) throws Exception {
        if (!checkAllInputDetails(username, password, id, firstName, lastName)) {
            throw new Exception("you should use only letters and numbers!");
        }
        Judge judge = new Judge(username, password, id, firstName, lastName, qualificationJudge);
        subscriberDb.createSubscriber(judge);
    }

    public void registerMajorJudge(String username, String password, Integer id, String firstName, String lastName, QualificationJudge qualificationJudge) throws Exception {
        if (!checkAllInputDetails(username, password, id, firstName, lastName)) {
            throw new Exception("you should use only letters and numbers!");
        }
        MajorJudge majorJudge = new MajorJudge(username, password, id, firstName, lastName, qualificationJudge);
        subscriberDb.createSubscriber(majorJudge);
    }

    public void registerPlayer(String username, String password, Integer id, String firstName, String lastName, Date birthDate, PlayerRole playerRole) throws Exception {
        if (!checkAllInputDetails(username, password, id, firstName, lastName)) {
            throw new Exception("you should use only letters and numbers!");
        }
        Player player = new Player(username, password, id, firstName, lastName, birthDate, playerRole);
        subscriberDb.createSubscriber(player);
    }

    public void registerRepresentativeAssociation(String username, String password, Integer id, String firstName, String lastName) throws Exception {
        if (!checkAllInputDetails(username, password, id, firstName, lastName)) {
            throw new Exception("you should use only letters and numbers!");
        }
        RepresentativeAssociation representativeAssociation = new RepresentativeAssociation(username, password, id, firstName, lastName);
        subscriberDb.createSubscriber(representativeAssociation);
    }

    public void registerSystemAdministrator(String username, String password, Integer id, String firstName, String lastName) throws Exception {
        if (!checkAllInputDetails(username, password, id, firstName, lastName)) {
            throw new Exception("you should use only letters and numbers!");
        }
        SystemAdministrator systemAdministrator = new SystemAdministrator(username, password, id, firstName, lastName);
        subscriberDb.createSubscriber(systemAdministrator);
    }

    public void registerTeamManager(String username, String password, Integer id, String firstName, String lastName, Integer ownedById) throws Exception {
        if (!checkAllInputDetails(username, password, id, firstName, lastName)) {
            throw new Exception("you should use only letters and numbers!");
        }
        TeamManager teamManager = new TeamManager(username, password, id, firstName, lastName, ownedById);
        subscriberDb.createSubscriber(teamManager);
    }

    public void registerTeamOwner(String username, String password, Integer id, String firstName, String lastName, Team team) throws Exception {
        if (!checkAllInputDetails(username, password, id, firstName, lastName)) {
            throw new Exception("you should use only letters and numbers!");
        }
        TeamOwner teamOwner = new TeamOwner(username, password, id, firstName, lastName, team);
        subscriberDb.createSubscriber(teamOwner);
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
