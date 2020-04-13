package Model.UsersTypes;

import Model.Team;

import java.util.HashMap;
import java.util.Map;

public class TeamOwner extends Subscriber {

    private Team team;
    private Map<Integer,TeamOwner> teamOwnersByThis;
    private Integer ownedById;

    public TeamOwner(String username, String password, Integer id,String firstName, String lastName, Team team ) {
        setRegisteringDetails(username,password,id,firstName,lastName);
        this.team = team;
        this.teamOwnersByThis = new HashMap<>();
    }

    public TeamOwner(Team team, Subscriber subscriber, Integer teamOwnerId) {
        setRegisteringDetails(subscriber.getId(),subscriber.getFirstName(),getLastName());
        this.team = team;
        this.ownedById = teamOwnerId;
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

    public Integer getOwnedById() {
        return ownedById;
    }

    public void setOwnedById(Integer ownedById) {
        this.ownedById = ownedById;
    }
}
