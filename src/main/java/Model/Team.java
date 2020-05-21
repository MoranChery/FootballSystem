package Model;

import Model.Enums.TeamStatus;
import Model.UsersTypes.Coach;
import Model.UsersTypes.Player;
import Model.UsersTypes.TeamManager;
import Model.UsersTypes.TeamOwner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Team {
   private String teamName;
   private Map <String, TeamOwner> teamOwners;
   private Map<String, Player> players;
   private Map<String,Coach> coaches;
   private Map<String, TeamManager> teamManagers;
   private Map<String, FinancialActivity> financialActivities;
   private Court court;
   private List<Game> games;
   private Double budget;
   private TeamStatus teamStatus;
   private TeamPage teamPage;
   private String teamClose;

    public Team() {
        teamOwners = new HashMap<>();
        players = new HashMap<>();
        coaches = new HashMap<>();
        teamManagers = new HashMap<>();
        games = new ArrayList<>();
        teamStatus = TeamStatus.ACTIVE;
        financialActivities = new HashMap<>();
        teamClose = null;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public Map<String, Player> getPlayers() {
        return players;
    }

    public void setPlayers(Map<String, Player> players) {
        this.players = players;
    }

    public void setCoaches(Map<String, Coach> coaches) {
        this.coaches = coaches;
    }

    public Map<String, Coach> getCoaches() {
        return coaches;
    }

    public Map<String, TeamManager> getTeamManagers() {
        return teamManagers;
    }

    public void setTeamManagers(Map<String, TeamManager> teamManagers) {
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

    public Map<String, TeamOwner> getTeamOwners() {
        return teamOwners;
    }

    public void setTeamOwners(Map<String, TeamOwner> teamOwners) {
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

    public String getTeamClose() {
        return teamClose;
    }

    public void setTeamClose(String teamClose) {
        this.teamClose = teamClose;
    }
}
