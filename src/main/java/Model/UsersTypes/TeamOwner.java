package Model.UsersTypes;

import Model.Team;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TeamOwner extends Subscriber {

    private String team;
    private List<String> teamOwnersByThis;
    private String ownedByEmailAddress;

    public TeamOwner(String emailAddress, String password, Integer id,String firstName, String lastName,String team) {
        setRegisteringDetails(emailAddress,password,id,firstName,lastName);
        this.team = team;
        this.teamOwnersByThis = new ArrayList<>();
    }

    public TeamOwner(String emailAddress, String password, Integer id,String firstName, String lastName) {
        setRegisteringDetails(emailAddress,password,id,firstName,lastName);
        this.teamOwnersByThis = new ArrayList<>();
    }

    public TeamOwner(String team, Subscriber subscriber, String teamOwnerEmail) {
        setRegisteringDetails(subscriber.getEmailAddress(),subscriber.getId(),subscriber.getFirstName(),getLastName());
        this.team = team;
        this.ownedByEmailAddress = teamOwnerEmail;
        this.teamOwnersByThis = new ArrayList<>();
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public List<String> getTeamOwnersByThis() {
        return teamOwnersByThis;
    }

    public void setTeamOwnersByThis(List<String> teamOwnersByThis) {
        this.teamOwnersByThis = teamOwnersByThis;
    }

    public String getOwnedByEmailAddress() {
        return ownedByEmailAddress;
    }

    public void setOwnedByEmailAddress(String ownedByEmailAddress) {
        this.ownedByEmailAddress = ownedByEmailAddress;
    }

    @Override
    public String toString() {
        return "TeamOwner{" +
                "team='" + team + '\'' +
                ", teamOwnersByThis=" + teamOwnersByThis.toString() +
                ", ownedByEmailAddress='" + ownedByEmailAddress + '\'' +
                ", emailAddress='" + emailAddress + '\'' +
                ", password='" + password + '\'' +
                ", id=" + id +
                ", firstName='" + firstName + '\'' +
                ", LastName='" + LastName + '\'' +
                ", status=" + status +
                '}';
    }
}
