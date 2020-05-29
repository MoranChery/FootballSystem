package Controller;

import Data.*;
import Model.Enums.*;
import Model.PageType;
import Model.Role;
import Model.UsersTypes.*;

import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

public class GuestController {
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
    private PageDb pageDb;
    private TeamDb teamDb;
    private SubscriberController subscriberController;

    public GuestController() {
//        subscriberDb = SubscriberDbInMemory.getInstance();
//        coachDb = CoachDbInMemory.getInstance();
//        judgeDb = JudgeDbInMemory.getInstance();
//        playerDb = PlayerDbInMemory.getInstance();
//        teamManagerDb = TeamManagerDbInMemory.getInstance();
//        teamOwnerDb = TeamOwnerDbInMemory.getInstance();
//        fanDb = FanDbInMemory.getInstance();
//        roleDb = RoleDbInMemory.getInstance();
//        systemAdministratorDb = SystemAdministratorDbInMemory.getInstance();
//        representativeAssociationDb = RepresentativeAssociationDbInMemory.getInstance();
//        pageDb = PageDbInMemory.getInstance();
//        teamDb = TeamDbInMemory.getInstance();
        subscriberDb = SubscriberDbInServer.getInstance();
        coachDb = CoachDbInServer.getInstance();
        judgeDb = JudgeDbInServer.getInstance();
        playerDb = PlayerDbInServer.getInstance();
        teamManagerDb = TeamManagerDbInServer.getInstance();
        teamOwnerDb = TeamOwnerDbInServer.getInstance();
        fanDb = FanDbInMemory.getInstance();
        roleDb = RoleDbInServer.getInstance();
        systemAdministratorDb = SystemAdministratorDbInServer.getInstance();
        representativeAssociationDb = RepresentativeAssociationDbInServer.getInstance();
        pageDb = PageDbInServer.getInstance();
        teamDb = TeamDbInServer.getInstance();
        subscriberController = new SubscriberController();
    }

    public void registerSubscriber(String userType) {
//        Subscriber subscriber = null;
//        switch (userType) {
//            case "Coach":
//                break;
//            case "Fan":
//                break;
//            case "Judge":
//                break;
//            case "Player":
//                break;
//            case "RepresentativeAssociation":
//                break;
//            case "SystemAdministrator":
//                break;
//            case "TeamManager":
//                break;
//            case "TeamOwner":
//                break;
//            default:
//                //create Fan
//        }
    }


    //use case 2.3

    /**
     * @param emailAddress
     * @param password
     * @return true if the login successfully
     * @throws Exception
     */
    public void login(String emailAddress, String password) throws Exception {
        if (emailAddress == null || password == null || !isValidEmail(emailAddress)) {
            throw new NullPointerException("bad input");
        }
        //check if subscriber exists
        Subscriber subscriber = subscriberDb.getSubscriber(emailAddress);
        if (!password.equals(subscriber.getPassword())) {
            throw new Exception("Wrong password");
        }
        subscriberDb.changeStatusToOnline(subscriber);
    }

    /**
     * registering of Coach
     *
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
        if (!checkAllInputDetails(emailAddress, password, id, firstName, lastName) || coachRole == null || qualificationCoach == null) {
            throw new Exception("try to enter details again!");
        }
        Coach coach = new Coach(emailAddress, password, id, firstName, lastName, coachRole, qualificationCoach);
        subscriberDb.insertSubscriber(coach);
        coachDb.insertCoach(coach);
        roleDb.createRoleInSystem(emailAddress, RoleType.COACH);
        pageDb.insertPage(coach.getEmailAddress(), PageType.COACH);
//        PersonalPage personalPage=(PersonalPage) pageDb.getPage(coach.getEmailAddress());
//        coach.setCoachPage(personalPage);
    }

    /**
     * registering of fan
     *
     * @param emailAddress
     * @param password
     * @param id
     * @param firstName
     * @param lastName
     * @throws Exception if the fan is already exist
     */
    public void registerFan(String emailAddress, String password, Integer id, String firstName, String lastName) throws Exception {
        if (!checkAllInputDetails(emailAddress, password, id, firstName, lastName)) {
            throw new Exception("try to enter details again!");
        }
        Fan fan = new Fan(emailAddress, password, id, firstName, lastName);
        subscriberDb.insertSubscriber(fan);
        fanDb.createFan(fan);
        roleDb.createRoleInSystem(emailAddress, RoleType.FAN);
    }

    /**
     * registering of judge
     *
     * @param emailAddress
     * @param password
     * @param id
     * @param firstName
     * @param lastName
     * @param qualificationJudge
//     * @param theJudgeType
     * @throws Exception if the judge is already exist
     */
    public void registerJudge(String emailAddress, String password, Integer id, String firstName, String lastName, QualificationJudge qualificationJudge) throws Exception {
        if (!checkAllInputDetails(emailAddress, password, id, firstName, lastName) || qualificationJudge == null ) {
            throw new Exception("try to enter details again!");
        }
        Judge judge = new Judge(emailAddress, password, id, firstName, lastName, qualificationJudge);
        subscriberDb.insertSubscriber(judge);
        judgeDb.insertJudge(judge);
        roleDb.createRoleInSystem(emailAddress, RoleType.JUDGE);

    }

    /**
     * registering of player
     *
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
        if (!checkAllInputDetails(emailAddress, password, id, firstName, lastName) || birthDate == null || playerRole == null) {
            throw new Exception("try to enter details again!");
        }
        Player player = new Player(emailAddress, password, id, firstName, lastName, birthDate, playerRole);
        subscriberDb.insertSubscriber(player);
        playerDb.insertPlayer(player);
        roleDb.createRoleInSystem(emailAddress, RoleType.PLAYER);
        pageDb.insertPage(player.getEmailAddress() + "", PageType.PLAYER);
    }

    /**
     * registering of representative Association
     *
     * @param emailAddress
     * @param password
     * @param id
     * @param firstName
     * @param lastName
     * @throws Exception if the representative Association is already exist
     */
    public void registerRepresentativeAssociation(String emailAddress, String password, Integer id, String firstName, String lastName) throws Exception {
        if (!checkAllInputDetails(emailAddress, password, id, firstName, lastName)) {
            throw new Exception("try to enter details again!");
        }
        RepresentativeAssociation representativeAssociation = new RepresentativeAssociation(emailAddress, password, id, firstName, lastName);
        subscriberDb.insertSubscriber(representativeAssociation);
        representativeAssociationDb.insertRepresentativeAssociation(representativeAssociation);
        roleDb.createRoleInSystem(emailAddress, RoleType.REPRESENTATIVE_ASSOCIATION);
    }

    /**
     * registering of system Administrator
     *
     * @param emailAddress
     * @param password
     * @param id
     * @param firstName
     * @param lastName
     * @throws Exception if the system Administrator is already exist
     */
    public void registerSystemAdministrator(String emailAddress, String password, Integer id, String firstName, String lastName) throws Exception {
        if (!checkAllInputDetails(emailAddress, password, id, firstName, lastName)) {
            throw new Exception("try to enter details again!");
        }
        SystemAdministrator systemAdministrator = new SystemAdministrator(emailAddress, password, id, firstName, lastName);
        subscriberDb.insertSubscriber(systemAdministrator);
        systemAdministratorDb.insertSystemAdministrator(systemAdministrator);
        roleDb.createRoleInSystem(emailAddress, RoleType.SYSTEM_ADMINISTRATOR);
    }

    /**
     * registering of team manager
     *
     * @param emailAddress
     * @param password
     * @param id
     * @param firstName
     * @param lastName
     * @param ownedByEmail
     * @throws Exception if the team manager is already exist
     */
    public void registerTeamManager(String emailAddress, String password, Integer id, String firstName, String lastName, String ownedByEmail) throws Exception {
        if (!checkAllInputDetails(emailAddress, password, id, firstName, lastName) || !isValidEmail(ownedByEmail)) {
            throw new Exception("try to enter details again!");
        }
        //check if the owned is in the subscriberDb and in the teamOwnerDb
        subscriberDb.getSubscriber(ownedByEmail);
        teamOwnerDb.getTeamOwner(ownedByEmail);
        TeamManager teamManager = new TeamManager(emailAddress, password, id, firstName, lastName, ownedByEmail);
        subscriberDb.insertSubscriber(teamManager);
        teamManagerDb.insertTeamManager(teamManager);
        roleDb.createRoleInSystem(emailAddress, RoleType.TEAM_MANAGER);
    }

    /**
     * registering of team owner
     *
     * @param emailAddress
     * @param password
     * @param id
     * @param firstName
     * @param lastName
     * @throws Exception if the team owner is already exist
     */
    public void registerTeamOwner(String emailAddress, String password, Integer id, String firstName, String lastName) throws Exception {
        if (!checkAllInputDetails(emailAddress, password, id, firstName, lastName)) {
            throw new Exception("try to enter details again!");
        }
//        try {
//            teamDb.getTeam(team.getTeamName());
//        } catch (Exception e) {
//            throw new Exception("try to enter details again!");
//        }
        TeamOwner teamOwner = new TeamOwner(emailAddress, password, id, firstName, lastName);
        subscriberDb.insertSubscriber(teamOwner);
        teamOwnerDb.insertTeamOwner(teamOwner);
        roleDb.createRoleInSystem(emailAddress, RoleType.TEAM_OWNER);
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
        if (!isLegalName(firstName) || !isLegalName(lastName) || !isLegalEmail(emailAddress) || !isLegalPassword(password) || id == null
                || id.toString().length() != 9 || id < 0) {
            return false;
        }
        return true;
    }

    /**
     * @param emailAddress to check
     * @return true if the email is valid
     */
    private boolean isLegalEmail(String emailAddress) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." + "[a-zA-Z0-9_+&*-]+)*@" + "(?:[a-zA-Z0-9-]+\\.)+[a-z" + "A-Z]{2,7}$";
        Pattern pat = Pattern.compile(emailRegex);
        if (emailAddress == null)
            return false;
        return pat.matcher(emailAddress).matches();
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
     * @param password: password
     * @return true if the terms to the username and the password are good
     */
    private boolean isLegalPassword(String password) {
        if (password == null || password.length() == 0 || password.equals("")) {
            return false;
        }
        for (int i = 0; i < password.length(); i++) {
            char ch = password.charAt(i);
            if ((!(ch >= 'A' && ch <= 'Z')) && (!(ch >= 'a' && ch <= 'z')) && (!(ch >= '0' && ch <= '9'))) {
                return false;
            }
        }
        return true;
    }

    /**
     * @param email to check
     * @return true if the email is valid
     */
    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." + "[a-zA-Z0-9_+&*-]+)*@" + "(?:[a-zA-Z0-9-]+\\.)+[a-z" + "A-Z]{2,7}$";
        Pattern pat = Pattern.compile(emailRegex);
        return pat.matcher(email).matches();
    }


    public List<Role> getRules(String email) throws Exception {
        return roleDb.getRoles(email);
    }
    public static void main(String[] args) throws Exception {
        GuestController representativeAssociationDbInServer = new GuestController();
        representativeAssociationDbInServer.registerRepresentativeAssociation("representativeAssociation1@gmail.com","Pass1234", 123456789, "FirstName", "LastName");
    }
}
