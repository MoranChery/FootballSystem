package Model.UsersTypes;

import Model.Team;

import java.util.Objects;

public class TeamManager extends Subscriber {
    private Team team;
    private Integer ownedById;

    public TeamManager(String username, String password,Integer id, String firstName, String lastName,Integer ownedById) {
        setRegisteringDetails(username,password,id,firstName,lastName);
        this.ownedById = ownedById;
    }

    public TeamManager(Team team,Subscriber subscriber, Integer ownedById) {
        super(subscriber);
        this.ownedById = ownedById;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public Integer getOwnedById() {
        return ownedById;
    }

    public void setOwnedById(Integer ownedById) {
        this.ownedById = ownedById;
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
