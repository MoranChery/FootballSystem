package Controller;

import Data.RoleDb;
import Data.RoleDbInMemory;
import Data.SubscriberDb;
import Data.SubscriberDbInMemory;

import Model.Enums.*;

import java.util.Date;

import Model.Team;
import Model.UsersTypes.*;

public class SubscriberController {
    private SubscriberDb subscriberDb;
    private RoleDb roleDb;

    public SubscriberController() {
        subscriberDb = SubscriberDbInMemory.getInstance();
        roleDb = RoleDbInMemory.getInstance();
    }

    //todo: call use case 2.2 from UI

    /**
     * UI function
     * @param userType
     */
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
//all the following registration functions are to create objects due to the input role type from UI

    //register coach to the system
    public void registerCoach(String emailAddress, String password, Integer id, String firstName, String lastName, CoachRole coachRole, QualificationCoach qualificationCoach) throws Exception {
        if (!checkAllInputDetails(emailAddress, password, id, firstName, lastName)) {
            throw new Exception("you should use only letters and numbers!");
        }
        Coach coach = new Coach(emailAddress, password, id, firstName, lastName, coachRole, qualificationCoach);
        subscriberDb.createSubscriber(coach);
        roleDb.createRoleInSystem( emailAddress,RoleType.COACH);
    }

    //register fan to the system
    public void registerFan(String emailAddress, String password, Integer id, String firstName, String lastName) throws Exception {
        if (!checkAllInputDetails(emailAddress, password, id, firstName, lastName)) {
            throw new Exception("you should use only letters and numbers!");
        }
        Fan fan = new Fan(emailAddress, password, id, firstName, lastName);
        subscriberDb.createSubscriber(fan);
        roleDb.createRoleInSystem( emailAddress,RoleType.FAN);
    }

    //register judge to the system
    public void registerJudge(String emailAddress, String password, Integer id, String firstName, String lastName, QualificationJudge qualificationJudge) throws Exception {
        if (!checkAllInputDetails(emailAddress, password, id, firstName, lastName)) {
            throw new Exception("you should use only letters and numbers!");
        }
        Judge judge = new Judge(emailAddress, password, id, firstName, lastName, qualificationJudge);
        subscriberDb.createSubscriber(judge);
        roleDb.createRoleInSystem( emailAddress,RoleType.JUDGE);
    }

    //register MajorJudge to the system
    public void registerMajorJudge(String emailAddress, String password, Integer id, String firstName, String lastName, QualificationJudge qualificationJudge) throws Exception {
        if (!checkAllInputDetails(emailAddress, password, id, firstName, lastName)) {
            throw new Exception("you should use only letters and numbers!");
        }
        MajorJudge majorJudge = new MajorJudge(emailAddress, password, id, firstName, lastName, qualificationJudge);
        subscriberDb.createSubscriber(majorJudge);
        roleDb.createRoleInSystem( emailAddress,RoleType.MAJOR_JUDGE);
    }

    //register Player to the system
    public void registerPlayer(String emailAddress, String password, Integer id, String firstName, String lastName, Date birthDate, PlayerRole playerRole) throws Exception {
        if (!checkAllInputDetails(emailAddress, password, id, firstName, lastName)) {
            throw new Exception("you should use only letters and numbers!");
        }
        Player player = new Player(emailAddress, password, id, firstName, lastName, birthDate, playerRole);
        subscriberDb.createSubscriber(player);
        roleDb.createRoleInSystem( emailAddress,RoleType.PLAYER);
    }

    //register Representative Association to the system
    public void registerRepresentativeAssociation(String emailAddress, String password, Integer id, String firstName, String lastName) throws Exception {
        if (!checkAllInputDetails(emailAddress, password, id, firstName, lastName)) {
            throw new Exception("you should use only letters and numbers!");
        }
        RepresentativeAssociation representativeAssociation = new RepresentativeAssociation(emailAddress, password, id, firstName, lastName);
        subscriberDb.createSubscriber(representativeAssociation);
        roleDb.createRoleInSystem( emailAddress,RoleType.REPRESENTATIVE_ASSOCIATION);
    }

    //register System Administrator to the system
    public void registerSystemAdministrator(String emailAddress, String password, Integer id, String firstName, String lastName) throws Exception {
        if (!checkAllInputDetails(emailAddress, password, id, firstName, lastName)) {
            throw new Exception("you should use only letters and numbers!");
        }
        SystemAdministrator systemAdministrator = new SystemAdministrator(emailAddress, password, id, firstName, lastName);
        subscriberDb.createSubscriber(systemAdministrator);
        roleDb.createRoleInSystem( emailAddress,RoleType.SYSTEM_ADMINISTRATOR);
    }

    //register Team Manager to the system
    public void registerTeamManager(String emailAddress, String password, Integer id, String firstName, String lastName, String ownedByEmail) throws Exception {
        if (!checkAllInputDetails(emailAddress, password, id, firstName, lastName)) {
            throw new Exception("you should use only letters and numbers!");
        }
        TeamManager teamManager = new TeamManager(emailAddress, password, id, firstName, lastName, ownedByEmail);
        subscriberDb.createSubscriber(teamManager);
        roleDb.createRoleInSystem( emailAddress,RoleType.TEAM_MANAGER);
    }

    //register Team Owner to the system
    public void registerTeamOwner(String emailAddress, String password, Integer id, String firstName, String lastName, Team team) throws Exception {
        if (!checkAllInputDetails(emailAddress, password, id, firstName, lastName)) {
            throw new Exception("you should use only letters and numbers!");
        }
        TeamOwner teamOwner = new TeamOwner(emailAddress, password, id, firstName, lastName, team);
        subscriberDb.createSubscriber(teamOwner);
        roleDb.createRoleInSystem( emailAddress,RoleType.TEAM_OWNER);
    }

    /**
     *
     * @param emailAddress of the subscriber
     * @return the subscriber with the emailAddress from the input
     * @throws Exception if the email input is null
     */
    public Subscriber getSubscriber(String emailAddress) throws Exception {
        if (emailAddress == null) {
            throw new NullPointerException();
        }
        return subscriberDb.getSubscriber(emailAddress);
    }

    //use case 2.3

    /**
     * @param emailAddress
     * @param password
     * @return true if the login successfully
     * @throws Exception
     */
    public boolean login(String emailAddress, String password) throws Exception {
        if (emailAddress == null || password == null) {
            return false;
        }
        Subscriber subscriber = subscriberDb.getSubscriber(emailAddress);
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
