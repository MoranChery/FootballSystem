package Service;

import Controller.TeamOwnerController;
import Model.Court;
import Model.Enums.*;
import Model.Team;
import Model.UsersTypes.Coach;
import Model.UsersTypes.Player;
import Model.UsersTypes.TeamManager;

import java.util.Date;
import java.util.List;

public class TeamService {
    private TeamOwnerController teamOwnerController;

    public TeamService() {
        this.teamOwnerController = new TeamOwnerController();
    }
    public Team getTeam(String teamName) throws Exception {
         return teamOwnerController.getTeam(teamName);
    }

    public void createNewTeam(String teamName, String teamOwnerEmail, List<Player> players, List<Coach> coaches, List<TeamManager> teamManagers, Court court,Double budget) throws Exception {
        teamOwnerController.createNewTeam(teamName, teamOwnerEmail, players,  coaches, teamManagers, court,budget);
    }
        /*will receive from the UI the team's name and the player Id want to add and will continue to controller*/
    public void addPlayer(String teamName, String ownerEmail,String emailAddress, Integer playerId, String firstName, String lastName, Date birthDate, PlayerRole playerRole) throws Exception {
        teamOwnerController.addPlayer(teamName,ownerEmail,emailAddress,playerId, firstName, lastName, birthDate, playerRole);
    }

    public void addTeamManager(String teamName, String emailAddress, Integer teamManagerId, String firstName, String lastName,List<PermissionType> permissionTypes,String ownedByEmail) throws Exception {
        teamOwnerController.addTeamManager(teamName,emailAddress, teamManagerId, firstName, lastName,permissionTypes,ownedByEmail);
    }

    public void addCoach(String teamName, String ownerEmail, String emailAddress, Integer coachId, String firstName, String lastName, CoachRole coachRole, QualificationCoach qualificationCoach) throws Exception {
        teamOwnerController.addCoach(teamName,ownerEmail,emailAddress, coachId, firstName, lastName, coachRole, qualificationCoach);
    }

    public void addCourt(String teamName,String emailAddress, String courtName, String courtCity) throws Exception {
        teamOwnerController.addCourt(teamName, emailAddress,courtName, courtCity);
    }

    public void removePlayer(String teamName,String emailAddress, String playerEmailAddress) throws Exception {
        teamOwnerController.removePlayer(teamName,emailAddress, playerEmailAddress);
    }

    public void removeTeamManager(String teamName, String ownerEmail, String teamManagerEmailAddress) throws Exception {
        teamOwnerController.removeTeamManager(teamName, ownerEmail,teamManagerEmailAddress);
    }

    public void removeCoach(String teamName, String emailAddress,String coachEmailAddress) throws Exception {
        teamOwnerController.removeCoach(teamName, emailAddress,coachEmailAddress);
    }

    public void removeCourt(String teamName, String emailAddress,String courtName) throws Exception {
        teamOwnerController.removeCourt(teamName, emailAddress,courtName);
    }

    public void subscriptionTeamOwner(String teamName, String teamOwnerEmail, String ownerToAddEmail) throws Exception {
        teamOwnerController.subscriptionTeamOwner(teamName,teamOwnerEmail,ownerToAddEmail);
    }

    public void subscriptionTeamManager(String teamName, String teamOwnerId, String managerToAddEmail,List<PermissionType> permissionTypes) throws Exception {
        teamOwnerController.subscriptionTeamManager(teamName,teamOwnerId,managerToAddEmail,permissionTypes);
    }

    public void removeSubscriptionTeamOwner(String teamName, String teamOwnerId, String ownerToRemove) throws Exception {
        teamOwnerController.removeSubscriptionTeamOwner(teamName,teamOwnerId,ownerToRemove);
    }

    public void removeSubscriptionTeamManager(String teamName, String teamOwnerEmail, String managerToRemoveEmail) throws Exception {
        teamOwnerController.removeSubscriptionTeamManager(teamName,teamOwnerEmail,managerToRemoveEmail);
    }

    public void addFinancialActivity(String teamName, String emailAddress, Double financialActivityAmount, String description, FinancialActivityType financialActivityType) throws Exception {
        teamOwnerController.addFinancialActivity(teamName, emailAddress,financialActivityAmount, description, financialActivityType);
    }

    public void changeStatusToInActive(String teamName, String emailAddress) throws Exception {
        teamOwnerController.changeStatus(teamName,emailAddress, TeamStatus.INACTIVE);
    }

    public void changeStatusToActive(String teamName, String emailAddress) throws Exception {
        teamOwnerController.changeStatus(teamName,emailAddress,TeamStatus.ACTIVE); }

    public void updatePlayerDetails(String teamName,String teamOwnerMail, String playerMail, String firstName, String lastName, Date birthDate, PlayerRole playerRole) throws Exception {
        teamOwnerController.updatePlayerDetails(teamName,teamOwnerMail,playerMail,firstName,lastName, birthDate, playerRole);
        }
    public void updateTeamManagerDetails(String teamName,String teamOwnerMail, String teamManagerMail, String firstName, String lastName,List<PermissionType> permissionTypes) throws Exception {
        teamOwnerController.updateTeamManagerDetails(teamName,teamOwnerMail,teamManagerMail,firstName,lastName,permissionTypes);
    }
    public void updateCoachDetails(String teamName,String teamOwnerMail, String coachMail, String firstName, String lastName,CoachRole coachRole,QualificationCoach qualificationCoach) throws Exception {
        teamOwnerController.updateCoachDetails(teamName,teamOwnerMail,coachMail,firstName,lastName,coachRole,qualificationCoach);
    }
    public void updateCourtDetails(String teamName,String ownerEmailAddress,String courtName, String courtCity) throws Exception {
        teamOwnerController.updateCourtDetails(teamName,ownerEmailAddress, courtName,courtCity);
    }
    }