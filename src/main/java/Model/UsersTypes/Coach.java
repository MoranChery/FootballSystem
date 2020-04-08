package Model.UsersTypes;

import Model.Enums.CoachRole;
import Model.Enums.Qualification;
import Model.Team;

public class Coach extends Subscriber {
    private Integer id;
    private Team team;
    private String firstName;
    private String lastName;
    private CoachRole coachRole;
    private Qualification qualification;

    public Coach(Integer id, String firstName, String lastName, CoachRole coachRole, Qualification qualification) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.coachRole = coachRole;
        this.qualification = qualification;
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

    public CoachRole getCoachRole() {
        return coachRole;
    }

    public void setCoachRole(CoachRole coachRole) {
        this.coachRole = coachRole;
    }

    public Qualification getQualification() {
        return qualification;
    }

    public void setQualification(Qualification qualification) {
        this.qualification = qualification;
    }
}
