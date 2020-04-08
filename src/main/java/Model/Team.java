package Model;

import Model.UsersTypes.Coach;
import Model.UsersTypes.Player;
import Model.UsersTypes.TeamManager;

import java.util.*;

public class Team {
   private String teamName;
   private Map<Integer, Player> players;
   private  Map<Integer,Coach> coaches;
   private Map<Integer, TeamManager> teamManagers;
   private HashMap<Integer, FinancialActivity> financialActivities;

    public Court getCourt() {
        return court;
    }

    public void setCourt(Court court) {
        this.court = court;
    }

    private Court court;
   private PersonalPage teamPage;
   private List<Game> games;

    public Team() {
        players = new HashMap<>();
        coaches = new HashMap<>();
        teamManagers = new HashMap<>();
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

    public void setCoaches(Map<Integer, Coach> coaches) {
        this.coaches = coaches;
    }

    public Map<Integer, Coach> getCoaches() {
        return coaches;
    }

    public Map<Integer, TeamManager> getTeamManagers() {
        return teamManagers;
    }

    public void setTeamManagers(Map<Integer, TeamManager> teamManagers) {
        this.teamManagers = teamManagers;
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
