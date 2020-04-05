package Service;

import Controller.PlayerController;
import Controller.TeamController;

public class TeamService {
    TeamController teamController;

    /*will receive from the UI the team's name and the player Id want to add and will continue to controller*/
    public void addPlayer(String teamName, Integer playerId) throws Exception {
        teamController.addPlayer(teamName,playerId);
    }
}
