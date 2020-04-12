package Model.UsersTypes;

import Model.Team;

public class Player extends Subscriber {
    private Integer id;
    private Team team;

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
}
