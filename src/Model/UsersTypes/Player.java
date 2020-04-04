package Model.UsersTypes;

import Model.Team;

public class Player extends Subscriber {
    private Team team;

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }
}
