package Model.UsersTypes;

import Model.Enums.CoachRole;
import Model.Enums.QualificationCoach;
import Model.Team;

public class Coach extends Subscriber {
    private Team team;
    private CoachRole coachRole;
    private QualificationCoach qualificationCoach;

    public Coach(String username,String password,Integer id, String firstName, String lastName, CoachRole coachRole, QualificationCoach qualificationCoach) {
        setRegisteringDetails(username,password,id,firstName,lastName);
        this.coachRole = coachRole;
        this.qualificationCoach = qualificationCoach;
    }

    public Coach(String emailAddress, Integer coachId, String firstName, String lastName, CoachRole coachRole, QualificationCoach qualificationCoach) {
        setRegisteringDetails(emailAddress,coachId,firstName,lastName);
        this.coachRole = coachRole;
        this.qualificationCoach = qualificationCoach;
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

    public QualificationCoach getQualificationCoach() {
        return qualificationCoach;
    }

    public void setQualificationCoach(QualificationCoach qualificationCoach) {
        this.qualificationCoach = qualificationCoach;
    }
}