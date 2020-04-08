package Model.UsersTypes;

import Model.FinancialActivity;
import Model.Team;

import java.util.HashMap;

public class TeamOwner extends Subscriber {

    private Integer id;
    private Team team;
    private String firstName;
    private String lastName;
    private HashMap<Integer,TeamOwner> teamOwnersByThis;

    public TeamOwner(Integer id, Team team, String firstName, String lastName, HashMap<Integer, TeamOwner> teamOwnersByThis) {
        this.id = id;
        this.team = team;
        this.firstName = firstName;
        this.lastName = lastName;
        this.teamOwnersByThis = teamOwnersByThis;
    }

    @Override
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public HashMap<Integer, TeamOwner> getTeamOwnersByThis() {
        return teamOwnersByThis;
    }

    public void setTeamOwnersByThis(HashMap<Integer, TeamOwner> teamOwnersByThis) {
        this.teamOwnersByThis = teamOwnersByThis;
    }
}
