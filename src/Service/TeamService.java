package Service;

import Controller.PlayerController;
import Controller.TeamController;
import Model.Enums.CoachRole;
import Model.Enums.PlayerRole;
import Model.Enums.Qualification;
import Model.Team;

import java.util.Date;

public class TeamService {
    TeamController teamController;

    /*will receive from the UI the team's name and the player Id want to add and will continue to controller*/
    public void addPlayer(String teamName, Integer playerId, String firstName, String lastName, Date birthDate, PlayerRole playerRole) throws Exception {
        teamController.addPlayer(teamName,playerId,firstName,lastName,birthDate,playerRole);
    }

    public void addTeamManager(String teamName, Integer teamManagerId, String firstName ,String lastName) throws Exception {
        teamController.addTeamManager(teamName, teamManagerId,firstName,lastName);
    }

    public void addCoach(String teamName, Integer coachId, String firstName, String lastName, CoachRole coachRole, Qualification qualification) throws Exception {
        teamController.addCoach(teamName, coachId, firstName, lastName, coachRole, qualification);
    }

    public void addCourt(String teamName, String courtName, String courtCity) throws Exception {
        teamController.addCourt(teamName, courtName, courtCity);
    }
}
