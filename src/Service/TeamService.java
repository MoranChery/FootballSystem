package Service;

import Controller.TeamController;
import Model.Enums.*;

import java.util.Date;

public class TeamService {
    TeamController teamController;

    public TeamService() {
        this.teamController = new TeamController();
    }

    /*will receive from the UI the team's name and the player Id want to add and will continue to controller*/
    public void addPlayer(String teamName, String emailAddress, Integer playerId, String firstName, String lastName, Date birthDate, PlayerRole playerRole) throws Exception {
        teamController.addPlayer(teamName,emailAddress,playerId, firstName, lastName, birthDate, playerRole);
    }

    public void addTeamManager(String teamName, String emailAddress, Integer teamManagerId, String firstName, String lastName,Integer ownedById) throws Exception {
        teamController.addTeamManager(teamName,emailAddress, teamManagerId, firstName, lastName,ownedById);
    }

    public void addCoach(String teamName,  String emailAddress, Integer coachId, String firstName, String lastName, CoachRole coachRole, QualificationCoach qualificationCoach) throws Exception {
        teamController.addCoach(teamName,emailAddress, coachId, firstName, lastName, coachRole, qualificationCoach);
    }

    public void addCourt(String teamName, String courtName, String courtCity) throws Exception {
        teamController.addCourt(teamName, courtName, courtCity);
    }

    public void removePlayer(String teamName, String playerEmailAddress) throws Exception {
        teamController.removePlayer(teamName, playerEmailAddress);
    }

    public void removeTeamManager(String teamName, String teamManagerEmailAddress) throws Exception {
        teamController.removeTeamManager(teamName, teamManagerEmailAddress);
    }

    public void removeCoach(String teamName, String coachEmailAddress) throws Exception {
        teamController.removeCoach(teamName, coachEmailAddress);
    }

    public void removeCourt(String teamName, String courtName) throws Exception {
        teamController.removeCourt(teamName, courtName);
    }

    public void subscriptionTeamOwner(String teamName, String teamOwnerEmail, String ownerToAddEmail) throws Exception {
        teamController.subscriptionTeamOwner(teamName,teamOwnerEmail,ownerToAddEmail);
    }

    public void subscriptionTeamManager(String teamName, Integer teamOwnerId, Integer managerToAdd) throws Exception {
        teamController.subscriptionTeamManager(teamName,teamOwnerId,managerToAdd);
    }

    public void removeSubscriptionTeamOwner(String teamName, Integer teamOwnerId, Integer ownerToRemove) throws Exception {
        teamController.removeSubscriptionTeamOwner(teamName,teamOwnerId,ownerToRemove);
    }

    public void removeSubscriptionTeamManager(String teamName, Integer teamOwnerId, Integer managerToRemove) throws Exception {
        teamController.removeSubscriptionTeamManager(teamName,teamOwnerId,managerToRemove);
    }

    public void addFinancialActivity(String teamName, Double financialActivityAmount, String description, FinancialActivityType financialActivityType) throws Exception {
        teamController.addFinancialActivity(teamName, financialActivityAmount, description, financialActivityType);
    }

    public void changeStatusToInActive(String teamName) throws Exception {
        teamController.changeStatus(teamName, TeamStatus.INACTIVE);
    }

    public void changeStatusToActive(String teamName) throws Exception {
        teamController.changeStatus(teamName,TeamStatus.ACTIVE);
    }
}