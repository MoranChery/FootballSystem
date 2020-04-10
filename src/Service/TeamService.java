package Service;

import Controller.PlayerController;
import Controller.TeamController;
import Model.Enums.CoachRole;
import Model.Enums.FinancialActivityType;
import Model.Enums.PlayerRole;
import Model.Enums.Qualification;
import Model.Team;

import java.util.Date;

public class TeamService {
    TeamController teamController;

    public TeamService(TeamController teamController) {
        this.teamController = teamController;
    }

    /*will receive from the UI the team's name and the player Id want to add and will continue to controller*/
    public void addPlayer(String teamName, Integer playerId, String firstName, String lastName, Date birthDate, PlayerRole playerRole) throws Exception {
        teamController.addPlayer(teamName, playerId, firstName, lastName, birthDate, playerRole);
    }

    public void addTeamManager(String teamName, Integer teamManagerId, String firstName, String lastName) throws Exception {
        teamController.addTeamManager(teamName, teamManagerId, firstName, lastName);
    }

    public void addCoach(String teamName, Integer coachId, String firstName, String lastName, CoachRole coachRole, Qualification qualification) throws Exception {
        teamController.addCoach(teamName, coachId, firstName, lastName, coachRole, qualification);
    }

    public void addCourt(String teamName, String courtName, String courtCity) throws Exception {
        teamController.addCourt(teamName, courtName, courtCity);
    }

    public void removePlayer(String teamName, Integer playerId) throws Exception {
        teamController.removePlayer(teamName, playerId);
    }

    public void removeTeamManager(String teamName, Integer teamManagerId) throws Exception {
        teamController.removeTeamManager(teamName, teamManagerId);
    }

    public void removeCoach(String teamName, Integer coachId) throws Exception {
        teamController.removeCoach(teamName, coachId);
    }

    public void removeCourt(String teamName, String courtName) throws Exception {
        teamController.removeCourt(teamName, courtName);
    }

    public void subscriptionTeamOwner(String teamName, Integer teamOwnerId, Integer ownerToAdd) throws Exception {
        teamController.subscriptionTeamOwner(teamName,teamOwnerId,ownerToAdd);
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
        teamController.changeStatusToInActive(teamName);
    }

    public void changeStatusToActive(String teamName) throws Exception {
        teamController.changeStatusToActive(teamName);
    }
}