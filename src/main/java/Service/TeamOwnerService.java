package Service;

import Controller.NotificationController;
import Controller.TeamOwnerController;
import Data.CourtDbInMemory;
import Data.RoleDbInMemory;
import Data.SubscriberDbInMemory;
import Model.Court;
import Model.Enums.*;
import Model.LoggerHandler;
import Model.Team;
import Model.UsersTypes.Coach;
import Model.UsersTypes.Player;
import Model.UsersTypes.TeamManager;
import Model.UsersTypes.TeamOwner;
import components.CreateTeamRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.ContextStoppedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Level;
import javax.annotation.PreDestroy;
import java.util.logging.Logger;

@RestController
public class TeamOwnerService {
    private Logger logger = Logger.getLogger(TeamOwnerService.class.getName());
    private TeamOwnerController teamOwnerController;
    private NotificationController notificationController = NotificationController.getInstance();
//    private LoggerHandler loggerHandler;


    public TeamOwnerService() {
        this.teamOwnerController = new TeamOwnerController();
        notificationController.setTeamOwnerController(this.teamOwnerController);
//        this.loggerHandler = new LoggerHandler(TeamOwnerService.class.getName());
        logger.addHandler(LoggerHandler.loggerErrorFileHandler);
        logger.addHandler(LoggerHandler.loggerEventFileHandler);
    }


    @EventListener
    public void onDestroy(ContextStoppedEvent event) throws Exception {
        logger.log(Level.WARNING, "The server is shutting down");
    }

    @GetMapping
    @CrossOrigin(origins = "http://localhost:63342")
    @ResponseStatus(HttpStatus.OK)
    public Map<String, String> helloNoy() {
        final Map<String, String> map = new HashMap<>();
        map.put("hi", "Noy");

        return map;
    }

    @CrossOrigin(origins = "http://localhost:63342")
    @GetMapping(value = "teams/{teamName}/")
    @ResponseStatus(HttpStatus.OK)
    public Team getTeam(@PathVariable String teamName) throws Exception {
        try {
            return teamOwnerController.getTeam(teamName);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "try Not Found", e);
        }
    }

    @CrossOrigin(origins = "http://localhost:63342")
    @GetMapping(value = "createNewTeam/{teamName}/{teamOwnerEmail}/{budget}/{courtName}/{courtCity}/")
    @ResponseStatus(HttpStatus.OK)
    public void createNewTeam(@PathVariable String teamName, @PathVariable String teamOwnerEmail, @PathVariable String budget, @PathVariable String courtName,@PathVariable String courtCity) throws Exception {
        //final String teamName = createTeamRequest.getTeamName();
        final List<Coach> coaches = new ArrayList<>();
//            final Double budget = createTeamRequest.getBudget();
//            final Court court = createTeamRequest.getCourt();
        final List<Player> players = new ArrayList<>();
        final List<TeamManager> teamManagers = new ArrayList<>();
//            final String teamOwnerEmail = createTeamRequest.getTeamOwnerEmail();
        try {
            Court court1 = new Court();
            court1.setCourtName(courtName);
            court1.setCourtCity(courtCity);
            teamOwnerController.createNewTeam(teamName, teamOwnerEmail, players, coaches, teamManagers, court1, Double.parseDouble(budget));
            logger.log(Level.INFO, "Created by: " + teamOwnerEmail + " Description: Team \"" + teamName + "\" was created");
        } catch (Exception e) {
            logger.log(Level.WARNING, "Created by: " + teamOwnerEmail + " Description: Team \"" + teamName + "\" wasn't created because: " + e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @CrossOrigin(origins = "http://localhost:63342")
    @GetMapping(value = "addPlayer/{teamName}/{ownerEmail}/{emailAddress}/{playerId}/{firstName}/{lastName}/{birthDate}/{playerRole}/")
    @ResponseStatus(HttpStatus.OK)
    /*will receive from the UI the team's name and the player Id want to add and will continue to controller*/
    public void addPlayer(@PathVariable String teamName, @PathVariable String ownerEmail, @PathVariable String emailAddress, @PathVariable String playerId, @PathVariable String firstName, @PathVariable String lastName, @PathVariable String birthDate, @PathVariable String playerRole) throws Exception {
        try {
            Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse(birthDate);
            PlayerRole playerRole1 = PlayerRole.getPlayerRole(playerRole);
            teamOwnerController.addPlayer(teamName, ownerEmail, emailAddress, Integer.parseInt(playerId), firstName, lastName, date1, playerRole1);
            logger.log(Level.INFO, "Created by: " + ownerEmail + " Description: Player \"" + emailAddress + "\" added to Team:" + teamName);

        } catch (Exception e) {
            logger.log(Level.WARNING, "Created by: " + ownerEmail + " Description: Player \"" + emailAddress + "\" wasn't added to Team: " + teamName + " because " + e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @CrossOrigin(origins = "http://localhost:63342")
    @GetMapping(value = "addTeamManager/{teamName}/{emailAddress}/{teamManagerId}/{firstName}/{lastName}/{permissionTypes}/{ownedByEmail}/")
    @ResponseStatus(HttpStatus.OK)
    public void addTeamManager(@PathVariable String teamName,@PathVariable  String emailAddress,@PathVariable  String teamManagerId,@PathVariable  String firstName,@PathVariable  String lastName,@PathVariable  String permissionTypes,@PathVariable  String ownedByEmail) throws Exception {
        try {
            teamOwnerController.addTeamManager(teamName, emailAddress, Integer.parseInt(teamManagerId), firstName, lastName, getPermissionsFromString(permissionTypes), ownedByEmail);
            logger.log(Level.INFO, "Created by: " + ownedByEmail + " Description: TeamManager \"" + emailAddress + "\" added to Team \"" + teamName + "\"");
        } catch (Exception e) {
            logger.log(Level.WARNING, "Created by: " + ownedByEmail + " Description: TeamManager \"" + emailAddress + "\" wasn't added to Team \"" + teamName + "\" because " + e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    private List<PermissionType> getPermissionsFromString(String permiString) {
        List<PermissionType> permissionsAsList = new ArrayList<>();
        String[] permissionTypesAsArray = permiString.split(",");
        for (String permissionToCheck : permissionTypesAsArray) {
            PermissionType type=PermissionType.getPermissionType(permissionToCheck);
            if(type!=null) {
                permissionsAsList.add(PermissionType.getPermissionType(permissionToCheck));
            }
        }
        return permissionsAsList;
    }

    @CrossOrigin(origins = "http://localhost:63342")
    @GetMapping(value = "addCoach/{teamName}/{ownerEmail}/{emailAddress}/{coachId}/{firstName}/{lastName}/{coachRole}/{qualificationCoach}/")
    @ResponseStatus(HttpStatus.OK)
    public void addCoach(@PathVariable String teamName, @PathVariable String ownerEmail, @PathVariable String emailAddress, @PathVariable String coachId, @PathVariable String firstName, @PathVariable String lastName, @PathVariable String coachRole, @PathVariable String qualificationCoach) throws Exception {
        try {
            CoachRole coachRole1 = CoachRole.getCoachRole(coachRole);
            QualificationCoach qualificationCoach1 = QualificationCoach.getQualificationCoach(qualificationCoach);
            teamOwnerController.addCoach(teamName, ownerEmail, emailAddress, Integer.parseInt(coachId), firstName, lastName, coachRole1, qualificationCoach1);
            logger.log(Level.INFO, "Created by: " + ownerEmail + " Description: Coach \"" + emailAddress + "\" added to Team: " + teamName);
        } catch (Exception e) {
            logger.log(Level.WARNING, "Created by: " + ownerEmail + " Description: Coach \"" + emailAddress + "\" wasn't added to Team \"" + teamName + "\" because " + e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    public void addCourt(String teamName, String teamOwnerMail, String courtName, String courtCity) throws Exception {
        try {
            teamOwnerController.addCourt(teamName, teamOwnerMail, courtName, courtCity);
            logger.log(Level.INFO, "Created by: " + teamOwnerMail + " Description: Court \"" + courtName + "\" added to Team \"" + teamName + "\"");
        } catch (Exception e) {
            logger.log(Level.WARNING, "Created by: " + teamOwnerMail + " Description: Court \"" + courtName + "\" wasn't added to Team \"" + teamName + "\" because " + e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    public void removePlayer(String teamName, String teamOwnerMail, String playerEmailAddress) throws Exception {
        try {
            teamOwnerController.removePlayer(teamName, teamOwnerMail, playerEmailAddress);
            logger.log(Level.INFO, "Created by: " + teamOwnerMail + " Description: Player \"" + playerEmailAddress + "\" removed from Team \"" + teamName + "\"");
        } catch (Exception e) {
            logger.log(Level.WARNING, "Created by: " + teamOwnerMail + " Description: Player \"" + playerEmailAddress + "\" wasn't removed from Team \"" + teamName + "\" because " + e.getMessage());
            throw e;
        }
    }

    public void removeTeamManager(String teamName, String ownerEmail, String teamManagerEmailAddress) throws Exception {
        try {
            teamOwnerController.removeTeamManager(teamName, ownerEmail, teamManagerEmailAddress);
            logger.log(Level.INFO, "Created by: " + ownerEmail + " Description: TeamManager \"" + teamManagerEmailAddress + "\" removed from Team \"" + teamName + "\"");
        } catch (Exception e) {
            logger.log(Level.WARNING, "Created by: " + ownerEmail + " Description: TeamManager \"" + teamManagerEmailAddress + "\" wasn't removed from Team \"" + teamName + "\" because " + e.getMessage());
            throw e;
        }
    }

    public void removeCoach(String teamName, String ownerEmail, String coachEmailAddress) throws Exception {
        try {
            teamOwnerController.removeCoach(teamName, ownerEmail, coachEmailAddress);
            logger.log(Level.INFO, "Created by: " + ownerEmail + " Description: Coach \"" + coachEmailAddress + "\" removed from Team \"" + teamName + "\"");
        } catch (Exception e) {
            logger.log(Level.WARNING, "Created by: " + ownerEmail + " Description: Coach \"" + coachEmailAddress + "\" wasn't removed from Team \"" + teamName + "\" because " + e.getMessage());
            throw e;
        }
    }

    public void removeCourt(String teamName, String ownerEmail, String courtName) throws Exception {
        try {
            teamOwnerController.removeCourt(teamName, ownerEmail, courtName);
            logger.log(Level.INFO, "Created by: " + ownerEmail + " Description: Court \"" + courtName + "\" removed from Team \"" + teamName + "\"");
        } catch (Exception e) {
            logger.log(Level.WARNING, "Created by: " + ownerEmail + " Description: Court \"" + courtName + "\" wasn't removed from Team \"" + teamName + "\" because " + e.getMessage());
            throw e;
        }
    }

    public void subscriptionTeamOwner(String teamName, String teamOwnerEmail, String ownerToAddEmail) throws Exception {
        try {
            teamOwnerController.subscriptionTeamOwner(teamName, teamOwnerEmail, ownerToAddEmail);
            logger.log(Level.INFO, "Created by: " + teamOwnerEmail + " Description: Owner \"" + ownerToAddEmail + "\" received subscription of teamOwner for Team \"" + teamName + "\"");
        } catch (Exception e) {
            logger.log(Level.WARNING, "Created by: " + teamOwnerEmail + " Description: Owner \"" + ownerToAddEmail + "\" wasn't received subscription of teamOwner for Team \"" + teamName + "\" because " + e.getMessage());
            throw e;
        }
    }

    public void subscriptionTeamManager(String teamName, String teamOwnerEmail, String managerToAddEmail, List<PermissionType> permissionTypes) throws Exception {
        try {
            teamOwnerController.subscriptionTeamManager(teamName, teamOwnerEmail, managerToAddEmail, permissionTypes);
            logger.log(Level.INFO, "Created by: " + teamOwnerEmail + " Description: TeamManager \"" + managerToAddEmail + "\" received subscription of teamManager for Team \"" + teamName + "\"");
        } catch (Exception e) {
            logger.log(Level.WARNING, "Created by: " + teamOwnerEmail + " Description: TeamManager \"" + managerToAddEmail + "\" wasn't received subscription of teamManager for Team \"" + teamName + "\" because " + e.getMessage());
            throw e;
        }
    }

    public void removeSubscriptionTeamOwner(String teamName, String teamOwnerEmail, String ownerToRemove) throws Exception {
        try {
            teamOwnerController.removeSubscriptionTeamOwner(teamName, teamOwnerEmail, ownerToRemove);
            logger.log(Level.INFO, "Created by: " + teamOwnerEmail + " Description: TeamOwner \"" + ownerToRemove + "\" removed subscription of teamOwner from Team \"" + teamName + "\"");
        } catch (Exception e) {
            logger.log(Level.WARNING, "Created by: " + teamOwnerEmail + " Description: TeamManager \"" + ownerToRemove + "\" wasn't removed subscription of teamOwner from Team \"" + teamName + "\" because " + e.getMessage());
            throw e;
        }
    }

    public void removeSubscriptionTeamManager(String teamName, String teamOwnerEmail, String managerToRemoveEmail) throws Exception {
        try {
            teamOwnerController.removeSubscriptionTeamManager(teamName, teamOwnerEmail, managerToRemoveEmail);
            logger.log(Level.INFO, "Created by: " + teamOwnerEmail + " Description: TeamManager \"" + managerToRemoveEmail + "\" removed subscription of teamManager from Team \"" + teamName + "\"");
        } catch (Exception e) {
            logger.log(Level.WARNING, "Created by: " + teamOwnerEmail + " Description: TeamManager \"" + managerToRemoveEmail + "\" wasn't removed subscription of teamManager from Team \"" + teamName + "\" because " + e.getMessage());
            throw e;
        }
    }

    public void addFinancialActivity(String teamName, String teamOwnerMail, Double financialActivityAmount, String Description, FinancialActivityType financialActivityType) throws Exception {
        try {
            teamOwnerController.addFinancialActivity(teamName, teamOwnerMail, financialActivityAmount, Description, financialActivityType);
            logger.log(Level.INFO, "Created by: " + teamOwnerMail + " Description: Financial Activity \"" + financialActivityAmount + " " + financialActivityType + "\"");
        } catch (Exception e) {
            logger.log(Level.WARNING, "Created by: " + teamOwnerMail + " Description: Financial Activity \"" + financialActivityAmount + " " + financialActivityType + "\" wasn't added to Team \"" + teamName + "\" because " + e.getMessage());
            throw e;
        }
    }

    public void changeStatusToInActive(String teamName, String teamOwnerMail) throws Exception {
        try {
            teamOwnerController.changeStatus(teamName, teamOwnerMail, TeamStatus.INACTIVE);
            logger.log(Level.INFO, "Created by: " + teamOwnerMail + " Description: Team \"" + teamName + "\" changed status to INACTIVE");
        } catch (Exception e) {
            logger.log(Level.WARNING, "Created by: " + teamOwnerMail + " Description: Team \"" + teamName + "\" can't changed status to INACTIVE because " + e.getMessage());
            throw e;
        }
    }

    public void changeStatusToActive(String teamName, String teamOwnerMail) throws Exception {
        try {
            teamOwnerController.changeStatus(teamName, teamOwnerMail, TeamStatus.ACTIVE);
            logger.log(Level.INFO, "Created by: " + teamOwnerMail + " Description: Team \"" + teamName + "\" changed status to ACTIVE");
        } catch (Exception e) {
            logger.log(Level.WARNING, "Created by: " + teamOwnerMail + " Description: Team \"" + teamName + "\" can't changed status to ACTIVE because " + e.getMessage());
            throw e;
        }
    }

    public void updatePlayerDetails(String teamName, String teamOwnerMail, String playerMail, String firstName, String lastName, Date birthDate, PlayerRole playerRole) throws Exception {
        teamOwnerController.updatePlayerDetails(teamName, teamOwnerMail, playerMail, firstName, lastName, birthDate, playerRole);
    }

    public void updateTeamManagerDetails(String teamName, String teamOwnerMail, String teamManagerMail, String firstName, String lastName, List<PermissionType> permissionTypes) throws Exception {
        teamOwnerController.updateTeamManagerDetails(teamName, teamOwnerMail, teamManagerMail, firstName, lastName, permissionTypes);
    }

    public void updateCoachDetails(String teamName, String teamOwnerMail, String coachMail, String firstName, String lastName, CoachRole coachRole, QualificationCoach qualificationCoach) throws Exception {
        teamOwnerController.updateCoachDetails(teamName, teamOwnerMail, coachMail, firstName, lastName, coachRole, qualificationCoach);
    }

    public void updateCourtDetails(String teamName, String ownerEmailAddress, String courtName, String courtCity) throws Exception {
        teamOwnerController.updateCourtDetails(teamName, ownerEmailAddress, courtName, courtCity);
    }

//
//    public static void main(String[] args) throws Exception {
//       TeamOwnerService teamOwnerService = new TeamOwnerService();
//        String teamName = "TeamName";
//        teamOwnerService.teamOwnerController.getTeamDb().insertTeam(teamName, budget, TeamStatus.ACTIVE);
//        String ownerMail = "owner@gmail.com";
//        Court court = new Court("courtName", "courtCity");
//        CourtDbInMemory.getInstance().createCourt(court);
//
//        TeamOwner teamOwner = new TeamOwner(ownerMail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName");
//        teamOwnerService.teamOwnerController.getTeamOwnerDb().createTeamOwner(teamOwner);
//        RoleDbInMemory.getInstance().createRole(ownerMail, teamName, RoleType.TEAM_OWNER);
//        SubscriberDbInMemory.getInstance().createSubscriber(teamOwner);
//
//
//        ArrayList<TeamManager> teamManagers = new ArrayList<>();
//        teamManagers.add(new TeamManager("email1@gmail.com", 2, "first", "last", "owner@gmail.com"));
//        teamManagers.add(new TeamManager("email2@gmail.com", 3, "first", "last", "owner@gmail.com"));
//        ArrayList<Coach> coaches = new ArrayList<>();
//        coaches.add(new Coach("email3@gmail.com", 4, "first", "last", CoachRole.MAJOR, QualificationCoach.UEFA_A));
//        coaches.add(new Coach("email4@gmail.com", 5, "first", "last", CoachRole.MAJOR, QualificationCoach.UEFA_A));
//        ArrayList<Player> players = new ArrayList<>();
//        players.add(new Player("email5@gmail.com", 6, "firstPlayer", "lastPlayer", new Date(), PlayerRole.GOALKEEPER));
//        players.add(new Player("email6@gmail.com", 7, "firstPlayer", "lastPlayer", new Date(), PlayerRole.GOALKEEPER));
//
//
////        teamOwnerService.createNewTeam(teamName, ownerMail, players, coaches, teamManagers, court, 1000.0);
//
//    }
}