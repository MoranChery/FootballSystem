package Model.UsersTypes;

import Model.FinancialActivity;
import Model.Team;

import java.util.HashMap;

public class TeamOwner extends Subscriber {

    private Integer id;
    private Team team;
    private String firstName;
    private String lastName;

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
}
