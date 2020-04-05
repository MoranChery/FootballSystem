package Model;

import Model.UsersTypes.Coach;
import Model.UsersTypes.Player;
import Model.UsersTypes.TeamManager;

import java.util.*;

public class Team {
   private String teamName;
   private Map<Integer, Player> players;
   private List<Coach> coaches;
   private List<TeamManager> teamManagers;
   private Court court;
   private PersonalPage teamPage;
   private List<Game> games;

    public Team() {
        players = new HashMap<>();
        coaches = new ArrayList<>();
        teamManagers = new ArrayList<>();
        games = new ArrayList<>();
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public Map<Integer, Player> getPlayers() {
        return players;
    }

    public void setPlayers(Map<Integer, Player> players) {
        this.players = players;
    }

    public List<Coach> getCoaches() {
        return coaches;
    }

    public void setCoaches(List<Coach> coaches) {
        this.coaches = coaches;
    }

    public List<TeamManager> getTeamManagers() {
        return teamManagers;
    }

    public void setTeamManagers(List<TeamManager> teamManagers) {
        this.teamManagers = teamManagers;
    }

    public Court getCourt() {
        return court;
    }

    public void setCourt(Court court) {
        this.court = court;
    }

    public PersonalPage getTeamPage() {
        return teamPage;
    }

    public void setTeamPage(PersonalPage teamPage) {
        this.teamPage = teamPage;
    }

    public List<Game> getGames() {
        return games;
    }

    public void setGames(List<Game> games) {
        this.games = games;
    }
}
