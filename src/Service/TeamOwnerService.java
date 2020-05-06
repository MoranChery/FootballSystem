package Service;

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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;

public class TeamOwnerService {
    private TeamOwnerController teamOwnerController;
    private LoggerHandler loggerHandler;

    public TeamOwnerService()  {
        this.teamOwnerController = new TeamOwnerController();
        this.loggerHandler = new LoggerHandler(TeamOwnerService.class.getName());
    }

    public Team getTeam(String teamName) throws Exception {
        return teamOwnerController.getTeam(teamName);
    }

    public void createNewTeam(String teamName, String teamOwnerEmail, List<Player> players, List<Coach> coaches, List<TeamManager> teamManagers, Court court, Double budget) throws Exception {
        try {
            teamOwnerController.createNewTeam(teamName, teamOwnerEmail, players, coaches, teamManagers, court, budget);
            loggerHandler.getLoggerEvents().log(Level.INFO, "Created by: " + teamOwnerEmail + " Description: Team \"" + teamName + "\" was created");
        } catch (Exception e) {
            loggerHandler.getLoggerErrors().log(Level.WARNING,"Created by: " + teamOwnerEmail+ " Description: Team \"" + teamName + "\" wasn't created because: " +  e.getMessage());
        }
    }

    /*will receive from the UI the team's name and the player Id want to add and will continue to controller*/
    public void addPlayer(String teamName, String ownerEmail, String emailAddress, Integer playerId, String firstName, String lastName, Date birthDate, PlayerRole playerRole) throws Exception {
        try {
            teamOwnerController.addPlayer(teamName, ownerEmail, emailAddress, playerId, firstName, lastName, birthDate, playerRole);
            loggerHandler.getLoggerEvents().log(Level.INFO, "Created by: " + ownerEmail + " Description: Player \"" + emailAddress + "\" added to Team:" + teamName);

        }catch(Exception e){
            loggerHandler.getLoggerErrors().log(Level.WARNING,"Created by: " + ownerEmail+ " Description: Player \"" + emailAddress + "\" wasn't added to Team: " + teamName + " because "+  e.getMessage());
        }
        }

    public void addTeamManager(String teamName, String emailAddress, Integer teamManagerId, String firstName, String lastName, List<PermissionType> permissionTypes, String ownedByEmail) throws Exception {
        try {
            teamOwnerController.addTeamManager(teamName, emailAddress, teamManagerId, firstName, lastName, permissionTypes, ownedByEmail);
            loggerHandler.getLoggerEvents().log(Level.INFO, "Created by: " + ownedByEmail + " Description: TeamManager \"" + emailAddress + "\" added to Team \"" + teamName + "\"");
        }catch (Exception e){
            loggerHandler.getLoggerErrors().log(Level.WARNING,"Created by: " + ownedByEmail+ " Description: TeamManager \"" + emailAddress + "\" wasn't added to Team \"" + teamName + "\" because "+  e.getMessage());
        }
    }

    public void addCoach(String teamName, String ownerEmail, String emailAddress, Integer coachId, String firstName, String lastName, CoachRole coachRole, QualificationCoach qualificationCoach) throws Exception {
      try{
        teamOwnerController.addCoach(teamName, ownerEmail, emailAddress, coachId, firstName, lastName, coachRole, qualificationCoach);
        loggerHandler.getLoggerEvents().log(Level.INFO, "Created by: " + ownerEmail + " Description: Coach \"" + emailAddress + "\" added to Team: " + teamName);
    }catch (Exception e){
          loggerHandler.getLoggerErrors().log(Level.WARNING,"Created by: " + ownerEmail+ " Description: Coach \"" + emailAddress + "\" wasn't added to Team \"" + teamName + "\" because "+  e.getMessage());

    }
    }

    public void addCourt(String teamName, String teamOwnerMail, String courtName, String courtCity) throws Exception {
        try {
            teamOwnerController.addCourt(teamName, teamOwnerMail, courtName, courtCity);
            loggerHandler.getLoggerEvents().log(Level.INFO, "Created by: " + teamOwnerMail + " Description: Court \"" + courtName + "\" added to Team \"" + teamName + "\"");
        }catch (Exception e){
            loggerHandler.getLoggerErrors().log(Level.WARNING,"Created by: " + teamOwnerMail+ " Description: Court \"" + courtName + "\" wasn't added to Team \"" + teamName + "\" because "+  e.getMessage());
        }
    }

    public void removePlayer(String teamName, String teamOwnerMail, String playerEmailAddress) throws Exception {
        try{
        teamOwnerController.removePlayer(teamName, teamOwnerMail, playerEmailAddress);
        loggerHandler.getLoggerEvents().log(Level.INFO, "Created by: " + teamOwnerMail + " Description: Player \"" + playerEmailAddress + "\" removed from Team \"" + teamName + "\"");
    }catch (Exception e){
            loggerHandler.getLoggerErrors().log(Level.WARNING,"Created by: " + teamOwnerMail+ " Description: Player \"" + playerEmailAddress + "\" wasn't removed from Team \"" + teamName + "\" because "+  e.getMessage());
    }
    }

    public void removeTeamManager(String teamName, String ownerEmail, String teamManagerEmailAddress) throws Exception {
        try{
        teamOwnerController.removeTeamManager(teamName, ownerEmail, teamManagerEmailAddress);
        loggerHandler.getLoggerEvents().log(Level.INFO, "Created by: " + ownerEmail + " Description: TeamManager \"" + teamManagerEmailAddress + "\" removed from Team \"" + teamName + "\"");
    }catch (Exception e){
            loggerHandler.getLoggerErrors().log(Level.WARNING,"Created by: " + ownerEmail+ " Description: TeamManager \"" + teamManagerEmailAddress + "\" wasn't removed from Team \"" + teamName + "\" because "+  e.getMessage());
    }
    }

    public void removeCoach(String teamName, String ownerEmail, String coachEmailAddress) throws Exception {
        try{
        teamOwnerController.removeCoach(teamName, ownerEmail, coachEmailAddress);
        loggerHandler.getLoggerEvents().log(Level.INFO, "Created by: " + ownerEmail + " Description: Coach \"" + coachEmailAddress + "\" removed from Team \"" + teamName + "\"");
        }catch (Exception e){
            loggerHandler.getLoggerErrors().log(Level.WARNING,"Created by: " + ownerEmail+ " Description: Coach \"" + coachEmailAddress + "\" wasn't removed from Team \"" + teamName + "\" because "+  e.getMessage());

        }
    }

    public void removeCourt(String teamName, String ownerEmail, String courtName) throws Exception {
        try{
        teamOwnerController.removeCourt(teamName, ownerEmail, courtName);
        loggerHandler.getLoggerEvents().log(Level.INFO, "Created by: " + ownerEmail + " Description: Court \"" + courtName + "\" removed from Team \"" + teamName + "\"");
        }catch (Exception e){
            loggerHandler.getLoggerErrors().log(Level.WARNING,"Created by: " + ownerEmail+ " Description: Court \"" + courtName + "\" wasn't removed from Team \"" + teamName + "\" because "+  e.getMessage());
        }
    }

    public void subscriptionTeamOwner(String teamName, String teamOwnerEmail, String ownerToAddEmail) throws Exception {
        try{
        teamOwnerController.subscriptionTeamOwner(teamName, teamOwnerEmail, ownerToAddEmail);
        loggerHandler.getLoggerEvents().log(Level.INFO, "Created by: " + teamOwnerEmail + " Description: Owner \"" + ownerToAddEmail + "\" received subscription of teamOwner for Team \"" + teamName + "\"");
    }catch (Exception e){
            loggerHandler.getLoggerErrors().log(Level.WARNING,"Created by: " + teamOwnerEmail+ " Description: Owner \"" + ownerToAddEmail + "\" wasn't received subscription of teamOwner for Team \"" + teamName + "\" because "+  e.getMessage());
    }
    }

    public void subscriptionTeamManager(String teamName, String teamOwnerEmail, String managerToAddEmail, List<PermissionType> permissionTypes) throws Exception {
        try{
        teamOwnerController.subscriptionTeamManager(teamName, teamOwnerEmail, managerToAddEmail, permissionTypes);
        loggerHandler.getLoggerEvents().log(Level.INFO, "Created by: " + teamOwnerEmail + " Description: TeamManager \"" + managerToAddEmail + "\" received subscription of teamManager for Team \"" + teamName + "\"");
    }catch (Exception e){
            loggerHandler.getLoggerErrors().log(Level.WARNING,"Created by: " + teamOwnerEmail+ " Description: TeamManager \"" + managerToAddEmail + "\" wasn't received subscription of teamManager for Team \"" + teamName + "\" because "+  e.getMessage());
    }
    }

    public void removeSubscriptionTeamOwner(String teamName, String teamOwnerEmail, String ownerToRemove) throws Exception {
     try{
        teamOwnerController.removeSubscriptionTeamOwner(teamName, teamOwnerEmail, ownerToRemove);
        loggerHandler.getLoggerEvents().log(Level.INFO, "Created by: " + teamOwnerEmail + " Description: TeamOwner \"" + ownerToRemove + "\" removed subscription of teamOwner from Team \"" + teamName + "\"");
    }catch (Exception e){
         loggerHandler.getLoggerErrors().log(Level.WARNING,"Created by: " + teamOwnerEmail+ " Description: TeamManager \"" + ownerToRemove + "\" wasn't removed subscription of teamOwner from Team \"" + teamName + "\" because "+  e.getMessage());
    }
    }

    public void removeSubscriptionTeamManager(String teamName, String teamOwnerEmail, String managerToRemoveEmail) throws Exception {
    try{
        teamOwnerController.removeSubscriptionTeamManager(teamName, teamOwnerEmail, managerToRemoveEmail);
        loggerHandler.getLoggerEvents().log(Level.INFO, "Created by: " + teamOwnerEmail + " Description: TeamManager \"" + managerToRemoveEmail + "\" removed subscription of teamManager from Team \"" + teamName + "\"");
    }catch (Exception e){
        loggerHandler.getLoggerErrors().log(Level.WARNING,"Created by: " + teamOwnerEmail+ " Description: TeamManager \"" + managerToRemoveEmail + "\" wasn't removed subscription of teamManager from Team \"" + teamName + "\" because "+  e.getMessage());
    }
    }

    public void addFinancialActivity(String teamName, String teamOwnerMail, Double financialActivityAmount, String Description, FinancialActivityType financialActivityType) throws Exception {
       try{
        teamOwnerController.addFinancialActivity(teamName, teamOwnerMail, financialActivityAmount, Description, financialActivityType);
        loggerHandler.getLoggerEvents().log(Level.INFO, "Created by: " + teamOwnerMail + " Description: Financial Activity \"" + financialActivityAmount + " " + financialActivityType + "\"");
    }catch (Exception e){
           loggerHandler.getLoggerErrors().log(Level.WARNING,"Created by: " + teamOwnerMail+ " Description: Financial Activity \"" + financialActivityAmount + " " + financialActivityType + "\" wasn't added to Team \"" + teamName + "\" because "+  e.getMessage());
       }
    }

    public void changeStatusToInActive(String teamName, String teamOwnerMail) throws Exception {
        try{
        teamOwnerController.changeStatus(teamName, teamOwnerMail, TeamStatus.INACTIVE);
        loggerHandler.getLoggerEvents().log(Level.INFO, "Created by: " + teamOwnerMail + " Description: Team \"" + teamName + "\" changed status to INACTIVE");
    }catch (Exception e){
            loggerHandler.getLoggerErrors().log(Level.WARNING,"Created by: " + teamOwnerMail+ " Description: Team \"" + teamName +  "\" can't changed status to INACTIVE because "+  e.getMessage());
    }
    }

    public void changeStatusToActive(String teamName, String teamOwnerMail) throws Exception {
        try{
        teamOwnerController.changeStatus(teamName, teamOwnerMail, TeamStatus.ACTIVE);
        loggerHandler.getLoggerEvents().log(Level.INFO, "Created by: " + teamOwnerMail + " Description: Team \"" + teamName + "\" changed status to ACTIVE");
        }catch (Exception e){
            loggerHandler.getLoggerErrors().log(Level.WARNING,"Created by: " + teamOwnerMail+ " Description: Team \"" + teamName +  "\" can't changed status to ACTIVE because "+  e.getMessage());
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


    public static void main(String[] args) throws Exception {
       TeamOwnerService teamOwnerService = new TeamOwnerService();
        String teamName = "TeamName";
        teamOwnerService.teamOwnerController.getTeamDb().createTeam(teamName);
        String ownerMail = "owner@gmail.com";
        Court court = new Court("courtName", "courtCity");
        CourtDbInMemory.getInstance().createCourt(court);

        TeamOwner teamOwner = new TeamOwner(ownerMail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName");
        teamOwnerService.teamOwnerController.getTeamOwnerDb().createTeamOwner(teamOwner);
        RoleDbInMemory.getInstance().createRole(ownerMail, teamName, RoleType.TEAM_OWNER);
        SubscriberDbInMemory.getInstance().createSubscriber(teamOwner);


        ArrayList<TeamManager> teamManagers = new ArrayList<>();
        teamManagers.add(new TeamManager("email1@gmail.com", 2, "first", "last", "owner@gmail.com"));
        teamManagers.add(new TeamManager("email2@gmail.com", 3, "first", "last", "owner@gmail.com"));
        ArrayList<Coach> coaches = new ArrayList<>();
        coaches.add(new Coach("email3@gmail.com", 4, "first", "last", CoachRole.MAJOR, QualificationCoach.UEFA_A));
        coaches.add(new Coach("email4@gmail.com", 5, "first", "last", CoachRole.MAJOR, QualificationCoach.UEFA_A));
        ArrayList<Player> players = new ArrayList<>();
        players.add(new Player("email5@gmail.com", 6, "firstPlayer", "lastPlayer", new Date(), PlayerRole.GOALKEEPER));
        players.add(new Player("email6@gmail.com", 7, "firstPlayer", "lastPlayer", new Date(), PlayerRole.GOALKEEPER));


        teamOwnerService.createNewTeam(teamName, ownerMail, players, coaches, teamManagers, court, 1000.0);

    }
}