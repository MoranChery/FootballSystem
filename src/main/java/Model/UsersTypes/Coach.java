package Model.UsersTypes;

import Model.Enums.CoachRole;
import Model.Enums.QualificationCoach;
import Model.PersonalPage;
import Model.Team;

public class Coach extends Subscriber {
    private String team;
    private CoachRole coachRole;
    private QualificationCoach qualificationCoach;
    private PersonalPage coachPage;

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

    public void setCoachPage(PersonalPage coachPage) {
        this.coachPage = coachPage;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
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

    public PersonalPage getCoachPage() {
        return coachPage;
    }

    @Override
    public String toString() {
        return "Coach{" +
                "team='" + team + '\'' +
                ", coachRole=" + coachRole +
                ", qualificationCoach=" + qualificationCoach +
                ", emailAddress='" + emailAddress + '\'' +
                ", password='" + password + '\'' +
                ", id=" + id +
                ", firstName='" + firstName + '\'' +
                ", LastName='" + LastName + '\'' +
                ", status=" + status +
                '}';
    }
}