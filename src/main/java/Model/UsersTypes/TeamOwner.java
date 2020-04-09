package Model.UsersTypes;

import Model.FinancialActivity;
import Model.Team;

import java.util.HashMap;
import java.util.Map;

public class TeamOwner extends Subscriber {

    private Team team;

    private Map<Integer,TeamOwner> teamOwnersByThis;
    private Integer ownedBy;

    public TeamOwner(Integer id, Team team, String firstName, String lastName) {
        super(id,firstName,lastName);
        this.team = team;
        this.teamOwnersByThis = new HashMap<>();
    }

    public TeamOwner(Team team, Subscriber subscriber, Integer teamOwnerId) {
        super(subscriber);
        this.team = team;
        this.ownedBy = teamOwnerId;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public Map<Integer, TeamOwner> getTeamOwnersByThis() {
        return teamOwnersByThis;
    }

    public void setTeamOwnersByThis(Map<Integer, TeamOwner> teamOwnersByThis) {
        this.teamOwnersByThis = teamOwnersByThis;
    }

    public Integer getOwnedBy() {
        return ownedBy;
    }

    public void setOwnedBy(Integer ownedBy) {
        this.ownedBy = ownedBy;
    }
}
