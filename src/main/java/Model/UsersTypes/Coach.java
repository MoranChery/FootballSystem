package Model.UsersTypes;

import Model.Enums.CoachRole;
import Model.Enums.Qualification;
import Model.Team;

public class Coach extends Subscriber {
    private Team team;
    private CoachRole coachRole;
    private Qualification qualification;

    public Coach(Integer id, String firstName, String lastName, CoachRole coachRole, Qualification qualification) {
        super(id,firstName,lastName);
        this.coachRole = coachRole;
        this.qualification = qualification;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
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