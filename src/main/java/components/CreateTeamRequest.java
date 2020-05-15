package components;

import Model.Court;
import Model.UsersTypes.Coach;
import Model.UsersTypes.Player;
import Model.UsersTypes.TeamManager;

import java.util.List;
import java.util.Objects;

public class CreateTeamRequest {
    private String teamName;
    private String teamOwnerEmail;
    private List<Player> players;
    private List<Coach> coaches;
    private List<TeamManager> teamManagers;
    private Model.Court court;
    private Double budget;

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getTeamOwnerEmail() {
        return teamOwnerEmail;
    }

    public void setTeamOwnerEmail(String teamOwnerEmail) {
        this.teamOwnerEmail = teamOwnerEmail;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
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

    public Double getBudget() {
        return budget;
    }

    public void setBudget(Double budget) {
        this.budget = budget;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CreateTeamRequest that = (CreateTeamRequest) o;
        return Objects.equals(teamName, that.teamName) &&
                Objects.equals(teamOwnerEmail, that.teamOwnerEmail) &&
                Objects.equals(players, that.players) &&
                Objects.equals(coaches, that.coaches) &&
                Objects.equals(teamManagers, that.teamManagers) &&
                Objects.equals(court, that.court) &&
                Objects.equals(budget, that.budget);
    }

    @Override
    public int hashCode() {
        return Objects.hash(teamName, teamOwnerEmail, players, coaches, teamManagers, court, budget);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("CreateTeamRequest{");
        sb.append("teamName='").append(teamName).append('\'');
        sb.append(", teamOwnerEmail='").append(teamOwnerEmail).append('\'');
        sb.append(", players=").append(players);
        sb.append(", coaches=").append(coaches);
        sb.append(", teamManagers=").append(teamManagers);
        sb.append(", court=").append(court);
        sb.append(", budget=").append(budget);
        sb.append('}');
        return sb.toString();
    }
}
