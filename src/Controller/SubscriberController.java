package Controller;

import Data.*;

import java.util.Date;

import Model.Enums.*;
import Model.Team;
import Model.UsersTypes.*;

public class SubscriberController {
    private SubscriberDb subscriberDb;
    private CoachDb coachDb;
    private JudgeDb judgeDb;
    private PlayerDb playerDb;
    private TeamManagerDb teamManagerDb;
    private TeamOwnerDb teamOwnerDb;
    private FanDb fanDb;
    private RoleDb roleDb;
    private SystemAdministratorDb systemAdministratorDb;
    private RepresentativeAssociationDb representativeAssociationDb;

    public SubscriberController() {
        subscriberDb = SubscriberDbInMemory.getInstance();
        coachDb = CoachDbInMemory.getInstance();
        judgeDb = JudgeDbInMemory.getInstance();
        playerDb = PlayerDbInMemory.getInstance();
        teamManagerDb = TeamManagerDbInMemory.getInstance();
        teamOwnerDb = TeamOwnerDbInMemory.getInstance();
        fanDb = FanDbInMemory.getInstance();
        roleDb= RoleDbInMemory.getInstance();
        systemAdministratorDb= SystemAdministratorDbInMemory.getInstance();
        representativeAssociationDb = RepresentativeAssociationDbInMemory.getInstance();
    }

    //todo: call use case 2.2 from UI
    public void registerSubscriber(String userType) throws Exception {
        Subscriber subscriber = null;
        switch (userType) {
            case "Coach":
                break;
            case "Fan":
                break;
            case "Judge":
                break;
            case "Player":
                break;
            case "RepresentativeAssociation":
                break;
            case "SystemAdministrator":
                break;
            case "TeamManager":
                break;
            case "TeamOwner":
                break;
        }
    }

    /**
     *  registering of Coach
     * @param emailAddress
     * @param password
     * @param id
     * @param firstName
     * @param lastName
     * @param coachRole
     * @param qualificationCoach
     * @throws Exception if the coach is already exist
     */
    public void registerCoach(String emailAddress, String password, Integer id, String firstName, String lastName, CoachRole coachRole, QualificationCoach qualificationCoach) throws Exception {
        if (!checkAllInputDetails(emailAddress, password, id, firstName, lastName)) {
            throw new Exception("you should use only letters and numbers!");
        }
        Coach coach = new Coach(emailAddress, password, id, firstName, lastName, coachRole, qualificationCoach);
        subscriberDb.createSubscriber(coach);
        coachDb.createCoach(coach);
        roleDb.createRoleInSystem( emailAddress, RoleType.COACH);


    }

    /**
     * registering of fan
     * @param emailAddress
     * @param password
     * @param id
     * @param firstName
     * @param lastName
     * @throws Exception if the fan is already exist
     */
    public void registerFan(String emailAddress, String password, Integer id, String firstName, String lastName) throws Exception {
        if (!checkAllInputDetails(emailAddress, password, id, firstName, lastName)) {
            throw new Exception("you should use only letters and numbers!");
        }
        Fan fan = new Fan(emailAddress, password, id, firstName, lastName);
        subscriberDb.createSubscriber(fan);
        fanDb.createFan(fan);
        roleDb.createRoleInSystem( emailAddress, RoleType.FAN);
    }

    /**
     * registering of judge
     * @param emailAddress
     * @param password
     * @param id
     * @param firstName
     * @param lastName
     * @param qualificationJudge
     * @param theJudgeType
     * @throws Exception if the judge is already exist
     */
    public void registerJudge(String emailAddress, String password, Integer id, String firstName, String lastName, QualificationJudge qualificationJudge, JudgeType theJudgeType) throws Exception {
        if (!checkAllInputDetails(emailAddress, password, id, firstName, lastName)) {
            throw new Exception("you should use only letters and numbers!");
        }
        Judge judge = new Judge(emailAddress, password, id, firstName, lastName, qualificationJudge, theJudgeType);
        subscriberDb.createSubscriber(judge);
        judgeDb.createJudge(judge);
        roleDb.createRoleInSystem( emailAddress, RoleType.JUDGE);
        
    }

    /**
     * registering of player
     * @param emailAddress
     * @param password
     * @param id
     * @param firstName
     * @param lastName
     * @param birthDate
     * @param playerRole
     * @throws Exception if the player is already exist
     */
    public void registerPlayer(String emailAddress, String password, Integer id, String firstName, String lastName, Date birthDate, PlayerRole playerRole) throws Exception {
        if (!checkAllInputDetails(emailAddress, password, id, firstName, lastName)) {
            throw new Exception("you should use only letters and numbers!");
        }
        Player player = new Player(emailAddress, password, id, firstName, lastName, birthDate, playerRole);
        subscriberDb.createSubscriber(player);
        playerDb.createPlayer(player);
        roleDb.createRoleInSystem( emailAddress, RoleType.PLAYER);
    }

    /**
     * registering of representative Association
     * @param emailAddress
     * @param password
     * @param id
     * @param firstName
     * @param lastName
     * @throws Exception if the representative Association is already exist
     */
    public void registerRepresentativeAssociation(String emailAddress, String password, Integer id, String firstName, String lastName) throws Exception {
        if (!checkAllInputDetails(emailAddress, password, id, firstName, lastName)) {
            throw new Exception("you should use only letters and numbers!");
        }
        RepresentativeAssociation representativeAssociation = new RepresentativeAssociation(emailAddress, password, id, firstName, lastName);
        subscriberDb.createSubscriber(representativeAssociation);
        representativeAssociationDb.createRepresentativeAssociation(representativeAssociation);
        roleDb.createRoleInSystem( emailAddress, RoleType.REPRESENTATIVE_ASSOCIATION);
    }

    /**
     * registering of system Administrator
     * @param emailAddress
     * @param password
     * @param id
     * @param firstName
     * @param lastName
     * @throws Exception if the system Administrator is already exist
     */
    public void registerSystemAdministrator(String emailAddress, String password, Integer id, String firstName, String lastName) throws Exception {
        if (!checkAllInputDetails(emailAddress, password, id, firstName, lastName)) {
            throw new Exception("you should use only letters and numbers!");
        }
        SystemAdministrator systemAdministrator = new SystemAdministrator(emailAddress, password, id, firstName, lastName);
        subscriberDb.createSubscriber(systemAdministrator);
        systemAdministratorDb.createSystemAdministrator(systemAdministrator);
        roleDb.createRoleInSystem( emailAddress, RoleType.SYSTEM_ADMINISTRATOR);
    }

    /**
     * registering of team manager
     * @param emailAddress
     * @param password
     * @param id
     * @param firstName
     * @param lastName
     * @param ownedByEmail
     * @throws Exception if the team manager is already exist
     */
    public void registerTeamManager(String emailAddress, String password, Integer id, String firstName, String lastName, String ownedByEmail) throws Exception {
        if (!checkAllInputDetails(emailAddress, password, id, firstName, lastName)) {
            throw new Exception("you should use only letters and numbers!");
        }
        TeamManager teamManager = new TeamManager(emailAddress, password, id, firstName, lastName, ownedByEmail);
        subscriberDb.createSubscriber(teamManager);
        teamManagerDb.createTeamManager(teamManager);
        roleDb.createRoleInSystem( emailAddress, RoleType.TEAM_MANAGER);
    }

    /**
     * registering of team owner
     * @param emailAddress
     * @param password
     * @param id
     * @param firstName
     * @param lastName
     * @param team
     * @throws Exception if the team owner is already exist
     */
    public void registerTeamOwner(String emailAddress, String password, Integer id, String firstName, String lastName, Team team) throws Exception {
        if (!checkAllInputDetails(emailAddress, password, id, firstName, lastName)) {
            throw new Exception("you should use only letters and numbers!");
        }
        TeamOwner teamOwner = new TeamOwner(emailAddress, password, id, firstName, lastName, team);
        subscriberDb.createSubscriber(teamOwner);
        teamOwnerDb.createTeamOwner(teamOwner);
        roleDb.createRoleInSystem( emailAddress, RoleType.TEAM_OWNER);
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
     * @param emailAddress
     * @param password
     * @param id
     * @param firstName
     * @param lastName
     * @return if all the details are meet the requirements
     */
    private boolean checkAllInputDetails(String emailAddress, String password, Integer id, String firstName, String lastName) {
        if (!isLegalName(firstName) || !isLegalName(lastName) ||
                !isLegalUsernameAndPassword(emailAddress) || !isLegalUsernameAndPassword(password) ||
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
