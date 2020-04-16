package Model;

import Model.Enums.TeamStatus;
import Model.UsersTypes.Coach;
import Model.UsersTypes.Player;
import Model.UsersTypes.TeamManager;
import Model.UsersTypes.TeamOwner;

import java.util.*;

public class Team {
   private String teamName;
   private Map <Integer, TeamOwner> teamOwners;
   private Map<Integer, Player> players;
   private Map<Integer,Coach> coaches;
   private Map<Integer, TeamManager> teamManagers;
   private Map<String, FinancialActivity> financialActivities;
   private Court court;
   private List<Game> games;
   private Double budget;
   private TeamStatus teamStatus;
   private TeamPage teamPage;

    public Team() {
        teamOwners = new HashMap<>();
        players = new HashMap<>();
        coaches = new HashMap<>();
        teamManagers = new HashMap<>();
        games = new ArrayList<>();
        teamStatus = TeamStatus.ACTIVE;
        financialActivities = new HashMap<>();

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

    public List<Game> getGames() {
        return games;
    }

    public void setGames(List<Game> games) {
        this.games = games;
    }

    public Court getCourt() {
        return court;
    }

    public void setCourt(Court court) {
        this.court = court;
    }

    public Map<Integer, TeamOwner> getTeamOwners() {
        return teamOwners;
    }

    public void setTeamOwners(Map<Integer, TeamOwner> teamOwners) {
        this.teamOwners = teamOwners;
    }

    public Map<String, FinancialActivity> getFinancialActivities() {
        return financialActivities;
    }

    public void setFinancialActivities(Map<String, FinancialActivity> financialActivities) {
        this.financialActivities = financialActivities;
    }

    public Double getBudget() {
        return budget;
    }

    public void setBudget(Double budget) {
        this.budget = budget;
    }

    public TeamStatus getTeamStatus() {
        return teamStatus;
    }

    public void setTeamStatus(TeamStatus teamStatus) {
        this.teamStatus = teamStatus;
    }

    public TeamPage getTeamPage() {
        return teamPage;
    }

    public void setTeamPage(TeamPage teamPage) {
        this.teamPage = teamPage;
    }
}
