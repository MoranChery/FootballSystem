package Model.UsersTypes;

import Model.Team;

import java.util.Objects;

public class TeamManager extends Subscriber {
    private Team team;
    private String ownedByEmail;

    public TeamManager(String emailAddress, String password,Integer id, String firstName, String lastName,String ownedByEmail) {
        setRegisteringDetails(emailAddress,password,id,firstName,lastName);
        this.ownedByEmail = ownedByEmail;
    }

    public TeamManager(Team team,Subscriber subscriber, String ownedById) {
        setRegisteringDetails(subscriber.getEmailAddress(),subscriber.getPassword(),subscriber.getId(),subscriber.getFirstName(),subscriber.getLastName());
        this.team = team;
        this.ownedByEmail = ownedById;
    }

    public TeamManager(String emailAddress,Integer teamManagerId, String firstName, String lastName, String ownedById) {
        setRegisteringDetails(emailAddress,teamManagerId,firstName,lastName);
        this.ownedByEmail = ownedById;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public String getOwnedByEmail() {
        return ownedByEmail;
    }

    public void setOwnedByEmail(String ownedByEmail) {
        this.ownedByEmail = ownedByEmail;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TeamManager)) return false;
        if (!super.equals(o)) return false;
        TeamManager that = (TeamManager) o;
        return Objects.equals(team, that.team);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), team);
    }
}
