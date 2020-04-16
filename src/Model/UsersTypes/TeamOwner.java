package Model.UsersTypes;

import Model.Team;

import java.util.HashMap;
import java.util.Map;

public class TeamOwner extends Subscriber {

    private Team team;
    private Map<String,TeamOwner> teamOwnersByThis;
    private String ownedByEmailAddress;

    public TeamOwner(String emailAddress, String password, Integer id,String firstName, String lastName, Team team ) {
        setRegisteringDetails(emailAddress,password,id,firstName,lastName);
        this.team = team;
        this.teamOwnersByThis = new HashMap<>();
    }

    public TeamOwner(Team team, Subscriber subscriber, String teamOwnerEmail) {
        setRegisteringDetails(subscriber.getEmailAddress(),subscriber.getId(),subscriber.getFirstName(),getLastName());
        this.team = team;
        this.ownedByEmailAddress = teamOwnerEmail;
        this.teamOwnersByThis = new HashMap<>();
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public Map<String, TeamOwner> getTeamOwnersByThis() {
        return teamOwnersByThis;
    }

    public void setTeamOwnersByThis(Map<String, TeamOwner> teamOwnersByThis) {
        this.teamOwnersByThis = teamOwnersByThis;
    }

    public String getOwnedByEmailAddress() {
        return ownedByEmailAddress;
    }

    public void setOwnedByEmailAddress(String ownedByEmailAddress) {
        this.ownedByEmailAddress = ownedByEmailAddress;
    }
}
